package modu.menu.vote.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.BaseTime;
import modu.menu.user.domain.User;
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
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Enumerated(EnumType.STRING)
    private VoteStatus voteStatus;
    @OneToMany(mappedBy = "vote")
    private List<VoteItem> voteItems = new ArrayList<>();

    public void addVoteItem(VoteItem voteItem) {
        voteItems.add(voteItem);
        voteItem.syncVote(this);
    }

    public void removeVoteItem(VoteItem voteItem) {
        voteItems.remove(voteItem);
        voteItem.syncVote(null);
    }
}
