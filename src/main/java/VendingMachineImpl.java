import CustomExceptions.NotFullyPaidException;
import CustomExceptions.NotSufficientChangeException;
import CustomExceptions.SoldOutException;
import Enums.Coin;
import Enums.Item;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class VendingMachineImpl implements VendingMachine {

    private Inventory<Coin> cashInventory;  //inventarul banilor
    private Inventory<Item> itemInventory;   //inventarul produselor
    private long totalSales;    //total vanzari
    private Item currentItem;   //produsul selectat
    private long currentBalance; //cantitatea curenta de bani introdusa de client


    public VendingMachineImpl() {
        initialize();
    }

    private void initialize() {
        itemInventory = new Inventory<Item>();
        itemInventory.put(Item.MARS, 100);
        itemInventory.put(Item.BOUNTY, 5);
        itemInventory.put(Item.TWIX, 5);

        cashInventory = new Inventory<Coin>();

        cashInventory.put(Coin.FIVE, 5);
        cashInventory.put(Coin.TEN, 5);
        cashInventory.put(Coin.TWENTY, 5);
        cashInventory.put(Coin.FIFTY, 1);

    }

    @Override
    public long selectItemAndGetPrice(Item item) throws SoldOutException {

        if (itemInventory.hasItem(item)) {
            currentItem = item;
            return item.getPrice();
        } else throw new SoldOutException("Sold out!");

    }

    @Override
    public void insertCoin(Coin coin) {
        cashInventory.add(coin);  //  adaug banii in inventar
        currentBalance += coin.getValue();
    }

    public void updateCashInventory(List<Coin> change) {

        for (Coin c : change){
            cashInventory.decrease(c);
        }
    }

    private boolean isFullyPaid() {
        return currentBalance >= currentItem.getPrice();
    }

    public List<Coin> getChange(long amount) throws NotSufficientChangeException {

        List<Coin> changes = Collections.EMPTY_LIST;
        changes = new LinkedList<Coin>();

        long balance = amount;

        while (balance > 0) {

            if (balance >= Coin.FIFTY.getValue() && cashInventory.hasItem(Coin.FIFTY)) {
                changes.add(Coin.FIFTY);
                balance -= Coin.FIFTY.getValue();
                continue;

            } else if (balance >= Coin.TWENTY.getValue() && cashInventory.hasItem((Coin.TWENTY))) {
                changes.add(Coin.TWENTY);
                balance -= Coin.TWENTY.getValue();
                continue;

            } else if (balance >= Coin.TEN.getValue() && cashInventory.hasItem((Coin.TEN))) {
                changes.add(Coin.TEN);
                balance -= Coin.TEN.getValue();
                continue;

            } else if (balance >= Coin.FIVE.getValue() && cashInventory.hasItem((Coin.FIVE))) {
                changes.add(Coin.FIVE);
                balance -= Coin.FIVE.getValue();
                continue;
            }
             else throw new NotSufficientChangeException("NotSufficientChangeException!");
        }
        return changes;
    }


    public boolean hasSufficientChangeForAmount(long amount) {

        try {
            getChange(amount);
        } catch (NotSufficientChangeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean hasSufficientChange() {
        return currentBalance - currentItem.getPrice() > 0;
    }

    private Item collectItem() throws NotFullyPaidException {
        if( isFullyPaid() && hasSufficientChange()){
            itemInventory.decrease(currentItem);
            return currentItem;
        }else
            throw  new NotFullyPaidException("NotFullyPaidException!",
                    currentBalance-currentItem.getPrice());
    }

    private List<Coin> collectChange() throws NotSufficientChangeException {
        long amount = currentBalance - currentItem.getPrice();
        List<Coin> change = getChange(amount);
        updateCashInventory(change);
        currentBalance = 0;
        currentItem = null;

        return change;
    }

    @Override
    public List<Coin> refund() throws NotSufficientChangeException{

        List<Coin> refund = getChange(currentBalance);
        updateCashInventory(refund);
        currentBalance = 0;
        currentItem = null;

        return refund;
    }

    @Override
    public PurchaseAndCoins<Item, List<Coin>> collectItemAndChange() throws NotSufficientChangeException, NotFullyPaidException {
        totalSales += currentItem.getPrice();
        Item item = collectItem();
        List<Coin> list_of_coins = collectChange();
        return new PurchaseAndCoins<Item, List<Coin>>(item,list_of_coins);
    }

    @Override
    public void reset() {
        itemInventory.clear();
        cashInventory.clear();
        totalSales = 0;
        currentItem = null;
        currentBalance = 0;

    }

    public long getCurrentBalance(){
        return currentBalance;
    }

    public long getTotalSales(){
        return  totalSales;
    }

    public void printStats(){
        System.out.println("Total sales: " + totalSales);
        System.out.println("Curent item inventory: " + itemInventory);
        System.out.println("Curent Cash inventory: " + cashInventory);
    }
}
