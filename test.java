import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class RestTemplateConfig {

    @Value("${proxy.host}")
    private String proxyHost;

    @Value("${proxy.port}")
    private int proxyPort;

    @Value("${api.baseurl}")
    private String apiBaseUrl;

    @Value("${api.bearertoken}")
    private String apiBearerToken;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + apiBearerToken);
            return execution.execute(request, body);
        });

        // Create a proxy settings
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        requestFactory.setProxy(proxy);
        restTemplate.setRequestFactory(requestFactory);

        return restTemplate;
    }
}

// Rest Controller
@RestController
@RequestMapping("/api")
public class ApiController {

    private final RestTemplate restTemplate;

    @Autowired
    public ApiController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/call-api")
    public ResponseEntity<String> callApiUsingProxy() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiBearerToken);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiBaseUrl + "/test");
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<String> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, entity, String.class);
        
        return response;
    }
}
