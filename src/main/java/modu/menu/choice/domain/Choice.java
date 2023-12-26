package modu.menu.choice.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.BaseTime;
import modu.menu.user.domain.User;
import modu.menu.voteItem.domain.VoteItem;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "choice_tb")
@Entity
public class Choice extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private VoteItem voteItem;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
