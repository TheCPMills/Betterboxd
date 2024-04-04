package test;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import org.json.simple.*;
import org.json.simple.parser.*;

import main.App;
import main.JSONObjectParser;
import main.Member;
import main.util.BetterboxdUtilities;
import main.Entry;
import main.Film;

public class AppTest {
    public static void main(String[] args) throws Exception {
        Member thecpmills = new Member("thecpmills");
        Member iohfr = new Member("iohfr");

        testReadFilms("assets/films.json");
        testWriteFilms();
        testReadFilms("assets/test/films.json");

        testReadEntries("assets/entries.json");
        testWriteEntries();
        testReadEntries("assets/test/entries.json");
        
        testReadMembers("assets/members.json");
        testWriteMembers();
        testReadMembers("assets/test/members.json");

        List<Film> films = BetterboxdUtilities.readFilmsCSV("assets/films.csv");

        List<Entry> entries = BetterboxdUtilities.readRatingsCSV("assets/ratings.csv", "thecpmills");

        // for (Film film : App.films.values()) {
        //     System.out.println(film);
        // }

        // for (Entry entry : App.entries.values()) {
        //     System.out.println(entry);
        // }

        for (Member member : App.members.values()) {
            System.out.println(member);
        }
    }

    private static void testReadFilms(String filmsFile) throws Exception {
        // create a JSON parser
        JSONParser parser = new JSONParser();

        // parse the file
        JSONObject filmsJSONObject = (JSONObject) parser.parse(new FileReader(filmsFile));

        // parse the JSON object
        JSONObjectParser jsonObjectParser = new JSONObjectParser();
        jsonObjectParser.parse(filmsJSONObject);
    }

    private static void testWriteFilms() throws Exception {
        // create a list of films
        HashMap<String, Film> films = new HashMap<>();

        // create the films
        Film theGodfather = new Film("The Godfather", 1972, "Francis Ford Coppola");
        Film pulpFiction = new Film("Pulp Fiction", 1994, "Quentin Tarantino");
        Film theDarkKnight = new Film("The Dark Knight", 2008, "Christopher Nolan");
        Film theShawshankRedemption = new Film("The Shawshank Redemption", 1994, "Frank Darabont");

        // add the films to the list
        films.put(theGodfather.getKey(), theGodfather);
        films.put(pulpFiction.getKey(), pulpFiction);
        films.put(theDarkKnight.getKey(), theDarkKnight);
        films.put(theShawshankRedemption.getKey(), theShawshankRedemption);

        // create a JSON object
        String filmsJSONObject = "{";

        // add the films to the JSON object
        for (Film film : films.values()) {
            filmsJSONObject += "\n" + film.toJSON() + ",\n";
        }

        // replace last comma with a closing bracket
        filmsJSONObject = filmsJSONObject.substring(0, filmsJSONObject.length() - 2) + "\n}";

        // write the JSON object to a file
        FileWriter file = new FileWriter("assets/test/films.json");
        file.write(filmsJSONObject);
        file.close();
    }

    private static void testReadEntries(String entriesFile) throws Exception {
        // create a JSON parser
        JSONParser parser = new JSONParser();

        // parse the file
        JSONObject entriesJSONObject = (JSONObject) parser.parse(new FileReader(entriesFile));

        // parse the JSON object
        JSONObjectParser jsonObjectParser = new JSONObjectParser();
        jsonObjectParser.parse(entriesJSONObject);
    }

    private static void testWriteEntries() throws Exception {
        // create a list of entries
        HashMap<String, Entry> entries = new HashMap<>();

        // create the entries
        Entry pulpFiction = new Entry("iohfr", "pulp-fiction-(1994)", 4.5f, "Great film!", "2020-01-01", false, null);
        Entry theShawshankRedemption = new Entry("iohfr", "the-shawshank-redemption-(1994)", 3.5f, "It's not as good as Gooby.", "2021-02-09", true, new LinkedList<>(Arrays.asList("prison", "escape", "hope")));

        // add the entries to the list
        entries.put(pulpFiction.getKey(), pulpFiction);
        entries.put(theShawshankRedemption.getKey(), theShawshankRedemption);

        // create a JSON object
        String entriesJSONObject = "{";

        // add the entries to the JSON object
        for (Entry entry : entries.values()) {
            entriesJSONObject += "\n" + entry.toJSON() + ",\n";
        }

        // replace last comma with a closing bracket
        entriesJSONObject = entriesJSONObject.substring(0, entriesJSONObject.length() - 2) + "\n}";

        // write the JSON object to a file
        FileWriter file = new FileWriter("assets/test/entries.json");
        file.write(entriesJSONObject);
        file.close();
    }

    private static void testReadMembers(String memberFile) throws Exception {
        // create a JSON parser
        JSONParser parser = new JSONParser();

        // parse the file
        JSONObject memberJSONObject = (JSONObject) parser.parse(new FileReader(memberFile));

        // parse the JSON object
        JSONObjectParser jsonObjectParser = new JSONObjectParser();
        jsonObjectParser.parse(memberJSONObject);
    }

    private static void testWriteMembers() throws Exception {
        // create a list of members
        HashMap<String, Member> members = new HashMap<>();

        // get iohfr member object
        Member iohfr = App.members.get("iohfr");

        // get list of entries
        LinkedList<Entry> entries = new LinkedList<>(App.entries.values());

        for (Entry entry : entries) {
            if (entry.getKey().contains("iohfr")) {
                iohfr.addEntry(entry.getKey());
            }
        }

        // update the members
        iohfr.addFilmToWatchlist("pulp-fiction-(1994)");
        iohfr.addFilmToFavorites("everything-everywhere-all-at-once-(2022)");
        members.put(iohfr.getKey(), iohfr);

        // create a JSON object
        String membersJSONObject = "{";

        // add the members to the JSON object
        for (Member member : members.values()) {
            membersJSONObject += "\n" + member.toJSON() + ",\n";
        }

        // replace last comma with a closing bracket
        membersJSONObject = membersJSONObject.substring(0, membersJSONObject.length() - 2) + "\n}";

        // write the JSON object to a file
        FileWriter file = new FileWriter("assets/test/members.json");
        file.write(membersJSONObject);
        file.close();
    }
}