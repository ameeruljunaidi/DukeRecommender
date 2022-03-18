import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

public class MovieRunnerWithFilters {
    public void printAverageRatings(String moviefile, String ratingfile, int threshold) {
        MovieDatabase.initialize(moviefile);
        ThirdRatings sr = new ThirdRatings(ratingfile);

        // Ust the YearsAfterFilter to calculate the number of movies in the database that have at least a minimal
        // number of ratings and came out in a particular year

        // Print the number of movies and number of raters from the files
        System.out.println("\nNumber of movies " + MovieDatabase.size());
        System.out.println("Number of raters " + sr.getRaterSize());

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

    public void printAverageRatingsByYear(String moviefile, String ratingfile, int threshold, int year) {
        MovieDatabase.initialize(moviefile);
        ThirdRatings sr = new ThirdRatings(ratingfile);

        // Ust the YearsAfterFilter to calculate the number of movies in the database that have at least a minimal
        // number of ratings and came out in a particular year

        // Print the number of movies and number of raters from the files
        System.out.println("\nNumber of movies " + MovieDatabase.size());
        System.out.println("Number of raters " + sr.getRaterSize());

        // Print list of movies and their average ratings, sorted by averages
        // Per line: ratings (lowest to highest), title
        List<Rating> ratings = sr.getAverageRatingsByFilter(threshold, new YearAfterFilter(year));
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movies has " + threshold + " or more ratings.");
        NumberFormat formatter = new DecimalFormat("#0.00");
        for (Rating rating : ratings) {
            System.out.println(
                    formatter.format(rating.getValue()) + " " + MovieDatabase.getTitle(rating.getItem()));
        }
    }

    public void printAverageRatingsByGenre(String moviefile, String ratingfile, int threshold, String genre) {
        MovieDatabase.initialize(moviefile);
        ThirdRatings sr = new ThirdRatings(ratingfile);

        // Ust the YearsAfterFilter to calculate the number of movies in the database that have at least a minimal
        // number of ratings and came out in a particular year

        // Print the number of movies and number of raters from the files
        System.out.println("\nNumber of movies " + MovieDatabase.size());
        System.out.println("Number of raters " + sr.getRaterSize());

        // Print list of movies and their average ratings, sorted by averages
        // Per line: ratings (lowest to highest), title
        List<Rating> ratings = sr.getAverageRatingsByFilter(threshold, new GenreFilter(genre));
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movies has " + threshold + " or more ratings.");
        NumberFormat formatter = new DecimalFormat("#0.00");
        for (Rating rating : ratings) {
            System.out.print(formatter.format(rating.getValue()) + " ");
            System.out.println(MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t" + MovieDatabase.getGenres(rating.getItem()));
        }
    }

    public void printAverageRatingsByMinutes(String moviefile, String ratingfile, int threshold, int min, int max) {
        MovieDatabase.initialize(moviefile);
        ThirdRatings sr = new ThirdRatings(ratingfile);

        // Ust the YearsAfterFilter to calculate the number of movies in the database that have at least a minimal
        // number of ratings and came out in a particular year

        // Print the number of movies and number of raters from the files
        System.out.println("\nNumber of movies " + MovieDatabase.size());
        System.out.println("Number of raters " + sr.getRaterSize());

        // Print list of movies and their average ratings, sorted by averages
        // Per line: ratings (lowest to highest), title
        List<Rating> ratings = sr.getAverageRatingsByFilter(threshold, new MinutesFilter(min, max));
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movies has " + threshold + " or more ratings.");
        NumberFormat formatter = new DecimalFormat("#0.00");
        for (Rating rating : ratings) {
            System.out.print(formatter.format(rating.getValue()) + " ");
            System.out.println(MovieDatabase.getTitle(rating.getItem()));
        }
    }

    public void printAverageRatingsByDirectors(String moviefile, String ratingfile, int threshold, String directors) {
        MovieDatabase.initialize(moviefile);
        ThirdRatings sr = new ThirdRatings(ratingfile);

        // Ust the YearsAfterFilter to calculate the number of movies in the database that have at least a minimal
        // number of ratings and came out in a particular year

        // Print the number of movies and number of raters from the files
        System.out.println("\nNumber of movies " + MovieDatabase.size());
        System.out.println("Number of raters " + sr.getRaterSize());

        // Print list of movies and their average ratings, sorted by averages
        // Per line: ratings (lowest to highest), title
        List<Rating> ratings = sr.getAverageRatingsByFilter(threshold, new DirectorsFilter(directors));
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movies has " + threshold + " or more ratings.");
        NumberFormat formatter = new DecimalFormat("#0.00");
        for (Rating rating : ratings) {
            System.out.print(formatter.format(rating.getValue()) + " ");
            System.out.println(MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t" + MovieDatabase.getDirector(rating.getItem()));
        }
    }

    public void printAverageRatingsByYearAfterAndGenre(String moviefile, String ratingfile, int threshold, int year,
                                                       String genre) {
        MovieDatabase.initialize(moviefile);
        ThirdRatings sr = new ThirdRatings(ratingfile);

        // Ust the YearsAfterFilter to calculate the number of movies in the database that have at least a minimal
        // number of ratings and came out in a particular year

        // Print the number of movies and number of raters from the files
        System.out.println("\nNumber of movies " + MovieDatabase.size());
        System.out.println("Number of raters " + sr.getRaterSize());

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

    public void printAverageRatingsByDirectorsAndMinutes(String moviefile, String ratingfile, int threshold,
                                                         String directors, int min, int max) {
        MovieDatabase.initialize(moviefile);
        ThirdRatings sr = new ThirdRatings(ratingfile);

        // Ust the YearsAfterFilter to calculate the number of movies in the database that have at least a minimal
        // number of ratings and came out in a particular year

        // Print the number of movies and number of raters from the files
        System.out.println("\nNumber of movies " + MovieDatabase.size());
        System.out.println("Number of raters " + sr.getRaterSize());

        // Print list of movies and their average ratings, sorted by averages
        // Per line: ratings (lowest to highest), title
        AllFilters af = new AllFilters();
        af.addFilter(new DirectorsFilter(directors));
        af.addFilter(new MinutesFilter(min, max));

        List<Rating> ratings = sr.getAverageRatingsByFilter(threshold, af);
        Collections.sort(ratings);

        System.out.println(ratings.size() + " movies has " + threshold + " or more ratings.");

        NumberFormat formatter = new DecimalFormat("#0.00");
        for (Rating rating : ratings) {
            System.out.print(formatter.format(rating.getValue()) + " ");
            System.out.print("Time: " + MovieDatabase.getMinutes(rating.getItem()) + " ");
            System.out.println(MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t" + MovieDatabase.getDirector(rating.getItem()));
        }
    }
}
