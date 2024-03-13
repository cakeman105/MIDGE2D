package cz.cvut.fel.pjv.cars.model;

import java.util.Objects;

public class Engine
{
    private String type;

    public Engine(String type)
    {
        this.type = type;
    }

    public boolean equals(Engine obj)
    {
        return this.type.equals(obj.type);
    }

    public String toString()
    {
        return String.format("%s", this.type);
    }

    public int hashCode()
    {
        return Objects.hash(type);
    }
}
