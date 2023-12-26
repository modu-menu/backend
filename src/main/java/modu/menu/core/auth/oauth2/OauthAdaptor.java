package modu.menu.core.auth.oauth2;

import java.util.HashMap;
import java.util.Map;

public class OauthAdaptor {

    private OauthAdaptor() {}

    // OauthProperties -> OauthProvider
    public static Map<String, OauthProvider> getOauthProviders(OauthProperties properties) {
        Map<String, OauthProvider> oauthProvider = new HashMap<>();

        properties.getUser().forEach((key, value) -> {
            oauthProvider.put(key, new OauthProvider(value, properties.getProvider().get(key)));
        });

        return oauthProvider;
    }
}
