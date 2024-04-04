package main;
import java.util.*;

public class Film implements Comparable<Film> {
    private final String title;
    private final int year;
    private final String director;
    private HashMap<String, Float> ratings;
    private Float averageRating;

    public Film(String title, int year, String director) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.ratings = new HashMap<String, Float>();
        this.averageRating = null;
        addFilm();
    }

    public Film(String title, int year, String director, HashMap<String, Float> ratings) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.ratings = ratings;
        computeAverageRating();
        addFilm();
    }

    private void addFilm() {
        App.films.put(getKey(), this);
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public HashMap<String, Float> getRatings() {
        return ratings;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public boolean updateRating(String memberKey, Float rating) {
        ratings.put(memberKey, rating);
        computeAverageRating();
        return true;
    }

    public Float computeAverageRating() {
        float sum = 0;
        int nonNullRatings = 0;
        for (Float rating : ratings.values()) {
            if (rating != null) {
                sum += rating;
                nonNullRatings++;
            }
        }

        if (ratings.size() == 0 || nonNullRatings == 0) {
            averageRating = null;
        } else {
            averageRating = sum / nonNullRatings;
        }

        return averageRating;
    }

    public String getKey() {
        return title.toLowerCase().replaceAll(" ", "-") + "-(" + year + ")";
    }

    public int compareTo(Film film) {
        if (film == null) {
            return 1;
        }
        return this.toString().compareTo(film.toString());
    }

    public String toString() {
        return title + " (" + year + ") by " + director;
    }
   
    public String toJSON() {
        String key = getKey();

        String ratingsString = "";
        for (Map.Entry<String, Float> rating : ratings.entrySet()) {
            ratingsString += "\n\t\t\t\"" + rating + ",\n";
        }
        ratingsString = ratingsString.replaceAll("=", "\": ");
        if (!ratingsString.isEmpty()) {
            ratingsString += "\n\t\t";
        }

        return "\t\"" + key + "\": {\n\t\t\"type\": \"film\",\n\t\t\"title\": \"" + title
                                                          + "\",\n\t\t\"year\": " + year
                                                          + ",\n\t\t\"director\": \"" + director
                                                          + "\",\n\t\t\"ratings\": {"
                                                          + ratingsString
                                                          + "},\n\t\t\"averageRating\": " + averageRating
                                                          + "\n\t}";
    }
}
