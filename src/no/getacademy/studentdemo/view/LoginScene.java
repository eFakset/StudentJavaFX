package no.getacademy.studentdemo.view;

import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import no.getacademy.studentdemo.App;
import no.getacademy.studentdemo.beans.*;

public class LoginScene extends Scene
{
    public LoginScene(Group root)
    {
        super(root, 300, 250); 

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Studentdemo");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        grid.add(new Label("Brukernavn:"), 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        grid.add(new Label("Passord:"), 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Log inn");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actionTarget = new Text();
        actionTarget.setFill(Color.FIREBRICK);
        grid.add(actionTarget, 1, 6);
        root.getChildren().add(grid);

        userTextField.setText("emmanuel");
        pwBox.setText("kant");

        btn.setOnAction(new EventHandler<ActionEvent>()
		{
            @Override
            public void handle(ActionEvent e)
			{
                String userName = userTextField.getText();
                String password = pwBox.getText();

                if (userName == null || userName.trim().length() == 0
                    || password == null || password.trim().length() == 0)
                {
                    actionTarget.setText("Brukernavn og passord\nmå fylles ut!");    
                }
                else
                {
                    User user = App.provider.logIn(userName, password);
                    if (user == null)
                        actionTarget.setText("Ugyldig brukernavn\nog/eller passord");
                    else
                    {
                        actionTarget.setText("Pålogget som " + userName);
                        App.loggedInUser.nameProperty().set(userName);
                        App.loggedInUser.idProperty().set(user.getId());
                        App.loggedInUser.userTypeIdProperty().set(user.getUserTypeId());
                    }
                }
            }
        });
    }
}
