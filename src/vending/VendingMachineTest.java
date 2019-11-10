package vending;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class VendingMachineTest {

    private static VendingMachineImpl vm;

    @BeforeClass
    public static void setUp(){

        vm = new VendingMachineImpl();
    }


    @Test
    public void buyProductWithoutChange() {

        Product product = Product.WATER;

        vm.selectProduct(product);
        vm.insertCoin(Coin.JEDENZLOTY);
        vm.insertCoin(Coin.PIECDZIESIATGROSZY);
        //150

        Bucket<Product, List<Coin>> collected = vm.collectProductAndChange();
        Product collectedProduct = collected.getFirst();
        List<Coin> collectedChange = collected.getSecond();

        assertEquals(product, collectedProduct);
        assertTrue(collectedChange.isEmpty());
    }

    @Test
    public void buyProductWithChange() {

        Product product = Product.MARS;
        vm.selectProduct(product);
        vm.insertCoin(Coin.DWAZLOTE);
        vm.insertCoin(Coin.PIECDZIESIATGROSZY);
        Bucket<Product, List<Coin>> collected = vm.collectProductAndChange();
        List<Coin> collectedChange = collected.getSecond();
        assertEquals(product, collected.getFirst());
        assertEquals(3, collectedChange.size());

    }

}
