package vending;

import java.util.*;

public class VendingMachineImpl implements VendingMachine {

    private Product selectedProduct;
    private int insertedAmount;
    private int machineBalance;

    private Inventory<Coin> coinInventory = new Inventory<>();
    private Inventory<Product> productInventory = new Inventory<>();


    public VendingMachineImpl() {
        initialize();
    }
    public VendingMachineImpl(ArrayList<Coin> excluded) {
        initializeWithExcludedDenomination(excluded);
    }

    private void initializeWithExcludedDenomination(ArrayList<Coin> excluded) {
        int coinsQuantity = 1;

        for(Coin c : Coin.values()){

            coinsQuantity = excluded.indexOf(c) > - 1 ? 0 : 1;

            machineBalance = machineBalance + c.getDenomination() * coinsQuantity;
            coinInventory.put(c, coinsQuantity);
        }

        for(Product i : Product.values()){
            productInventory.put(i, coinsQuantity);
        }
    }


    private void initialize() {
        int coinsQuantity = 1;

        for(Coin c : Coin.values()){

            machineBalance = machineBalance + c.getDenomination() * coinsQuantity;
            coinInventory.put(c, coinsQuantity);
        }

        for(Product i : Product.values()){
            productInventory.put(i, coinsQuantity);
        }

    }


    @Override
    public void selectProduct(Product product) throws SoldOutException, NotSufficientBalanceForChangeException{
        if (machineBalance <= 73) {

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

        int productPrice = selectedProduct.getPrice();

        int changeAmount = insertedAmount - productPrice;

        List<Coin> change = Collections.EMPTY_LIST;


        if (changeAmount > 0) {
            change = getChange(changeAmount);
        }


        int changeToBeReturnedSum = change.stream().mapToInt(i -> i.getDenomination()).sum();


        if (changeToBeReturnedSum != changeAmount) {
            while (change.size() == 0) {
                changeAmount = changeAmount + 1;
                change = getChange(changeAmount);
            }
        }

        insertedAmount = 0;
        selectedProduct = null;

        for (Coin c: change) {
            machineBalance = machineBalance - c.getDenomination();
            updateCoinInventory(c);

        }

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

        prepareChangeValues(0, changeAmount, coinValues, availableCoins, changeToBeReturned, coins);


        ArrayList<Coin> change = new ArrayList();

        for(Coin c : Coin.values()){
            for(int changeInt : changeToBeReturned) {
                if (c.getDenomination() == changeInt) {
                    change.add(c);
                }
            }
        }

        return change;
    }

    private void updateCoinInventory(Coin coin) {
            coinInventory.deduct(coin);
    }

    public static void prepareChangeValues(int pos, int changeAmount, ArrayList<Integer> coinValues, ArrayList<Integer> availableCoins, List<Integer> changeValues, LinkedList<Integer> coins)
    {

        if (changeAmount == 0) {

            if (changeValues.isEmpty() || changeValues.size() < coins.size()) {

                changeValues.clear();
                changeValues.addAll(coins);
            }
        }
        else if (changeAmount < 0) {
            return;
        }

        for (int i = pos; i < coinValues.size() && coinValues.get(i) <= changeAmount; i++) {

            if (availableCoins.get(i) > 0) {

                // TODO: refactor types of availableCoins and coinValues and write algorithm in more functional way
                int a = availableCoins.get(i);
                availableCoins.set(i, a - 1);
                coins.addLast(coinValues.get(i));
                prepareChangeValues(i, changeAmount - coinValues.get(i), coinValues, availableCoins, changeValues, coins);
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

}
