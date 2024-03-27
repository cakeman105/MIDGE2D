package cz.cvut.fel.pjv.cars.model;

public class ServiceBook
{
    private Car car;
    private static final int CAPACITY = 16;
    private String[] serviceRecords;
    private int count;
    public ServiceBook(Car car)
    {
        this.car = car;
        this.serviceRecords = new String[ServiceBook.CAPACITY];
        car.addServiceBook(this);
        this.count = 0;
    }

    public void addRecord(String obj)
    {
        serviceRecords[count++] = obj;
    }

    public String toString()
    {
        String out = "";
        int i = 0;
        while (this.serviceRecords[i] != null)
        {
            out += this.serviceRecords[i] + '\n';
            i++;
        }

        return out;
    }
}
