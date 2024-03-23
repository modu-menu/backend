package modu.menu.placevibe.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.place.domain.Place;
import modu.menu.vibe.domain.Vibe;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "place_vibe_tb")
@Entity
public class PlaceVibe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vibe_id")
    private Vibe vibe;

    public void syncPlace(Place place) {
        this.place = place;
    }
}
