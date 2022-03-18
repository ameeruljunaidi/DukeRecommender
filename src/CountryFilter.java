public class CountryFilter implements Filter {
    String[] countries;

    public CountryFilter(String[] countries) {
        this.countries = countries;
    }

    @Override public boolean satisfies(String id) {
        for (String country : this.countries) {
            if (MovieDatabase.getCountry(id).contains(country)) return true;
        }
        return false;
    }
}
