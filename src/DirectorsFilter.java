public class DirectorsFilter implements Filter {
    private final String[] directors;

    DirectorsFilter(String directors) {
        this.directors = directors.split(",");
    }

    @Override public boolean satisfies(String id) {
        for (String director : directors) if (MovieDatabase.getDirector(id).contains(director)) return true;
        return false;
    }
}
