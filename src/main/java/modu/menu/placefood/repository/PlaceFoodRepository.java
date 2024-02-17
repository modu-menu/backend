package modu.menu.placefood.repository;

import modu.menu.placefood.domain.PlaceFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceFoodRepository extends JpaRepository<PlaceFood, Long> {
}
