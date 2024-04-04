import sys
import os
import io
import zipfile
import csv
import urllib.request
import re
import time
import operator
import itertools

FILMS_FILENAME = "films.csv"
RATINGS_FILENAME = "ratings.csv"
REQUEST_DELAY_SECONDS = 0.01

def fetchDirector(uri):
    print("Fetching director for '{}'... ".format(uri), end="")

    request = urllib.request.Request(uri)
    request.add_header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")
    response = urllib.request.urlopen(request)
    content = response.read().decode("utf-8")

    match = re.search(r"twitter:data1\" content=\"(.*)\"", content)
    if not match:
        print("FAILED", flush=True)
        sys.exit("Exiting...")

    director = match.group(1)
    print("Director Found:", director)

    return director

def getData(watchedReader, watchlistReader):
    titles = []
    years = []
    directors = []
    uris = []
    
    for entry in watchedReader:
        title = entry[1]
        year = entry[2]
        uri = entry[3]
        
        if uri not in uris:
            uris.append(uri)
            titles.append(title)
            years.append(year)
    
    for entry in watchlistReader:
        title = entry[1]
        year = entry[2]
        uri = entry[3]

        if uri not in uris:
            uris.append(uri)
            titles.append(title)
            years.append(year)

    # print("Processing {} films...".format(len(uris)))

    for uri in uris:
        director = fetchDirector(uri)
        directors.append(director)
        time.sleep(REQUEST_DELAY_SECONDS)
    
    return titles, years, directors

def getRatingData(ratingReader):
    keys = [];
    ratings = [];

    for entry in ratingReader:
        title = entry[1]
        year = entry[2]
        key = title.lower().replace(" ", "-") + "-(" + year + ")"
        rating = entry[4]

        if key not in keys:
            keys.append(key)
            ratings.append(rating)
        
    return keys, ratings

def process(dataFilename):
    # read the data from the csv file
    try:
        with zipfile.ZipFile(dataFilename) as dataFile:
            watchedData = dataFile.read("watched.csv").decode("utf-8")
            watchlistData = dataFile.read("watchlist.csv").decode("utf-8")

            watchedDataReader = csv.reader(io.StringIO(watchedData))
            watchlistReader = csv.reader(io.StringIO(watchlistData))

            next(watchedDataReader, None)
            next(watchlistReader, None)

            titles, years, directors = getData(watchedDataReader, watchlistReader)

            # write the data to the csv file
            with open(FILMS_FILENAME, "w", newline="") as cacheFile:
                cacheWriter = csv.writer(cacheFile)
                cacheWriter.writerow(["Title", "Year", "Director"])
                for title, year, director in zip(titles, years, directors):
                    cacheWriter.writerow([title, year, director])
            
            # ============================================================================

            ratedData = dataFile.read("ratings.csv").decode("utf-8")
            ratedDataReader = csv.reader(io.StringIO(ratedData))
            next(ratedDataReader, None)

            keys, ratings = getRatingData(ratedDataReader)

            # write the data to the csv file
            with open(RATINGS_FILENAME, "a", newline="") as cacheFile:
                cacheWriter = csv.writer(cacheFile)
                cacheWriter.writerow(["Key", "Rating"])
                for key, rating in zip(keys, ratings):
                    cacheWriter.writerow([key, rating])

    except FileNotFoundError:
        print("Could not open file: '{}'".format(data_filename), file=sys.stderr)
    except (zipfile.BadZipFile, KeyError):
        print("Invalid data file: '{}'".format(data_filename), file=sys.stderr)



def main(argv=None):
    data_filename = "data.zip"
    process(data_filename)


if __name__ == "__main__":
    main()
