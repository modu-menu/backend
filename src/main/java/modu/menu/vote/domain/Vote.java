package modu.menu.vote.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.BaseTime;
import modu.menu.participant.domain.Participant;
import modu.menu.voteItem.domain.VoteItem;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "vote_tb")
@Entity
public class Vote extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private VoteStatus voteStatus;
    @Builder.Default
    @OneToMany(mappedBy = "vote")
    private List<Participant> participants = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "vote")
    private List<VoteItem> voteItems = new ArrayList<>();

    public void addParticipant(Participant participant) {
        participants.add(participant);
        participant.syncVote(this);
    }

    public void removeParticipant(Participant participant) {
        participants.remove(participant);
        participant.syncVote(null);
    }

    public void addVoteItem(VoteItem voteItem) {
        voteItems.add(voteItem);
        voteItem.syncVote(this);
    }

    public void removeVoteItem(VoteItem voteItem) {
        voteItems.remove(voteItem);
        voteItem.syncVote(null);
    }
}
