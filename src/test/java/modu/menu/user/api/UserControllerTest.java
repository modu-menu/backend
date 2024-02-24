package modu.menu.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import modu.menu.core.auth.jwt.JwtProvider;
import modu.menu.user.api.request.TempJoinRequest;
import modu.menu.user.api.request.TempLoginRequest;
import modu.menu.user.service.UserService;
import modu.menu.user.service.dto.TempJoinServiceResponse;
import modu.menu.user.service.dto.TempLoginServiceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UserController 단위테스트")
@ActiveProfiles("test")
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtProvider jwtProvider;
    @MockBean
    private UserService userService;

    @DisplayName("새로운 회원을 등록한다.")
    @Test
    void crateUser() throws Exception {
        // given
        TempJoinRequest tempJoinRequest = TempJoinRequest.builder()
                .email("test1234@naver.com")
                .password("test1234")
                .name("홍길동")
                .nickname("test1234")
                .gender("M")
                .age(15)
                .birthdate(LocalDate.of(1997, 1, 1))
                .phoneNumber("01012345678")
                .build();

        // when
        when(userService.tempJoin(any()))
                .thenReturn(TempJoinServiceResponse.builder()
                        .id(1L)
                        .name(tempJoinRequest.getName())
                        .nickname(tempJoinRequest.getNickname())
                        .profileImageUrl("")
                        .accessToken("")
                        .build());

        // then
        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tempJoinRequest)))
                .andExpect(status().isOk());
    }

    @DisplayName("등록된 회원으로 로그인한다.")
    @Test
    void login() throws Exception {
        // given
        TempLoginRequest tempLoginRequest = TempLoginRequest.builder()
                .email("test1234@naver.com")
                .password("test1234")
                .build();

        // when
        when(userService.tempLogin(any()))
                .thenReturn(TempLoginServiceResponse.builder()
                        .id(1L)
                        .name("홍길동")
                        .nickname("test1234")
                        .profileImageUrl("")
                        .accessToken("token")
                        .build());

        // then
        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tempLoginRequest)))
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", "token"));
    }
}