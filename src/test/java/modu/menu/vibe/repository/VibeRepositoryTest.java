package modu.menu.vibe.repository;

import modu.menu.vibe.domain.Vibe;
import modu.menu.vibe.domain.VibeType;
import modu.menu.vibe.repository.VibeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("VibeRepository 단위테스트")
@ActiveProfiles("test")
@DataJpaTest
public class VibeRepositoryTest {

    @Autowired
    private VibeRepository vibeRepository;

    @BeforeEach
    void setUp() {
        vibeRepository.saveAll(Stream.of(VibeType.values())
                .map(this::createVibe)
                .toList());
    }

    @DisplayName("VibeType을 통해 분위기를 조회한다.")
    @Test
    void findByVibeType() {
        // given
        VibeType type = VibeType.NOISY;

        // when
        Optional<Vibe> vibeResult = vibeRepository.findByVibeType(type);

        // then
        assertThat(vibeResult).get()
                .hasFieldOrPropertyWithValue("id", 4L)
                .hasFieldOrPropertyWithValue("type", VibeType.NOISY);
    }

    private Vibe createVibe(VibeType type) {
        return Vibe.builder()
                .type(type)
                .build();
    }
}
