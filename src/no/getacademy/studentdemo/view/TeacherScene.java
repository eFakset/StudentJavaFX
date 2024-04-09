package no.getacademy.studentdemo.view;

import java.util.*;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import no.getacademy.studentdemo.*;
import no.getacademy.studentdemo.beans.*;

public class TeacherScene extends Scene 
{
    private ComboBox<Teacher> teacherCb;
    private ComboBox<Level> levelCb;
    private TreeSet<Student> students;
    private ObservableList<Student> displayStudents;

    public TeacherScene(Group root)
    {
        super(root, App.SCREEN_WIDTH, App.SCREEN_HEIGHT);  

        BorderPane mainPane = new BorderPane();

        HBox topBox = new HBox();
        topBox.setPadding(new Insets(15, 12, 15, 12));
        topBox.setSpacing(10);        

        this.teacherCb = new ComboBox<>();
        topBox.getChildren().add(new Text("Lærer:"));
        topBox.getChildren().add(this.teacherCb);

        this.levelCb = new ComboBox<>();
        topBox.getChildren().add(new Text("Nivå:"));
        topBox.getChildren().add(this.levelCb);

        mainPane.setTop(topBox);

        mainPane.setCenter(this.createTableView());
        root.getChildren().add(mainPane);
    }

    private TableView<Student>
    createTableView()
    {
        this.students = new TreeSet<>();

        this.displayStudents = FXCollections.observableArrayList(this.students);

        TableView<Student> tableView = new TableView<>(displayStudents);

        TableColumn<Student, Integer> idCol = new TableColumn<>("Nr");
        idCol.setCellValueFactory(new PropertyValueFactory<>(Student.IDPROPERTY_NAME));
        TableColumn<Student, String> nameCol = new TableColumn<>("Navn");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(Student.NAMEPROPERTY_NAME));
        TableColumn<Student, String> mailIdCol = new TableColumn<>("Mail");
        mailIdCol.setCellValueFactory(new PropertyValueFactory<>(Student.MAILIDPROPERTY_NAME));
        TableColumn<Student, String> discordNameCol = new TableColumn<>("Discord");
        discordNameCol.setCellValueFactory(new PropertyValueFactory<>(Student.DISCORDNAMEPROPERTY_NAME));
        TableColumn<Student, String> gitHubNameCol = new TableColumn<>("GitHub");
        gitHubNameCol.setCellValueFactory(new PropertyValueFactory<>(Student.GITHUBNAMEPROPERTY_NAME));

        tableView.getColumns().setAll(idCol, nameCol, mailIdCol, discordNameCol, gitHubNameCol);        

        return tableView;
    }

    public void
    activate()
    {
        this.teacherCb.getItems().add(new Teacher()); // "navn" = ...
        this.teacherCb.getItems().addAll(App.teachers);

        this.teacherCb.setOnAction(new EventHandler<>() 
        {
            @Override
            public void handle(ActionEvent e)
            {
                students = App.provider.getStudents(teacherCb.getSelectionModel().getSelectedItem().getId(), 
                                                    levelCb.getSelectionModel().getSelectedItem().getId());
                displayStudents.setAll(students);
            }
        });

        TreeSet<Level> levels = App.provider.getLevels();

        this.levelCb.getItems().add(new Level(0, "...")); // "navn" = ...
        this.levelCb.getItems().addAll(levels);
        this.levelCb.getSelectionModel().selectFirst();

        this.levelCb.setOnAction(new EventHandler<>() 
        {
            @Override
            public void handle(ActionEvent e)
            {
                students = App.provider.getStudents(teacherCb.getSelectionModel().getSelectedItem().getId(), 
                                                    levelCb.getSelectionModel().getSelectedItem().getId());
                displayStudents.setAll(students);
            }
        });

        Teacher teacher = App.provider.getTeacherByUserId(App.loggedInUser.getId());

        this.teacherCb.getSelectionModel().select(teacher);
        this.students = App.provider.getStudents(teacherCb.getSelectionModel().getSelectedItem().getId(), 0);
        this.displayStudents.setAll(students);
    }
}
