package modu.menu.vibe.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.placevibe.domain.PlaceVibe;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "vibe_tb")
@Entity
public class Vibe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private VibeType vibeType;
}
