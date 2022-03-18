// An immutable passive data object (PDO) to represent the rating data
public class Rating implements Comparable<Rating> {
    private final String item;
    private final double value;

    public Rating(String item, double value) {
        this.item = item;
        this.value = value;
    }

    // Returns item being rated
    public String getItem() {
        return item;
    }

    // Returns the value of this rating (as a number so it can be used in calculations)
    public double getValue() {
        return value;
    }

    // Returns a string of all the rating information
    public String toString() {
        return "[" + getItem() + ", " + getValue() + "]";
    }

    public int compareTo(Rating other) {
        return Double.compare(value, other.value);
    }
}
