package modu.menu.vibe.repository;

import modu.menu.vibe.domain.Vibe;
import modu.menu.vibe.domain.VibeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VibeRepository extends JpaRepository<Vibe, Long> {

    @Query("""
            select v
            from Vibe v
            where v.type = :type
            """)
    Optional<Vibe> findByVibeType(@Param("type") VibeType type);
}
