package modu.menu.user.api;

import lombok.RequiredArgsConstructor;
import modu.menu.core.auth.jwt.JwtProperties;
import modu.menu.core.exception.Exception400;
import modu.menu.core.response.ApiResponse;
import modu.menu.core.response.ErrorMessage;
import modu.menu.user.domain.User;
import modu.menu.user.dto.UserResponse;
import modu.menu.user.service.UserService;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/login/oauth/{provider}")
    public ResponseEntity<ApiResponse> login(@PathVariable String provider, String code) {
        if (!provider.equals("kakao")) {
            throw new Exception400("provider", ErrorMessage.LOGIN_USER_PROVIDER_ERROR);
        }
        User user = userService.login(provider, code);
        Pair<String, String> tokens = userService.issue(user);

        return ResponseEntity.ok()
                .header(JwtProperties.HEADER_ACCESS, tokens.getFirst())
                .header(JwtProperties.HEADER_REFRESH, tokens.getSecond())
                .body(new ApiResponse(UserResponse.LoginResponse.builder()
                        .name(user.getName())
                        .nickname(user.getNickname())
                        .profileImageUrl(user.getProfileImageUrl())
                        .build())
                );
    }
}
