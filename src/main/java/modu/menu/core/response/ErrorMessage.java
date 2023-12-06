package modu.menu.core.response;

public enum ErrorMessage {

    // User
    POST_USERS_EMPTY_NAME("이름을 입력해주세요.");

    // domainEx1

    // domainEx2

    private final String message;

    private ErrorMessage(String message) {
        this.message = message;
    }
}
