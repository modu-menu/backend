package modu.menu.vote.service;

import lombok.RequiredArgsConstructor;
import modu.menu.choice.repository.ChoiceRepository;
import modu.menu.core.exception.Exception404;
import modu.menu.core.response.ErrorMessage;
import modu.menu.core.util.CalculateDistanceUtil;
import modu.menu.food.domain.Food;
import modu.menu.food.repository.FoodRepository;
import modu.menu.place.domain.Place;
import modu.menu.place.reposiotry.PlaceRepository;
import modu.menu.placefood.domain.PlaceFood;
import modu.menu.placevibe.domain.PlaceVibe;
import modu.menu.vibe.repository.VibeRepository;
import modu.menu.vote.api.request.VoteResultRequest;
import modu.menu.vote.api.response.VibeDto;
import modu.menu.vote.api.response.VoteResult;
import modu.menu.vote.api.response.VoteResultsResponse;
import modu.menu.vote.domain.Vote;
import modu.menu.vote.repository.VoteRepository;
import modu.menu.voteItem.domain.VoteItem;
import modu.menu.voteItem.repository.VoteItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public VoteResultsResponse getVoteResult(Long voteId, VoteResultRequest voteResultRequest) {

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

        return new VoteResultsResponse(voteItems.stream()
                .map(v -> {
                    Place place = v.getPlace();
                    double distance = Math.round(CalculateDistanceUtil.calculateDistance(
                            place.getLatitude(),
                            place.getLongitude(),
                            voteResultRequest.getLatitude(),
                            voteResultRequest.getLongitude()
                    ) * 1000) / 1000;

                    int voteCount = voteCountMap.getOrDefault(v.getId(), 0);

                    return VoteResult.builder()
                            .name(place.getName())
                            .food(place.getPlaceFoods().stream()
                                    .map(PlaceFood::getFood)
                                    .map(Food::getName)
                                    .collect(Collectors.joining()))
                            .vibes(place.getPlaceVibes().stream()
                                    .map(PlaceVibe::getVibe)
                                    .map(vibe -> new VibeDto(vibe.getName()))
                                    .toList())
                            .address(place.getAddress())
                            .distance(distance >= 1 ? distance + "km" : distance + "m")
                            .img(place.getImageUrl())
                            .voteRating(Math.round(voteCount * 100 / voterCount) + "%")
                            .build();
                })
                .toList()
        );
    }

}
