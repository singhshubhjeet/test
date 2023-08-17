@RequestMapping("/api")
public class ApiController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.url}")
    private String apiUrl;

    @GetMapping("/call-api")
    public ResponseEntity<String> callApi() {
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        return response;
    }
