package no.getacademy.studentdemo.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.layout.GridPane;
import no.getacademy.studentdemo.App;
import no.getacademy.studentdemo.beans.*;

public class StudentScene extends Scene
{
    private Text studentIdTx, studentNameTx;

    public StudentScene(Group root)
    {
        super(root, App.SCREEN_WIDTH, App.SCREEN_HEIGHT); 

        root.getChildren().add(this.buildGrid());
    }

    private GridPane
    buildGrid()
    {
        GridPane grid = new GridPane();

        grid.add(new Text("Id:"), 0, 1);

        this.studentIdTx = new Text();
        grid.add(studentIdTx, 1, 1);  

        grid.add(new Text("Navn:"), 0, 2);

        this.studentNameTx = new Text();
        grid.add(studentNameTx, 1, 2);  
        
        return grid;
    }

    public void
    activate()
    {
        Student student = App.provider.getStudentByUserId(App.loggedInUser.getId());
        this.studentIdTx.setText(Integer.toString(student.getUserId())); 
        this.studentNameTx.setText(student.getName()); 
    }
}