package vending;

public enum Product {

    WATER("Water", 150),
    PEPSI("Pepsi", 290),
    SPRITE("Sprite", 295),
    MARS("Mars", 243);

    private String name;
    private int price;

    Product(String name, int price){
        this.name = name;
        this.price = price;
    }
    public String getName(){

        return name;
    }
    public int getPrice() {

        return price;
    }
}

