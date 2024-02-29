package modu.menu.vote.service;

import lombok.RequiredArgsConstructor;
import modu.menu.choice.repository.ChoiceRepository;
import modu.menu.core.exception.Exception404;
import modu.menu.core.response.ErrorMessage;
import modu.menu.core.util.DistanceCalculator;
import modu.menu.food.domain.Food;
import modu.menu.food.domain.FoodType;
import modu.menu.food.repository.FoodRepository;
import modu.menu.place.domain.Place;
import modu.menu.place.reposiotry.PlaceRepository;
import modu.menu.placefood.domain.PlaceFood;
import modu.menu.placevibe.domain.PlaceVibe;
import modu.menu.vibe.domain.Vibe;
import modu.menu.vibe.repository.VibeRepository;
import modu.menu.vote.api.request.VoteResultRequest;
import modu.menu.vote.service.dto.VoteResultServiceResponse;
import modu.menu.vote.api.response.VoteResultResponse;
import modu.menu.vote.domain.Vote;
import modu.menu.vote.repository.VoteRepository;
import modu.menu.voteItem.domain.VoteItem;
import modu.menu.voteItem.repository.VoteItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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

    // 투표 결과 조회
    public VoteResultResponse getVoteResult(Long voteId, VoteResultRequest voteResultRequest) {

        // 투표 존재 여부를 확인한다.(fetch join을 활용해 응답에 필요한 연관 데이터까지 가져온다.)
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
                .map(v -> {
                    Place place = v.getPlace();
                    double distance = DistanceCalculator.calculateDistance(
                            place.getLatitude(),
                            place.getLongitude(),
                            voteResultRequest.getLatitude(),
                            voteResultRequest.getLongitude()
                    );

                    int voteCount = voteCountMap.getOrDefault(v.getId(), 0);

                    return VoteResultServiceResponse.builder()
                            .name(place.getName())
                            .food(place.getPlaceFoods().stream()
                                    .map(PlaceFood::getFood)
                                    .map(Food::getType)
                                    .map(FoodType::getDetail)
                                    .collect(Collectors.joining()))
                            .vibes(place.getPlaceVibes().stream()
                                    .map(PlaceVibe::getVibe)
                                    .map(Vibe::getType)
                                    .toList())
                            .address(place.getAddress())
                            .distance(distance >= 1000.0 ? String.format("%.1f", distance / 1000.0) + "km" : Math.round(distance) + "m")
                            .img(place.getImageUrl())
                            .voteRating(Math.round(voteCount * 100.0 / voterCount) + "%")
                            .build();
                })
                .sorted((voteResultServiceResponse1, voteResultServiceResponse2) -> {
                    if (parseInt(voteResultServiceResponse2.getVoteRating().replace("%" , "")) == parseInt(voteResultServiceResponse1.getVoteRating().replace("%" , ""))) {
                        return voteResultServiceResponse1.getName().compareTo(voteResultServiceResponse2.getName());
                    }
                    return parseInt(voteResultServiceResponse2.getVoteRating().replace("%" , ""))
                            - parseInt(voteResultServiceResponse1.getVoteRating().replace("%" , ""));
                })
                .toList()
        );
    }

}
