package org.example;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main {
    public static final String REMOTE_SERVICE_URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        CloseableHttpClient client = HttpClientBuilder.create()
                .setUserAgent("cat")
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();


        HttpGet request = new HttpGet(REMOTE_SERVICE_URL);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        CloseableHttpResponse response = client.execute(request);

        InputStream content = response.getEntity().getContent();
        List<Post> posts = mapper.readValue(content, new TypeReference<>() {
        });

        posts.stream()
                .filter(post -> post.getUpvotes() == null)
                .forEach(System.out::println);
    }
}