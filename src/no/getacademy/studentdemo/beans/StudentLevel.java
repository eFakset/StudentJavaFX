package no.getacademy.studentdemo.beans;

import javafx.beans.property.*;
import java.util.*;
import java.time.LocalDate;
import no.getacademy.studentdemo.util.*;

public class StudentLevel extends AbstractItem implements Comparable<Level>
{
    private SimpleObjectProperty<LocalDate> fromProperty, toProperty;
    private SimpleStringProperty companyProperty;
    private SimpleIntegerProperty teamNoProperty;

    public StudentLevel(int id, String name, int teamNo, String companyName, Date fromDate, Date toDate)
    {
        super(id, name);

        this.teamNoProperty().set(teamNo);
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
            this.toProperty().set(to);
        }

        this.companyProperty().set(companyName);
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

    public int 
    getTeamNo()
    { 
        return this.teamNoProperty().get();
    }

    public IntegerProperty 
    teamNoProperty()
    {
        if (this.teamNoProperty == null)
            this.teamNoProperty = new SimpleIntegerProperty(this, PropertyConstants.STUDENT_TEAMNOPROPERTY_NAME);
        return this.teamNoProperty;
    }    

    public String 
    getCompanyName()
    { 
        return this.companyProperty().get();
    }

    public StringProperty 
    companyProperty()
    {
        if (this.companyProperty == null)
            this.companyProperty = new SimpleStringProperty(this, PropertyConstants.COMPANYNAMEPROPERTY_NAME);
        return this.companyProperty;
    }    

    @Override
    public int compareTo(Level compLevel)
    {   // todo Tar ikke hensyn til nullverdi!
        if (this.getId() == compLevel.getId())
            return this.getName().compareTo(compLevel.getName());
        else
            return this.getId() - compLevel.getId();
    } 

    @Override
    public boolean
    equals(Object o)
    {
        if (o instanceof Level)
            return this.getId() == ((Level)o).getId();
        else
            return false;
    }
}