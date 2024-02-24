package modu.menu.review.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import modu.menu.choice.domain.Choice;
import modu.menu.core.auth.jwt.JwtProvider;
import modu.menu.food.domain.Food;
import modu.menu.place.domain.Place;
import modu.menu.place.reposiotry.PlaceRepository;
import modu.menu.placefood.domain.PlaceFood;
import modu.menu.placevibe.domain.PlaceVibe;
import modu.menu.placevibe.repository.PlaceVibeRepository;
import modu.menu.review.api.request.CreateReviewRequest;
import modu.menu.review.api.request.VibeRequest;
import modu.menu.review.domain.HasRoom;
import modu.menu.review.domain.Review;
import modu.menu.review.service.ReviewService;
import modu.menu.reviewvibe.domain.ReviewVibe;
import modu.menu.reviewvibe.repository.ReviewVibeRepository;
import modu.menu.user.domain.Gender;
import modu.menu.user.domain.User;
import modu.menu.user.domain.UserStatus;
import modu.menu.user.repository.UserRepository;
import modu.menu.vibe.domain.Vibe;
import modu.menu.vibe.domain.VibeType;
import modu.menu.vibe.repository.VibeRepository;
import modu.menu.vote.api.request.VoteResultRequest;
import modu.menu.vote.domain.Vote;
import modu.menu.vote.domain.VoteStatus;
import modu.menu.voteItem.domain.VoteItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("ReviewService 단위테스트")
@Sql("classpath:db/teardown.sql")
@ActiveProfiles("test")
@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewVibeRepository reviewVibeRepository;
    @Autowired
    private VibeRepository vibeRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtProvider jwtProvider;

    @DisplayName("회원이 작성한 리뷰를 등록한다.")
    @Test
    void createReview() {
        // given
        User user1 = createUser("hong1234@naver.com");
        User user2 = createUser("kim1234@naver.com");
        User user3 = createUser("new1234@naver.com");
        Place place1 = createPlace("타코벨");
        Place place2 = createPlace("이자카야모리");
        Place place3 = createPlace("서가앤쿡 노원역점");
        Vibe vibe1 = createVibe(VibeType.NOISY);
        Vibe vibe2 = createVibe(VibeType.QUIET);
        Vibe vibe3 = createVibe(VibeType.GOOD_SERVICE);
        Vibe vibe4 = createVibe(VibeType.MODERN);
        Vibe vibe5 = createVibe(VibeType.NICE_VIEW);
        Vibe vibe6 = createVibe(VibeType.TRENDY);
        userRepository.saveAll(List.of(user1, user2, user3));
        placeRepository.saveAll(List.of(place1, place2, place3));
        vibeRepository.saveAll(List.of(vibe1, vibe2, vibe3, vibe4, vibe5, vibe6));

        Long userId = 1L;
        Long placeId = 1L;
        CreateReviewRequest createReviewRequest = CreateReviewRequest.builder()
                .rating(1)
                .vibes(List.of(new VibeRequest(VibeType.NOISY)))
                .participants(3)
                .hasRoom(HasRoom.NO)
                .content("리뷰")
                .build();

        // when
        reviewService.createReview(userId, placeId, createReviewRequest);

        // then
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

    private Food createFood(String name) {
        return Food.builder()
                .name(name)
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
