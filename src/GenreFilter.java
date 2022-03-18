
public class GenreFilter implements Filter {
    private final String genre;

    GenreFilter(String genre) {
        this.genre = genre;
    }

    @Override public boolean satisfies(String id) {
        return MovieDatabase.getGenres(id).contains(this.genre);
    }
}
