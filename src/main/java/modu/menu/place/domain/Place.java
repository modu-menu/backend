package modu.menu.place.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.BaseTime;
import modu.menu.placefood.domain.PlaceFood;
import modu.menu.placevibe.domain.PlaceVibe;
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
    @Builder.Default
    @OneToMany(mappedBy = "place")
    private List<VoteItem> voteItems = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "place")
    private List<PlaceFood> placeFoods = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "place")
    private List<PlaceVibe> placeVibes = new ArrayList<>();

    public void addVoteItem(VoteItem voteItem) {
        voteItems.add(voteItem);
        voteItem.syncPlace(this);
    }

    public void removeVoteItem(VoteItem voteItem) {
        voteItems.remove(voteItem);
        voteItem.syncPlace(null);
    }

    public void addPlaceFood(PlaceFood placeFood) {
        placeFoods.add(placeFood);
        placeFood.syncPlace(this);
    }

    public void removePlaceFood(PlaceFood placeFood) {
        placeFoods.remove(placeFood);
        placeFood.syncPlace(null);
    }

    public void addPlaceVibe(PlaceVibe placeVibe) {
        placeVibes.add(placeVibe);
        placeVibe.syncPlace(this);
    }

    public void removePlaceVibe(PlaceVibe placeVibe) {
        placeVibes.remove(placeVibe);
        placeVibe.syncPlace(null);
    }
}
