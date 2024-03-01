package modu.menu.place.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import modu.menu.choice.domain.Choice;
import modu.menu.core.auth.jwt.JwtProvider;
import modu.menu.food.domain.Food;
import modu.menu.food.domain.FoodType;
import modu.menu.food.repository.FoodRepository;
import modu.menu.place.api.response.SearchPlaceResponse;
import modu.menu.place.domain.Place;
import modu.menu.place.reposiotry.PlaceCustomPagingRepository;
import modu.menu.place.reposiotry.PlaceRepository;
import modu.menu.placefood.domain.PlaceFood;
import modu.menu.placefood.repository.PlaceFoodRepository;
import modu.menu.placevibe.domain.PlaceVibe;
import modu.menu.placevibe.repository.PlaceVibeRepository;
import modu.menu.user.domain.Gender;
import modu.menu.user.domain.User;
import modu.menu.user.domain.UserStatus;
import modu.menu.vibe.domain.Vibe;
import modu.menu.vibe.domain.VibeType;
import modu.menu.vibe.repository.VibeRepository;
import modu.menu.vote.domain.Vote;
import modu.menu.vote.domain.VoteStatus;
import modu.menu.voteItem.domain.VoteItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PlaceService 통합테스트")
@Sql("classpath:db/teardown.sql")
@ActiveProfiles("test")
@SpringBootTest
public class PlaceServiceTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private PlaceCustomPagingRepository placeQueryRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private VibeRepository vibeRepository;
    @Autowired
    private PlaceVibeRepository placeVibeRepository;
    @Autowired
    private PlaceFoodRepository placeFoodRepository;
    @Autowired
    private FoodRepository foodRepository;

    @DisplayName("현재 위도와 경도, 페이지 번호를 통해 가까운 거리 순으로 정렬되어 페이징된 모든 음식점 정보를 조회한다.")
    @Test
    void searchPlace() {
        // given
        Place place1 = createPlaceByLatitudeAndLongitude("타코벨", 37.5251923, 127.027536);
        Place place2 = createPlaceByLatitudeAndLongitude("이자카야모리", 37.6555775, 127.0631909);
        Place place3 = createPlaceByLatitudeAndLongitude("서가앤쿡 노원역점", 37.6558247, 127.0634196);
        Vibe vibe1 = createVibe(VibeType.NOISY);
        Vibe vibe2 = createVibe(VibeType.QUIET);
        Vibe vibe3 = createVibe(VibeType.GOOD_SERVICE);
        PlaceVibe placeVibe1 = createPlaceVibe(place1, vibe1);
        place1.addPlaceVibe(placeVibe1);
        PlaceVibe placeVibe2 = createPlaceVibe(place2, vibe2);
        place2.addPlaceVibe(placeVibe2);
        PlaceVibe placeVibe3 = createPlaceVibe(place3, vibe3);
        place3.addPlaceVibe(placeVibe3);
        placeRepository.saveAll(List.of(place1, place2, place3));
        vibeRepository.saveAll(List.of(vibe1, vibe2, vibe3));
        placeVibeRepository.saveAll(List.of(placeVibe1, placeVibe2, placeVibe3));

        Food food1 = createFood(FoodType.LATIN);
        Food food2 = createFood(FoodType.MEAT);
        PlaceFood placeFood1 = createPlaceFood(place1, food1);
        place1.addPlaceFood(placeFood1);
        PlaceFood placeFood2 = createPlaceFood(place2, food1);
        place2.addPlaceFood(placeFood2);
        PlaceFood placeFood3 = createPlaceFood(place3, food2);
        place3.addPlaceFood(placeFood3);
        foodRepository.saveAll(List.of(food1, food2));
        placeFoodRepository.saveAll(List.of(placeFood1, placeFood2, placeFood3));

        Double latitude = 37.6737992;
        Double longitude = 127.060022;
        Integer page = 0;

        // when
        SearchPlaceResponse searchResult = placeService.searchPlace(latitude, longitude, null, null, page);

        // then
        assertThat(searchResult).isNotNull();
        assertThat(searchResult.getResults().get(0))
                .extracting("name", "food", "address", "distance", "img")
                .containsExactlyInAnyOrder("서가앤쿡 노원역점", "육류,고기요리", "address", "2.0km", "image");
        assertThat(searchResult.getResults().get(0).getVibes())
                .extracting("title")
                .containsExactlyInAnyOrder(VibeType.GOOD_SERVICE.getTitle());
    }

    @DisplayName("음식점 후보를 검색할 때 페이지 번호가 페이징 결과 존재하지 않을 경우 null을 반환한다.")
    @Test
    void searchPlaceByExceededPageNumber() {
        // given
        Place place1 = createPlaceByLatitudeAndLongitude("타코벨", 37.5251923, 127.027536);
        Place place2 = createPlaceByLatitudeAndLongitude("이자카야모리", 37.6555775, 127.0631909);
        Place place3 = createPlaceByLatitudeAndLongitude("서가앤쿡 노원역점", 37.6558247, 127.0634196);
        Vibe vibe1 = createVibe(VibeType.NOISY);
        Vibe vibe2 = createVibe(VibeType.QUIET);
        Vibe vibe3 = createVibe(VibeType.GOOD_SERVICE);
        PlaceVibe placeVibe1 = createPlaceVibe(place1, vibe1);
        place1.addPlaceVibe(placeVibe1);
        PlaceVibe placeVibe2 = createPlaceVibe(place2, vibe2);
        place2.addPlaceVibe(placeVibe2);
        PlaceVibe placeVibe3 = createPlaceVibe(place3, vibe3);
        place3.addPlaceVibe(placeVibe3);
        placeRepository.saveAll(List.of(place1, place2, place3));
        vibeRepository.saveAll(List.of(vibe1, vibe2, vibe3));
        placeVibeRepository.saveAll(List.of(placeVibe1, placeVibe2, placeVibe3));

        Food food1 = createFood(FoodType.LATIN);
        Food food2 = createFood(FoodType.MEAT);
        PlaceFood placeFood1 = createPlaceFood(place1, food1);
        place1.addPlaceFood(placeFood1);
        PlaceFood placeFood2 = createPlaceFood(place2, food1);
        place2.addPlaceFood(placeFood2);
        PlaceFood placeFood3 = createPlaceFood(place3, food2);
        place3.addPlaceFood(placeFood3);
        foodRepository.saveAll(List.of(food1, food2));
        placeFoodRepository.saveAll(List.of(placeFood1, placeFood2, placeFood3));

        Double latitude = 37.6737992;
        Double longitude = 127.060022;
        Integer page = 4;

        // when
        SearchPlaceResponse searchResult = placeService.searchPlace(latitude, longitude, null, null, page);

        // then
        assertThat(searchResult).isNull();
    }

    @DisplayName("현재 위도와 경도, 페이지 번호, 음식 조건들을 통해 가까운 거리 순으로 정렬되어 페이징된 모든 음식점 정보를 조회한다.")
    @Test
    void searchPlaceByFoodTypes() {
        // given
        Place place1 = createPlaceByLatitudeAndLongitude("타코벨", 37.5251923, 127.027536);
        Place place2 = createPlaceByLatitudeAndLongitude("이자카야모리", 37.6555775, 127.0631909);
        Place place3 = createPlaceByLatitudeAndLongitude("서가앤쿡 노원역점", 37.6558247, 127.0634196);
        Vibe vibe1 = createVibe(VibeType.NOISY);
        Vibe vibe2 = createVibe(VibeType.QUIET);
        Vibe vibe3 = createVibe(VibeType.GOOD_SERVICE);
        PlaceVibe placeVibe1 = createPlaceVibe(place1, vibe1);
        place1.addPlaceVibe(placeVibe1);
        PlaceVibe placeVibe2 = createPlaceVibe(place2, vibe2);
        place2.addPlaceVibe(placeVibe2);
        PlaceVibe placeVibe3 = createPlaceVibe(place3, vibe3);
        place3.addPlaceVibe(placeVibe3);
        placeRepository.saveAll(List.of(place1, place2, place3));
        vibeRepository.saveAll(List.of(vibe1, vibe2, vibe3));
        placeVibeRepository.saveAll(List.of(placeVibe1, placeVibe2, placeVibe3));

        Food food1 = createFood(FoodType.LATIN);
        Food food2 = createFood(FoodType.MEAT);
        PlaceFood placeFood1 = createPlaceFood(place1, food1);
        place1.addPlaceFood(placeFood1);
        PlaceFood placeFood2 = createPlaceFood(place2, food1);
        place2.addPlaceFood(placeFood2);
        PlaceFood placeFood3 = createPlaceFood(place3, food2);
        place3.addPlaceFood(placeFood3);
        foodRepository.saveAll(List.of(food1, food2));
        placeFoodRepository.saveAll(List.of(placeFood1, placeFood2, placeFood3));

        Double latitude = 37.6737992;
        Double longitude = 127.060022;
        Integer page = 0;

        // when
        SearchPlaceResponse searchResult = placeService.searchPlace(latitude, longitude, List.of(FoodType.LATIN), null, page);

        // then
        assertThat(searchResult).isNotNull();
        assertThat(searchResult.getResults().get(0))
                .extracting("name", "food", "address", "distance", "img")
                .containsExactlyInAnyOrder("이자카야모리", "멕시칸,브라질", "address", "2.0km", "image");
        assertThat(searchResult.getResults().get(0).getVibes())
                .extracting("title")
                .containsExactlyInAnyOrder(VibeType.QUIET.getTitle());
    }

    @DisplayName("현재 위도와 경도, 페이지 번호, 분위기 조건들을 통해 가까운 거리 순으로 정렬되어 페이징된 모든 음식점 정보를 조회한다.")
    @Test
    void searchPlaceByVibeTypes() {
        // given
        Place place1 = createPlaceByLatitudeAndLongitude("타코벨", 37.5251923, 127.027536);
        Place place2 = createPlaceByLatitudeAndLongitude("이자카야모리", 37.6555775, 127.0631909);
        Place place3 = createPlaceByLatitudeAndLongitude("서가앤쿡 노원역점", 37.6558247, 127.0634196);
        Vibe vibe1 = createVibe(VibeType.NOISY);
        Vibe vibe2 = createVibe(VibeType.QUIET);
        Vibe vibe3 = createVibe(VibeType.GOOD_SERVICE);
        PlaceVibe placeVibe1 = createPlaceVibe(place1, vibe1);
        place1.addPlaceVibe(placeVibe1);
        PlaceVibe placeVibe2 = createPlaceVibe(place2, vibe2);
        place2.addPlaceVibe(placeVibe2);
        PlaceVibe placeVibe3 = createPlaceVibe(place3, vibe3);
        place3.addPlaceVibe(placeVibe3);
        placeRepository.saveAll(List.of(place1, place2, place3));
        vibeRepository.saveAll(List.of(vibe1, vibe2, vibe3));
        placeVibeRepository.saveAll(List.of(placeVibe1, placeVibe2, placeVibe3));

        Food food1 = createFood(FoodType.LATIN);
        Food food2 = createFood(FoodType.MEAT);
        PlaceFood placeFood1 = createPlaceFood(place1, food1);
        place1.addPlaceFood(placeFood1);
        PlaceFood placeFood2 = createPlaceFood(place2, food1);
        place2.addPlaceFood(placeFood2);
        PlaceFood placeFood3 = createPlaceFood(place3, food2);
        place3.addPlaceFood(placeFood3);
        foodRepository.saveAll(List.of(food1, food2));
        placeFoodRepository.saveAll(List.of(placeFood1, placeFood2, placeFood3));

        Double latitude = 37.6737992;
        Double longitude = 127.060022;
        Integer page = 0;

        // when
        SearchPlaceResponse searchResult = placeService.searchPlace(latitude, longitude, null, List.of(VibeType.QUIET), page);

        // then
        assertThat(searchResult).isNotNull();
        assertThat(searchResult.getResults().get(0))
                .extracting("name", "food", "address", "distance", "img")
                .containsExactlyInAnyOrder("이자카야모리", "멕시칸,브라질", "address", "2.0km", "image");
        assertThat(searchResult.getResults().get(0).getVibes())
                .extracting("title")
                .containsExactlyInAnyOrder(VibeType.QUIET.getTitle());
    }

    @DisplayName("현재 위도와 경도, 페이지 번호, 음식 조건, 분위기 조건들을 통해 가까운 거리 순으로 정렬되어 페이징된 모든 음식점 정보를 조회한다.")
    @Test
    void searchPlaceByFoodTypesAndVibeTypes() {
        // given
        Place place1 = createPlaceByLatitudeAndLongitude("타코벨", 37.5251923, 127.027536);
        Place place2 = createPlaceByLatitudeAndLongitude("이자카야모리", 37.6555775, 127.0631909);
        Place place3 = createPlaceByLatitudeAndLongitude("서가앤쿡 노원역점", 37.6558247, 127.0634196);
        Vibe vibe1 = createVibe(VibeType.NOISY);
        Vibe vibe2 = createVibe(VibeType.QUIET);
        Vibe vibe3 = createVibe(VibeType.GOOD_SERVICE);
        PlaceVibe placeVibe1 = createPlaceVibe(place1, vibe1);
        place1.addPlaceVibe(placeVibe1);
        PlaceVibe placeVibe2 = createPlaceVibe(place2, vibe2);
        place2.addPlaceVibe(placeVibe2);
        PlaceVibe placeVibe3 = createPlaceVibe(place3, vibe3);
        place3.addPlaceVibe(placeVibe3);
        placeRepository.saveAll(List.of(place1, place2, place3));
        vibeRepository.saveAll(List.of(vibe1, vibe2, vibe3));
        placeVibeRepository.saveAll(List.of(placeVibe1, placeVibe2, placeVibe3));

        Food food1 = createFood(FoodType.LATIN);
        Food food2 = createFood(FoodType.MEAT);
        PlaceFood placeFood1 = createPlaceFood(place1, food1);
        place1.addPlaceFood(placeFood1);
        PlaceFood placeFood2 = createPlaceFood(place2, food1);
        place2.addPlaceFood(placeFood2);
        PlaceFood placeFood3 = createPlaceFood(place3, food2);
        place3.addPlaceFood(placeFood3);
        foodRepository.saveAll(List.of(food1, food2));
        placeFoodRepository.saveAll(List.of(placeFood1, placeFood2, placeFood3));

        Double latitude = 37.6737992;
        Double longitude = 127.060022;
        Integer page = 0;

        // when
        SearchPlaceResponse searchResult = placeService.searchPlace(latitude, longitude, List.of(FoodType.LATIN), List.of(VibeType.QUIET), page);

        // then
        assertThat(searchResult).isNotNull();
        assertThat(searchResult.getResults().get(0))
                .extracting("name", "food", "address", "distance", "img")
                .containsExactlyInAnyOrder("이자카야모리", "멕시칸,브라질", "address", "2.0km", "image");
        assertThat(searchResult.getResults().get(0).getVibes())
                .extracting("title")
                .containsExactlyInAnyOrder(VibeType.QUIET.getTitle());
    }

    private User createUser(String email) {
        return User.builder()
                .email(email)
                .name("name")
                .nickname("nickname")
                .gender(Gender.MALE)
                .age(25)
                .birthday(LocalDate.now())
                .password("test1234")
                .phoneNumber("01012345678")
                .status(UserStatus.ACTIVE)
                .build();
    }

    private Place createPlace(String name) {
        return Place.builder()
                .name(name)
                .address("address")
                .ph("string")
                .businessHours("hours")
                .menu("메뉴")
                .latitude(37.676051439616)
                .longitude(127.05563369603)
                .imageUrl("image")
                .voteItems(new ArrayList<>())
                .placeVibes(new ArrayList<>())
                .placeFoods(new ArrayList<>())
                .build();
    }

    private Place createPlaceByLatitudeAndLongitude(String name, Double latitude, Double longitude) {
        return Place.builder()
                .name(name)
                .address("address")
                .ph("string")
                .businessHours("hours")
                .menu("메뉴")
                .latitude(latitude)
                .longitude(longitude)
                .imageUrl("image")
                .voteItems(new ArrayList<>())
                .placeVibes(new ArrayList<>())
                .placeFoods(new ArrayList<>())
                .build();
    }

    private Vibe createVibe(VibeType type) {
        return Vibe.builder()
                .type(type)
                .build();
    }

    private PlaceVibe createPlaceVibe(Place place, Vibe vibe) {
        return PlaceVibe.builder()
                .place(place)
                .vibe(vibe)
                .build();
    }

    private Food createFood(FoodType type) {
        return Food.builder()
                .type(type)
                .build();
    }

    private PlaceFood createPlaceFood(Place place, Food food) {
        return PlaceFood.builder()
                .place(place)
                .food(food)
                .build();
    }

    private Vote createVote(VoteStatus voteStatus) {
        return Vote.builder()
                .voteStatus(voteStatus)
                .voteItems(new ArrayList<>())
                .build();
    }

    private VoteItem createVoteItem(Vote vote, Place place) {
        return VoteItem.builder()
                .vote(vote)
                .place(place)
                .build();
    }

    private Choice createChoice(VoteItem voteItem, User user) {
        return Choice.builder()
                .voteItem(voteItem)
                .user(user)
                .build();
    }
}
