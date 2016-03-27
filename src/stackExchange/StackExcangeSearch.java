package stackExchange;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by Andrey on 27.03.16.
 */
public class StackExcangeSearch {
    private static final String API_SEARCH_ADDRESS_DEFAULT = "https://api.stackexchange.com/2.2/search?order=desc&sort=activity&intitle=%s&site=%s";

    private String site;

    public StackExcangeSearch(String site) {
        this.site = site;
    }

    public SearchResults search(String text) {
        SearchResults result = null;

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String url = String.format(API_SEARCH_ADDRESS_DEFAULT, URLEncoder.encode(text, "UTF-8"), site);
            HttpGet httpget = new HttpGet(url);

            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };

            String responseBody = httpclient.execute(httpget, responseHandler);
            result = SearchResults.deserialize(responseBody);
        }
        catch (IOException e) {
            result = new SearchResults();
            result.setHasError(true);
            result.setItems(new ArrayList<>());
        }

        return result;
    }
}
