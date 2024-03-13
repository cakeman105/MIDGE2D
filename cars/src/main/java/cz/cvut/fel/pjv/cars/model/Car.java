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

    public String getManufacturer()
    {
        return this.manufacturer;
    }

    public String getModelName()
    {
        return this.modelName;
    }

    public int getYear()
    {
        return this.year;
    }

    public UUID getVinCode()
    {
        return this.vinCode;
    }
}