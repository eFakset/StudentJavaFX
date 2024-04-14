package no.getacademy.studentdemo.beans;

import javafx.beans.property.*;
import no.getacademy.studentdemo.util.*;

public class Contact extends AbstractItem implements Comparable<Level>
{
    private SimpleIntegerProperty teacherIdProperty;
    private SimpleStringProperty teacherNameProperty;

    public Contact(int id, String name, int teacherId, String teacherName)
    {
        super(id, name);

        this.teacherIdProperty().set(teacherId);
        this.teacherNameProperty().set(teacherName);
    }
    public IntegerProperty
    teacherIdProperty()
    {
        if (this.teacherIdProperty == null)
            this.teacherIdProperty = new SimpleIntegerProperty(this, PropertyConstants.TEACHERIDPROPERTY_NAME);
        return this.teacherIdProperty;
    }

    public int
    getTeacherId()
    {
        return this.teacherIdProperty.get();
    }

    public String 
    getTeacherName()
    { 
        return this.teacherNameProperty().get();
    }

    public StringProperty 
    teacherNameProperty()
    {
        if (this.teacherNameProperty == null)
            this.teacherNameProperty = new SimpleStringProperty(this, PropertyConstants.TEACHERNAMEPROPERTY_NAME);
        return this.teacherNameProperty;
    }    

    @Override
    public int compareTo(Level compLevel)
    {   // todo Tar ikke hensyn til nullverdi!
        if (this.getId() == compLevel.getId())
            return this.getName().compareTo(compLevel.getName());
        else
            return this.getId() - compLevel.getId();
    } 

}
