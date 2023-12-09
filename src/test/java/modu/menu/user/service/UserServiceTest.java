package modu.menu.user.service;

import modu.menu.user.domain.Gender;
import modu.menu.user.domain.User;
import modu.menu.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.awaitility.Awaitility.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @DisplayName("User가 회원가입을 요청하고, 성공한다.")
    @Test
    void join() {
        User user = User.builder()
                .id(1L)
                .ci("ci")
                .email("test@test.com")
                .name("이승민")
                .gender(Gender.MALE)
                .age(26)
                .phoneNumber("01012345678")
                .build();
        userService.join(user);

        verify(userRepository).save(any());
    }
}