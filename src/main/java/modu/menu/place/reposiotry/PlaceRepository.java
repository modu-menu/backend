package modu.menu.place.reposiotry;

import modu.menu.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceQueryRepository {
}
