package modu.menu.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import modu.menu.core.auth.jwt.JwtProvider;
import modu.menu.core.response.ApiCommonResponse;
import modu.menu.user.api.request.TempJoinRequest;
import modu.menu.user.api.request.TempLoginRequest;
import modu.menu.user.api.response.TempJoinResponse;
import modu.menu.user.api.response.TempLoginResponse;
import modu.menu.user.service.UserService;
import modu.menu.user.service.dto.TempJoinResultDto;
import modu.menu.user.service.dto.TempLoginResultDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/api/user")
    public ResponseEntity<ApiCommonResponse<TempJoinResponse>> tempJoin(
            @Valid @RequestBody TempJoinRequest tempJoinRequest,
            BindingResult bindingResult
    ) {
        TempJoinResultDto response = userService.tempJoin(tempJoinRequest);

        return ResponseEntity.ok()
                .header(jwtProvider.ACCESS_HEADER, response.getAccessToken())
                .body(
                        new ApiCommonResponse<>(TempJoinResponse.builder()
                                .id(response.getId())
                                .name(response.getName())
                                .nickname(response.getNickname())
                                .profileImageUrl(response.getProfileImageUrl())
                                .build())
                );
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<ApiCommonResponse<TempLoginResponse>> tempLogin(
            @Valid @RequestBody TempLoginRequest tempLoginRequest,
            BindingResult bindingResult
    ) {
        TempLoginResultDto response = userService.tempLogin(tempLoginRequest);

        return ResponseEntity.ok()
                .header(jwtProvider.ACCESS_HEADER, response.getAccessToken())
                .body(
                        new ApiCommonResponse<>(TempLoginResponse.builder()
                                .id(response.getId())
                                .name(response.getName())
                                .nickname(response.getNickname())
                                .profileImageUrl(response.getProfileImageUrl())
                                .build())
                );
    }

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
}
