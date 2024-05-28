package modu.menu.place.reposiotry;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import modu.menu.core.util.DistanceCalculator;
import modu.menu.food.domain.FoodType;
import modu.menu.place.domain.Place;
import modu.menu.vibe.domain.VibeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

import static modu.menu.food.domain.QFood.food;
import static modu.menu.place.domain.QPlace.place;
import static modu.menu.placefood.domain.QPlaceFood.placeFood;
import static modu.menu.placevibe.domain.QPlaceVibe.placeVibe;
import static modu.menu.vibe.domain.QVibe.vibe;

@Repository
@RequiredArgsConstructor
public class PlaceQueryRepository {

    private final JPAQueryFactory query;
    private static final int PAGE_SIZE = 20;

    /**
     * 검색 정책
     * 1) 검색 조건에 부합하는 식당을 노출하고,
     * 1-a) 가까운 거리순
     * 1-b) 거리 동일한 경우, 음식점명 가나다순
     * <p>
     * 2) 그 뒤에 검색 조건 중 '분위기'를 제외한 나머지 조건에 부합하는 식당 리스트 추가 노출
     * 2-a) 가까운 거리순
     * 2-b) 거리 동일한 경우, 음식점명 가나다순
     */
    public Page<Place> findByCondition(Double latitude, Double longitude, List<FoodType> foods, List<VibeType> vibes, Integer page) {
        List<Place> firstPlaces = query.select(place)
                .from(place)
                .join(place.placeFoods, placeFood)
                .join(place.placeVibes, placeVibe)
                .join(placeFood.food, food)
                .join(placeVibe.vibe, vibe)
                .where(foodNames(foods), vibeNames(vibes))
                .fetch();

        List<Place> secondPlaces = query.select(place)
                .from(place)
                .join(place.placeFoods, placeFood)
                .join(placeFood.food, food)
                .where(foodNames(foods))
                .fetch();

        // 중복 제거 후 검색 정책에 따라 정렬
        List<Place> sortedPlaces = Stream.concat(firstPlaces.stream(), secondPlaces.stream())
                .distinct()
                .filter(place -> DistanceCalculator.calculate(latitude, longitude, place.getLatitude(), place.getLongitude()) <= 1000.0)
                .sorted((place1, place2) -> {
                    double distance1 = DistanceCalculator.calculate(latitude, longitude, place1.getLatitude(), place1.getLongitude());
                    double distance2 = DistanceCalculator.calculate(latitude, longitude, place2.getLatitude(), place2.getLongitude());

                    if (distance1 == distance2) {
                        return place1.getName().compareTo(place2.getName());
                    }
                    if (distance1 > distance2) {
                        return 1;
                    } else {
                        return -1;
                    }
                })
                .toList();

        return new PageImpl<>(
                sortedPlaces.subList(Math.min(page * PAGE_SIZE, sortedPlaces.size()),
                        Math.min((page + 1) * PAGE_SIZE, sortedPlaces.size())),
                PageRequest.of(page, PAGE_SIZE),
                sortedPlaces.size()
        );
    }

    private BooleanExpression foodNames(List<FoodType> foods) {
        if (foods == null || foods.isEmpty()) {
            return null;
        }
        return food.type.in(foods);
    }

    private BooleanExpression vibeNames(List<VibeType> vibes) {
        if (vibes == null || vibes.isEmpty()) {
            return null;
        }
        return vibe.type.in(vibes);
    }
}
