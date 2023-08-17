 @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setProxy(new java.net.Proxy(java.net.Proxy.Type.HTTP, java.net.ProxySelector.getDefault()));
        return new RestTemplate(factory);
    }
