package vending;

import java.util.ArrayList;

public class VendingMachineFactory {
    public static VendingMachine createVendingMachine() {
        return new VendingMachineImpl();
    }
    public static VendingMachine createVendingMachineWithExcluded5gr() {
        ArrayList<Coin> excluded = new ArrayList<>();
        excluded.add(Coin.PIECGROSZY);
        return new VendingMachineImpl(excluded);
    }

    public static VendingMachine createVendingMachineWithLessThan73gr() {
        ArrayList<Coin> excluded = new ArrayList<>();
        excluded.add(Coin.PIECDZIESIATGROSZY);
        excluded.add(Coin.JEDENZLOTY);
        excluded.add(Coin.DWAZLOTE);
        excluded.add(Coin.PIECZLOTYCH);
        return new VendingMachineImpl(excluded);
    }



}
