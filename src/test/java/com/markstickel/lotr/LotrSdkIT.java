package com.markstickel.lotr;

import com.markstickel.lotr.client.domain.Movie;
import com.markstickel.lotr.client.domain.Quote;
import com.markstickel.lotr.client.invoker.ApiException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author mstickel
 */
public class LotrSdkIT {

    private LotrClient lotrClient;

    @BeforeEach
    public void setUp() {
        lotrClient = new LotrClient.Builder().authToken("RQyh4TvpOomu-MUHqMe1").build();
    }

    @Test
    public void testPerformOperations() {
        try {
            // test the list endpoint
            List<Movie> movies = lotrClient.listMovies();
            assertNotNull(movies);
            assertTrue(movies.size() >= 1);

            String movieId = "5cd95395de30eff6ebccde5d";

            // test the get single movie endpoint
            Movie singleMovie = lotrClient.getMovie(movieId);
            assertNotNull(singleMovie);

            // test the get quotes for movie endpoint
            List<Quote> quotes = lotrClient.getQuotes(movieId);
            assertNotNull(quotes);
            assertTrue(quotes.size() >= 1);
        } catch (ApiException e) {
            e.printStackTrace();
            fail();
        }
    }
}
