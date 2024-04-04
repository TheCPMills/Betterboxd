package main;

import java.util.*;
import main.util.Date;

public class Entry implements Comparable<Entry> {
    private final String memberKey;
    private final Date entryDate;
    private final String filmKey;
    private Float rating;
    private String review;
    private final Date watchDate;
    private boolean rewatch;
    private LinkedList<String> tags;

    public Entry(String memberKey, String filmKey) {
        this.memberKey = memberKey;
        this.entryDate = new Date();
        this.filmKey = filmKey;
        setRating(null);
        this.review = null;
        this.watchDate = null;
        this.rewatch = false;
        this.tags = new LinkedList<>();
        markEntry();
    }

    public Entry(String memberKey, String filmKey, Float rating) {
        this.memberKey = memberKey;
        this.entryDate = new Date();
        this.filmKey = filmKey;
        setRating(rating);
        this.review = null;
        this.watchDate = null;
        this.rewatch = false;
        this.tags = new LinkedList<>();
        markEntry();
    }

    public Entry(String memberKey, String entryDateKey, String filmKey, Float rating) {
        this.memberKey = memberKey;
        this.entryDate = new Date(entryDateKey);
        this.filmKey = filmKey;
        setRating(rating);
        this.review = null;
        this.watchDate = null;
        this.rewatch = false;
        this.tags = new LinkedList<>();
        markEntry();
    }

    public Entry(String memberKey, String filmKey, Float rating, String review, String watchDateKey, boolean rewatch, LinkedList<String> tags) {
        this.memberKey = memberKey;
        this.entryDate = new Date();
        this.filmKey = filmKey;
        setRating(rating);
        this.review = review;
        
        if (watchDateKey == null) {
            this.watchDate = null;
        } else {
            this.watchDate = new Date(watchDateKey + " 00:00:00.000 UTC");
        }

        this.rewatch = rewatch;
        this.tags = tags;

        if (this.tags == null) {
            this.tags = new LinkedList<String>();
        }

        markEntry();
    }

    public Entry(String memberKey, String entryDateKey, String filmKey, Float rating, String review, String watchDateKey, boolean rewatch, LinkedList<String> tags) {
        this.memberKey = memberKey;
        this.entryDate = new Date(entryDateKey);
        this.filmKey = filmKey;
        setRating(rating);
        this.review = review;
        
        if (watchDateKey == null) {
            this.watchDate = null;
        } else {
            this.watchDate = new Date(watchDateKey + " 00:00:00.000 UTC");
        }

        this.rewatch = rewatch;
        this.tags = tags;

        if (this.tags == null) {
            this.tags = new LinkedList<String>();
        }

        markEntry();
    }

    private boolean markEntry() {
        App.entries.put(getKey(), this);
        Member member = getMember();
        return member.addEntry(getKey());
    }

    public Member getMember() {
        return App.members.get(memberKey);
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public Film getFilm() {
        return App.films.get(filmKey);
    }

    public Float getRating() {
        return rating;
    }

    public boolean setRating(Float rating) {
        this.rating = rating;
        return getFilm().updateRating(memberKey, rating);
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getWatchDate() {
        return watchDate;
    }

    public boolean isRewatch() {
        return rewatch;
    }

    public void setRewatch(boolean rewatch) {
        this.rewatch = rewatch;
    }

    public LinkedList<String> getTags() {
        return tags;
    }

    public void setTags(LinkedList<String> tags) {
        this.tags = tags;
    }

    public String getKey() {
        if (entryDate == null) {
            return filmKey + " " + memberKey;
        } else {
            return filmKey + " " + entryDate.getKey() + " " + memberKey;
        }
    }

    public int compareTo(Entry entry) {
        if (entry == null) {
            return -1;
        }

        if (entryDate == null) {
            return 1;
        }

        return entryDate.compareTo(entry.getEntryDate());
    }

    public String toString() {
        String entryString = (rewatch) ? "Rewatched " : "Watched ";
        entryString += getFilm() + ":\n";

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