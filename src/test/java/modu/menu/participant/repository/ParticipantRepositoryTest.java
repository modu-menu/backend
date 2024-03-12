package modu.menu.participant.repository;

import jakarta.persistence.EntityManager;
import modu.menu.choice.domain.Choice;
import modu.menu.food.domain.Food;
import modu.menu.food.domain.FoodType;
import modu.menu.participant.domain.Participant;
import modu.menu.participant.domain.VoteRole;
import modu.menu.place.domain.Place;
import modu.menu.placefood.domain.PlaceFood;
import modu.menu.placevibe.domain.PlaceVibe;
import modu.menu.review.domain.HasRoom;
import modu.menu.review.domain.Review;
import modu.menu.review.domain.ReviewStatus;
import modu.menu.user.domain.Gender;
import modu.menu.user.domain.User;
import modu.menu.user.domain.UserStatus;
import modu.menu.user.repository.UserRepository;
import modu.menu.vibe.domain.Vibe;
import modu.menu.vibe.domain.VibeType;
import modu.menu.vote.domain.Vote;
import modu.menu.vote.domain.VoteStatus;
import modu.menu.vote.repository.VoteRepository;
import modu.menu.voteItem.domain.VoteItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ParticipantRepository 단위테스트")
@ActiveProfiles("test")
@DataJpaTest
class ParticipantRepositoryTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoteRepository voteRepository;

    @BeforeEach
    void setUp() {
        entityManager.createNativeQuery("ALTER TABLE participant_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE vote_tb ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
    }

    @DisplayName("userId를 통해 회원이 투표에 초대받은 사람인지 확인한다.")
    @Test
    void findByUserId() {
        // given
        User user1 = createUser("gildong123@naver.com");
        User user2 = createUser("seungmin123@naver.com");
        Vote vote1 = createVote(VoteStatus.ACTIVE);
        Participant participant = createParticipant(user1, vote1, VoteRole.PARTICIPANT);
        userRepository.saveAll(List.of(user1, user2));
        voteRepository.save(vote1);
        participantRepository.save(participant);

        Long userId = user1.getId();

        // when
        Optional<Participant> result = participantRepository.findByUserId(userId);

        // then
        assertThat(result)
                .isNotEmpty()
                .get()
                .hasFieldOrPropertyWithValue("id", 1L);
        assertThat(result.get().getUser().getId()).isEqualTo(1L);
    }

    @DisplayName("초대 받지 않은 회원의 userId를 이용해 초대 받은 사람인지 확인한다.")
    @Test
    void findByUserIdReturnEmptyParticipant() {
        // given
        User user1 = createUser("gildong123@naver.com");
        User user2 = createUser("seungmin123@naver.com");
        Vote vote1 = createVote(VoteStatus.ACTIVE);
        Participant participant = createParticipant(user1, vote1, VoteRole.PARTICIPANT);
        userRepository.saveAll(List.of(user1, user2));
        voteRepository.save(vote1);
        participantRepository.save(participant);

        Long userId = user2.getId();

        // when
        Optional<Participant> result = participantRepository.findByUserId(userId);

        // then
        assertThat(result).isEmpty();
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
                .latitude(125.00000)
                .longitude(14.12133)
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

    private Review createReview(User user, Vote vote, Place place) {
        return Review.builder()
                .user(user)
                .vote(vote)
                .place(place)
                .rating(3)
                .participants(10)
                .hasRoom(HasRoom.UNKNOWN)
                .content("맛집!")
                .status(ReviewStatus.ACTIVE)
                .build();
    }

    private Participant createParticipant(User user, Vote vote, VoteRole voteRole) {
        return Participant.builder()
                .user(user)
                .vote(vote)
                .voteRole(voteRole)
                .build();
    }
}
