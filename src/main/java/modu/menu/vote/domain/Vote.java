package modu.menu.vote.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.BaseTime;
import modu.menu.user.domain.User;

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
}
