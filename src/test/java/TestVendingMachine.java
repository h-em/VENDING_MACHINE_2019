import CustomExceptions.NotFullyPaidException;
import CustomExceptions.NotSufficientChangeException;
import CustomExceptions.SoldOutException;
import Enums.Coin;
import Enums.Item;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.junit.Assert.assertEquals;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestVendingMachine {

    private static VendingMachineImpl vm;

    @Before
    public void init() {
        vm = (VendingMachineImpl) VendingMachineFactory.createVendingMacchine();
    }

/*
    @AfterClass
    public static void  clear(){
        vm = null;
    }*/

    @Test
    public void testIfPriceOfMarsThanGetPriceMethodReturn25() throws SoldOutException {
        assertEquals(25, vm.selectItemAndGetPrice(Item.MARS));
    }

    @Test
    public void testIfPriceOfTwixThanGetPriceMethodReturn35() throws SoldOutException {
        assertEquals(35, vm.selectItemAndGetPrice(Item.TWIX));
    }

    @Test
    public void testWhenAdd75CoinThenCurrentBalanceIs75() {
        vm.insertCoin(Coin.FIFTY);
        vm.insertCoin(Coin.TWENTY);
        vm.insertCoin(Coin.FIVE);
        assertEquals(75, vm.getCurrentBalance());
    }

    @Test
    public void testWhenBuyMultipleItemsThenTotalSalseIs85() throws NotSufficientChangeException, NotFullyPaidException, SoldOutException {
        vm.insertCoin(Coin.FIFTY);
        vm.selectItemAndGetPrice(Item.TWIX);
        vm.collectItemAndChange();

        vm.insertCoin(Coin.FIFTY);
        vm.selectItemAndGetPrice(Item.MARS);
        vm.collectItemAndChange();

        vm.insertCoin(Coin.FIFTY);
        vm.selectItemAndGetPrice(Item.MARS);
        vm.collectItemAndChange();

        assertEquals(85, vm.getTotalSales());
    }

    @Test
    public void testWhenAdd150CoinAndAnItemIsBoughtThenRefoundMethodReturns125Coins() throws SoldOutException, NotSufficientChangeException, NotFullyPaidException {
        vm.insertCoin(Coin.FIFTY);
        vm.insertCoin(Coin.FIFTY);
        vm.insertCoin(Coin.FIFTY);

        vm.selectItemAndGetPrice(Item.TWIX);
        List<Coin> list = vm.collectItemAndChange().getCoins();
        int s = 0;
        for (Coin x : list) {
            s += x.getValue();
        }
        assertEquals(115, s);
    }

    @Test(expected = SoldOutException.class)
    public void testWhenAnItemDoesNotExistThenThrowSoldOutException() throws SoldOutException, NotSufficientChangeException, NotFullyPaidException {

        for (int i = 0; i <= 5; i++) {
            vm.insertCoin(Coin.FIFTY);
            vm.selectItemAndGetPrice(Item.BOUNTY);
            vm.collectItemAndChange();
        }
    }


    /*
        @Test(expected = NotSufficientChangeException.class)
        public void testWhenDoesNotExistEnoughtCashThenThrowNotSufficientChangeException() throws SoldOutException, NotSufficientChangeException, NotFullyPaidException {

            for(int i=0; i<4; i++) {
                vm.insertCoin(Coin.FIFTY);
                vm.selectItemAndGetPrice(Item.MARS);
                vm.collectItemAndChange();
            }
        }*/

    @Test(expected = NotFullyPaidException .class)
    public void testWhenNotEnoughtCoinAreAddedIntoVMThenThrowNotFullyPaidException() throws SoldOutException, NotSufficientChangeException, NotFullyPaidException {
            vm.insertCoin(Coin.FIVE);
            vm.selectItemAndGetPrice(Item.MARS);
            vm.collectItemAndChange();
        }
    }
