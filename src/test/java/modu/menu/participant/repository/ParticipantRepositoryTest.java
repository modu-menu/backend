package modu.menu.participant.repository;

import modu.menu.IntegrationTestSupporter;
import modu.menu.participant.domain.Participant;
import modu.menu.participant.domain.VoteRole;
import modu.menu.user.domain.Gender;
import modu.menu.user.domain.User;
import modu.menu.user.domain.UserStatus;
import modu.menu.user.repository.UserRepository;
import modu.menu.vote.domain.Vote;
import modu.menu.vote.domain.VoteStatus;
import modu.menu.vote.repository.VoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ParticipantRepository 단위테스트")
class ParticipantRepositoryTest extends IntegrationTestSupporter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private ParticipantRepository participantRepository;

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

    private Vote createVote(VoteStatus voteStatus) {
        return Vote.builder()
                .voteStatus(voteStatus)
                .voteItems(new ArrayList<>())
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
