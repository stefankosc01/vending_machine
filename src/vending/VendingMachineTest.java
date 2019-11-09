package vending;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

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

        Bucket collected = vm.collectProductAndChange();
        System.out.println(collected);

        assertEquals(product, collected.getFirst());
    }

    @Test
    public void buyProductWithChange() {
        Product product = Product.MARS;
        vm.selectProduct(product);
        vm.insertCoin(Coin.DWAZLOTE);
        vm.insertCoin(Coin.PIECDZIESIATGROSZY);
        Bucket collected = vm.collectProductAndChange();

        assertEquals(product, collected.getFirst());


    }

}
