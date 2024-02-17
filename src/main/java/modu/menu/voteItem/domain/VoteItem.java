package modu.menu.voteItem.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.place.domain.Place;
import modu.menu.vote.domain.Vote;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "vote_item_tb")
@Entity
public class VoteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Vote vote;
    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    public void syncPlace(Place place) {
        this.place = place;
    }

    public void syncVote(Vote vote) {
        this.vote = vote;
    }
}
