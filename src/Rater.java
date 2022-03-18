import java.util.ArrayList;

public interface Rater {
    boolean equals(Rater r);
    void addRating(String s, double d);
    boolean hasRating(String s);
    String getID();
    double getRating(String s);
    ArrayList<String> getMoviesRated();
    String toString();
}
