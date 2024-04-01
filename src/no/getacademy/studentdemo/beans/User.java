package no.getacademy.studentdemo.beans;

import javafx.beans.property.*;

public class User extends AbstractItem
{
    private SimpleIntegerProperty userTypeIdProperty;

    public User()
    {
        this(-1, "No User", AbstractItem.TYPE_NOT_SET);
    }

    public User(int userId, String userName, int userTypeId)  
    {
        super(userId, userName);

        this.userTypeIdProperty().set(userTypeId);  
    }

    public int
    getUserTypeId()
    {
        return this.userTypeIdProperty.get();
    }

    public SimpleIntegerProperty
    userTypeIdProperty()
    {
        if (this.userTypeIdProperty == null)
            this.userTypeIdProperty = new SimpleIntegerProperty(this, "userTypeId");
        return this.userTypeIdProperty;
    }
}
