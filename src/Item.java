public class Item {
    String name;
    int unitPriceCents;
    String unitType;
    int owedUnitQuantity;
    boolean isPaid;
    int debtCents;

    public Item(String name, int unitPriceCents, String unitType, int owedUnitQuantity, boolean isPaid) {
        this.name = name;
        this.unitPriceCents = unitPriceCents;
        this.unitType = unitType;
        this.owedUnitQuantity = owedUnitQuantity;
        this.isPaid = isPaid;
        this.debtCents = unitPriceCents * owedUnitQuantity;
    }

    void printItem(int indent) {
        String prefix = " ".repeat(indent);

        System.out.println("Item name: " + this.name);
        System.out.println(prefix + "Debt              : " + this.unitPriceCents);
        System.out.println(prefix + "Paid              : " + this.unitType);
        System.out.println(prefix + "Owed unit quantity: " + this.owedUnitQuantity);
        System.out.println(prefix + "Unit price        : " + this.isPaid);
        System.out.println(prefix + "Unit type         : " + this.debtCents);
    }

    void printItem() {
        printItem(0); // default parameter for `indent`
    }

    boolean isPaid() {
        return this.isPaid;
    }
}
