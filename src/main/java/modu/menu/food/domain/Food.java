package modu.menu.food.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "food_tb")
@Entity
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private FoodType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food parent;

    @OneToMany(mappedBy = "food")
    private List<Food> children = new ArrayList<>();

    public void addChildren(Food food) {
        food.syncParent(this);
        children.add(food);
    }

    public void removeChildren(Food food) {
        food.syncParent(null);
        children.remove(food);
    }

    public void syncParent(Food food) {
        this.parent = food;
    }
}
