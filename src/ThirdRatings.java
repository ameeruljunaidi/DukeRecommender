import java.util.ArrayList;
import java.util.List;

public class ThirdRatings {
    private final List<Rater> myRaters;

    public ThirdRatings(String ratingsfile) {
        FirstRatings fr = new FirstRatings();
        this.myRaters = fr.loadRaters(ratingsfile);
    }

    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }

    public int getRaterSize() {
        return this.myRaters.size();
    }


    /**
     * @param movieId       the id of the movie
     * @param minimalRaters the minimum number of raters to consider this rating
     * @return the average if minimalRaters is met
     */
    private double getAverageByID(String movieId, int minimalRaters) {
        int ratingCount = 0;
        double average = 0;

        for (Rater rater : this.myRaters) {
            if (rater.hasRating(movieId)) {
                ratingCount++;
                average += rater.getRating(movieId);
            }
        }

        if (ratingCount >= minimalRaters) return average / ratingCount;
        else return 0.0;
    }

    /**
     * find the average rating for every movie that has been rated by at least minimalRaters raters
     * Store each rating in a Rating object in which the movie ID and the average rating are used in creating the object
     *
     * @param minimalRaters the minimum threshold amount of raters
     * @return list of Ratings
     */
    public List<Rating> getAverageRatings(int minimalRaters) {
        List<String> movies = MovieDatabase.filterBy(new TrueFilter());
        List<Rating> averageRatings = new ArrayList<>();

        for (String movie : movies) {
            Rating addRating = new Rating(movie, getAverageByID(movie, minimalRaters));
            if (addRating.getValue() != 0.0) averageRatings.add(addRating);
        }

        return averageRatings;
    }

    /**
     * Get a list of ratings of all the movies that fits the criteria
     *
     * @param minimalRaters  is the minimum number of ratings a movie must have
     * @param filterCriteria the criteria to filter for
     * @return a List of Rating of movies that fits the criteria on average
     */
    public List<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        // Need to create the ArrayList<String> of movieIds from the MovieDatabase using the filterBy method
        // before calculating the averages
        // ! Assume that the MovieDatabase is already initialized
        ArrayList<String> movieIds = MovieDatabase.filterBy(filterCriteria);

        // Rating has movieId and movieRating
        List<Rating> averageRatings = new ArrayList<>();

        for (String movieId : movieIds) {
            Rating addRating = new Rating(movieId, getAverageByID(movieId, minimalRaters));
            if (addRating.getValue() != 0.0) averageRatings.add(addRating);
        }

        return averageRatings;
    }

}
