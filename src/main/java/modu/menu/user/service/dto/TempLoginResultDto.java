package modu.menu.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TempLoginResultDto {

    private Long id;
    private String name;
    private String nickname;
    private String profileImageUrl;
    private String accessToken;
}
