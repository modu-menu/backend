package modu.menu.review.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.BaseTime;
import modu.menu.place.domain.Place;
import modu.menu.user.domain.User;
import modu.menu.vibe.domain.Vibe;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "review_tb")
@Entity
public class Review extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 리뷰어
    @ManyToOne(fetch = FetchType.LAZY)
    private Place place; // 리뷰 대상
    private Integer rating; // 평점
    private Integer participants; // 총 인원 수
    private HasRoom hasRoom; // 룸 여부
    @Column(length = 512)
    private String content;
    @Enumerated(EnumType.STRING)
    private ReviewStatus status;
    @ManyToMany(mappedBy = "review")
    @JoinTable(name = "review_vibe_tb")
    private List<Vibe> vibes;
}
