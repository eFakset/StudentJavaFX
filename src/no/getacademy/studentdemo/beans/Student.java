package no.getacademy.studentdemo.beans;

import java.time.LocalDate;
import java.util.*;
import javafx.beans.property.*;
import no.getacademy.studentdemo.util.*;

/**
 * @author Ellen Fakset
 */
public class Student extends AbstractItem implements Comparable<Student>
{
    private IntegerProperty userIdProperty, userTypeIdProperty, levelIdProperty, studentLevelIdProperty;
    private StringProperty mailIdProperty, discordNameProperty, gitHubNameProperty, userNameProperty, studentLevelNameProperty, updatedByNameProperty;
    private SimpleObjectProperty<LocalDate> createdProperty, updatedProperty;

    public Student(int userId, int userTypeId, int id, 
                    String name, String mailId, String discordName, String gitHubName, String userName, 
                    int levelId, int studentLevelId, Date createdDate, Date updatedDate, String updatedBy)
    {
        super(id, name);

        this.userIdProperty().set(userId);
        this.userTypeIdProperty().set(userTypeId);

        this.userNameProperty().set(userName);
        this.discordNameProperty().set(discordName);
        this.gitHubNameProperty().set(gitHubName);
        this.mailIdProperty().set(mailId);  
        this.levelIdProperty().set(levelId);
        this.studentLevelIdProperty().set(studentLevelId);

        Calendar createdCal = Calendar.getInstance();
        createdCal.setTime(createdDate);

        LocalDate created = LocalDate.of(createdCal.get(Calendar.YEAR),
                                        createdCal.get(Calendar.MONTH) + 1,
                                        createdCal.get(Calendar.DAY_OF_MONTH)
                                    );
        this.createdProperty().set(created);

        Calendar updatedCal = Calendar.getInstance();
        updatedCal.setTime(createdDate);

        LocalDate updated = LocalDate.of(createdCal.get(Calendar.YEAR),
                        updatedCal.get(Calendar.MONTH) + 1,
                        updatedCal.get(Calendar.DAY_OF_MONTH)
                        );
        this.updatedProperty().set(updated);
        this.updatedByNameProperty().set(updatedBy);
    }

    public SimpleObjectProperty<LocalDate>
    createdProperty()
    {
        if (this.createdProperty == null)
            this.createdProperty = new SimpleObjectProperty<>(this, PropertyConstants.CREATED_DATEPROPERTY_NAME);
        return this.createdProperty;
    }

    public LocalDate
    getCreatedDate()
    {
        return this.createdProperty.get();
    }

    public SimpleObjectProperty<LocalDate>
    updatedProperty()
    {
        if (this.updatedProperty == null)
            this.updatedProperty = new SimpleObjectProperty<>(this, PropertyConstants.UPDATED_DATEPROPERTY_NAME);
        return this.updatedProperty;
    }

    public LocalDate
    getUpdatedDate()
    {
        return this.updatedProperty.get();
    }

    public String
    getUpdatedByName()
    {
        return this.updatedByNameProperty.get();
    }

    public StringProperty
    updatedByNameProperty()
    {
        if (this.updatedByNameProperty == null)
            this.updatedByNameProperty = new SimpleStringProperty(this, PropertyConstants.UPDATEDBY_NAMEPROPERTY_NAME);
        return this.updatedByNameProperty;
    }

    public IntegerProperty
    userIdProperty()
    {
        if (this.userIdProperty == null)
            this.userIdProperty = new SimpleIntegerProperty(this, PropertyConstants.USERIDPROPERTY_NAME);
        return this.userIdProperty;
    }

    public int
    getUserId()
    {
        return this.userIdProperty.get();
    }

    public IntegerProperty
    userTypeIdProperty()
    {
        if (this.userTypeIdProperty == null)
            this.userTypeIdProperty = new SimpleIntegerProperty(this, PropertyConstants.USERTYPEIDPROPERTY_NAME);
        return this.userTypeIdProperty;
    }

    public int
    getUserTypeId()
    {
        return this.userIdProperty.get();
    }

    public int
    getStudentLevelId()
    {
        return this.studentLevelIdProperty.get();
    }

    public IntegerProperty
    studentLevelIdProperty()
    {
        if (this.studentLevelIdProperty == null)
            this.studentLevelIdProperty = new SimpleIntegerProperty(this, PropertyConstants.STUDENT_LEVELIDPROPERTY_NAME);
        return this.studentLevelIdProperty;
    }

    public String
    getStudentLevelName()
    {
        return this.studentLevelNameProperty.get();
    }

    public StringProperty
    studentLevelNameProperty()
    {
        if (this.studentLevelNameProperty == null)
            this.studentLevelNameProperty = new SimpleStringProperty(this, PropertyConstants.STUDENT_LEVELNAMEPROPERTY_NAME);
        return this.studentLevelNameProperty;
    }

    public int
    getLevelId()
    {
        return this.levelIdProperty.get();
    }

    public IntegerProperty
    levelIdProperty()
    {
        if (this.levelIdProperty == null)
            this.levelIdProperty = new SimpleIntegerProperty(this, PropertyConstants.LEVELIDPROPERTY_NAME);
        return this.levelIdProperty;
    }

    public String 
    getUserName()
    { 
        return this.userNameProperty().get();
    }

    public StringProperty 
    userNameProperty()
    {
        if (this.userNameProperty == null)
            this.userNameProperty = new SimpleStringProperty(this, PropertyConstants.USERNAMEPROPERTY_NAME);
        return this.userNameProperty;
    }    

    public String 
    getDiscordName()
    { 
        return this.discordNameProperty().get();
    }

    public StringProperty 
    discordNameProperty()
    {
        if (this.discordNameProperty == null)
            this.discordNameProperty = new SimpleStringProperty(this, PropertyConstants.DISCORDNAMEPROPERTY_NAME);
        return this.discordNameProperty;
    } 

    public String 
    getGitHubName()
    { 
        return this.gitHubNameProperty().get();
    }

    public StringProperty 
    gitHubNameProperty()
    {
        if (this.gitHubNameProperty == null)
            this.gitHubNameProperty = new SimpleStringProperty(this, PropertyConstants.GITHUBNAMEPROPERTY_NAME);
        return this.gitHubNameProperty;
    }    

    public String 
    getMailId()
    { 
        return this.mailIdProperty().get();
    }

    public StringProperty 
    mailIdProperty()
    {
        if (this.mailIdProperty == null)
            this.mailIdProperty = new SimpleStringProperty(this, PropertyConstants.MAILIDPROPERTY_NAME);
        return this.mailIdProperty;
    } 
       
    @Override
    public int compareTo(Student compStudent)
    {   // todo Tar ikke hensyn til nullverdi!
        if (this.getId() == compStudent.getId())
            return this.getName().compareTo(compStudent.getName());
        else
            return this.getId() - compStudent.getId();
    } 
}