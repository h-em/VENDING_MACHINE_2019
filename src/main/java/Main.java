import CustomExceptions.NotFullyPaidException;
import CustomExceptions.NotSufficientChangeException;
import CustomExceptions.SoldOutException;
import Enums.Coin;
import Enums.Item;

public class Main {

    public static void main(String[] args) {


       // VendingMachineImpl vm = new VendingMachineImpl();

        //cu factory Design
        VendingMachineImpl vm = (VendingMachineImpl) VendingMachineFactory.createVendingMacchine();


        vm.printStats();

        try {
            System.out.println(vm.selectItemAndGetPrice(Item.MARS));
        } catch (SoldOutException e) {
            e.printStackTrace();
        }

        vm.insertCoin(Coin.TEN);
        vm.insertCoin(Coin.TWENTY);
        vm.insertCoin(Coin.FIFTY);
        vm.insertCoin(Coin.FIFTY);

        try {
           System.out.println(vm.collectItemAndChange());
        } catch (NotSufficientChangeException e) {
            e.printStackTrace();
        } catch (NotFullyPaidException e) {
            System.out.println("Balance: " + e.getRemaining());
            e.printStackTrace();
        }
        vm.printStats();

        vm.insertCoin(Coin.TEN);

        vm.printStats();

    }
}
