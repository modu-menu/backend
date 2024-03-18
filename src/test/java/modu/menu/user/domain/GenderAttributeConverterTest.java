package modu.menu.user.domain;

import modu.menu.IntegrationTestSupporter;
import modu.menu.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GenderAttributeConverter 통합테스트")
class GenderAttributeConverterTest extends IntegrationTestSupporter {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("Gender.MALE을 DB에 저장할 때 'M'으로 변환한다.")
    @Test
    void convertToDatabaseColumn() {
        // given
        User user = User.builder()
                .name("홍길동")
                .nickname("nickname")
                .email("gildong123@naver.com")
                .age(10)
                .birthday(LocalDate.now())
                .password("password")
                .phoneNumber("phoneNumber")
                .profileImageUrl("img")
                .status(UserStatus.ACTIVE)
                .gender(Gender.MALE)
                .build();

        // when
        User savedUser = userRepository.save(user);
        String gender = (String) entityManager.createNativeQuery("select gender from user_tb u where u.id = :userId")
                .setParameter("userId", savedUser.getId())
                .getSingleResult();

        // then
        assertThat(gender).isEqualTo("M");
    }

    @DisplayName("DB에 저장된 값 'M'을 Gender.MALE로 변환한다.")
    @Test
    void convertToEntityAttribute() {
        // given
        User user = User.builder()
                .name("홍길동")
                .nickname("nickname")
                .email("gildong123@naver.com")
                .age(10)
                .birthday(LocalDate.now())
                .password("password")
                .phoneNumber("phoneNumber")
                .profileImageUrl("img")
                .status(UserStatus.ACTIVE)
                .gender(Gender.MALE)
                .build();

        // when
        User savedUser = userRepository.save(user);
        Object gender = entityManager.createQuery("select u.gender from User u where u.id = :userId")
                .setParameter("userId", savedUser.getId())
                .getSingleResult();

        // then
        assertThat(gender).isEqualTo(Gender.MALE);
    }
}