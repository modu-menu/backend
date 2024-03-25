package modu.menu.placevibe.repository;

import modu.menu.place.domain.Place;
import modu.menu.placevibe.domain.PlaceVibe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceVibeRepository extends JpaRepository<PlaceVibe, Long> {

    Optional<PlaceVibe> findPlaceVibeByPlace(Place place);
}
