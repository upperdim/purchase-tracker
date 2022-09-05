import java.util.ArrayList;
import java.util.Date;

public class Purchase {
    static int numPurchases = 0;

    String id;
    String name;
    String date;
    ArrayList<Item> items;
    boolean isPaid;
    int debtCents;

    public Purchase(String name, String date, ArrayList<Item> items) {
        ++numPurchases;
        this.id = String.valueOf(numPurchases);
        this.name = name;
        this.date = date;
        this.items = items;
        this.isPaid = checkIfPaid();
        this.debtCents = calculateItemDebtSum();
    }

    boolean checkIfPaid() {
        boolean isPaid = true;
        for (Item item: this.items) {
            if (!item.isPaid()) {
                isPaid = false;
                break;
            }
        }
        return isPaid;
    }

    int calculateItemDebtSum() {
        int totalDebtCents = 0;
        for(Item item: this.items)
            totalDebtCents += item.debtCents;
        return totalDebtCents;
    }

    void printPurchase() {
        System.out.println(this.name
            + " #" + this.id
            + " [" + this.date.toString()
            + "] (" + (this.debtCents / CurrencyConversions.KURUSH_PER_TRY)
            + CurrencyConversions.displayCurrency + ") "
            + (isPaid ? "[+] Paid" : "[ ] NOT Paid")
        );
    }

    void printItems(int indent) {
        if (this.items == null || this.items.size() == 0) {
            System.out.println("This purchase has no items.");
            return;
        }

        System.out.println("Items:");
        for (int i = 0; i < this.items.size(); ++i) {
            System.out.print("" + (i + 1) + " - ");
            this.items.get(i).printItem(indent);
            System.out.println("");
        }
    }

    void printItems() {
        printItems(0); // default parameter for `indent`
    }

    void addItem(Item item) {
        this.items.add(item);
    }

    void removeItem(int index) {
        this.items.remove(index);
    }
}
