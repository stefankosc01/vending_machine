package vending;

import com.sun.xml.internal.bind.v2.TODO;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.Assert.*;

public class VendingMachineTest {


    @Test
    public void buyProductWithoutChange() {

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

    }

    @Test
    public void buyWhenNoEqualChangeAvailable() {

        VendingMachine vm = VendingMachineFactory.createVendingMachineWithExcludedDenomination();

        Product product = Product.SPRITE;
        vm.selectProduct(product);
        vm.insertCoin(Coin.DWAZLOTE);
        vm.insertCoin(Coin.JEDENZLOTY);
        Bucket<Product, List<Coin>> collected = vm.collectProductAndChange();
        List<Coin> collectedChange = collected.getSecond();
        assertEquals(product, collected.getFirst());

        // TODO: parametrize expected
        assertEquals(1, collectedChange.size());


    }


}
