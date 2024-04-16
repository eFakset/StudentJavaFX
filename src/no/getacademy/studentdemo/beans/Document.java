package no.getacademy.studentdemo.beans;

import javafx.beans.property.*;
import no.getacademy.studentdemo.util.*;

public class Document extends AbstractItem implements Comparable<Level>
{
    private SimpleIntegerProperty documentTypeIdProperty;
    private SimpleStringProperty documentTypeNameProperty;

    public Document(int id, String name, int documentTypeId, String documentTypeName)
    {
        super(id, name);

        this.documentTypeIdProperty().set(documentTypeId);
        this.documentTypeNameProperty().set(documentTypeName);
    }
    public IntegerProperty
    documentTypeIdProperty()
    {
        if (this.documentTypeIdProperty == null)
            this.documentTypeIdProperty = new SimpleIntegerProperty(this, PropertyConstants.DOCUMENTTYPEIDPROPERTY_NAME);
        return this.documentTypeIdProperty;
    }

    public int
    getdocumentTypeId()
    {
        return this.documentTypeIdProperty.get();
    }

    public String 
    getDocumentTypeName()
    { 
        return this.documentTypeNameProperty().get();
    }

    public StringProperty 
    documentTypeNameProperty()
    {
        if (this.documentTypeNameProperty == null)
            this.documentTypeNameProperty = new SimpleStringProperty(this, PropertyConstants.DOCUMENTTYPENAMEPROPERTY_NAME);
        return this.documentTypeNameProperty;
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