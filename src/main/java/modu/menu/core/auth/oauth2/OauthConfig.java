package modu.menu.core.auth.oauth2;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableConfigurationProperties(OauthProperties.class)
// yml의 oauth property들을 객체로 바인딩할 수 있게 해주는 클래스
public class OauthConfig {

    private final OauthProperties properties;

    public OauthConfig(OauthProperties properties) {
        this.properties = properties;
    }

    @Bean
    public InMemoryProviderRepository inMemoryProviderRepository() {
        Map<String, OauthProvider> providers = OauthAdaptor.getOauthProviders(properties);

        return new InMemoryProviderRepository(providers);
    }
}
