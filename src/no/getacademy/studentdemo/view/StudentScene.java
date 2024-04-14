package no.getacademy.studentdemo.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import no.getacademy.studentdemo.App;
import no.getacademy.studentdemo.beans.*;

public class StudentScene extends Scene
{
    private StudentGrid         studentGrid;

    public StudentScene(Group root)
    {
        super(root, App.SCREEN_WIDTH, App.SCREEN_HEIGHT); 

        this.studentGrid = new StudentGrid();
        root.getChildren().add(this.studentGrid);
    }

    public void
    activate()
    {
        Student student = App.provider.getStudentByUserId(App.loggedInUser.getId());
        this.studentGrid.activate(student); 
    }
}