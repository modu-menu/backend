package modu.menu.core.response;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    // 맨 앞에 붙이는 동사의 경우 추가는 ADD, 수정은 UPDATE, 삭제는 DELETE로 통일한다.
    // User
    ADD_USERS_DB_ERROR("User 저장 도중 DB 에러가 발생했습니다.");

    // domainEx1

    // domainEx2

    private final String value;

    private ErrorMessage(String value) {
        this.value = value;
    }
}
