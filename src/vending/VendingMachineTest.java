package vending;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class VendingMachineTest {


    @Test
    public void buyWithoutChange() {

        VendingMachine vm = VendingMachineFactory.createVendingMachine();

        Product product = Product.WATER;

        vm.selectProduct(product);
        vm.insertCoin(Coin.JEDENZLOTY);
        vm.insertCoin(Coin.PIECDZIESIATGROSZY);

        Bucket<Product, List<Coin>> collected = vm.collectProductAndChange();
        Product collectedProduct = collected.getFirst();
        List<Coin> collectedChange = collected.getSecond();

        assertEquals(product, collectedProduct);
        assertTrue(collectedChange.isEmpty());
    }

    @Test
    public void buyMarsWithChange() {

        VendingMachine vm = VendingMachineFactory.createVendingMachine();

        Product product = Product.MARS;
        vm.selectProduct(product);
        vm.insertCoin(Coin.DWAZLOTE);
        vm.insertCoin(Coin.PIECDZIESIATGROSZY);
        Bucket<Product, List<Coin>> collected = vm.collectProductAndChange();
        List<Coin> collectedChange = collected.getSecond();
        assertEquals(product, collected.getFirst());
        // TODO: parametrize expected
        assertEquals(2, collectedChange.size());
        int expectedChange = 7;
        int change = collectedChange.stream().mapToInt(i -> i.getDenomination()).sum();
        assertEquals(expectedChange, change);

    }

    @Test
    public void buyWhenNoEqualChangeAvailable() {

        VendingMachine vm = VendingMachineFactory.createVendingMachineWithExcluded5gr();

        Product product = Product.SPRITE;
        vm.selectProduct(product);
        vm.insertCoin(Coin.DWAZLOTE);
        vm.insertCoin(Coin.JEDENZLOTY);
        Bucket<Product, List<Coin>> collected = vm.collectProductAndChange();
        List<Coin> collectedChange = collected.getSecond();
        assertEquals(product, collected.getFirst());

        // TODO: parametrize expected
        assertEquals(1, collectedChange.size());
        int expectedChange = 10;
        int change = collectedChange.stream().mapToInt(i -> i.getDenomination()).sum();
        assertEquals(expectedChange, change);

    }

    @Test(expected = NotSufficientBalanceForChangeException.class)
    public void buyWhenNoChangeAvailable() {

        VendingMachine vm = VendingMachineFactory.createVendingMachineWithLessThan73gr();

        Product product = Product.SPRITE;
        vm.selectProduct(product);
    }
}
