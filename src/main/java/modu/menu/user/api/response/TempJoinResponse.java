package modu.menu.user.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TempJoinResponse {

    private Long id;
    private String name;
    private String nickname;
    private String profileImageUrl;
}
