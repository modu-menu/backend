package modu.menu.core.response;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    ERROR_LOG_TEST_MESSAGE("Slack 에러 로그 테스트"),
    ADD_REQUEST_BODY_ERROR("RestTemplate RequestBody 생성에 실패했습니다."),
    MAPPER_READ_TREE_ERROR("ObjectMapper readTree 실행에 실패했습니다."),
    CANT_CONVERT_STRING_TO_ENUM("주어진 문자열을 적절한 Enum으로 변환할 수 없습니다."),

    // Exception500
    CHECK_STACK_TRACE("서버에서 처리하지 못한 예외입니다. 스택 트레이스를 확인하세요."),

    // User
    DUPLICATE_EMAIL("중복된 이메일입니다."),
    CANT_MATCH_TOKEN_WITH_PATH_VARIABLE("토큰과 Path Variable의 id가 일치하지 않습니다."),
    NOT_EXIST_USER_TOKEN("존재하지 않는 회원의 토큰입니다."),
    NOT_ACTIVE_USER_TOKEN("탈퇴한 회원의 토큰입니다."),
    EMPTY_TOKEN("토큰 값이 비어있거나 null입니다."),
    TOKEN_VERIFICATION_FAIL("토큰 검증에 실패했습니다."),
    EXPIRED_TOKEN("만료된 토큰입니다."),
    ADD_USER_DB_ERROR("회원 저장 도중 DB 에러가 발생했습니다."),
    LOGIN_USER_WRONG_EMAIL("회원이 아닌 이메일이거나 이메일을 잘못 입력했습니다."),
    LOGIN_USER_WRONG_PASSWORD("비밀번호를 잘못 입력했습니다."),
    LOGIN_USER_PROVIDER_ERROR("로그인 요청시 /login/oauth/{provider}로 입력해주세요."),
    GET_KAKAO_TOKEN_ERROR("카카오 서버 문제로 인해 토큰 발급 요청에 실패했습니다."),
    GET_KAKAO_USER_ERROR("카카오 서버 문제로 인해 사용자 정보 요청에 실패했습니다."),

    // Vote
    NOT_EXIST_VOTE("해당 ID와 일치하는 투표가 존재하지 않습니다."),
    CANT_INVITE_TO_END_VOTE("종료된 투표에는 초대할 수 없습니다."),
    NOT_EXIST_PLACE("해당 ID와 일치하는 음식점이 존재하지 않습니다."),
    NOT_EXIST_PLACE_IN_VOTE("해당 ID와 일치하는 음식점이 투표에 존재하지 않습니다."),
    CANT_FINISH_ALREADY_END_VOTE("이미 종료된 투표입니다."),
    NOT_ALLOWED_USER("투표에 초대된 회원이 아닙니다."),
    CANT_FINISH_BY_PARTICIPANT("주최자만 투표를 종료할 수 있습니다.");

    // domainEx1

    // domainEx2

    private final String value;

    ErrorMessage(String value) {
        this.value = value;
    }
}
