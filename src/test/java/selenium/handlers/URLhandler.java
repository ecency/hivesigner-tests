package selenium.handlers;

import selenium.ConfProperties;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class URLhandler {

    public String oauthUrlConstructor(String redirectUrl, String responseType, String clientId){
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("redirect_uri", redirectUrl);
        queryParams.put("response_type", responseType);
        queryParams.put("client_id", clientId);

        String url =  ConfProperties.getProperty("oauth2Url");
        Collection<String> queryParamsCollection = queryParams.keySet()
                .stream()
                .map(queryName -> queryName + "=" + queryParams.get(queryName))
                .collect(Collectors.toList());
        String query = String.join("&", queryParamsCollection);
        String oauthUrl = url + "?" + query;

        return oauthUrl;
    }
}
