package modu.menu.place.service;

import lombok.RequiredArgsConstructor;
import modu.menu.core.util.DistanceCalculator;
import modu.menu.food.domain.Food;
import modu.menu.food.domain.FoodType;
import modu.menu.place.api.response.SearchPlaceResponse;
import modu.menu.place.domain.Place;
import modu.menu.place.reposiotry.PlaceCustomPagingRepository;
import modu.menu.place.service.dto.SearchResultServiceResponse;
import modu.menu.placefood.domain.PlaceFood;
import modu.menu.placevibe.domain.PlaceVibe;
import modu.menu.vibe.domain.Vibe;
import modu.menu.vibe.domain.VibeType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceCustomPagingRepository placeQueryRepository;

    // 음식점 후보 검색
    public SearchPlaceResponse searchPlace(
            Double latitude,
            Double longitude,
            List<FoodType> foods,
            List<VibeType> vibes,
            Integer page
    ) {

        Page<Place> places;
        if (foods == null && vibes == null) {
            places = placeQueryRepository.findAll(latitude, longitude, page);
        } else if (foods == null) {
            places = placeQueryRepository.findByVibeTypes(latitude, longitude, page, vibes);
        } else if (vibes == null) {
            places = placeQueryRepository.findByFoodTypes(latitude, longitude, page, foods);
        } else {
            places = placeQueryRepository.findByFoodTypesAndVibeTypes(latitude, longitude, page, foods, vibes);
        }

        if (places.isEmpty()) {
            return null;
        }

        return SearchPlaceResponse.builder()
                .foods(foods)
                .vibes(vibes)
                .results(places.getContent().stream()
                        .map(place -> {
                            double distance = DistanceCalculator.calculateDistance(
                                    latitude,
                                    longitude,
                                    place.getLatitude(),
                                    place.getLongitude()
                            );

                            return SearchResultServiceResponse.builder()
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
                                    .build();
                        })
                        .toList())
                .totalElements(places.getTotalElements())
                .totalPages(places.getTotalPages())
                .currentPageNumber(places.getNumber())
                .isFirst(places.isFirst())
                .isLast(places.isLast())
                .isEmpty(places.isEmpty())
                .build();
    }
}