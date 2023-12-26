package modu.menu.core.auth.oauth2;

import java.util.Map;

public class InMemoryProviderRepository {
    private final Map<String, OauthProvider> providers;

    public InMemoryProviderRepository(Map<String, OauthProvider> providers) {
        this.providers = providers;
    }

    public OauthProvider findByProviderName(String name) {
        return providers.get(name);
    }
}
