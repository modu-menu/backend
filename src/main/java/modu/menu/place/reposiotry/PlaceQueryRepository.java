package modu.menu.place.reposiotry;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import modu.menu.food.domain.FoodType;
import modu.menu.place.domain.Place;
import modu.menu.vibe.domain.VibeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

import static modu.menu.core.util.DistanceCalculator.calculateDistance;

// 페이징 쿼리의 결과를 가까운 거리 순으로 정렬하고, 혹시라도 거리가 같은 경우엔 음식점명을 가나다 순으로 정렬한 뒤 페이지를 반환한다.
@RequiredArgsConstructor
@Repository
public class PlaceQueryRepository {

    private final EntityManager entityManager;
    private final int SIZE = 20;

    public Page<Place> findAll(Double latitude, Double longitude, Integer page) {
        List<Place> places = entityManager.createQuery("select p from Place p join PlaceFood pf join fetch Food f join fetch Vibe v")
                .setFirstResult(page * SIZE) // 시작 번호
                .setMaxResults(SIZE) // 페이지 당 요소 갯수
                .getResultList();

        return getTargetPage(latitude, longitude, page, places);
    }

    public Page<Place> findByVibeTypes(Double latitude, Double longitude, Integer page, List<VibeType> vibes) {
        List<Place> places = entityManager.createQuery("select p from Place p join PlaceFood pf join fetch Food f join fetch Vibe v where v.type in :vibes")
                .setParameter("vibes", vibes)
                .setFirstResult(page * SIZE) // 시작 번호
                .setMaxResults(SIZE) // 페이지 당 요소 갯수
                .getResultList();

        return getTargetPage(latitude, longitude, page, places);
    }

    public Page<Place> findByFoodTypes(Double latitude, Double longitude, Integer page, List<FoodType> foods) {
        List<Place> places = entityManager.createQuery("select p from Place p join PlaceFood pf join fetch Food f join fetch Vibe v where f.type in :foods")
                .setParameter("foods", foods)
                .setFirstResult(page * SIZE) // 시작 번호
                .setMaxResults(SIZE) // 페이지 당 요소 갯수
                .getResultList();

        return getTargetPage(latitude, longitude, page, places);
    }

    public Page<Place> findByFoodTypesAndVibeTypes(Double latitude, Double longitude, Integer page, List<FoodType> foods, List<VibeType> vibes) {
        List<Place> places = entityManager.createQuery("select p from Place p join PlaceFood pf join fetch Food f join fetch Vibe v where f.type in :foods and v.type in :vibes")
                .setParameter("foods", foods)
                .setParameter("vibes", vibes)
                .setFirstResult(page * SIZE) // 시작 번호
                .setMaxResults(SIZE) // 페이지 당 요소 갯수
                .getResultList();

        return getTargetPage(latitude, longitude, page, places);
    }

    private PageImpl<Place> getTargetPage(Double latitude, Double longitude, Integer page, List<Place> places) {
        List<Place> sortedPlaces = places.stream()
                .sorted((place1, place2) -> {
                    double distance1 = calculateDistance(latitude, longitude, place1.getLatitude(), place1.getLongitude());
                    double distance2 = calculateDistance(latitude, longitude, place2.getLatitude(), place2.getLongitude());

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

        int start = page * SIZE;
        int end = Math.min((page + 1) * SIZE, places.size());

        List<Place> pageContent = sortedPlaces.subList(start, end);

        return new PageImpl<>(pageContent, PageRequest.of(page, SIZE), places.size());
    }
}
