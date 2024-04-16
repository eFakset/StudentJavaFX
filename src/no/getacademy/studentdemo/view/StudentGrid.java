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
    private       Student currentStudent;

    private final Label document2Lb, document3Lb;
    private final Text studentIdTx, levelTx, teamNoTx, levelFromTx, teacherNKTx, teacherITTx, lastUpdatedTx, lastUpdatedByTx;
    private final TextField studentNameTxf, mailTxf, discordTxf, githubTxf, companyTxf;
    private final ComboBox<Level> levelCb;
    private final ComboBox<Teacher> teacherNKCb, teacherITCb;
    private final TextField document1Txf, document2Txf, document3Txf;
    private final Button document1Bn, document2Bn, document3Bn, okBn;

    private final TreeSet<Teacher> teachers;

    public StudentGrid()
    {
        this.setAlignment(Pos.TOP_LEFT);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        int rowNo = 1;
        this.add(new Label("Id:"), 0, rowNo);
        this.studentIdTx = new Text();
        this.add(studentIdTx, 1, rowNo);  
        rowNo++;

        this.add(new Label("Navn:"), 0, rowNo);
        this.studentNameTxf = new TextField();
        this.add(studentNameTxf, 1, rowNo); 
        rowNo++;
        
        this.add(new Label("Nivå:"), 0, rowNo);
        this.levelTx = new Text();
        this.levelCb = new ComboBox<>();
        this.add(this.levelTx, 1, rowNo);
        this.add(this.levelCb, 1, rowNo);
        this.add(new Label("Siden:"), 2, rowNo);
        this.levelFromTx = new Text();
        this.add(this.levelFromTx, 3, rowNo);

        this.levelCb.getItems().add(new Level(0, "...")); // "navn" = ...
        this.levelCb.getItems().addAll(App.provider.getLevels());
        this.levelCb.getSelectionModel().selectFirst();
        rowNo++;

        this.add(new Label("Team:"), 0, rowNo);
        this.teamNoTx = new Text();
        this.add(teamNoTx, 1, rowNo);  
        rowNo++;

        this.add(new Label("Discord:"), 0, rowNo);
        this.discordTxf = new TextField();
        this.add(discordTxf, 1, rowNo); 
        rowNo++;

        this.add(new Label("GitHub:"), 0, rowNo);
        this.githubTxf = new TextField();
        this.add(githubTxf, 1, rowNo); 
        rowNo++;

        this.add(new Label("Mailadresse:"), 0, rowNo);
        this.mailTxf = new TextField();
        this.add(mailTxf, 1, rowNo, 3, 1); 
        rowNo++;

        this.add(new Label("Fadder NK:"), 0, rowNo);
        this.teacherNKTx = new Text();
        this.teacherNKCb = new ComboBox<>();
        this.add(teacherNKTx, 1, rowNo); 
        this.add(teacherNKCb, 1, rowNo); 
        rowNo++;

        this.add(new Label("Fadder IT:"), 0, rowNo);
        this.teacherITTx = new Text();
        this.teacherITCb = new ComboBox<>();
        this.add(teacherITTx, 1, rowNo); 
        this.add(teacherITCb, 1, rowNo); 
        rowNo++;

        this.add(new Label("Opplæringsplan:"), 0, rowNo);
        this.document1Txf = new TextField();
        this.document1Txf.setMinWidth(300);
        this.add(document1Txf, 1, rowNo, 3, 1); 
        this.document1Bn = new Button("Åpne");
        this.add(document1Bn, 5, rowNo); 
        rowNo++;

        this.document2Lb = new Label("Kontaktpunkt:");
        this.add(this.document2Lb, 0, rowNo);
        this.document2Txf = new TextField();
        this.add(document2Txf, 1, rowNo, 3, 1); 
        this.document2Bn = new Button("Åpne");
        this.add(document2Bn, 5, rowNo); 
        rowNo++;

        this.add(new Label("Bedrift:"), 0, rowNo);
        this.companyTxf = new TextField();
        this.add(companyTxf, 1, rowNo); 
        rowNo++;

        this.document3Lb = new Label("Egne notater:");
        this.add(this.document3Lb, 0, rowNo);
        this.document3Txf = new TextField();
        this.add(document3Txf, 1, rowNo, 3, 1); 
        this.document3Bn = new Button("Åpne");
        this.add(document3Bn, 5, rowNo); 
        rowNo++;

        this.add(new Label("Sist endret:"), 0, rowNo);
        this.lastUpdatedTx = new Text();
        this.add(lastUpdatedTx, 1, rowNo); 
        this.add(new Label("av:"), 2, rowNo);
        this.lastUpdatedByTx = new Text();
        this.add(lastUpdatedByTx, 3, rowNo); 
        rowNo++;

        this.okBn = new Button("Lagre");
        this.add(this.okBn, 0, rowNo);

// todo Flytte til noe a la addListeners()

        document1Bn.setOnAction(new EventHandler<ActionEvent>()
		{
            @Override
            public void handle(ActionEvent ae)
			{
                String ref = document1Txf.getText();
                if (ref == null)
                    return;
                String trimmedRef = ref.trim();
                if (trimmedRef.length() == 0)
                    return;
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
                String ref = document2Txf.getText();
                if (ref == null)
                    return;
                String trimmedRef = ref.trim();
                if (trimmedRef.length() == 0)
                    return;
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

        document3Bn.setOnAction(new EventHandler<ActionEvent>()
		{
            @Override
            public void handle(ActionEvent ae)
			{
                String ref = document3Txf.getText();
                if (ref == null)
                    return;
                String trimmedRef = ref.trim();
                if (trimmedRef.length() == 0)
                    return;
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

        okBn.setOnAction(new EventHandler<ActionEvent>()
		{
            @Override
            public void handle(ActionEvent ae)
			{
                currentStudent.nameProperty().set(studentNameTxf.getText());
                currentStudent.mailIdProperty().set(mailTxf.getText());
                currentStudent.discordNameProperty().set(discordTxf.getText());
                currentStudent.gitHubNameProperty().set(githubTxf.getText());
                currentStudent.updatedByNameProperty().set(App.loggedInUser.getName());

                boolean ok = App.provider.updateStudent(currentStudent);
                if (ok)
                {
                    lastUpdatedTx.setText(currentStudent.getUpdatedDate().toString());
                    lastUpdatedByTx.setText(currentStudent.getUpdatedByName().toString());
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
        this.currentStudent = student;

        this.studentIdTx.setText(Integer.toString(this.currentStudent.getUserId())); 
        this.studentNameTxf.setText(this.currentStudent.getName()); 

        StudentLevel studentLevel = App.provider.getStudentLevel(this.currentStudent);

        TreeMap<Integer, Contact> contacts = App.provider.getContacts(this.currentStudent);
        Contact contactNK = contacts.get(2);
        int teacherNKId = contactNK.getTeacherId();
        Contact contactIT = contacts.get(1);
        int teacherITId = contactIT.getTeacherId();
        this.teamNoTx.setText(Integer.toString(studentLevel.getTeamNo()));

        TreeMap<Integer, Document> documents = App.provider.getDocuments(this.currentStudent);
        String url1 = "", url2 = "", url3 = "";
        Document document1 = documents.get(1);
        if (document1 != null)
            url1 = document1.getName();
        Document document2 = documents.get(2);
        if (document2 != null)
            url2 = document2.getName();
        Document document3 = documents.get(3);
        if (document3 != null)
            url3 = document3.getName();
    
        if (App.loggedInUser.getUserTypeId() == 1)
        {
            this.levelCb.setVisible(false);
            this.levelTx.setText(this.currentStudent.getStudentLevelName()); 
            this.teacherNKCb.setVisible(false);
            this.teacherNKTx.setText(contactNK.getTeacherName()); 
            this.teacherITCb.setVisible(false);
            this.teacherITTx.setText(contactIT.getTeacherName()); 

            this.document1Txf.setEditable(false);
            this.document1Txf.setText(url1);
            this.document2Lb.setVisible(false);
            this.document2Txf.setVisible(false);
            this.document2Bn.setVisible(false);
            this.document3Lb.setVisible(false);
            this.document3Txf.setVisible(false);
            this.document3Bn.setVisible(false);
            this.companyTxf.setEditable(false);
        }
        else
        {
            this.levelTx.setVisible(false);
            this.levelCb.getSelectionModel().select(new Level(this.currentStudent.getLevelId(), this.currentStudent.getStudentLevelName()));
// todo Forenkle dette!  Egen, forenklet const for Teacher?  Trenger jeg this.teachers? 
            this.teacherNKTx.setVisible(false);
            this.teacherNKCb.getSelectionModel().select(new Teacher(-1, 2, teacherNKId, contactNK.getTeacherName(), "N/A", "N/A"));
            this.teacherITTx.setVisible(false);
            this.teacherITCb.getSelectionModel().select(new Teacher(-1, 2, teacherITId, contactIT.getTeacherName(), "N/A", "N/A"));
            this.document1Txf.setText(url1);
            this.document2Txf.setText(url2);
            this.document3Txf.setText(url3);
        }
        this.levelFromTx.setText(studentLevel.getFromDate().toString());
        this.companyTxf.setText(studentLevel.getCompanyName());
        this.lastUpdatedTx.setText(this.currentStudent.getUpdatedDate().toString());
        this.lastUpdatedByTx.setText(this.currentStudent.getUpdatedByName().toString());

        this.discordTxf.setText(this.currentStudent.getDiscordName()); 
        this.githubTxf.setText(this.currentStudent.getGitHubName()); 
        this.mailTxf.setText(this.currentStudent.getMailId()); 
    }
}