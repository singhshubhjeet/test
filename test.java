import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ProxyRestTemplateExample {

    public static void main(String[] args) {
        String apiUrl = "https://api.example.com/data";
        String proxyHost = "proxy.example.com";
        int proxyPort = 8080;
        String proxyUsername = "your_proxy_username";
        String proxyPassword = "your_proxy_password";

        // Create a RestTemplate with proxy settings
        RestTemplate restTemplate = createProxyRestTemplate(proxyHost, proxyPort, proxyUsername, proxyPassword);

        // Set up headers or other parameters if needed
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer your_access_token");

        // Make a GET request to the API using the proxy
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class, headers);

        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody());
    }

    private static RestTemplate createProxyRestTemplate(String proxyHost, int proxyPort, String proxyUsername, String proxyPassword) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setProxy(createProxy(proxyHost, proxyPort, proxyUsername, proxyPassword));
        return new RestTemplate(factory);
    }

    private static Proxy createProxy(String proxyHost, int proxyPort, String proxyUsername, String proxyPassword) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(proxyUsername, proxyPassword.toCharArray());
            }
        };

        Authenticator.setDefault(authenticator);

        return proxy;
    }
}
