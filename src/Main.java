public class Main {
    public static void main(String[] args) {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        RecommendationRunner rr = new RecommendationRunner();
        rr.getItemsToRate();
        rr.printRecommendationsFor("175");

        // https://www.dukelearntoprogram.com//capstone/recommender.php?id=60NUuIiVda4LM9
    }
}
