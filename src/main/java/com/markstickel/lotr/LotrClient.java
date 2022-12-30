package com.markstickel.lotr;

import com.markstickel.lotr.client.api.MovieApi;
import com.markstickel.lotr.client.domain.Movie;
import com.markstickel.lotr.client.domain.Quote;
import com.markstickel.lotr.client.invoker.ApiClient;
import com.markstickel.lotr.client.invoker.ApiException;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mstickel
 * The LotrClient is the primary interface to the SDK.  Public methods in this class should be called to communicate with the backend.
 */
public class LotrClient {

    private static final Logger logger = LoggerFactory.getLogger(LotrClient.class);

    private MovieApi movieApi;

    private String authToken;

    private LotrClient(String authToken) {
        this(authToken, null);
    }

    /**
     * For internal testing only.
     * @param authToken
     * @param movieApi
     */
    LotrClient(String authToken, MovieApi movieApi) {
        this.authToken = authToken;
        this.movieApi = movieApi != null ? movieApi : new MovieApi(createApiClient());
    }

    private ApiClient createApiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setHost("localhost"); // allow user to specify connection params?  Might not make sense for a commercial SDK however
        apiClient.setPort(3001);
        apiClient.setScheme("http");
        apiClient.setBasePath("/v2");
        apiClient.setRequestInterceptor(req -> {
           req.header("Authorization", "Bearer " + authToken);
           HttpRequest copy = req.copy().build();
           logger.info("Request url: {} {}", copy.method(), copy.uri());
        });
        return apiClient;
    }

    /**
     * Lists the movies known to the service.
     * @return
     * @throws ApiException
     */
    public List<Movie> listMovies()
            throws ApiException {
        return listMovies(null, null, null, null, null);
    }

    /**
     * Lists the movies known to the service.
     * @param limit Maximum number of results on a page
     * @param page Page
     * @param offset Offset
     * @param sort The name of a movie field, followed by ":asc" or ":desc" to indicate sort order.
     * @param filterMap filtering works by casting simple url parameter expressions to mongodb lookup expressions and can be applied to any available key on the data models.
     * @return A (possibly empty) list of movies.
     * @throws ApiException An issue occurred while communicating with the backend.
     */
    public List<Movie> listMovies(Integer limit, Integer page, Integer offset, String sort, Map<String, Object> filterMap)
            throws ApiException {
        return movieApi.getMovies(limit, page, offset, sort, filterMap).getDocs();
    }

    /**
     * Gets details of a singular movie.
     * @param id The id of the movie.
     * @return Movie
     * @throws ApiException An issue occurred while communicating with the backend.
     */
    public Movie getMovie(String id)
            throws ApiException {
        return movieApi.getMovie(id);
    }

    /**
     * Gets quotes from the specified movie.
     * @param movieId Id of the movie.
     * @return A (possibly empty) list of quotes.
     * @throws ApiException An issue occurred while communicating with the backend.
     */
    public List<Quote> getQuotes(String movieId)
            throws ApiException {
        return getQuotes(movieId, null, null, null, null, null);
    }

    /**
     * Gets quotes from the specified movie.
     * @param movieId The id of the movie.
     * @param limit Number of results per page.
     * @param page The page number.
     * @param offset The result offset.
     * @param sort The name of a quote field, followed by ":asc" or ":desc" to indicate sort order.
     * @param filterMap filtering works by casting simple url parameter expressions to mongodb lookup expressions and can be applied to any available key on the data models.
     * @return A (possibly empty) list of quotes for the given movie and paging/sorting/filtering parameters.
     * @throws ApiException
     */
    public List<Quote> getQuotes(String movieId, Integer limit, Integer page, Integer offset, String sort, Map<String, Object> filterMap)
            throws ApiException {
        return movieApi.getQuotes(movieId, limit, page, offset, sort, filterMap).getDocs();
    }

    /**
     * Client builder.  Used to build an instance of the client.  The authToken is a required field.
     */
    public static class Builder {

        private String authToken;

        /**
         * Sets the auth token on the client
         * @param authToken String authorization bearer token.  Presented with each request in the Authorization header.
         * @return Builder.
         */
        public Builder authToken(String authToken) {
            this.authToken = authToken;
            return this;
        }

        /**
         * Builds the client.
         * @return the client instance.
         */
        public LotrClient build() {
            return new LotrClient(authToken);
        }
    }
}
