package com.example.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import io.chucknorris.client.ChuckNorrisClient;
import io.chucknorris.client.Joke;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

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
        return client.getRandomJoke("dev").toString();

// search jokes with free-text
        //List<Joke> jokes = client.searchJokes("developer");

// get a list of available categories
        //List<String> categories = client.getCategories();
    }
}
