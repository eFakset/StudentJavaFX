package no.getacademy.studentdemo.beans;

import javafx.beans.property.*;

public class Teacher extends AbstractItem implements Comparable<Teacher> 
{
    private IntegerProperty userIdProperty, userTypeIdProperty;
    private StringProperty mailIdProperty, userNameProperty;

    public Teacher()
    {
        this(-1, -1, -1, "...", null, null);
    }

    public Teacher(int userId, int userTypeId, int teacherId, String teacherName, String mailId, String userName)
    {
        super(teacherId, teacherName);

        this.userIdProperty().set(userId);
        this.userTypeIdProperty().set(userTypeId);

        this.userNameProperty().set(userName);
        this.mailIdProperty().set(mailId);  
    }


    public IntegerProperty
    userIdProperty()
    {
        if (this.userIdProperty == null)
            this.userIdProperty = new SimpleIntegerProperty(this, Teacher.USERIDPROPERTY_NAME);
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
            this.userTypeIdProperty = new SimpleIntegerProperty(this, Teacher.USERTYPEIDPROPERTY_NAME);
        return this.userTypeIdProperty;
    }

    public int
    getUserTypeId()
    {
        return this.userIdProperty.get();
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
            this.userNameProperty = new SimpleStringProperty(this, Teacher.USERNAMEPROPERTY_NAME);
        return this.userNameProperty;
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
            this.mailIdProperty = new SimpleStringProperty(this, Teacher.MAILIDPROPERTY_NAME);
        return this.mailIdProperty;
    }    

    @Override
    public int compareTo(Teacher compTeacher)
    {   // todo Tar ikke hensyn til nullverdi!
        if (this.getId() == compTeacher.getId())
            return this.getName().compareTo(compTeacher.getName());
        else
            return this.getId() - compTeacher.getId();
    } 
}
