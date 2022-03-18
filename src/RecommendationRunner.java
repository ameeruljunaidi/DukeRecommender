import java.util.*;

public class RecommendationRunner implements Recommender {
    private ArrayList<String> selectedMovieIds = new ArrayList<>();

    @Override public ArrayList<String> getItemsToRate() {
        // Only get a list of movie ids if the selectedMovies has not been initialized
        if (!selectedMovieIds.isEmpty()) return selectedMovieIds;

        // Get all the movie ids and random add it to the selectedMovies list
        selectedMovieIds = new ArrayList<>();
        AllFilters af = new AllFilters();
        af.addFilter(new YearAfterFilter(2010));
        String[] countries = {"UK", "Canada", "USA"};
        af.addFilter(new CountryFilter(countries));
        List<String> allMovieIds = MovieDatabase.filterBy(af);
        List<Rating> allMovieRatings = getMovieRatings(allMovieIds);
        allMovieRatings.sort(Collections.reverseOrder());
        HashMap<String, Integer> usedGenres = new HashMap<>();

        int movieCount = 0, idx = 0;
        while (movieCount < 20) { // Limit to add 20
            Movie currentMovie = MovieDatabase.getMovie(allMovieRatings.get(idx).getItem());
            String[] currentGenres = currentMovie.getGenres().split(",");
            if (!checkIfGenreUsed(currentGenres, usedGenres)) {
                selectedMovieIds.add(allMovieRatings.get(idx).getItem());
                ++movieCount;
                ++idx;
            }
        }

        return selectedMovieIds;
    }

    private boolean checkIfGenreUsed(String[] currentGenres, HashMap<String, Integer> usedGenres) {
        for (String genre : currentGenres) {
            if (!usedGenres.containsKey(genre)) {
                usedGenres.put(genre, 1);
            } else {
                if (usedGenres.get(genre) >= 10) return true;
                usedGenres.put(genre, usedGenres.get(genre) + 1);
            }
        }
        return false;
    }

    private List<Rating> getMovieRatings(List<String> allMovieIds) {
        List<Rating> movieRatings = new ArrayList<>();
        for (String movieId : allMovieIds) {
            movieRatings.add(getTotalRatings(movieId));
        }
        return movieRatings;
    }

    private Rating getTotalRatings(String movieId) {
        double totalRating = 0;
        int raterCount = 0;
        for (Rater rater : RaterDatabase.getRaters()) {
            if (!rater.hasRating(movieId)) continue;
            totalRating += rater.getRating(movieId);
            ++raterCount;
        }
        return new Rating(movieId, totalRating);
    }

    @Override public void printRecommendationsFor(String webRaterID) {
        FourthRatings sr = new FourthRatings();

        List<Rating> ratings = sr.getSimilarRatings(webRaterID, 100, 10);

        if (ratings.size() == 0) {
            showErrorMessage();
            return;
        }

        List<String> movieIds = new ArrayList<>();
        for (Rating rating : ratings) {
            if (!selectedMovieIds.contains(rating.getItem())) movieIds.add(rating.getItem());
            if (movieIds.size() > 20) break;
        }

        if (movieIds.size() == 0) {
            showErrorMessage();
            return;
        }

        showMovieRecommendations(movieIds);
    }

    private void showMovieRecommendations(List<String> movieIds) {
        // @formatter:off
        System.out.print(
            "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<style>\n" +
            "\n" +
            "table {\n" +
            "    font-family: arial, sans-serif;\n" +
            "    border-collapse: collapse;\n" +
            "    width: 100%;\n" +
            "}\n" +
            "\n" +
            "td, th {\n" +
            "    border: 1px solid #dddddd;\n" +
            "    text-align: left;\n" +
            "    padding: 8px;\n" +
            "}\n" +
            "\n" +
            "tr:nth-child(even) {\n" +
            "    background-color: #dddddd;\n" +
            "}\n" +
            "\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<h1>Recommended Movies</h1>\n" +
            "\n" +
            "<table>\n" +
            "<tr>\n" +
            "    <th>Title</th>\n" +
            "    <th>Year</th>\n" +
            "    <th>Genres</th>\n" +
            "    <th>Minutes</th>\n" +
            "    <th>Director</th>\n" +
            "    <th>Country</th>\n" +
            "</tr>"
        );

        for (String movieId : movieIds) {
            System.out.println(
                "<tr>\n" +
                "    <td>" + MovieDatabase.getTitle(movieId) + "</td>\n" +
                "    <td>" + MovieDatabase.getYear(movieId) + "</td>\n" +
                "    <td>" + MovieDatabase.getGenres(movieId) + "</td>\n" +
                "    <td>" + MovieDatabase.getMinutes(movieId) + "</td>\n" +
                "    <td>" + MovieDatabase.getDirector(movieId) + "</td>\n" +
                "    <td>" + MovieDatabase.getCountry(movieId) + "</td>\n" +
                "</tr>"
            );
        }

        System.out.println(
            "</table>\n" +
            "\n" +
            "</body>\n" +
            "</html>"
        );
        // @formatter:on
    }

    private void showErrorMessage() {
        // @formatter:off
        System.out.println(
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <body>\n" +
                "          <p> Error: No movies found! </p>\n" +
                "    </body>\n" +
                "</html>"
        );
        // @formatter:on
    }
}
