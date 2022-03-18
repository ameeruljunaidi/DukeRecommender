import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

public class MovieRunnerAverage {
    public void printAverageRatings(String moviefile, String ratingfile) {
        ThirdRatings sr = new ThirdRatings(ratingfile);

        // Print list of movies and their average ratings, sorted by averages
        // Per line: ratings (lowest to highest), title
        int threshold = 50;
        List<Rating> ratings = sr.getAverageRatings(threshold);
        Collections.sort(ratings);
        NumberFormat formatter = new DecimalFormat("#0.00");
        for (Rating rating : ratings) {
            System.out.println(
                    formatter.format(rating.getValue()) + " " + MovieDatabase.getTitle(rating.getItem()));
        }
        System.out.println(ratings.size() + " movies has " + threshold + " or more ratings.");

        // Print the number of movies and number of raters from the files
        System.out.println("\nNumber of movies " + MovieDatabase.size());
        System.out.println("Number of raters " + sr.getRaterSize());
    }

    public void getAverageRatingOneMovie(String moviefile, String ratingfile) {
        SecondRatings sr = new SecondRatings(moviefile, ratingfile);

        // Print out the average ratings for a specific movie title
        String movieTitle = "Moneyball";
        List<Rating> ratings = sr.getAverageRatings(0);
        for (Rating rating : ratings) {
            if (rating.getItem().equals(sr.getID(movieTitle))) {
                System.out.println("Average rating for " + movieTitle + " is " + rating.getValue());
            }
        }
    }

}
