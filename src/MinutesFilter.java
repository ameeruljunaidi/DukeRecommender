public class MinutesFilter implements Filter {
    // At least min minutes and no more than max minutes
    private final int min;
    private final int max;

    MinutesFilter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override public boolean satisfies(String id) {
        return MovieDatabase.getMinutes(id) >= min && MovieDatabase.getMinutes(id) <= max;
    }
}
