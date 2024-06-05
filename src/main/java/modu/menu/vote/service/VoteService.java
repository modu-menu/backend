package modu.menu.vote.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import modu.menu.choice.repository.ChoiceRepository;
import modu.menu.core.exception.Exception400;
import modu.menu.core.exception.Exception403;
import modu.menu.core.exception.Exception404;
import modu.menu.core.response.ErrorMessage;
import modu.menu.core.util.DistanceCalculator;
import modu.menu.food.repository.FoodRepository;
import modu.menu.participant.domain.Participant;
import modu.menu.participant.domain.VoteRole;
import modu.menu.participant.repository.ParticipantRepository;
import modu.menu.place.domain.Place;
import modu.menu.place.reposiotry.PlaceRepository;
import modu.menu.user.domain.User;
import modu.menu.user.repository.UserRepository;
import modu.menu.vibe.repository.VibeRepository;
import modu.menu.vote.api.request.SaveVoteRequest;
import modu.menu.vote.api.request.VoteResultRequest;
import modu.menu.vote.api.response.VoteResultResponse;
import modu.menu.vote.domain.Vote;
import modu.menu.vote.domain.VoteStatus;
import modu.menu.vote.repository.VoteRepository;
import modu.menu.vote.service.dto.VoteResultServiceResponse;
import modu.menu.voteItem.domain.VoteItem;
import modu.menu.voteItem.repository.VoteItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final ChoiceRepository choiceRepository;
    private final VoteItemRepository voteItemRepository;
    private final PlaceRepository placeRepository;
    private final VibeRepository vibeRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final HttpServletRequest request;

    // 투표 생성
    @Transactional
    public void saveVote(SaveVoteRequest saveVoteRequest) {

        List<Place> places = placeRepository.findAllById(saveVoteRequest.getPlaceIds());

        if (places.isEmpty() || places.size() != saveVoteRequest.getPlaceIds().size()) {
            throw new Exception404(ErrorMessage.NOT_EXIST_PLACE);
        }

        Vote vote = voteRepository.save(Vote.builder()
                .voteStatus(VoteStatus.ACTIVE)
                .build());
        places.forEach(place -> {
            VoteItem voteItem = voteItemRepository.save(VoteItem.builder()
                    .vote(vote)
                    .place(place)
                    .build());

            vote.addVoteItem(voteItem);
        });
    }

    // 투표 초대
    @Transactional
    public void invite(Long voteId, Long userId) {

        // 투표 존재 여부를 확인한다.
        Vote vote = voteRepository.findById(voteId).orElseThrow(
                () -> new Exception404(ErrorMessage.NOT_EXIST_VOTE)
        );

        if (vote.getVoteStatus().equals(VoteStatus.END)) {
            throw new Exception404(ErrorMessage.CANT_INVITE_TO_END_VOTE);
        }

        // JWT 검증 시 존재 여부를 확인했으므로 바로 get으로 객체를 꺼낸다.
        User user = userRepository.findById(userId).get();

        Optional<Participant> participantOptional = participantRepository.findByUserId(userId);
        if (participantOptional.isEmpty()) {
            Participant participant = Participant.builder()
                    .user(user)
                    .vote(vote)
                    .voteRole(VoteRole.PARTICIPANT)
                    .build();
            vote.addParticipant(participant);
            participantRepository.save(participant);
        }
    }

    // 투표 종료
    @Transactional
    public void finishVote(Long voteId) {

        Vote vote = voteRepository.findById(voteId).orElseThrow(
                () -> new Exception404(ErrorMessage.NOT_EXIST_VOTE)
        );
        if (vote.getVoteStatus().equals(VoteStatus.END)) {
            throw new Exception400(String.valueOf(voteId), ErrorMessage.CANT_FINISH_ALREADY_END_VOTE.getValue());
        }

        Long userId = (Long) request.getAttribute("userId");
        Participant participant = participantRepository.findByUserIdAndVoteId(userId, voteId).orElseThrow(
                () -> new Exception403(ErrorMessage.NOT_ALLOWED_USER)
        );
        if (participant.getVoteRole().equals(VoteRole.PARTICIPANT)) {
            throw new Exception403(ErrorMessage.CANT_FINISH_BY_PARTICIPANT);
        }

        vote.updateVoteStatus(VoteStatus.END);
    }

    // 투표 결과 조회
    public VoteResultResponse getResult(Long voteId, VoteResultRequest voteResultRequest) {

        // 투표 존재 여부를 확인한다. fetch join을 이용해 선택지도 함께 가져온다.
        Vote vote = voteRepository.findVoteResultById(voteId).orElseThrow(
                () -> new Exception404(ErrorMessage.NOT_EXIST_VOTE)
        );

        // 회원과 각 투표 항목에 해당하는 음식점 간의 거리를 계산한다.
        // 투표 항목 별 투표율을 계산한다.
        List<VoteItem> voteItems = vote.getVoteItems();

        Map<Long, Integer> voteCountMap = voteItems.stream()
                .collect(Collectors.toMap(VoteItem::getId, v ->
                        choiceRepository.countByVoteItemId(v.getId())));

        int voterCount = voteItems.stream()
                .mapToInt(v -> voteCountMap.getOrDefault(v.getId(), 0))
                .sum();

        return new VoteResultResponse(voteItems.stream()
                .map(voteItem -> {
                    Place place = voteItem.getPlace();
                    double distance = DistanceCalculator.calculate(
                            place.getLatitude(),
                            place.getLongitude(),
                            voteResultRequest.getLatitude(),
                            voteResultRequest.getLongitude()
                    );

                    int voteCount = voteCountMap.getOrDefault(voteItem.getId(), 0);

                    return VoteResultServiceResponse.builder()
                            .name(place.getName())
                            .foods(place.getPlaceFoods().stream()
                                    .map(placeFood -> placeFood.getFood().getType())
                                    .toList())
                            .vibes(place.getPlaceVibes().stream()
                                    .map(placeVibe -> placeVibe.getVibe().getType())
                                    .toList())
                            .address(place.getAddress())
                            .distance(distance >= 1000.0 ? String.format("%.1f", distance / 1000.0) + "km" : Math.round(distance) + "m")
                            .img(place.getImageUrl())
                            .voteRating(Math.round(voteCount * 100.0 / voterCount) + "%")
                            .build();
                })
                .sorted((voteResultServiceResponse1, voteResultServiceResponse2) -> {
                    if (parseInt(voteResultServiceResponse2.getVoteRating().replace("%", "")) == parseInt(voteResultServiceResponse1.getVoteRating().replace("%", ""))) {
                        return voteResultServiceResponse1.getName().compareTo(voteResultServiceResponse2.getName());
                    }
                    return parseInt(voteResultServiceResponse2.getVoteRating().replace("%", ""))
                            - parseInt(voteResultServiceResponse1.getVoteRating().replace("%", ""));
                })
                .toList()
        );
    }
}
