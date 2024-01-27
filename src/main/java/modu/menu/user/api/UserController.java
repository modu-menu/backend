package modu.menu.user.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modu.menu.core.auth.jwt.JwtProperties;
import modu.menu.user.api.request.TempJoinRequest;
import modu.menu.user.api.request.TempLoginRequest;
import modu.menu.user.api.response.TempJoinResponse;
import modu.menu.user.api.response.TempLoginResponse;
import modu.menu.user.service.dto.TempJoinResultDto;
import modu.menu.user.service.UserService;
import modu.menu.user.service.dto.TempLoginResultDto;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

//    @GetMapping("/login/oauth/{provider}")
//    public ResponseEntity<ApiResponse> login(
//            @PathVariable String provider,
//            String code
//    ) {
//        if (!provider.equals("kakao")) {
//            throw new Exception400("provider", ErrorMessage.LOGIN_USER_PROVIDER_ERROR);
//        }
//        User user = userService.login(provider, code);
//        Pair<String, String> tokens = userService.issue(user);
//
//        return ResponseEntity.ok()
//                .header(JwtProperties.HEADER_ACCESS, tokens.getFirst())
//                .header(JwtProperties.HEADER_REFRESH, tokens.getSecond())
//                .body(new ApiResponse(UserResponse.LoginResponse.builder()
//                        .name(user.getName())
//                        .nickname(user.getNickname())
//                        .profileImageUrl(user.getProfileImageUrl())
//                        .build())
//                );
//    }

    @PostMapping("/api/user")
    public ResponseEntity<TempJoinResponse> tempJoin(
            @Valid @RequestBody TempJoinRequest tempJoinRequest,
            BindingResult bindingResult
    ) {
        TempJoinResultDto response = userService.tempJoin(tempJoinRequest);

        return ResponseEntity.ok()
                .header(JwtProperties.HEADER_ACCESS, response.getAccessToken())
                .body(
                        TempJoinResponse.builder()
                        .id(response.getId())
                        .name(response.getName())
                        .nickname(response.getNickname())
                        .profileImageUrl(response.getProfileImageUrl())
                        .build()
                );
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<TempLoginResponse> tempLogin(
            @Valid @RequestBody TempLoginRequest tempLoginRequest,
            BindingResult bindingResult
    ) {
        TempLoginResultDto response = userService.tempLogin(tempLoginRequest);

        return ResponseEntity.ok()
                .header(JwtProperties.HEADER_ACCESS, response.getAccessToken())
                .body(
                        TempLoginResponse.builder()
                                .id(response.getId())
                                .name(response.getName())
                                .nickname(response.getNickname())
                                .profileImageUrl(response.getProfileImageUrl())
                                .build()
                );
    }
}
