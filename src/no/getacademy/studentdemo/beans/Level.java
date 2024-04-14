package no.getacademy.studentdemo.beans;

public class Level extends AbstractItem implements Comparable<Level>
{
    public Level(int id, String name)
    {
        super(id, name);
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