package selenium.handlers;

import selenium.ConfProperties;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class URLhandler {
    private ConfProperties confProperties;

    public String getRedirectUri() {
    return queryParams.get("redirect_uri");
    }
    public String getResponseType() {
        return queryParams.get("response_type");
    }

    public String getClientId() {
        return queryParams.get("client_id");
    }


    private final Map<String, String> queryParams;

    public URLhandler(String redirectUrl, String responseType, String clientId) {
        queryParams = new HashMap<>();
        queryParams.put("redirect_uri", redirectUrl);
        queryParams.put("response_type", responseType);
        queryParams.put("client_id", clientId);
    }

    public String getOAuthUrl() {
        String url = confProperties.oauth2Url;
        Collection<String> queryParamsCollection = queryParams.keySet()
                .stream()
                .map(queryName -> queryName + "=" + queryParams.get(queryName))
                .collect(Collectors.toList());
        String query = String.join("&", queryParamsCollection);
        String oauthUrl = url + "?" + query;

        return oauthUrl;
    }
}
