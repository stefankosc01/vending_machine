package vending;

import java.util.List;

public interface VendingMachine {
    void selectProduct(Product product);
    void insertCoin(Coin coin);
    Bucket<Product, List<Coin>> collectProductAndChange();
}
