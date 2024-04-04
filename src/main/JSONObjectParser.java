package main;

import java.util.LinkedList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONObjectParser {
    public void parse(JSONObject jsonObject) {
        for (Object key : jsonObject.keySet()) {
            JSONObject value = (JSONObject) jsonObject.get((String) key);
            String type = (String) value.get("type");

            switch (type) {
                case "film":
                    parseFilm(value);
                    break;
                case "entry":
                    parseEntry(value);
                    break;
                case "member":
                    parseMember(value);
                    break;
                default:
                    break;
            }
        }
    }

    private Film parseFilm(JSONObject filmJSONObject) {
        String title = (String) filmJSONObject.get("title");
        long year = (long) filmJSONObject.get("year");
        String director = (String) filmJSONObject.get("director");

        HashMap<String, Float> ratings = new HashMap<>();
        JSONObject ratingsJSONObject = (JSONObject) filmJSONObject.get("ratings");
        for (Object memberKey : ratingsJSONObject.keySet()) {
            float rating = (float) ((Long) ratingsJSONObject.get((String) memberKey));
            ratings.put((String) memberKey, rating);
        }

        Film film = new Film(title, (int) year, director, ratings);
        return film;
    }

    private Entry parseEntry(JSONObject entryJSONObject) {
        String memberKey = (String) entryJSONObject.get("member");
        String entryDateKey = (String) entryJSONObject.get("entryDate");
        String filmKey = (String) entryJSONObject.get("film");
        Float rating;
        
        Double ratingValue = (Double) entryJSONObject.get("rating");
        if (ratingValue == null) {
            rating = null;
        } else {
            rating = ratingValue.floatValue();
        }

        String review = (String) entryJSONObject.get("review");
        String watchDateKey = (String) entryJSONObject.get("watchDate");
        boolean rewatch = (boolean) entryJSONObject.get("rewatch");

        JSONArray tagsJSONArray = (JSONArray) entryJSONObject.get("tags");
        LinkedList<String> tags = new LinkedList<String>();
        for (Object tag : tagsJSONArray) {
            tags.add((String) tag);
        }

        Entry entry = new Entry(memberKey, entryDateKey, filmKey, rating, review, watchDateKey, rewatch, tags);
        return entry;
    }

    private Member parseMember(JSONObject memberJSONObject) {
        String username = (String) memberJSONObject.get("username");

        LinkedList<String> diary = new LinkedList<>();
        JSONArray diaryJSONArray = (JSONArray) memberJSONObject.get("diary");
        for (Object entryKey : diaryJSONArray) {
            String entry = (String) entryKey;
            diary.add(entry);
        }
        
        LinkedList<String> watchlist = new LinkedList<>();
        JSONArray watchlistJSONArray = (JSONArray) memberJSONObject.get("watchlist");
        for (Object filmKey : watchlistJSONArray) {
            String film = (String) filmKey;
            watchlist.add(film);
        }

        LinkedList<String> watched = new LinkedList<>();
        JSONArray watchedJSONArray = (JSONArray) memberJSONObject.get("watched");
        for (Object filmKey : watchedJSONArray) {
            String film = (String) filmKey;
            watched.add(film);
        }

        LinkedList<String> likedMovies = new LinkedList<>();
        JSONArray likedMoviesJSONArray = (JSONArray) memberJSONObject.get("likedMovies");
        for (Object filmKey : likedMoviesJSONArray) {
            String film = (String) filmKey;
            likedMovies.add(film);
        }

        String[] favorites = new String[10];
        JSONArray favoritesJSONArray = (JSONArray) memberJSONObject.get("favorites");
        for (int i = 0; i < favoritesJSONArray.size(); i++) {
            String film = (String) favoritesJSONArray.get(i);
            favorites[i] = film;
        }

        return new Member(username, diary, watchlist, watched, likedMovies, favorites);
    }
}
