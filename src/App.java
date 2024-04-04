import java.io.File;
import java.util.*;

import org.controlsfx.control.Rating;

import com.opencsv.CSVParser;

import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class App extends Application {
    private static Stage primaryStage;
    private static Scene index, filmDatabase, filmAdder, memberProfileViewer, memberAdder, diaryScene, entryAdder;
    private static LinkedList<Film> films = new LinkedList<Film>();
    private static LinkedList<Member> members = new LinkedList<Member>();

    @Override public void start(Stage stage) {
        // populate films
        File filmFile = new File("films.csv");
        try {
            CSVParser parser = new CSVParser();
            Scanner scanner = new Scanner(filmFile);
            scanner.nextLine(); // skip header
            while (scanner.hasNextLine()) {
                String[] line = parser.parseLine(scanner.nextLine());
                String title = line[0];
                int year = Integer.parseInt(line[1]);
                String director = line[2];
                films.add(new Film(title, year, director));
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        
        primaryStage = stage;
        changeScene(Backdrop.INDEX);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }
    
    private static ToolBar createToolBar() {
        ToolBar toolbar = new ToolBar();
        toolbar.setPrefSize(1504, 40);
        toolbar.setLayoutX(0);
        toolbar.setLayoutY(0);

        Button filmDatabaseButton = new Button("Film Database");
        filmDatabaseButton.setOnAction(e -> {
            changeScene(Backdrop.FILM_DATABASE);
        });

        Button filmAdderButton = new Button("Add Film");
        filmAdderButton.setOnAction(e -> {
            changeScene(Backdrop.FILM_ADDER);
        });

        Button memberProfileViewerButton = new Button("Profile Viewer");
        memberProfileViewerButton.setOnAction(e -> {
            changeScene(Backdrop.MEMBER_PROFILE_VIEWER);
        });

        Button memberAdderButton = new Button("Add Member");
        memberAdderButton.setOnAction(e -> {
            changeScene(Backdrop.MEMBER_ADDER);
        });

        Button diaryButton = new Button("Diary");
        diaryButton.setOnAction(e -> {
            changeScene(Backdrop.DIARY);
        });

        Button entryAdderButton = new Button("Add Entry");
        entryAdderButton.setOnAction(e -> {
            changeScene(Backdrop.ENTRY_ADDER);
        });

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button closeButton = new Button("X");
        closeButton.setOnMouseEntered(e -> {
            closeButton.setStyle("-fx-background-color: #ff0020;");
            closeButton.setTextFill(Color.WHITE);
        });
        closeButton.setOnMouseExited(e -> {
            closeButton.setStyle("-fx-background-color: #eeeeee;");
            closeButton.setTextFill(Color.BLACK);
        });
        closeButton.setOnAction(e -> {
            primaryStage.close();
        });

        toolbar.getItems().addAll(filmDatabaseButton, filmAdderButton, memberProfileViewerButton, memberAdderButton, diaryButton, entryAdderButton, spacer, closeButton);
        return toolbar;
    }

    private static void createIndex() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1504, 1002.66666667);

        ToolBar toolbar = createToolBar();
        root.getChildren().add(toolbar);

        index = new Scene(root);
    }

    private static void createFilmDatabase() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1504, 1002.66666667);

        ToolBar toolbar = createToolBar();

        Pane activePane = new Pane();
        activePane.setPrefSize(1128, 752);
        activePane.setLayoutX((root.getPrefWidth() - toolbar.getWidth()) / 2 - activePane.getPrefWidth() / 2);
        activePane.setLayoutY((root.getPrefHeight() - toolbar.getHeight()) / 2 - activePane.getPrefHeight() / 2);

        Text header = new Text("Film Database");
        header.setFont(new Font(30));
        header.textAlignmentProperty().set(TextAlignment.CENTER);
        header.textOriginProperty().set(VPos.CENTER);
        header.wrappingWidthProperty().bind(activePane.widthProperty()); // center header to toolbar
        
        // TableView<Film> table = new TableView<>();
        // table.setPrefSize(1128, 671);
        // table.setLayoutY(20);

        // TableColumn<Film, String> titleColumn = new TableColumn<>("Title");
        // titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        // titleColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.5975));

        // TableColumn<Film, Integer> yearColumn = new TableColumn<>("Year");
        // yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        // yearColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.045));
        // yearColumn.setStyle("-fx-alignment: CENTER;");

        // TableColumn<Film, String> directorColumn = new TableColumn<>("Director");
        // directorColumn.setCellValueFactory(new PropertyValueFactory<>("director"));
        // directorColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.245));

        // TableColumn<Film, Float> averageRatingColumn = new TableColumn<>("Average Rating");
        // averageRatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        // averageRatingColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.0975));
        // averageRatingColumn.setResizable(false);
        // averageRatingColumn.setStyle("-fx-alignment: CENTER;");
        // averageRatingColumn.setCellFactory(column -> {
        //     return new TableCell<Film, Float>() {
        //         @Override
        //         protected void updateItem(Float item, boolean empty) {
        //             super.updateItem(item, empty);
        //             if (item == null && !empty) {
        //                 setText("-");
        //             } else {
        //                 setText(item == null ? null : String.format("%.2f", item));
        //             }
        //         }
        //     };
        // });

        // table.getColumns().add(titleColumn);
        // table.getColumns().add(yearColumn);
        // table.getColumns().add(directorColumn);
        // table.getColumns().add(averageRatingColumn);

        // for (Film film : films) {
        //     table.getItems().add(film);
        // }

        Image poster = new Image("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/6CoRTJTmijhBLJTUNoVSUNxZMEI.jpg");
        ImageView posterView = new ImageView(poster);
        posterView.setFitWidth(300);
        posterView.setPreserveRatio(true);
        posterView.setLayoutX((activePane.getPrefWidth() - posterView.getFitWidth()) / 2);
        posterView.setLayoutY(150);

        Button watchedButton = new Button("Watched");
        Button likButton = new Button("Like");
        Button watchlistButton = new Button("Watchlist");

        // 5 star rating system with half stars
        Rating rating = new Rating();
        rating.setPartialRating(true);
        rating.setPrefWidth(300);
        rating.setLayoutX((activePane.getPrefWidth() - rating.getPrefWidth()) / 2);
        rating.setLayoutY(700);
        rating.ratingProperty().addListener((observable, oldValue, newValue) -> {
            double ratingValue = newValue.doubleValue();
            // round to nearest half star
            ratingValue = Math.round(ratingValue * 2) / 2.0;
            ratingValue = Math.max(0, ratingValue);
            ratingValue = Math.min(5, ratingValue);
            rating.setRating(ratingValue);
        });
        

        ComboBox<Film> filmSelector = new ComboBox<>();
        filmSelector.setPromptText("Select Film");
        filmSelector.setPrefWidth(673.98);
        filmSelector.setLayoutX((activePane.getPrefWidth() - filmSelector.getPrefWidth()) / 2);
        filmSelector.setLayoutY(65);
        for (Film film : films) {
            filmSelector.getItems().add(film);
        }
        filmSelector.setOnAction(e -> {

        });

        Pane searchPane = new Pane();
        searchPane.setPrefSize(350, 10);
        searchPane.setLayoutX((activePane.getPrefWidth() - searchPane.getPrefWidth()) / 2);
        searchPane.setLayoutY(30);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search");
        searchBar.setPrefWidth(200);

        Button searchButton = new Button("Search");
        searchButton.setPrefWidth(100);
        searchButton.setLayoutX(210);
        searchButton.setOnAction(e -> {
            filmSelector.getItems().clear();
            String query = searchBar.getText();
            for (Film film : films) {
                if (film.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    // table.scrollTo(film);
                    // table.getSelectionModel().select(film);
                    // break;
                    filmSelector.getItems().add(film);
                }
            }
        });

        searchPane.getChildren().add(searchBar);
        searchPane.getChildren().add(searchButton);

        activePane.getChildren().add(header);
        // activePane.getChildren().add(table);
        activePane.getChildren().add(posterView);
        // activePane.getChildren().add(watchedButton);
        // activePane.getChildren().add(likButton);
        // activePane.getChildren().add(watchlistButton);
        activePane.getChildren().add(rating);
        activePane.getChildren().add(filmSelector);
        activePane.getChildren().add(searchPane);

        root.getChildren().add(toolbar);
        root.getChildren().add(activePane);

        filmDatabase = new Scene(root);
    }

    private static void createFilmAdder() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1504, 1002.66666667);

        ToolBar toolbar = createToolBar();

        Pane activePane = new Pane();
        activePane.setPrefSize(1128, 752);
        activePane.setLayoutX((root.getPrefWidth() - toolbar.getWidth()) / 2 - activePane.getPrefWidth() / 2);
        activePane.setLayoutY((root.getPrefHeight() - toolbar.getHeight()) / 2 - activePane.getPrefHeight() / 2);

        Text header = new Text("Add Film");
        header.setFont(new Font(30));
        header.textAlignmentProperty().set(TextAlignment.CENTER);
        header.textOriginProperty().set(VPos.CENTER);
        header.wrappingWidthProperty().bind(activePane.widthProperty()); // center header to toolbar

        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        titleField.setPrefWidth(300);
        titleField.setLayoutX((activePane.getPrefWidth() - titleField.getPrefWidth()) / 2);
        titleField.setLayoutY(30);

        TextField yearField = new TextField();
        yearField.setPromptText("Year");
        yearField.setPrefWidth(300);
        yearField.setLayoutX((activePane.getPrefWidth() - yearField.getPrefWidth()) / 2);
        yearField.setLayoutY(60);

        TextField directorField = new TextField();
        directorField.setPromptText("Director");
        directorField.setPrefWidth(300);
        directorField.setLayoutX((activePane.getPrefWidth() - directorField.getPrefWidth()) / 2);
        directorField.setLayoutY(90);

        Button addButton = new Button("Add");
        addButton.setPrefWidth(100);
        addButton.setLayoutX((activePane.getPrefWidth() - addButton.getPrefWidth()) / 2);
        addButton.setLayoutY(120);
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            int year = Integer.parseInt(yearField.getText());
            String director = directorField.getText();
            films.add(new Film(title, year, director));
            titleField.clear();
            yearField.clear();
            directorField.clear();
        });

        activePane.getChildren().add(header);
        activePane.getChildren().add(titleField);
        activePane.getChildren().add(yearField);
        activePane.getChildren().add(directorField);
        activePane.getChildren().add(addButton);

        root.getChildren().add(toolbar);
        root.getChildren().add(activePane);

        filmAdder = new Scene(root);
    }

    private static void createMemberProfileViewer() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1504, 1002.66666667);

        ToolBar toolbar = createToolBar();

        Pane activePane = new Pane();
        activePane.setPrefSize(1128, 752);
        activePane.setLayoutX((root.getPrefWidth() - toolbar.getWidth()) / 2 - activePane.getPrefWidth() / 2);
        activePane.setLayoutY((root.getPrefHeight() - toolbar.getHeight()) / 2 - activePane.getPrefHeight() / 2);

        Text header = new Text("Profile Viewer");
        header.setFont(new Font(30));
        header.textAlignmentProperty().set(TextAlignment.CENTER);
        header.textOriginProperty().set(VPos.CENTER);
        header.wrappingWidthProperty().bind(activePane.widthProperty()); // center header to toolbar

        ComboBox<String> memberSelector = new ComboBox<>();
        memberSelector.setPromptText("Select Member");
        memberSelector.setPrefWidth(300);
        memberSelector.setLayoutX((activePane.getPrefWidth() - memberSelector.getPrefWidth()) / 2);
        memberSelector.setLayoutY(30);
        for (Member member : members) {
            memberSelector.getItems().add(member.getUsername());
        }

        activePane.getChildren().add(header);
        activePane.getChildren().add(memberSelector);

        root.getChildren().add(toolbar);
        root.getChildren().add(activePane);

        memberProfileViewer = new Scene(root);
    }

    private static void createMemberAdder() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1504, 1002.66666667);

        ToolBar toolbar = createToolBar();

        root.getChildren().add(toolbar);
        // root.getChildren().add();

        memberAdder = new Scene(root);
    }

    private static void createEntryAdder() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1504, 1002.66666667);

        ToolBar toolbar = createToolBar();

        root.getChildren().add(toolbar);
        // root.getChildren().add();

        entryAdder = new Scene(root);
    }

    private static void createDiary() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(1504, 1002.66666667);

        ToolBar toolbar = createToolBar();

        root.getChildren().add(toolbar);
        // root.getChildren().add();

        diaryScene = new Scene(root);
    }

    private static void changeScene(Backdrop backdrop) {
        Scene scene = new Scene(new AnchorPane());
        switch (backdrop) {
            case INDEX:
                createIndex();
                scene = index;
                break;
            case FILM_DATABASE:
                createFilmDatabase();
                scene = filmDatabase;
                break;
            case FILM_ADDER:
                createFilmAdder();
                scene = filmAdder;
                break;
            case MEMBER_PROFILE_VIEWER:
                createMemberProfileViewer();
                scene = memberProfileViewer;
                break;
            case MEMBER_ADDER:
                createMemberAdder();
                scene = memberAdder;
                break;
            case DIARY:
                createDiary();
                scene = diaryScene;
                break;
            case ENTRY_ADDER:
                createEntryAdder();
                scene = entryAdder;
                break;
        }

        primaryStage.setScene(scene);
    }

    static enum Backdrop {
        INDEX, FILM_DATABASE, FILM_ADDER, MEMBER_PROFILE_VIEWER, MEMBER_ADDER, DIARY, ENTRY_ADDER;
    }
}
