import java.util.HashMap;
import java.util.Map;

public class Inventory<T> {

    private Map<T, Integer> inventory;

    public Inventory() {
        inventory = new HashMap<T, Integer>();
    }

    public int getQuantity(T item) { // returneaza stocul unui item
        return inventory.get(item);
    }

    public void add(T item) { //creste valoarea
        int quantity = inventory.containsKey(item) ? (inventory.get(item) + 1) : 0;
        inventory.put(item, quantity);
    }

    public boolean hasItem(T item) { //verific daca item-ul are stocul > 0
        return getQuantity(item) > 0;
    }


    public void decrease(T item) { //scad valoarea
        if (inventory.get(item) > 0) {
            inventory.put(item, (inventory.get(item) - 1));
        }
    }

    public void put(T item, int quantity) {
        inventory.put(item, quantity);
    }

    public void clear() {
        inventory.clear();
    }

    @Override
    public String toString() {
        return "" + inventory;

    }

}
