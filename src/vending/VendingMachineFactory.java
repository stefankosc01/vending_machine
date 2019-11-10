package vending;

public class VendingMachineFactory {
    public static VendingMachine createVendingMachine() {
        return new VendingMachineImpl();
    }
    public static VendingMachine createVendingMachineWithExcludedDenomination() {
        return new VendingMachineImpl(Coin.PIECGROSZY.getDenomination());
    }


}
