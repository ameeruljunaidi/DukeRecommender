import java.util.ArrayList;
import java.util.HashMap;

public class EfficientRater implements Rater {
    private final String myID;
    private final HashMap<String, Rating> myRatings;

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<>();
    }

    public String getID() {
        return myID;
    }

    /**
     * Get the rating of a movie given its movie id
     *
     * @param movieId the movie id
     * @return the rating of the movie if found, -1 otherwise
     */
    public double getRating(String movieId) {
        if (this.myRatings.containsKey(movieId)) return this.myRatings.get(movieId).getValue();
        else return -1;
    }

    public boolean equals(Rater other) {
        return this.myID.equals(other.getID());
    }

    public int numRatings() {
        return myRatings.size();
    }

    /**
     * Add a rating to the rater
     *
     * @param movieId     the id of the movie
     * @param movieRating the rating of the movie
     */
    public void addRating(String movieId, double movieRating) {
        myRatings.put(movieId, new Rating(movieId, movieRating));
    }

    /**
     * Check if rater has a rating for any given movieId
     *
     * @param movieId the movie id
     * @return true if rater contains the movie id, false otherwise
     */
    public boolean hasRating(String movieId) {
        return this.myRatings.containsKey(movieId);
    }

    /**
     * Get the list of movie ids that has been rated
     *
     * @return the ArrayList of movie ids that has been rated
     */
    public ArrayList<String> getMoviesRated() {
        ArrayList<String> list = new ArrayList<>();
        for (String movieId : this.myRatings.keySet()) list.add(this.myRatings.get(movieId).getItem());

        return list;
    }

    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append("Rater ID: ").append(this.myID).append(", Ratings: ");
        for (String movieId : this.myRatings.keySet()) {
            toString.append(" ").append(this.myRatings.get(movieId)).append(" ");
        }
        return toString.toString();
    }
}
