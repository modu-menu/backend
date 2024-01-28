package modu.menu.user.service;

import modu.menu.user.api.request.TempJoinRequest;
import modu.menu.user.api.request.TempLoginRequest;
import modu.menu.user.domain.Gender;
import modu.menu.user.domain.User;
import modu.menu.user.domain.UserStatus;
import modu.menu.user.repository.UserRepository;
import modu.menu.user.service.dto.TempJoinResultDto;
import modu.menu.user.service.dto.TempLoginResultDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.awaitility.Awaitility.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;


    @DisplayName("회원 정보를 입력하면, 새로운 회원을 등록하고 해당 회원의 데이터를 반환한다.")
    @Test
    void tempJoin() {
        // given
        TempJoinRequest tempJoinRequest = TempJoinRequest.builder()
                .email("test@naver.com")
                .password("password1")
                .name("name1")
                .nickname("nickname1")
                .gender("M")
                .age(1)
                .phoneNumber("01012345678")
                .build();
        User tempUser = createUser(1L, "test@naver.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(tempUser);

        // when
        TempJoinResultDto tempJoinResultDto = userService.tempJoin(tempJoinRequest);

        // then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @DisplayName("회원의 이메일과 비밀번호를 입력하면, 해당 회원의 데이터를 반환한다.")
    @Test
    void tempLogin() {
        // given
        TempLoginRequest tempLoginRequest = TempLoginRequest.builder()
                .email("test@naver.com")
                .password("test1234")
                .build();
        User tempUser = createUser(1L, "test@naver.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(tempUser));

        // when
        TempLoginResultDto tempLoginResultDto = userService.tempLogin(tempLoginRequest);

        // then
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    private User createUser(Long id, String email) {
        return User.builder()
                .id(id)
                .email(email)
                .name("name")
                .nickname("nickname")
                .gender(Gender.MALE)
                .age(id.intValue())
                .birthday(LocalDate.now())
                .password("test1234")
                .phoneNumber("01012345678")
                .status(UserStatus.ACTIVE)
                .build();
    }
}