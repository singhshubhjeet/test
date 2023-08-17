import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class ApiController {

    @Value("${proxy.host}") // Read proxy host from application.properties
    private String proxyHost;

    @Value("${proxy.port}") // Read proxy port from application.properties
    private int proxyPort;

    @Value("${api.url}") // Read API URL from application.properties
    private String apiUrl;

    @Value("${bearer.token}") // Read bearer token from application.properties
    private String bearerToken;

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        
        // Set proxy for RestTemplate
        if (!proxyHost.isEmpty() && proxyPort > 0) {
            factory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)));
        }
        
        return new RestTemplate(factory);
    }

    @GetMapping("/call-api")
    public ResponseEntity<String> callApiWithProxyAndBearerToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + bearerToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity, String.class);

        return responseEntity;
    }
}
