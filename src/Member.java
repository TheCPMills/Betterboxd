import java.util.*;

public class Member {
    private final String username;
    private LinkedList<String> diaryKeys;
    private LinkedList<String> watchlistKeys;
    private LinkedList<String> watchedKeys;
    private LinkedList<String> likedFilmsKeys;
    private String[] favoritesKeys;

    public Member(String username) {
        this.username = username;
        this.diaryKeys = new LinkedList<>();
        this.watchlistKeys = new LinkedList<>();
        this.watchedKeys = new LinkedList<>();
        this.likedFilmsKeys = new LinkedList<>();
        this.favoritesKeys = new String[10];
        addMember();
    }

    public Member(String username, LinkedList<String> diaryKeys, LinkedList<String> watchlistKeys, LinkedList<String> watchedKeys, LinkedList<String> likedFilmsKeys, String[] favoritesKeys) {
        this.username = username;
        this.diaryKeys = new LinkedList<>();
        this.watchlistKeys = new LinkedList<>();
        this.watchedKeys = new LinkedList<>();
        this.likedFilmsKeys = new LinkedList<>();

        if (diaryKeys != null) {
            for (String key : diaryKeys) {
                addEntry(key);
            }
        }

        if (watchlistKeys != null) {
            for (String key : watchlistKeys) {
                addFilmToWatchlist(key);
            }
        }

        if (watchedKeys != null) {
            for (String key : watchedKeys) {
                addFilmTowatched(key);
            }
        }

        if (likedFilmsKeys != null) {
            for (String key : likedFilmsKeys) {
                addFilmToLikedFilms(key);
            }
        }

        this.favoritesKeys = new String[10];
        if (favoritesKeys != null) {
            for (String key : favoritesKeys) {
                addFilmToFavorites(key);
            }
        }

        addMember();
    }

    private void addMember() {
        UniversalController.members.put(username, this);
    }

    public String getUsername() {
        return username;
    }

    public LinkedList<Entry> getDiary() {
        LinkedList<Entry> diary = new LinkedList<>();
        for (String key : diaryKeys) {
            diary.add(UniversalController.entries.get(key));
        }
        BetterboxdUtilities.sort(diary);
        return diary;
    }

    public LinkedList<Film> getWatchlist() {
        LinkedList<Film> watchlist = new LinkedList<>();
        for (String key : watchlistKeys) {
            watchlist.add(UniversalController.films.get(key));
        }
        BetterboxdUtilities.sort(watchlist);
        return watchlist;
    }

    public LinkedList<Film> getwatched() {
        LinkedList<Film> watched = new LinkedList<>();
        for (String key : watchedKeys) {
            watched.add(UniversalController.films.get(key));
        }
        BetterboxdUtilities.sort(watched);
        return watched;
    }

    public LinkedList<Film> getLikedFilms() {
        LinkedList<Film> likedFilms = new LinkedList<>();
        for (String key : likedFilmsKeys) {
            likedFilms.add(UniversalController.films.get(key));
        }
        BetterboxdUtilities.sort(likedFilms);
        return likedFilms;
    }

    public Film[] getFavorites() {
        Film[] favorites = new Film[10];
        for (int i = 0; i < favorites.length; i++) {
            favorites[i] = UniversalController.films.get(favoritesKeys[i]);
        }
        return favorites;
    }

    public boolean addEntry(String entryKey) {
        if (diaryKeys.contains(entryKey)) {
            return false;
        } else {
            diaryKeys.add(entryKey);
            Entry entry = UniversalController.entries.get(entryKey);
            return addFilmTowatched(entry.getFilm().getKey());
        }
    }

    public boolean addFilmToWatchlist(String filmKeys) {
        if (watchlistKeys.contains(filmKeys) || watchedKeys.contains(filmKeys)) {
            return false;
        } else {
            return watchlistKeys.add(filmKeys);
        }
    }

    public boolean addFilmTowatched(String filmKeys) {
        if (watchedKeys.contains(filmKeys)) {
            return false;
        } else {
            return watchedKeys.add(filmKeys) && removeFilmFromWatchlist(filmKeys);
        }
    }

    public boolean addFilmToLikedFilms(String filmKeys) {
        if (likedFilmsKeys.contains(filmKeys) || !watchedKeys.contains(filmKeys)) {
            return false;
        } else {
            return likedFilmsKeys.add(filmKeys);
        }
    }

    public boolean addFilmToFavorites(String filmKey) {
        if (Arrays.asList(favoritesKeys).contains(filmKey) || !watchedKeys.contains(filmKey)) {
            return false;
        } else {
            int indexNull = -1;
            for (int i = 0; i < favoritesKeys.length; i++) {
                if (favoritesKeys[i] == null && indexNull == -1) {
                    indexNull = i;
                }
            }

            if (indexNull != -1) {
                favoritesKeys[indexNull] = filmKey;
                return true;
            }
            return false;
        }
    }

    public boolean addFilmToFavorites(String filmKey, int index) {
        if (!watchedKeys.contains(filmKey)) {
            return false;
        } else if (Arrays.asList(favoritesKeys).contains(filmKey)) {
            removeFilmFromFavorites(filmKey);
        }
        String temp = null;
        int i = 0;
        while (i < favoritesKeys.length && temp == null) {
            if (favoritesKeys[i] == filmKey) {
                if (i < index - 1) {
                    for (; i < index - 1; i++) {
                        temp = favoritesKeys[i + 1];
                        favoritesKeys[i] = favoritesKeys[i + 1];
                    }
                } else if (i > index) {
                    for (; i > index - 1; i--) {
                        temp = favoritesKeys[i - 1];
                        favoritesKeys[i] = favoritesKeys[i - 1];
                    }
                }
            }
            i++;
        }

        if (i == favoritesKeys.length) {
            i--;
            for (; i > index - 1; i--) {
                temp = favoritesKeys[i];
                favoritesKeys[i] = favoritesKeys[i - 1];
            }
        }

        favoritesKeys[index - 1] = filmKey;
        return true;
    }

    public boolean setFavorites(String[] favorites) {
        for (int i = 0; i < this.favoritesKeys.length; i++) {
            this.favoritesKeys[i] = favorites[i];
        }
        return true;
    }

    public boolean removeFilmFromWatchlist(String filmKey) {
        return watchlistKeys.remove(filmKey);
    }

    public boolean removeFilmFromwatched(String filmKey) {
        if (likedFilmsKeys.contains(filmKey)) {
            removeFilmFromLikedFilms(filmKey);
        }
        if (Arrays.asList(favoritesKeys).contains(filmKey)) {
            removeFilmFromFavorites(filmKey);
        }
        return watchedKeys.remove(filmKey);
    }

    public boolean removeFilmFromLikedFilms(String filmKey) {
        return likedFilmsKeys.remove(filmKey);
    }

    public boolean removeFilmFromFavorites(String filmKey) {
        for (int i = 0; i < favoritesKeys.length; i++) {
            if (favoritesKeys[i] == filmKey) {
                return removeFilmFromFavorites(i + 1);
            }
        }
        return false;
    }

    public boolean removeFilmFromFavorites(int index) {
        for (; index < favoritesKeys.length; index++) {
            favoritesKeys[index - 1] = favoritesKeys[index];
        }
        favoritesKeys[favoritesKeys.length - 1] = null;
        return true;
    }

    public String getKey() {
        return username.toLowerCase();
    }

    public String toString() {
        String diaryString = "\tDiary:\n";
        for (Entry entry : getDiary()) {
            if (entry != null) {
                diaryString += "\t\t" + entry.toString().replace("\t", "\t\t\t") + "\n";
            } else {
                diaryString += "\t\t" + "<empty>" + "\n";
            }
        }

        String watchlistString = "\tWatchlist:\n";
        for (Film film : getWatchlist()) {
            if (film != null) {
                watchlistString += "\t\t" + film.toString() + "\n";
            } else {
                watchlistString += "\t\t" + "<empty>" + "\n";
            }
        }

        String watchedString = "\tWatched Films:\n";
        for (Film film : getwatched()) {
            if (film != null) {
                watchedString += "\t\t" + film.toString() + "\n";
            } else {
                watchedString += "\t\t" + "<empty>" + "\n";
            }
        }

        String likedFilmsString = "\tLiked Films:\n";
        for (Film film : getLikedFilms()) {
            if (film != null) {
                likedFilmsString += "\t\t" + film.toString() + "\n";
            } else {
                likedFilmsString += "\t\t" + "<empty>" + "\n";
            }
        }

        String favoritesString = "\tFavorites:\n";
        for (Film film : getFavorites()) {
            if (film != null) {
                favoritesString += "\t\t" + film.toString() + "\n";
            } else {
                favoritesString += "\t\t" + "<empty>" + "\n";
            }
        }

        return username + "\n" + diaryString + watchlistString + watchedString + likedFilmsString + favoritesString;
    }

    public String toJSON() {
        String key = getKey();

        String diaryString = "";
        String watchlistString = "";
        String watchedString = "";
        String likedFilmsString = "";
        String favoritesString = "";

        if (diaryKeys.isEmpty()) {
            diaryString = "[]";
        } else {
            diaryString = "[\n";

            for (String entryKey : diaryKeys) {
                diaryString += "\t\t\t\"" + entryKey + "\",\n";
            }

            diaryString = diaryString.substring(0, diaryString.length() - 2);
            diaryString += "\n\t\t]";
        }

        if (watchlistKeys.isEmpty()) {
            watchlistString = "[]";
        } else {
            watchlistString = "[\n";

            for (String filmKey : watchlistKeys) {
                watchlistString += "\t\t\t\"" + filmKey + "\",\n";
            }

            watchlistString = watchlistString.substring(0, watchlistString.length() - 2);
            watchlistString += "\n\t\t]";
        }

        if (watchedKeys.isEmpty()) {
            watchedString = "[]";
        } else {
            watchedString = "[\n";

            for (String filmKey : watchedKeys) {
                watchedString += "\t\t\t\"" + filmKey + "\",\n";
            }

            watchedString = watchedString.substring(0, watchedString.length() - 2);
            watchedString += "\n\t\t]";
        }

        if (likedFilmsKeys.isEmpty()) {
            likedFilmsString = "[]";
        } else {
            likedFilmsString = "[\n";

            for (String filmKey : likedFilmsKeys) {
                likedFilmsString += "\t\t\t\"" + filmKey + "\",\n";
            }

            likedFilmsString = likedFilmsString.substring(0, likedFilmsString.length() - 2);
            likedFilmsString += "\n\t\t]";
        }

        if (favoritesKeys[0] == null) {
            favoritesString = "[]";
        } else {
            favoritesString = "[\n";

            for (String filmKey : favoritesKeys) {
                if (filmKey != null) {
                    favoritesString += "\t\t\t\"" + filmKey + "\",\n";
                }
            }

            favoritesString = favoritesString.substring(0, favoritesString.length() - 2);
            favoritesString += "\n\t\t]";
        }

        return "\t\"" + key + "\": {\n\t\t\"type\": \"member\",\n\t\t\"username\": \"" + username
                                                             + "\",\n\t\t\"diary\": " + diaryString
                                                             + ",\n\t\t\"watchlist\": " + watchlistString
                                                             + ",\n\t\t\"watched\": " + watchedString
                                                             + ",\n\t\t\"likedMovies\": " + likedFilmsString
                                                             + ",\n\t\t\"favorites\": " + favoritesString
                                                             + "\n\t}";
    }
}