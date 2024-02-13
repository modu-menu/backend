package modu.menu.placefood.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.food.domain.Food;
import modu.menu.place.domain.Place;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "place_food_tb")
@Entity
public class PlaceFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;
    @ManyToOne(fetch = FetchType.LAZY)
    private Food food;
}
