package no.getacademy.studentdemo.beans;

import javafx.beans.property.*;

public abstract class AbstractItem
{
    private IntegerProperty idProperty;
    private StringProperty nameProperty;

    public final static String IDPROPERTY_NAME = "id";   
    public final static String NAMEPROPERTY_NAME = "name";   

    public final static int TYPE_NOT_SET = 0;
    public final static int STUDENT = 1;
    public final static int TEACHER = 2;

    protected AbstractItem(int id, String name)
    {
        this.idProperty().set(id);
        this.nameProperty().set(name);
    }
    
    public int
    getId()
    {
        return this.idProperty().get();
    }

    public IntegerProperty
    idProperty()
    {
        if (this.idProperty == null)
            this.idProperty = new SimpleIntegerProperty(this, AbstractItem.IDPROPERTY_NAME);
        return this.idProperty;
    }

    public String 
    getName()
    { 
        return this.nameProperty().get();
    }

    public StringProperty 
    nameProperty()
    {
        if (this.nameProperty == null)
            this.nameProperty = new SimpleStringProperty(this, AbstractItem.NAMEPROPERTY_NAME);
        return this.nameProperty;
    }    

    @Override
    public String
    toString()
    {
        return this.nameProperty().get();
    }
}