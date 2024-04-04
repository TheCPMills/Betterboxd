import java.util.*;

import javafx.beans.property.*;
import javafx.collections.*;

public class Film implements Comparable<Film> {
    private final StringProperty titleProperty;
    private final Property<Integer> yearProperty;
    private final StringProperty directorProperty;
    private final MapProperty<String, Float> ratingsProperty;
    private Property<Float> averageRatingProperty;

    public Film(String title, int year, String director) {
        this.titleProperty = new SimpleStringProperty(title);
        this.yearProperty = new SimpleObjectProperty<Integer>(year);
        this.directorProperty = new SimpleStringProperty(director);
        this.ratingsProperty = new SimpleMapProperty<String, Float>(FXCollections.observableHashMap());
        this.averageRatingProperty = new SimpleObjectProperty<Float>(null);
        addFilm();
    }

    public Film(String title, int year, String director, HashMap<String, Float> ratings) {
        this.titleProperty = new SimpleStringProperty(title);
        this.yearProperty = new SimpleObjectProperty<Integer>(year);
        this.directorProperty = new SimpleStringProperty(director);
        this.ratingsProperty = new SimpleMapProperty<String, Float>(FXCollections.observableMap(ratings));
        computeAverageRating();
        addFilm();
    }

    private void addFilm() {
        UniversalController.films.put(getKey(), this);
    }

    public StringProperty titleProperty() {
        return titleProperty;
    }

    public String getTitle() {
        return titleProperty.get();
    }

    public Property<Integer> yearProperty() {
        return yearProperty;
    }

    public int getYear() {
        return yearProperty.getValue();
    }

    public StringProperty directorProperty() {
        return directorProperty;
    }

    public String getDirector() {
        return directorProperty.get();
    }

    public MapProperty<String, Float> ratingsProperty() {
        return ratingsProperty;
    }

    public HashMap<String, Float> getRatings() {
        return new HashMap<String, Float>(ratingsProperty);
    }

    public Property<Float> averageRatingProperty() {
        return averageRatingProperty;
    }

    public float getAverageRating() {
        return averageRatingProperty.getValue();
    }

    public boolean updateRating(String memberKey, Float rating) {
        if (rating == null) {
            ratingsProperty.remove(memberKey);
        } else {
            ratingsProperty.put(memberKey, rating);
        }
        computeAverageRating();
        return true;
    }

    public Float computeAverageRating() {
        HashMap<String, Float> ratings = getRatings();
        Float averageRating;

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
        return getTitle().toLowerCase().replaceAll(" ", "-") + "-(" + getYear() + ")";
    }

    public int compareTo(Film film) {
        if (film == null) {
            return 1;
        }
        return this.toString().compareTo(film.toString());
    }

    public String toString() {
        return getTitle() + " (" + getYear() + ") by " + getDirector();
    }
   
    public String toJSON() {
        String key = getKey();
        String title = getTitle();
        int year = getYear();
        String director = getDirector();
        HashMap<String, Float> ratings = getRatings();
        Float averageRating = getAverageRating();

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
