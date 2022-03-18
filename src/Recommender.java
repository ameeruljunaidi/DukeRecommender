import java.util.ArrayList;

public interface Recommender {

    /**
     * When user first visits the recommender site, our code will call the method getItemsToRate() to get a list of
     * movies to display on the web page for user to rate
     * After the user submits their ratings, our code will call the method printRecommendationsFor() to get your
     * recommendations based on the user's ratings and display them
     * Can choose randomly, 10-20 movies should be good
     *
     * @return a List of Strings representing movie IDs that will be used to present movies to the user
     */
    ArrayList<String> getItemsToRate();

    /**
     * Prints out an HTML table of movies recommended by your program for the user based on the movies they rated
     * Need to use FourthRatings class
     * Display about 10-20 recommended movies, and not include movies the user rated
     * If no movies were rated by the number minimal rater specified in the recommender, show a message
     *
     * @param webRaterID a String that is the ID of the user, who has been added by our code to the RaterDatabase
     *                   with the ratings they entered
     */
    void printRecommendationsFor(String webRaterID);
}
