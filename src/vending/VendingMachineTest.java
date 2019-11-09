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

        Product collectedProduct = vm.collectProduct();
        System.out.println(collectedProduct);

        assertEquals(product, collectedProduct);
    }

    @Test
    public void buyProductWithChange() {
        Product product = Product.MARS;
        vm.selectProduct(product);
        vm.insertCoin(Coin.DWAZLOTE);
        vm.insertCoin(Coin.PIECDZIESIATGROSZY);
        Product collectedProduct = vm.collectProduct();
        List<Coin> change = vm.collectChange();

        assertEquals(product, collectedProduct);


    }

}
