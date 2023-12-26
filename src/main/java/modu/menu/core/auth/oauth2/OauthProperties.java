package modu.menu.core.auth.oauth2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Getter
@ConfigurationProperties(prefix = "oauth2")
// yml의 oauth property들을 객체로 바인딩할 수 있게 해주는 클래스
public class OauthProperties {
    private final Map<String, User> user = new HashMap<>();
    private final Map<String, Provider> provider = new HashMap<>();

    @Getter
    @Setter
    public static class User {
        private String clientId;
        private String clientSecret;
        private String redirectUri;
    }

    @Getter
    @Setter
    public static class Provider {
        private String tokenUri;
    }
}
