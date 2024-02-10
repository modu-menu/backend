package modu.menu.place.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.BaseTime;
import modu.menu.food.domain.Food;
import modu.menu.vibe.domain.Vibe;
import modu.menu.voteItem.domain.VoteItem;

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
    private Double latitude; // 위도, 소수점 다섯 번째 자리까지 사용할 경우 1m 단위까지 표현 가능
    private Double longitude; // 경도, 소수점 다섯 번째 자리까지 사용할 경우 1m 단위까지 표현 가능
    private String imageUrl;
    @OneToMany(mappedBy = "place")
    private List<VoteItem> voteItems;
    @ManyToMany
    @JoinTable(name = "place_food_tb")
    private List<Food> foods;
    @ManyToMany
    @JoinTable(name = "place_vibe_tb")
    private List<Vibe> vibes;
}
