package com.markstickel.lotr;

import com.markstickel.lotr.client.api.MovieApi;
import com.markstickel.lotr.client.domain.GetMovies200Response;
import com.markstickel.lotr.client.domain.Movie;
import com.markstickel.lotr.client.invoker.ApiException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author mstickel
 */
@ExtendWith(MockitoExtension.class)
public class LotrClientTest {

    @Mock
    private MovieApi movieApi;

    private LotrClient lotrClient;

    @BeforeEach
    public void setUp() {
        lotrClient = new LotrClient("foo", movieApi);
    }

    @Test
    public void testListMovies() {
        try {
            GetMovies200Response response = mock(GetMovies200Response.class);
            List<Movie> expected = new ArrayList<>();
            when(response.getDocs()).thenReturn(expected);
            when(movieApi.getMovies(null, null, null, null, null)).thenReturn(response);
            List<Movie> result = lotrClient.listMovies();
            assertEquals(expected, result);
        } catch (ApiException e) {
            fail();
        }
    }
}
