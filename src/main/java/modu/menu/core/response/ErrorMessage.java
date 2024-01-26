package modu.menu.core.response;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    ADD_REQUEST_BODY_ERROR("RestTemplate RequestBody 생성에 실패했습니다."),
    MAPPER_READ_TREE_ERROR("ObjectMapper readTree 실행에 실패했습니다."),

    // User
    DUPLICATE_EMAIL("중복된 이메일입니다."),
    NOT_EXIST_USER_TOKEN("존재하지 않는 회원의 토큰입니다."),
    TOKEN_VERIFICATION_FAIL("토큰 검증에 실패했습니다."),
    EXPIRED_TOKEN("만료된 토큰입니다."),
    ADD_USER_DB_ERROR("회원 저장 도중 DB 에러가 발생했습니다."),
    LOGIN_USER_PROVIDER_ERROR("로그인 요청시 /login/oauth/{provider}로 입력해주세요."),
    GET_KAKAO_TOKEN_ERROR("카카오 서버 문제로 인해 토큰 발급 요청에 실패했습니다."),
    GET_KAKAO_USER_ERROR("카카오 서버 문제로 인해 사용자 정보 요청에 실패했습니다.");

    // domainEx1

    // domainEx2

    private final String value;

    private ErrorMessage(String value) {
        this.value = value;
    }
}
