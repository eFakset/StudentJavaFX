package no.getacademy.studentdemo.view;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.event.*;

import java.util.TreeMap;
import java.util.TreeSet;
import java.net.URI;
import java.awt.Desktop;

import javafx.geometry.*;
import no.getacademy.studentdemo.App;
import no.getacademy.studentdemo.beans.*;

public class StudentGrid extends GridPane
{
    private final Label document2Lb;
    private final Text studentIdTx, levelTx, levelFromTx, teacherNKTx, teacherITTx;
    private final TextField studentNameTxf, mailTxf, discordTxf, githubTxf;
    private final ComboBox<Level> levelCb;
    private final ComboBox<Teacher> teacherNKCb, teacherITCb;
    private final TextField document1Txf, document2Txf;
    private final Button document1Bn, document2Bn;

    private final TreeSet<Teacher> teachers;

    public StudentGrid()
    {
        this.setAlignment(Pos.TOP_LEFT);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        this.add(new Label("Id:"), 0, 1);
        this.studentIdTx = new Text();
        this.add(studentIdTx, 1, 1);  

        this.add(new Label("Navn:"), 0, 2);
        this.studentNameTxf = new TextField();
        this.add(studentNameTxf, 1, 2); 
        
        this.add(new Label("Nivå:"), 0, 3);
        this.levelTx = new Text();
        this.levelCb = new ComboBox<>();
        this.add(this.levelTx, 1, 3);
        this.add(this.levelCb, 1, 3);
        this.add(new Label("Siden:"), 2, 3);
        this.levelFromTx = new Text();
        this.add(this.levelFromTx, 3, 3);

        this.levelCb.getItems().add(new Level(0, "...")); // "navn" = ...
        this.levelCb.getItems().addAll(App.provider.getLevels());
        this.levelCb.getSelectionModel().selectFirst();

        this.add(new Label("Discord:"), 0, 4);
        this.discordTxf = new TextField();
        this.add(discordTxf, 1, 4); 

        this.add(new Label("GitHub:"), 0, 5);
        this.githubTxf = new TextField();
        this.add(githubTxf, 1, 5); 

        this.add(new Label("Mailadresse:"), 0, 6);
        this.mailTxf = new TextField();
        this.add(mailTxf, 1, 6, 3, 1); 

        this.add(new Label("Fadder NK:"), 0, 7);
        this.teacherNKTx = new Text();
        this.teacherNKCb = new ComboBox<>();
        this.add(teacherNKTx, 1, 7); 
        this.add(teacherNKCb, 1, 7); 

        this.add(new Label("Fadder IT:"), 0, 8);
        this.teacherITTx = new Text();
        this.teacherITCb = new ComboBox<>();
        this.add(teacherITTx, 1, 8); 
        this.add(teacherITCb, 1, 8); 

        this.add(new Label("Opplæringsplan:"), 0, 9);
        this.document1Txf = new TextField();
        this.document1Txf.setMinWidth(300);
        this.add(document1Txf, 1, 9, 3, 1); 
        this.document1Bn = new Button("Åpne");
        this.add(document1Bn, 5, 9); 

        this.document2Lb = new Label("Kontaktpunkt:");
        this.add(this.document2Lb, 0, 10);
        this.document2Txf = new TextField();
        this.add(document2Txf, 1, 10, 3, 1); 
        this.document2Bn = new Button("Åpne");
        this.add(document2Bn, 5, 10); 

// todo Flytte til noe a la addListeners()

        document1Bn.setOnAction(new EventHandler<ActionEvent>()
		{
            @Override
            public void handle(ActionEvent ae)
			{
                String trimmedRef = document1Txf.getText().trim();
                try
                {
                    Desktop.getDesktop().browse(URI.create(trimmedRef));
                }
                catch (Exception e)
                {
                    System.err.println("Her gikk noe galt!");
                }
   			}
        });

        document2Bn.setOnAction(new EventHandler<ActionEvent>()
		{
            @Override
            public void handle(ActionEvent ae)
			{
                String trimmedRef = document2Txf.getText().trim();
                try
                {
                    Desktop.getDesktop().browse(URI.create(trimmedRef));
                }
                catch (Exception e)
                {
                    System.err.println("Her gikk noe galt!");
                }
   			}
        });

        this.teachers = App.provider.getTeachers();
        this.fillCbs();
    }

    private void
    fillCbs()
    {
// todo CB for Teachers kan gjenbrukes        
        this.teacherNKCb.getItems().add(new Teacher()); // "navn" = ...
        this.teacherNKCb.getItems().addAll(this.teachers);
        this.teacherITCb.getItems().add(new Teacher()); // "navn" = ...
        this.teacherITCb.getItems().addAll(this.teachers);
    }

    public void
    activate(Student student)
    {
        this.studentIdTx.setText(Integer.toString(student.getUserId())); 
        this.studentNameTxf.setText(student.getName()); 

        TreeMap<Integer, Contact> contacts = App.provider.getContacts(student);
        Contact contactNK = contacts.get(2);
        int teacherNKId = contactNK.getTeacherId();
        Contact contactIT = contacts.get(1);
        int teacherITId = contactIT.getTeacherId();

        TreeMap<Integer, Document> documents = App.provider.getDocuments(student);
        String url1 = "", url2 = "";
        Document document1 = documents.get(1);
        if (document1 != null)
            url1 = document1.getName();
        Document document2 = documents.get(2);
        if (document2 != null)
            url2 = document2.getName();

        if (App.loggedInUser.getUserTypeId() == 1)
        {
            this.levelCb.setVisible(false);
            this.levelTx.setText(student.getStudentLevelName()); 
            this.teacherNKCb.setVisible(false);
            this.teacherNKTx.setText(contactNK.getTeacherName()); 
            this.teacherITCb.setVisible(false);
            this.teacherITTx.setText(contactIT.getTeacherName()); 

            this.document1Txf.setEditable(false);
            this.document1Txf.setText(url1);
            this.document2Lb.setVisible(false);
            this.document2Txf.setVisible(false);
            this.document2Bn.setVisible(false);
        }
        else
        {
            this.levelTx.setVisible(false);
            this.levelCb.getSelectionModel().select(new Level(student.getLevelId(), student.getStudentLevelName()));
// todo Forenkle dette!  Egen, forenklet const for Teacher?  Trenger jeg this.teachers? 
            this.teacherNKTx.setVisible(false);
            this.teacherNKCb.getSelectionModel().select(new Teacher(-1, 2, teacherNKId, contactNK.getTeacherName(), "N/A", "N/A"));
            this.teacherITTx.setVisible(false);
            this.teacherITCb.getSelectionModel().select(new Teacher(-1, 2, teacherITId, contactIT.getTeacherName(), "N/A", "N/A"));
            this.document1Txf.setText(url1);
            this.document2Txf.setText(url2);
        }
        this.levelFromTx.setText(student.getFromDate().toString());

        this.discordTxf.setText(student.getDiscordName()); 
        this.githubTxf.setText(student.getGitHubNameProperty()); 
        this.mailTxf.setText(student.getMailId()); 
    }
}
