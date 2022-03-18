import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

public class MovieRunnerSimilarRatings {
    public void printAverageRatings(String moviefile, String ratingfile, int threshold) {
        MovieDatabase.initialize(moviefile);
        RaterDatabase.initialize(ratingfile);
        FourthRatings sr = new FourthRatings();

        // Ust the YearsAfterFilter to calculate the number of movies in the database that have at least a minimal
        // number of ratings and came out in a particular year

        // Print the number of movies and number of raters from the files
        System.out.println("\nNumber of movies " + MovieDatabase.size());
        System.out.println("Number of raters " + RaterDatabase.size());

        // Print list of movies and their average ratings, sorted by averages
        // Per line: ratings (lowest to highest), title
        List<Rating> ratings = sr.getAverageRatings(threshold);
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movies has " + threshold + " or more ratings.");
        NumberFormat formatter = new DecimalFormat("#0.00");
        for (Rating rating : ratings) {
            System.out.println(
                    formatter.format(rating.getValue()) + " " + MovieDatabase.getTitle(rating.getItem()));
        }

    }

    public void printAverageRatingsByYearAfterAndGenre(String moviefile, String ratingfile, int threshold, int year,
                                                       String genre) {
        MovieDatabase.initialize(moviefile);
        RaterDatabase.initialize(ratingfile);
        FourthRatings sr = new FourthRatings();

        // Ust the YearsAfterFilter to calculate the number of movies in the database that have at least a minimal
        // number of ratings and came out in a particular year

        // Print the number of movies and number of raters from the files
        System.out.println("\nNumber of movies " + MovieDatabase.size());
        System.out.println("Number of raters " + RaterDatabase.size());

        // Print list of movies and their average ratings, sorted by averages
        // Per line: ratings (lowest to highest), title
        AllFilters af = new AllFilters();
        af.addFilter(new YearAfterFilter(year));
        af.addFilter(new GenreFilter(genre));

        List<Rating> ratings = sr.getAverageRatingsByFilter(threshold, af);
        Collections.sort(ratings);

        System.out.println(ratings.size() + " movies has " + threshold + " or more ratings.");

        NumberFormat formatter = new DecimalFormat("#0.00");
        for (Rating rating : ratings) {
            System.out.print(formatter.format(rating.getValue()) + " ");
            System.out.print(MovieDatabase.getYear(rating.getItem()) + " ");
            System.out.println(MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t" + MovieDatabase.getGenres(rating.getItem()));
        }
    }

    public void printSimilarRatings(String moviefile, String ratingfile, String raterId, int numSimilarRaters,
                                    int minRateSimilarRatings) {
        MovieDatabase.initialize(moviefile);
        RaterDatabase.initialize(ratingfile);
        FourthRatings sr = new FourthRatings();

        List<Rating> ratings = sr.getSimilarRatings(raterId, numSimilarRaters, minRateSimilarRatings);
        System.out.println(ratings.size() + " movies has " + minRateSimilarRatings + " or more ratings.");

        NumberFormat formatter = new DecimalFormat("#0.00");
        for (Rating rating : ratings) {
            System.out.print(formatter.format(rating.getValue()) + " ");
            System.out.println(MovieDatabase.getTitle(rating.getItem()));
        }
    }

    public void printSimilarRatingsByGenre(String moviefile, String ratingfile, String raterId, int numSimilarRaters,
                                           int minRateSimilarRatings, String genre) {

        MovieDatabase.initialize(moviefile);
        RaterDatabase.initialize(ratingfile);
        FourthRatings sr = new FourthRatings();

        List<Rating> ratings =
                sr.getSimilarRatingsByFilter(raterId, numSimilarRaters, minRateSimilarRatings, new GenreFilter(genre));
        System.out.println(ratings.size() + " movies has " + minRateSimilarRatings + " or more ratings.");

        NumberFormat formatter = new DecimalFormat("#0.00");
        for (Rating rating : ratings) {
            System.out.print(formatter.format(rating.getValue()) + " ");
            System.out.println(MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t" + MovieDatabase.getGenres(rating.getItem()));
        }
    }

    public void printSimilarRatingsByDirector(String moviefile, String ratingfile, String raterId, int numSimilarRaters,
                                              int minRateSimilarRatings, String directors) {

        MovieDatabase.initialize(moviefile);
        RaterDatabase.initialize(ratingfile);
        FourthRatings sr = new FourthRatings();

        List<Rating> ratings =
                sr.getSimilarRatingsByFilter(raterId, numSimilarRaters, minRateSimilarRatings,
                        new DirectorsFilter(directors));
        System.out.println(ratings.size() + " movies has " + minRateSimilarRatings + " or more ratings.");

        NumberFormat formatter = new DecimalFormat("#0.00");
        for (Rating rating : ratings) {
            System.out.print(formatter.format(rating.getValue()) + " ");
            System.out.println(MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t" + MovieDatabase.getGenres(rating.getItem()));
        }
    }

    public void printSimilarRatingsByGenreAndMinutes(String moviefile, String ratingfile, String raterId,
                                                     int numSimilarRaters,
                                                     int minRateSimilarRatings, String genre, int min, int max) {

        MovieDatabase.initialize(moviefile);
        RaterDatabase.initialize(ratingfile);
        FourthRatings sr = new FourthRatings();

        AllFilters af = new AllFilters();
        af.addFilter(new GenreFilter(genre));
        af.addFilter(new MinutesFilter(min, max));

        List<Rating> ratings =
                sr.getSimilarRatingsByFilter(raterId, numSimilarRaters, minRateSimilarRatings, af);
        System.out.println(ratings.size() + " movies has " + minRateSimilarRatings + " or more ratings.");

        NumberFormat formatter = new DecimalFormat("#0.00");
        for (Rating rating : ratings) {
            System.out.print(formatter.format(rating.getValue()) + " ");
            System.out.println(MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t" + MovieDatabase.getGenres(rating.getItem()));
        }
    }

    public void printSimilarRatingsByYearAfterAndMinutes(String moviefile, String ratingfile, String raterId,
                                                         int numSimilarRaters,
                                                         int minRateSimilarRatings, int year, int min, int max) {

        MovieDatabase.initialize(moviefile);
        RaterDatabase.initialize(ratingfile);
        FourthRatings sr = new FourthRatings();

        AllFilters af = new AllFilters();
        af.addFilter(new YearAfterFilter(year));
        af.addFilter(new MinutesFilter(min, max));

        List<Rating> ratings =
                sr.getSimilarRatingsByFilter(raterId, numSimilarRaters, minRateSimilarRatings, af);
        System.out.println(ratings.size() + " movies has " + minRateSimilarRatings + " or more ratings.");

        NumberFormat formatter = new DecimalFormat("#0.00");
        for (Rating rating : ratings) {
            System.out.print(formatter.format(rating.getValue()) + " ");
            System.out.println(MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t" + MovieDatabase.getGenres(rating.getItem()));
        }
    }
}
