package no.getacademy.studentdemo.view;

import java.util.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import no.getacademy.studentdemo.*;
import no.getacademy.studentdemo.beans.*;
import no.getacademy.studentdemo.util.*;

public class TeacherScene extends Scene 
{
    private ComboBox<Teacher> teacherCb;
    private ComboBox<Level> levelCb;
    private TreeSet<Student> students;
    private ObservableList<Student> displayStudents;
    private StudentGrid studentGrid;

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

        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(2);
        tilePane.getChildren().add(mainPane);
        this.studentGrid = new StudentGrid();
//        studentGrid.setBorder(new Border(new BorderStroke(Color.BLACK ,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        tilePane.getChildren().add(studentGrid);
        
        this.fillCBs();

        root.getChildren().add(tilePane);
    }

    private void
    fillCBs()
    {
        this.teacherCb.getItems().add(new Teacher()); // "navn" = ...
        this.teacherCb.getItems().addAll(App.provider.getTeachers());

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

        this.levelCb.getItems().add(new Level(0, "...")); // "navn" = ...
        this.levelCb.getItems().addAll(App.provider.getLevels());
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
    }

    private TableView<Student>
    createTableView()
    {
        this.students = new TreeSet<>();

        this.displayStudents = FXCollections.observableArrayList(this.students);

        TableView<Student> tableView = new TableView<>(displayStudents);

        TableColumn<Student, Integer> idCol = new TableColumn<>("Nr");
        idCol.setCellValueFactory(new PropertyValueFactory<>(PropertyConstants.IDPROPERTY_NAME));
        TableColumn<Student, String> nameCol = new TableColumn<>("Navn");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(PropertyConstants.NAMEPROPERTY_NAME));
        TableColumn<Student, String> mailIdCol = new TableColumn<>("Mail");
        mailIdCol.setCellValueFactory(new PropertyValueFactory<>(PropertyConstants.MAILIDPROPERTY_NAME));
        TableColumn<Student, String> discordNameCol = new TableColumn<>("Discord");
        discordNameCol.setCellValueFactory(new PropertyValueFactory<>(PropertyConstants.DISCORDNAMEPROPERTY_NAME));
        TableColumn<Student, String> gitHubNameCol = new TableColumn<>("GitHub");
        gitHubNameCol.setCellValueFactory(new PropertyValueFactory<>(PropertyConstants.GITHUBNAMEPROPERTY_NAME));
        TableColumn<Student, String> levelNameCol = new TableColumn<>("Nivå");
        levelNameCol.setCellValueFactory(new PropertyValueFactory<>(PropertyConstants.STUDENT_LEVELNAMEPROPERTY_NAME));

        tableView.getColumns().setAll(idCol, nameCol, mailIdCol, discordNameCol, gitHubNameCol, levelNameCol);  
        
        tableView.setRowFactory( tv -> 
        {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> 
            {
                if (event.getButton().equals(MouseButton.PRIMARY) && !row.isEmpty()) 
                {
                    Student student = row.getItem();
                    studentGrid.activate(student);
                }
            });
            return row ;
        });

        return tableView;
    }

    public void
    activate()
    {
        Teacher teacher = App.provider.getTeacherByUserId(App.loggedInUser.getId());

        this.teacherCb.getSelectionModel().select(teacher);
        this.students = App.provider.getStudents(teacherCb.getSelectionModel().getSelectedItem().getId(), 0);
        this.displayStudents.setAll(students);
    }
}
