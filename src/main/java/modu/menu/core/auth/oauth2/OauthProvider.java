package modu.menu.core.auth.oauth2;

import lombok.Getter;

@Getter
// Spring Security의 ClientRegistration 역할을 하는 클래스
public class OauthProvider {
    private final String clientId;
    private final String clientSecret;
    private final String redirectUrl;
    private final String tokenUrl;

    public OauthProvider(OauthProperties.User user, OauthProperties.Provider provider) {
        this(user.getClientId(), user.getClientSecret(), user.getRedirectUri(), provider.getTokenUri());
    }

    public OauthProvider(String clientId, String clientSecret, String redirectUrl, String tokenUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.tokenUrl = tokenUrl;
    }
}
