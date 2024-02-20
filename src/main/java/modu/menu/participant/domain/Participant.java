package modu.menu.participant.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.BaseTime;
import modu.menu.user.domain.User;
import modu.menu.vote.domain.Vote;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "participant_tb")
@Entity
public class Participant extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Vote vote;

    @Enumerated(EnumType.STRING)
    private VoteRole voteRole;

    public void syncVote(Vote vote) {
        this.vote = vote;
    }
}
