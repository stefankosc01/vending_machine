package vending;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VendingMachineImpl implements VendingMachine {

    private Product selectedProduct;
    private int balance;

    private Inventory<Coin> coinInventory= new Inventory<>();
    private Inventory<Product> productInventory = new Inventory<>();

    public Inventory<Coin> coinInventoryState() {
        return coinInventory;
    }

    public VendingMachineImpl() {
        initialize();
    }

    public void initialize() {

        for(Coin c : Coin.values()){
            coinInventory.put(c, 10);
        }

        for(Product i : Product.values()){
            productInventory.put(i, 10);
        }
    }


    @Override
    public void selectProduct(Product product) {
        if (productInventory.hasItem(product)) {
            selectedProduct = product;
            return;

        }
        throw new SoldOutException("Sold Out, Please buy another item");

    }

    @Override
    public void insertCoin(Coin coin) {
        balance = balance + coin.getDenomination();
        coinInventory.add(coin);
    }

    @Override
    public Bucket<Product, List<Coin>> collectProductAndChange() {

        Product product = collectProduct();
        List<Coin> change = collectChange();


        return new Bucket<>(product, change);
    }



    private List<Coin> collectChange() {
        long changeAmount = balance - selectedProduct.getPrice();
        System.out.println("change");
        System.out.println(changeAmount);
        ArrayList<Coin> change = new ArrayList<>();

        if (changeAmount > 0) {


        }

        return null;
    }

    private Product collectProduct() {
        System.out.println("balance");
        System.out.println(balance);
        if (balance >= selectedProduct.getPrice()) {
            return selectedProduct;
        }

        return null;
    }

    @Override
    public List<Coin> refund() {
        return null;
    }
}
