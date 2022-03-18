/**
 * Write a description of class Rater here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.*;

public class PlainRater implements Rater {
    private final String myID;
    private final ArrayList<Rating> myRatings;

    public PlainRater(String id) {
        myID = id;
        myRatings = new ArrayList<Rating>();
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
        for (Rating myRating : myRatings) {
            if (myRating.getItem().equals(movieId)) {
                return myRating.getValue();
            }
        }

        return -1;
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
        myRatings.add(new Rating(movieId, movieRating));
    }

    /**
     * Check if rater has a rating for any given movieId
     *
     * @param movieId the movie id
     * @return true if rater contains the movie id, false otherwise
     */
    public boolean hasRating(String movieId) {
        for (Rating myRating : myRatings) {
            if (myRating.getItem().equals(movieId)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get the list of movie ids that has been rated
     *
     * @return the ArrayList of movie ids that has been rated
     */
    public ArrayList<String> getMoviesRated() {
        ArrayList<String> list = new ArrayList<>();
        for (Rating myRating : myRatings) {
            list.add(myRating.getItem());
        }

        return list;
    }

    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append("Rater ID: ").append(this.myID).append(", Ratings: ");
        for (Rating rating : this.myRatings) toString.append(" ").append(rating).append(" ");
        return toString.toString();
    }
}
