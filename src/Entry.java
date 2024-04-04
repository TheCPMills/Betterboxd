import java.util.*;

import javafx.beans.property.*;
import javafx.collections.*;
import util.Date;

public class Entry implements Comparable<Entry> {
    private final StringProperty memberKeyProperty;
    private final Property<Date> entryDateProperty;
    private final StringProperty filmKeyProperty;
    private FloatProperty ratingProperty;
    private StringProperty reviewProperty;
    private final Property<Date> watchDateProperty;
    private BooleanProperty rewatchProperty;
    private ListProperty<String> tagsProperty;

    public Entry(String memberKey, String filmKey) {
        this.memberKeyProperty = new SimpleStringProperty(memberKey);
        this.entryDateProperty = new SimpleObjectProperty<Date>(new Date());
        this.filmKeyProperty = new SimpleStringProperty(filmKey);
        setRating(null);
        this.reviewProperty = new SimpleStringProperty(null);
        this.watchDateProperty = new SimpleObjectProperty<Date>(null);
        this.rewatchProperty = new SimpleBooleanProperty(false);
        this.tagsProperty = new SimpleListProperty<String>(FXCollections.observableArrayList());
        markEntry();
    }

    public Entry(String memberKey, String filmKey, Float rating) {
        this.memberKeyProperty = new SimpleStringProperty(memberKey);
        this.entryDateProperty = new SimpleObjectProperty<Date>(new Date());
        this.filmKeyProperty = new SimpleStringProperty(filmKey);
        setRating(rating);
        this.reviewProperty = new SimpleStringProperty(null);
        this.watchDateProperty = new SimpleObjectProperty<Date>(null);
        this.rewatchProperty = new SimpleBooleanProperty(false);
        this.tagsProperty = new SimpleListProperty<String>(FXCollections.observableArrayList());
        markEntry();
    }

    public Entry(String memberKey, String entryDateKey, String filmKey, Float rating) {
        this.memberKeyProperty = new SimpleStringProperty(memberKey);
        this.entryDateProperty = new SimpleObjectProperty<Date>(new Date(entryDateKey));
        this.filmKeyProperty = new SimpleStringProperty(filmKey);
        setRating(rating);
        this.reviewProperty = new SimpleStringProperty(null);
        this.watchDateProperty = new SimpleObjectProperty<Date>(null);
        this.rewatchProperty = new SimpleBooleanProperty(false);
        this.tagsProperty = new SimpleListProperty<String>(FXCollections.observableArrayList());
        markEntry();
    }

    public Entry(String memberKey, String filmKey, Float rating, String review, String watchDateKey, boolean rewatch, LinkedList<String> tags) {
        this.memberKeyProperty = new SimpleStringProperty(memberKey);
        this.entryDateProperty = new SimpleObjectProperty<Date>(new Date());
        this.filmKeyProperty = new SimpleStringProperty(filmKey);
        setRating(rating);
        this.reviewProperty = new SimpleStringProperty(review);

        if (watchDateKey == null) {
            this.watchDateProperty = new SimpleObjectProperty<Date>(null);
        } else {
            this.watchDateProperty = new SimpleObjectProperty<Date>(new Date(watchDateKey + " 00:00:00.000 UTC"));
        }
        
        this.rewatchProperty = new SimpleBooleanProperty(rewatch);
        if (tags == null) {
            this.tagsProperty = new SimpleListProperty<String>(FXCollections.observableArrayList());
        } else {
            this.tagsProperty = new SimpleListProperty<String>(FXCollections.observableArrayList(tags));
        }

        markEntry();
    }

    public Entry(String memberKey, String entryDateKey, String filmKey, Float rating, String review, String watchDateKey, boolean rewatch, LinkedList<String> tags) {
        this.memberKeyProperty = new SimpleStringProperty(memberKey);
        this.entryDateProperty = new SimpleObjectProperty<Date>(new Date(entryDateKey));
        this.filmKeyProperty = new SimpleStringProperty(filmKey);
        setRating(rating);
        this.reviewProperty = new SimpleStringProperty(review);

        if (watchDateKey == null) {
            this.watchDateProperty = new SimpleObjectProperty<Date>(null);
        } else {
            this.watchDateProperty = new SimpleObjectProperty<Date>(new Date(watchDateKey + " 00:00:00.000 UTC"));
        }
        
        this.rewatchProperty = new SimpleBooleanProperty(rewatch);
        if (tags == null) {
            this.tagsProperty = new SimpleListProperty<String>(FXCollections.observableArrayList());
        } else {
            this.tagsProperty = new SimpleListProperty<String>(FXCollections.observableArrayList(tags));
        }

        markEntry();
    }

    private boolean markEntry() {
        UniversalController.entries.put(getKey(), this);
        Member member = getMember();
        return member.addEntry(getKey());
    }

    public Property<Member> getMemberProperty() {
        return new SimpleObjectProperty<Member>(getMember());
    }

    public Member getMember() {
        return UniversalController.members.get(memberKeyProperty.get());
    }

    public Property<Date> getEntryDateProperty() {
        return entryDateProperty;
    }

    public Date getEntryDate() {
        return entryDateProperty.getValue();
    }

    public Property<Film> getFilmProperty() {
        return new SimpleObjectProperty<Film>(getFilm());
    }

    public Film getFilm() {
        return UniversalController.films.get(filmKeyProperty.get());
    }

    public FloatProperty getRatingProperty() {
        return ratingProperty;
    }

    public Float getRating() {
        return ratingProperty.get();
    }

    public boolean setRating(Float rating) {
        this.ratingProperty.set(rating);
        return getFilm().updateRating(memberKeyProperty.get(), rating);
    }

    public StringProperty getReviewProperty() {
        return reviewProperty;
    }

    public String getReview() {
        return reviewProperty.get();
    }

    public void setReview(String review) {
        this.reviewProperty.set(review);
    }

    public Property<Date> getWatchDateProperty() {
        return watchDateProperty;
    }

    public Date getWatchDate() {
        return watchDateProperty.getValue();
    }

    public BooleanProperty getRewatchProperty() {
        return rewatchProperty;
    }

    public boolean isRewatch() {
        return rewatchProperty.get();
    }

    public void setRewatch(boolean rewatch) {
        this.rewatchProperty.set(rewatch);
    }

    public ListProperty<String> getTagsProperty() {
        return tagsProperty;
    }

    public LinkedList<String> getTags() {
        return new LinkedList<>(tagsProperty.get());
    }

    public void setTags(LinkedList<String> tags) {
        this.tagsProperty.set(FXCollections.observableArrayList(tags));
    }

    public String getKey() {
        String memberKey = memberKeyProperty.get();
        Date entryDate = getEntryDate();
        String filmKey = filmKeyProperty.get();

        if (getEntryDate() == null) {
            return filmKey + " " + memberKey;
        } else {
            return filmKey + " " + entryDate.getKey() + " " + memberKey;
        }
    }

    public int compareTo(Entry entry) {
        if (entry == null) {
            return -1;
        }

        if (getEntryDate() == null) {
            return 1;
        }

        return getEntryDate().compareTo(entry.getEntryDate());
    }

    public String toString() {
        String entryString = (isRewatch()) ? "Rewatched " : "Watched ";
        entryString += getFilm() + ":\n";

        Float rating = getRating();
        String review = getReview();
        Date watchDate = getWatchDate();
        LinkedList<String> tags = getTags();

        if (rating != null) {
            entryString += "\tRating: " + rating + "\n";
        }

        if (review != null) {
            entryString += "\tReview: " + review + "\n";
        }
        
        if (watchDate != null) {
            entryString += "\tWatch Date: " + watchDate.format("dd MMM yyyy") + "\n";
        }

        if (!tags.isEmpty()) {
            entryString += "\tTags: " + tags + "\n";
        }

        return entryString;
    }

    public String toJSON() {
        String key = getKey();
        String memberKey = memberKeyProperty.get();
        Date entryDate = getEntryDate();
        String filmKey = filmKeyProperty.get();
        Float rating = getRating();
        String review = getReview();
        Date watchDate = getWatchDate();
        boolean rewatch = isRewatch();
        LinkedList<String> tags = getTags();

        String tagsString = "";

        if (tags.isEmpty()) {
            tagsString = "[]";
        } else {
            tagsString = "[\n";

            for (String tag : tags) {
                tagsString += "\t\t\t\"" + tag + "\",\n";
            }

            tagsString = tagsString.substring(0, tagsString.length() - 2);
            tagsString += "\n\t\t]";
        }

        return "\t\"" + key + "\": {\n\t\t\"type\": \"entry\",\n\t\t\"member\": \"" + memberKey
                                                            + "\",\n\t\t\"entryDate\": \"" + entryDate.getKey()
                                                            + "\",\n\t\t\"film\": \"" + filmKey
                                                            + "\",\n\t\t\"rating\": " + rating
                                                            + ",\n\t\t\"review\": \"" + review
                                                            + "\",\n\t\t\"watchDate\": \"" + watchDate.format("yyyy-MM-dd")
                                                            + "\",\n\t\t\"rewatch\": " + rewatch + ",\n\t\t\"tags\": " + tagsString
                                                            + "\n\t}";
    }
}