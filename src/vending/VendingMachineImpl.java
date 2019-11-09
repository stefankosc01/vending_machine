package vending;

import java.util.*;

public class VendingMachineImpl implements VendingMachine {

    private Product selectedProduct;
    private int insertedAmount;
    private long machineBalance;

    private Inventory<Coin> coinInventory = new Inventory<>();
    private Inventory<Product> productInventory = new Inventory<>();

    public Inventory<Coin> coinInventoryState() {
        return coinInventory;
    }

    public VendingMachineImpl() {
        initialize();
    }

    public void initialize() {
        int quantity = 6;

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
        if (productInventory.hasItem(product)) {
            selectedProduct = product;
            return;

        }

        if (machineBalance < 73) {
            throw new NotSufficientBalanceForChangeException("Machine does not have enough balance to return change");
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
//            change = new ArrayList<>();
            change = getChange(changeAmount);


        }

        return change;
    }

    private ArrayList<Coin> getChange(int changeAmount) {

        int amount = changeAmount;
        System.out.println(amount);

        ArrayList<Integer> values = new ArrayList<>();
        ArrayList<Integer> available = new ArrayList<>();


        for ( Coin c : Coin.values()) {
            values.add(c.getDenomination());
            available.add(coinInventory.getQuantity(c));
        }
        System.out.println(values);
        System.out.println(available);

        List<Integer> changeToBeReturned = new ArrayList<>();
        LinkedList<Integer> coins = new LinkedList<>();

        getChangeRecursively(0, amount, values, available, changeToBeReturned, coins);

        System.out.println(changeToBeReturned);


        ArrayList<Coin> change = new ArrayList();


        return change;
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

    public static void getChangeRecursively(int pos, int change, ArrayList<Integer> values, ArrayList<Integer> available, List<Integer> changeToBeReturned, LinkedList<Integer> coins)
    {
        if (change == 0)
        {
            if (changeToBeReturned.isEmpty() || changeToBeReturned.size() < coins.size())
            {
                changeToBeReturned.clear();
                changeToBeReturned.addAll(coins);
            }
        }
        else if (change < 0)
        {
            return;
        }

        for (int i = pos; i < values.size() && values.get(i) <= change; i++)
        {
            System.out.println("position");
            System.out.println(pos);
            if (available.get(i) > 0)
            {
                int a = available.get(i);
                available.set(i, a - 1);
                coins.addLast(values.get(i));
                getChangeRecursively(i, change - values.get(i), values, available, changeToBeReturned, coins);
                coins.removeLast();
                int b = available.get(i);
                available.set(i, b + 1);            }
        }
    }
}
