import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
	static Scanner scanner;

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}

	static void viewPurchaseDetails(Purchase purchase) {
		System.out.println("\n- Purchase Details -");
		purchase.printPurchase();
		System.out.println("");
		purchase.printItems();
	}

	static void viewPurchases(ArrayList<Purchase> purchases) {
		if (purchases == null || purchases.size() == 0) {
			System.out.println("\n- List Purchases -");
			System.out.println("There are no purchases.");
			return;
		}

		while (true) {
			System.out.println("\n- List Purchases -");
			for (int i = 0; i < purchases.size(); ++i) {
				System.out.print("" + (i + 1) + " - ");
				purchases.get(i).printPurchase();
			}

			System.out.println("Enter 'Q' to quit and go back.");
			System.out.print("Enter index of purchase to extend it: ");
			String chosenPurchaseNumber = scanner.nextLine();
			// TODO: check python code and update & fix following expression
			while (!"q".equalsIgnoreCase(chosenPurchaseNumber)
					|| (isNumeric(chosenPurchaseNumber) && (Integer.parseInt(chosenPurchaseNumber) < 1 && Integer.parseInt(chosenPurchaseNumber) > purchases.size()))) {
				System.out.println("Error. Undefined input.");
				System.out.println("Enter 'Q' to quit and go back.");
				System.out.print("Enter index of purchase to extend it: ");
				chosenPurchaseNumber = scanner.nextLine();
			}

			if ("q".equalsIgnoreCase(chosenPurchaseNumber)) {
				return;
			}

			viewPurchaseDetails(purchases.get(Integer.parseInt(chosenPurchaseNumber) - 1));
		}
	}

	// Creates an item with the user input and returns it
	static Item createInputItem() {
		System.out.println("- Add Item -");
		int unitPriceCents = 0, totalPrice, totalAmount;

		System.out.print("Item name: ");
		String name = scanner.nextLine();

		boolean isPaid = false;
		String input = "";
		while (input.length() != 1 || !("y".equalsIgnoreCase(input) || "n".equalsIgnoreCase(input))) {
			System.out.print("Was this item paid? [y/n]: ");
			input = scanner.nextLine();
		}
		if ("y".equalsIgnoreCase(input)) {
			isPaid = true;
		} else if ("n".equalsIgnoreCase(input)) {
			isPaid = false;
		}

		System.out.print("Unit type [gram, milliliter, apple...]: ");
		String unitType = scanner.nextLine();

		input = "";
		while (input.length() != 1 || !("u".equalsIgnoreCase(input) || "t".equalsIgnoreCase(input))) {
			System.out.print("Want to enter unit price or total price of the item? [u/t]: ");
			input = scanner.nextLine();
		}
		if ("u".equalsIgnoreCase(input)) {
			// Input price in dollar notation (double)
			System.out.print("Unit price (" + CurrencyConversions.displayCurrency + "): ");
			double inputPrice = scanner.nextDouble();

			// Change price to cents (int)
			inputPrice *= CurrencyConversions.KURUSH_PER_TRY;
			unitPriceCents = (int) inputPrice; // TODO: rounding
		} else if ("t".equalsIgnoreCase(input)) {
			System.out.print("Total price of the item (" + CurrencyConversions.displayCurrency + "): ");
			totalPrice = scanner.nextInt();
			System.out.print("Total amount of " + unitType + ": ");
			totalAmount = scanner.nextInt();
			unitPriceCents = totalPrice / totalAmount;
			unitPriceCents *= CurrencyConversions.KURUSH_PER_TRY;
		}


		System.out.print("Amount of " + unitType + "owed: ");
		int owedUnitQuantity = scanner.nextInt();

		System.out.println("");
		return new Item(name, unitPriceCents, unitType, owedUnitQuantity, isPaid);
	}

	static void addPurchase(ArrayList<Purchase> purchases) {
		System.out.println("\n- Add Purchase -");

		System.out.print("Name: ");
		String name = scanner.nextLine();

		System.out.print("Date (\"now\" for today): ");
		String date = scanner.nextLine();
		if ("now".equals(date))
			date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		ArrayList<Item> items = new ArrayList<>();
		while (true) {
			String wantToAdd = "";
			while (wantToAdd.length() != 1 || !("y".equalsIgnoreCase(wantToAdd) || "n".equalsIgnoreCase(wantToAdd))) {
				System.out.print("Add a new item to the purchase? [y/n]: ");
				wantToAdd = scanner.nextLine();
			}
			if ("y".equalsIgnoreCase(wantToAdd)) {
				items.add(createInputItem());
			} else if ("n".equalsIgnoreCase(wantToAdd)) {
				break;
			}
		}

		purchases.add(new Purchase(name, date, items));
	}

	static void printMainMenu() {
		System.out.println("\nTaha Management System");
		System.out.println("1 - Add purchase");
		System.out.println("2 - View purchase");
		System.out.println("3 - Exit");
		// TODO: edit, delete purchases
	}

	public static void main(String[] args) {
		scanner = new Scanner(System.in);

		ArrayList<Purchase> purchases = new ArrayList<>(); // arrays are being passed as pass-by-reference in java
		while (true) {
			printMainMenu();
			System.out.print("Choose option: ");
			int option = scanner.nextInt();
			scanner.nextLine();
			switch (option) {
				case 1:
					addPurchase(purchases);
					break;
				case 2:
					viewPurchases(purchases);
					break;
				case 3:
					System.exit(0);
					break;
				default:
					System.out.println("Error. Undefined input.");
					break;
			}
		}
	}
}
