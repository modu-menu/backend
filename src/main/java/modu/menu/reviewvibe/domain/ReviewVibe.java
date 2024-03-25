package modu.menu.reviewvibe.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.review.domain.Review;
import modu.menu.vibe.domain.Vibe;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "review_vibe_tb")
@Entity
public class ReviewVibe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vibe_id")
    private Vibe vibe;
}
