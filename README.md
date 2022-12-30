# Stickel SDK
### Lord of the Rings Movie Database SDK for Java 17+

### Get It

This artifact will be deployed to Maven Central.  Coordinates:

```
<dependency>
    <groupId>com.markstickel.lotr</groupId>
    <artifactId>stickel-sdk</artifactId>
    <version>${project.version}</version> <!-- currently '1.0-SNAPSHOT' -->
</dependency>
```

### Get the Source

If you'd prefer to have a look at the source code, you can find it on GitHub:

```
git clone https://github.com/mstickel/stickel-sdk
```

### Build It

To build it, simply run `mvn clean install`.  Optionally, you can run the integration tests by running `mvn integration-test`

### Use It

To use this SDK library in your code, add it as a Maven/Gradle dependency to your project, and then use the `LotrClient` to call endpoints.  Something like the following:

```
public class YourClass {

    public static void main(String[] args) {
        LotrClient lotrClient = new LotrClient.Builder().authToken("RQyh4TvpOomu-MUHqMe1").build();
        List<Movie> movies = lotrClient.listMovies(); // or with paging/sorting/filtering info - see JavaDocs
        Movie singleMovie = lotrClient.getMovie(movies.get(0).getId()); // or whatever movie id you choose
        List<Quote> quotes = lotrClient.getQuotes(movieId); 
    }
}
```

### Learn It

To generate the documentation for this project, run

`mvn site:site`

and then open a browser and navigate to `${project.basedir}/target/site/index.html`.  Lots of good information here including JavaDocs and test coverage (JaCoCo).

### Test It

To run the included tests against the SDK, run `mvn integration-test`.  This runs a suite of integration tests.  You'll need to have the backend up and running (with MongoDB) in order to do this, and you'll need to register a user.  More information on how to spin up the server side of the application, populate the database, register a user, and get that user's token can be found at https://github.com/gitfrosh/lotr-api.
