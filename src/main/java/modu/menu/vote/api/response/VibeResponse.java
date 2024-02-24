package modu.menu.vote.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import modu.menu.vibe.domain.VibeType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VibeResponse {

    @Schema(description = "분위기", example = "시끌벅적해요")
    private VibeType type;
}
