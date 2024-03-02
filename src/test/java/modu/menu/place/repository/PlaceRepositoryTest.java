package modu.menu.place.repository;

import jakarta.persistence.EntityManager;
import modu.menu.choice.domain.Choice;
import modu.menu.food.domain.Food;
import modu.menu.food.domain.FoodType;
import modu.menu.food.repository.FoodRepository;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PlaceRepository 단위테스트")
@ActiveProfiles("test")
@Import(PlaceCustomPagingRepository.class)
@DataJpaTest
class PlaceRepositoryTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PlaceCustomPagingRepository placeCustomPagingRepository;
    @Autowired
    private VibeRepository vibeRepository;
    @Autowired
    private PlaceVibeRepository placeVibeRepository;
    @Autowired
    private PlaceFoodRepository placeFoodRepository;
    @Autowired
    private FoodRepository foodRepository;

    @BeforeEach
    void setUp() {
        entityManager.createNativeQuery("ALTER TABLE vibe_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE place_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE place_vibe_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE place_food_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE food_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
    }

    @DisplayName("현재 위도와 경도, 페이지 번호를 통해 가까운 거리 순으로 정렬되어 페이징된 모든 음식점 정보를 조회한다.")
    @Test
    void findAll() {
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
        Page<Place> places = placeCustomPagingRepository.findAll(latitude, longitude, page);

        // then
        assertThat(places.getContent()).hasSize(3);
        assertThat(places.getContent().get(0).getName()).isEqualTo("서가앤쿡 노원역점");
        assertThat(places.getContent().get(1).getName()).isEqualTo("이자카야모리");
        assertThat(places.getContent().get(2).getName()).isEqualTo("타코벨");
    }

    @DisplayName("현재 위도와 경도, 페이지 번호, 분위기 조건들을 통해 가까운 거리 순으로 정렬되어 페이징된 모든 음식점 정보를 조회한다.")
    @Test
    void findAllByVibeTypes() {
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
        List<VibeType> vibes = List.of(VibeType.NOISY);

        // when
        Page<Place> places = placeCustomPagingRepository.findByVibeTypes(latitude, longitude, page, vibes);

        // then
        assertThat(places.getContent()).hasSize(1);
        assertThat(places.getContent().get(0).getName()).isEqualTo("타코벨");
    }

    @DisplayName("현재 위도와 경도, 페이지 번호, 음식 조건들을 통해 가까운 거리 순으로 정렬되어 페이징된 모든 음식점 정보를 조회한다.")
    @Test
    void findAllByFoodTypes() {
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
        List<FoodType> foods = List.of(FoodType.LATIN);

        // when
        Page<Place> places = placeCustomPagingRepository.findByFoodTypes(latitude, longitude, page, foods);

        // then
        assertThat(places.getContent()).hasSize(2);
        assertThat(places.getContent().get(0).getName()).isEqualTo("이자카야모리");
        assertThat(places.getContent().get(1).getName()).isEqualTo("타코벨");
    }

    @DisplayName("현재 위도와 경도, 페이지 번호, 분위기와 음식 조건들을 통해 가까운 거리 순으로 정렬되어 페이징된 모든 음식점 정보를 조회한다.")
    @Test
    void findByFoodTypesAndVibeTypes() {
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
        List<FoodType> foods = List.of(FoodType.LATIN);
        List<VibeType> vibes = List.of(VibeType.NOISY);

        // when
        Page<Place> places = placeCustomPagingRepository.findByFoodTypesAndVibeTypes(latitude, longitude, page, foods, vibes);

        // then
        assertThat(places.getContent()).hasSize(1);
        assertThat(places.getContent().get(0).getName()).isEqualTo("타코벨");
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
