package vending;

import java.util.*;

public class VendingMachineImpl implements VendingMachine {

    private Product selectedProduct;
    private int insertedAmount;
    private int machineBalance;

    private Inventory<Coin> coinInventory = new Inventory<>();
    private Inventory<Product> productInventory = new Inventory<>();

    public int getMachineBalance() {
        return machineBalance;
    }

    public VendingMachineImpl() {
        initialize();
    }

    public void initialize() {
        int quantity = 2;

        for(Coin c : Coin.values()){
            machineBalance = machineBalance + c.getDenomination() * quantity;
            coinInventory.put(c, quantity);
        }

        for(Product i : Product.values()){
            productInventory.put(i, quantity);
        }

    }


    @Override
    public void selectProduct(Product product) throws SoldOutException, NotSufficientBalanceForChangeException{
        if (machineBalance < 73) {

            throw new NotSufficientBalanceForChangeException("Machine does not have enough balance to return change");
        }
        if (productInventory.hasItem(product)) {
            selectedProduct = product;
            return;

        }

        throw new SoldOutException("Sold Out, Please select another item");

    }

    @Override
    public void insertCoin(Coin coin) {
        insertedAmount = insertedAmount + coin.getDenomination();
        machineBalance = machineBalance + coin.getDenomination();
        coinInventory.add(coin);
    }

    @Override
    public Bucket<Product, List<Coin>> collectProductAndChange() {

        Product product = collectProduct();
        List<Coin> change = collectChange();


        return new Bucket<>(product, change);
    }



    private List<Coin> collectChange() {

        int changeAmount = insertedAmount - selectedProduct.getPrice();

        List<Coin> change = Collections.EMPTY_LIST;


        if (changeAmount > 0) {
            change = getChange(changeAmount);

        }

        insertedAmount = 0;


        return change;
    }

    private ArrayList<Coin> getChange(int changeAmount) {

        ArrayList<Integer> coinValues = new ArrayList<>();
        ArrayList<Integer> availableCoins = new ArrayList<>();


        for ( Coin c : Coin.values()) {
            coinValues.add(c.getDenomination());
            availableCoins.add(coinInventory.getQuantity(c));
        }


        List<Integer> changeToBeReturned = new ArrayList<>();
        LinkedList<Integer> coins = new LinkedList<>();

        getChangeRecursively(0, changeAmount, coinValues, availableCoins, changeToBeReturned, coins);


        ArrayList<Coin> change = new ArrayList();

        for(Coin c : Coin.values()){
            for(int changeInt : changeToBeReturned) {
                if (c.getDenomination() == changeInt) {
                    change.add(c);
                }
            }
        }

        for (Coin a: change) {
            machineBalance = machineBalance - a.getDenomination();
            updateCoinInventory(a);

        }


        return change;
    }

    private void updateCoinInventory(Coin coin) {
            coinInventory.deduct(coin);
    }

    public static void getChangeRecursively(int pos, int changeAmount, ArrayList<Integer> coinValues, ArrayList<Integer> availableCoins, List<Integer> changeToBeReturned, LinkedList<Integer> coins)
    {

        if (changeAmount == 0) {

            if (changeToBeReturned.isEmpty() || changeToBeReturned.size() < coins.size()) {

                changeToBeReturned.clear();
                changeToBeReturned.addAll(coins);
            }
        }
        else if (changeAmount < 0) {
            return;
        }

        for (int i = pos; i < coinValues.size() && coinValues.get(i) <= changeAmount; i++) {

            if (availableCoins.get(i) > 0) {

                int a = availableCoins.get(i);
                availableCoins.set(i, a - 1);
                coins.addLast(coinValues.get(i));
                getChangeRecursively(i, changeAmount - coinValues.get(i), coinValues, availableCoins, changeToBeReturned, coins);
                coins.removeLast();
                int b = availableCoins.get(i);
                availableCoins.set(i, b + 1);
            }
        }
    }

    private Product collectProduct() {
        if (insertedAmountSufficient()) {

            productInventory.deduct(selectedProduct);
            return selectedProduct;
        }

        return null;
    }

    private boolean insertedAmountSufficient() {
        return insertedAmount >= selectedProduct.getPrice();
    }

    @Override
    public List<Coin> refund() {
        return null;
    }

}
