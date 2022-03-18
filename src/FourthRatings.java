import java.util.ArrayList;
import java.util.Collections;

public class FourthRatings {
    /**
     * Goes through all the raters and get the average
     *
     * @param movieId       the id of the movie
     * @param minimalRaters the minimum number of raters to consider this rating
     * @return the average if minimalRaters is met
     */
    private double getAverageByID(String movieId, int minimalRaters) {
        int ratingCount = 0;
        double average = 0;

        for (Rater rater : RaterDatabase.getRaters()) {
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
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> averageRatings = new ArrayList<>();

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
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        // Need to create the ArrayList<String> of movieIds from the MovieDatabase using the filterBy method
        // before calculating the averages
        // ! Assume that the MovieDatabase is already initialized
        ArrayList<String> movieIds = MovieDatabase.filterBy(filterCriteria);

        // Rating has movieId and movieRating
        ArrayList<Rating> averageRatings = new ArrayList<>();

        for (String movieId : movieIds) {
            Rating addRating = new Rating(movieId, getAverageByID(movieId, minimalRaters));
            if (addRating.getValue() != 0.0) averageRatings.add(addRating);
        }

        return averageRatings;
    }

    /**
     * First translate a rating from the scale of 0 to 10 to the scale -5 to 5 and get the dot product
     *
     * @param me the first rater
     * @param r  the second rater
     * @return the dot product of the ratings of movies they both rated
     */
    private double dotProduct(Rater me, Rater r) {
        ArrayList<String> moviesRatedByMe = new ArrayList<>();
        ArrayList<String> moviesRatedByOther = new ArrayList<>();

        for (String movieId : MovieDatabase.filterBy(new TrueFilter())) {
            if (me.hasRating(movieId)) moviesRatedByMe.add(movieId);
        }

        for (String movieId : MovieDatabase.filterBy(new TrueFilter())) {
            if (r.hasRating(movieId)) moviesRatedByOther.add(movieId);
        }

        ArrayList<String> moviesRatedByBoth = new ArrayList<>();
        for (String movieId : moviesRatedByMe) {
            if (moviesRatedByOther.contains(movieId)) moviesRatedByBoth.add(movieId);
        }

        ArrayList<Double> myRatings = new ArrayList<>();
        ArrayList<Double> otherRatings = new ArrayList<>();
        for (String movieId : moviesRatedByBoth) {
            myRatings.add(me.getRating(movieId) - 5);
            otherRatings.add(r.getRating(movieId) - 5);
        }

        int dotProduct = 0;
        for (int i = 0; i < myRatings.size(); i++) {
            dotProduct += (myRatings.get(i) * otherRatings.get(i));
        }

        return dotProduct;
    }

    /**
     * Computes a similarity rating for each rater in the RaterDatabase (except the rater with the ID given by the
     * parameter) to see how similar they are to the Rater
     * Note that in each Rating object the item field is a rater's ID, and the value field is the dot product
     * comparison between that rater and the rater whose ID is the parameter to getSimilarities
     *
     * @param id the id of the rater that we want to compare to
     * @return an ArrayList of Rating sorted by ratings from highest to lowest rating with the highest rating first
     * and only including those raters who have a positive similarity rating since those negative values are not
     * similar in any way
     */
    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> similarityRatings = new ArrayList<>();

        for (Rater rater : RaterDatabase.getRaters()) {
            if (rater.getID().equals(id)) continue;
            if (dotProduct(RaterDatabase.getRater(id), rater) < 0) continue;

            Rating newRaterRating = new Rating(rater.getID(), dotProduct(RaterDatabase.getRater(id), rater));
            similarityRatings.add(newRaterRating);
        }

        similarityRatings.sort(Collections.reverseOrder());
        return similarityRatings;
    }

    /**
     * @param id              the rater id
     * @param numSimilarRater the number of other raters we want to look for
     * @param minimalRaters   the minimum number of raters in the similarRater that must have had rated the movie
     * @return ArrayList of Rating of movies and their weighted average ratings using only the top numSimilarRaters
     * with positive ratings and including only those movies that have at least minimalRaters ratings from those most
     * similar raters (not just minimalRaters ratings overall)
     */
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRater, int minimalRaters) {
        ArrayList<Rating> weightedAverageRatings = new ArrayList<>();
        ArrayList<Rating> similarRaters = getSimilarities(id);

        for (String movieId : MovieDatabase.filterBy(new TrueFilter())) {
            double movieRating = 0;
            int numOfRaters = 0;

            for (int i = 0; i < numSimilarRater; i++) {
                Rating similarRater = similarRaters.get(i);
                Rater rater = RaterDatabase.getRater(similarRater.getItem());
                if (!rater.hasRating(movieId)) continue;

                double raterRatingOfMovie = rater.getRating(movieId);
                movieRating += raterRatingOfMovie * similarRater.getValue();
                numOfRaters++;
            }

            if (numOfRaters < minimalRaters) continue;
            double weightedAverageRating = movieRating / numOfRaters;
            weightedAverageRatings.add(new Rating(movieId, weightedAverageRating));
        }

        weightedAverageRatings.sort(Collections.reverseOrder());
        return weightedAverageRatings;
    }

    /**
     * @param id              the rater id
     * @param numSimilarRater the number of other raters we want to look for
     * @param minimalRaters   the minimum number of raters in the similarRater that must have had rated the movie
     * @return ArrayList of Rating of movies and their weighted average ratings using only the top numSimilarRaters
     * with positive ratings and including only those movies that have at least minimalRaters ratings from those most
     * similar raters (not just minimalRaters ratings overall)
     */
    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRater, int minimalRaters,
                                                       Filter filterCriteria) {
        ArrayList<Rating> weightedAverageRatings = new ArrayList<>();
        ArrayList<Rating> similarRaters = getSimilarities(id);

        for (String movieId : MovieDatabase.filterBy(filterCriteria)) {
            double movieRating = 0;
            int numOfRaters = 0;

            for (int i = 0; i < numSimilarRater; i++) {
                Rating similarRater = similarRaters.get(i);
                Rater rater = RaterDatabase.getRater(similarRater.getItem());
                if (!rater.hasRating(movieId)) continue;

                double raterRatingOfMovie = rater.getRating(movieId);
                movieRating += raterRatingOfMovie * similarRater.getValue();
                numOfRaters++;
            }

            if (numOfRaters < minimalRaters) continue;
            double weightedAverageRating = movieRating / numOfRaters;
            weightedAverageRatings.add(new Rating(movieId, weightedAverageRating));
        }

        weightedAverageRatings.sort(Collections.reverseOrder());
        return weightedAverageRatings;
    }
}
