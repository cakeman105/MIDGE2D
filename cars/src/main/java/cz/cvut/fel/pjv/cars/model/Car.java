package cz.cvut.fel.pjv.cars.model;

import java.util.Objects;
import java.util.UUID;

public class Car
{
    private static int count = 0;
    private String manufacturer;
    private String modelName;
    private int year;
    private UUID vinCode;
    private Engine engine;

    public Car(String manufacturer, String modelName, int year, String type)
    {
        this.manufacturer = manufacturer;
        this.modelName = modelName;
        this.year = year;
        this.vinCode = UUID.randomUUID();
        this.engine = new Engine(type);
        count++;
    }

    public String toString()
    {
        return String.format("%s %s year %d VIN: %s", this.manufacturer, this.modelName, this.year, this.vinCode);
    }

    public static int getNumberOfExistingCars()
    {
        return count;
    }

    public boolean equals(Car obj)
    {
        return this.vinCode.equals(obj.vinCode);
    }

    public int hashCode()
    {
        return Objects.hash(vinCode);
    }
}

class Engine
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
