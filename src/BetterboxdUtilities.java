import java.util.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class BetterboxdUtilities {
    public static <T extends Comparable<? super T>> void sort(LinkedList<T> list) { 
        LinkedList<T> sortedList = new LinkedList<>();
        
        for (T item : list) {
            if (sortedList.isEmpty()) {
                sortedList.add(item);
            } else {
                int sortedListSize = sortedList.size();
                int index = 0;
                while (index <= sortedListSize && sortedList.size() == sortedListSize) {
                    if (item == null || index == sortedListSize) {
                        sortedList.add(item);
                    } else if (item.compareTo(sortedList.get(index)) < 0) {
                        sortedList.add(index, item);
                    }
                    index++;
                }
            }
        }

        list.clear();
        list.addAll(sortedList);
    }

    public static List<Film> readFilmsCSV(String filename) throws IOException, CsvValidationException {
        List<Film> films = new ArrayList<>();

        CSVReader csvReader = new CSVReader(new FileReader(filename)); 

        String[] values = null;
        values = csvReader.readNext(); // skip header
        while ((values = csvReader.readNext()) != null) {
            String year = values[1];
            if (year.equals("")) {
                year = "0";
            }

            Film film = new Film(values[0], Integer.parseInt(year), values[2]);
            films.add(film);
        }

        System.out.println("Read " + films.size() + " films from " + filename);

        csvReader.close();

        return films;
    }

    public static List<Entry> readRatingsCSV(String filename, String memberKey) throws IOException, CsvValidationException {
        List<Entry> entries = new ArrayList<>();

        CSVReader csvReader = new CSVReader(new FileReader(filename));

        String[] values = null;
        values = csvReader.readNext(); // skip header
        while ((values = csvReader.readNext()) != null) {
            Entry entry = new Entry(memberKey, values[0], Float.parseFloat(values[1]));
            entries.add(entry);
        }

        System.out.println("Read " + entries.size() + " entries from " + filename);

        csvReader.close();

        return entries;
    }

    public static float getRating(String filmKey, String memberKey) {
        LinkedList<Entry> entries = new LinkedList<>();

        for (Entry entry : UniversalController.entries.values()) {
            if (entry.getFilm().getKey().equals(filmKey) && entry.getMember().getKey().equals(memberKey)) {
                entries.add(entry);
            }
        }

        BetterboxdUtilities.sort(entries);
        return entries.getLast().getRating();
    }

    public static boolean hasWatchedFilm(String filmKey, String memberKey) {
        return false;
    }
}
