package com.example.demo.service;

import io.chucknorris.client.ChuckNorrisClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class RandomText {
    /*
    public static String getText() {
        URL url = null;
        try {
            url = new URL("https://api.api-ninjas.com/v1/chucknorris");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseStream);
            return root.path("fact").asText();
            // HfjWQMHhQOKm7MiZER03KQ==E4XptIkiMSm0uQIi
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
     */
    public static String getText() {
        // create the chuck norris client
        ChuckNorrisClient client = new ChuckNorrisClient();

// get a random joke and print it
        //Joke joke = client.getRandomJoke();
        //System.out.println(joke.getValue());

// get a random joke with a specifc category
        //Joke joke = client.getRandomJoke("dev");
        return client.getRandomJoke("dev").getValue();
        //return client.getRandomJoke("dev").toString();

// search jokes with free-text
        //List<Joke> jokes = client.searchJokes("developer");

// get a list of available categories
        //List<String> categories = client.getCategories();

        //return createRandomString();
    }

    static String createRandomString() {

        int length = 100;
        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
}
