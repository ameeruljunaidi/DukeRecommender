import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class will process the movie and ratings data to answer questions about them
 */
public class FirstRatings {

    /**
     * Process every record from the CSV file whose name is a filename, a file of movie information
     *
     * @param filename is the file to parse through
     * @return an ArrayList of Movies with all the movie data from the file
     */
    public List<Movie> loadMovies(String filename) {
        List<Movie> ret = new ArrayList<>();

        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord r : parser) {
            Movie toAdd = new Movie(r.get("id"), r.get("title"), r.get("year"), r.get("genre"), r.get("director"),
                    r.get("country"), r.get("poster"), Integer.parseInt(r.get("minutes")));

            ret.add(toAdd);
        }

        return ret;
    }

    /**
     * Get a list of raters from the CSV
     *
     * @param filename the file that contains all the raters
     * @return a list of all the raters
     */
    public List<Rater> loadRaters(String filename) {
        List<Rater> ret = new ArrayList<>();
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord r : parser) {
            Rater rater = new EfficientRater(r.get("rater_id")); // This is where to change (plain / efficient)

            boolean isDuplicate = false;
            for (Rater returnRater : ret)
                if (rater.equals(returnRater)) {
                    rater = returnRater;
                    isDuplicate = true;
                }

            rater.addRating(r.get("movie_id"), Double.parseDouble(r.get("rating")));
            if (!isDuplicate) ret.add(rater);
        }

        return ret;
    }

    /**
     * Call the method loadMovies on the file ratedmovies_short and store the result in an ArrayList local variable
     * Print the number of movies, and print each movie
     * How many movies include the Comedy genre
     * Determine how many movies are greater than 150 minutes in length
     * The maximum number of movies by any director and who the directors are that directed that many movies
     * Some movies may have more than one director
     */
    public void testLoadMovie(String filename) {
        List<Movie> movies = loadMovies(filename);

        // Print out the number of movies
        System.out.println("Number of movies: " + movies.size());

        // Print out count mof movies that are comedy
        int comedyCount = 0;
        for (Movie movie : movies) if (movie.getGenres().contains("Comedy")) comedyCount++;
        System.out.println("Count of comedy movies: " + comedyCount);

        // Print out the number of movies that are long
        int longMovieCount = 0;
        for (Movie movie : movies) if (movie.getMinutes() > 150) longMovieCount++;
        System.out.println("Movies greater than 150 minutes in length: " + longMovieCount);

        // Figure out the number of movies by any director
        HashMap<String, Integer> directors = new HashMap<>();
        ArrayList<String> activeDirectors = new ArrayList<>();
        int mostDirectingCount = 0;
        for (Movie movie : movies) {
            String[] directorForMovie = movie.getDirector().split("\\s*,\\s*");
            for (String director : directorForMovie) {
                if (!directors.containsKey(director)) directors.put(director, 1);
                else directors.put(director, directors.get(director) + 1);
                if (directors.get(director) > mostDirectingCount) mostDirectingCount = directors.get(director);
            }
        }
        for (String director : directors.keySet()) {
            if (directors.get(director) == mostDirectingCount) activeDirectors.add(director);
        }
        System.out.print("Most active directors with " + mostDirectingCount + " movies is/are: ");
        System.out.println(activeDirectors);
    }

    public void testLoadRaters(String filename) {
        List<Rater> raters = loadRaters(filename);

        // Print the total number of raters
        System.out.println("Total number of raters: " + raters.size());

        // For each rater, print the rater's ID and the number of ratings they did on one line
        for (Rater rater : raters) {
            System.out.print("Current rater ID is: " + rater.getID());
            System.out.print(". Number of ratings done: " + rater.getMoviesRated().size() + "\n");
            System.out.println(rater);
        }

        // Find max number of ratings by any rater
        int maxRatingCount = 0;
        for (Rater rater : raters) {
            int ratingCount = rater.getMoviesRated().size();
            if (ratingCount > maxRatingCount) maxRatingCount = ratingCount;
        }

        // Which rater has this max count?
        List<Rater> highestRater = new ArrayList<>();
        for (Rater rater : raters)
            if (rater.getMoviesRated().size() == maxRatingCount) highestRater.add(rater);
        System.out.print("The most frequent raters with " + maxRatingCount + " ratings is are rater IDs: ");
        for (Rater rater : highestRater) System.out.print(rater.getID());
        System.out.println();

        // Find the number of ratings a particular movie has
        HashMap<String, Integer> movieRatingCount = new HashMap<>();
        for (Rater rater : raters) {
            List<String> movies = rater.getMoviesRated();
            for (String movie : movies) {
                if (!movieRatingCount.containsKey(movie)) movieRatingCount.put(movie, 1);
                else movieRatingCount.put(movie, movieRatingCount.get(movie) + 1);
            }
        }

        String movieId = "1798709";
        System.out.println("Movie with ID " + movieId + " was rated by " + movieRatingCount.get(movieId) + " raters.");

        // Determine how many movies have been rated by all these raters
        System.out.println("There were " + movieRatingCount.size() + " movies rated by all raters.");
    }
}
