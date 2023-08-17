import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import java.net.InetSocketAddress;
import java.net.Proxy;

public class ApiCaller {
    public static void main(String[] args) {
        String apiUrl = "https://abc.com/test";
        String proxyHost = "xyz.fd.dsd";
        int proxyPort = 8080;
        String bearerToken = "yourBearerTokenHere";
        
        // Set up a proxy using the provided proxy host and port
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        requestFactory.setProxy(proxy);

        // Create the RestTemplate instance with the proxy configuration
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        // Set the Bearer token in the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + bearerToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the API call using the RestTemplate
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        // Print the response
        System.out.println("Response: " + response.getBody());
    }
}
