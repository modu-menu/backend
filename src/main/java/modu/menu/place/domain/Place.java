package modu.menu.place.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.BaseTime;
import modu.menu.food.domain.Food;
import modu.menu.vibe.Vibe;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "place_tb")
@Entity
public class Place extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String name;
    @Column(length = 512)
    private String address;
    @Column(length = 30)
    private String ph;
    private String businessHours;
    private String menu;
    private Float latitude; // 위도, 소수점 다섯 번째 자리까지 사용할 경우 1m 단위까지 표현 가능
    private Float longitude; // 경도, 소수점 다섯 번째 자리까지 사용할 경우 1m 단위까지 표현 가능
    @ManyToMany
    @JoinTable(name = "place_food_tb")
    private List<Food> foods = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "place_vibe_tb")
    private List<Vibe> vibes = new ArrayList<>();
}
