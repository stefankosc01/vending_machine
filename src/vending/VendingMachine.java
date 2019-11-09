package vending;

import java.util.List;

public interface VendingMachine {
    void selectProduct(Product product);
    void insertCoin(Coin coin);
    Product collectProduct();
    List<Coin> collectChange();
    List<Coin> refund();
}
