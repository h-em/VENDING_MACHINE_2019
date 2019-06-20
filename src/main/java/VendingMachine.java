import CustomExceptions.NotFullyPaidException;
import CustomExceptions.NotSufficientChangeException;
import CustomExceptions.SoldOutException;
import Enums.Coin;
import Enums.Item;

import java.util.List;

public interface VendingMachine {

    long  selectItemAndGetPrice(Item item) throws SoldOutException; //   SoldOutException
    void insertCoin(Coin coin);
    List<Coin> refund() throws NotSufficientChangeException;   //NotSufficientChangeException
    PurchaseAndCoins<Item, List<Coin>> collectItemAndChange() throws NotSufficientChangeException, NotFullyPaidException; // NotSufficientChangeException NotFullyPaid
    void reset();

}
