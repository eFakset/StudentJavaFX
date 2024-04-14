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
    private StringProperty mailIdProperty, discordNameProperty, gitHubNameProperty, userNameProperty, studentLevelNameProperty;
    private SimpleObjectProperty<LocalDate> fromProperty, toProperty;

    public Student(int userId, int userTypeId, int id, 
                    String name, String mailId, String discordName, String gitHubName, String userName, 
                    int levelId, int studentLevelId, Date fromDate, Date toDate)
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

        if (fromDate != null)
        {
            Calendar fromCal = Calendar.getInstance();
            fromCal.setTime(fromDate);

            LocalDate from = LocalDate.of(fromCal.get(Calendar.YEAR),
                                          fromCal.get(Calendar.MONTH) + 1,
                                          fromCal.get(Calendar.DAY_OF_MONTH)
                            );
            this.fromProperty().set(from);
        }
        if (toDate != null)
        {
            Calendar toCal = Calendar.getInstance();
            toCal.setTime(toDate);

            LocalDate to = LocalDate.of(toCal.get(Calendar.YEAR),
                                        toCal.get(Calendar.MONTH) + 1,
                                        toCal.get(Calendar.DAY_OF_MONTH)
                            );
            this.fromProperty().set(to);
        }
    }

    public SimpleObjectProperty<LocalDate>
    fromProperty()
    {
        if (this.fromProperty == null)
            this.fromProperty = new SimpleObjectProperty<>(this, PropertyConstants.FROM_DATEPROPERTY_NAME);
        return this.fromProperty;
    }

    public LocalDate
    getFromDate()
    {
        return this.fromProperty.get();
    }

    public SimpleObjectProperty<LocalDate>
    toProperty()
    {
        if (this.toProperty == null)
            this.toProperty = new SimpleObjectProperty<>(this, PropertyConstants.TO_DATEPROPERTY_NAME);
        return this.toProperty;
    }

    public LocalDate
    getToDate()
    {
        return this.toProperty.get();
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
    getGitHubNameProperty()
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