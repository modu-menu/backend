package modu.menu.user.api;

import modu.menu.ControllerTestComponent;
import modu.menu.user.api.request.TempJoinRequest;
import modu.menu.user.service.dto.TempJoinResultDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerTest extends ControllerTestComponent {

    @DisplayName("새로운 사용자를 등록한다.(임시)")
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
                .phoneNumber("01012345678")
                .build();

        // when
        when(userService.join(any()))
                .thenReturn(TempJoinResultDto.builder()
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
}