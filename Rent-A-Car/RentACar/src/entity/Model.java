package entity;

import core.ComboItem;

public class Model {
    private int id;
    private int brand_id;
    private String name;
    private String year;
    private Type type;
    private Fuel fuel;
    private Gear gear;
    private Brand brand;
    public enum Fuel {
        GASOLINE,
        LPG,
        ELECTRIC,
        DIESEL
    }

    public enum Gear {
        MANUEL,
        AUTO
    }

    public enum Type {
        SEDAN,
        HACBACK
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public Gear getGear() {
        return gear;
    }

    public void setGear(Gear gear) {
        this.gear = gear;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
        public ComboItem getComboItem(){
        return new ComboItem(this.getId(),this.getBrand().getName()+" - "+this.getName()+" - "+this.getYear()+" - "+this.getGear());

        }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", brand_id=" + brand_id +
                ", name='" + name + '\'' +
                ", year='" + year + '\'' +
                ", type=" + type +
                ", fuel=" + fuel +
                ", gear=" + gear +
                ", brand=" + brand +
                '}';
    }
}
