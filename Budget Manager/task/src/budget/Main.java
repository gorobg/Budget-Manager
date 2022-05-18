package budget;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new BudgetManager().startManager();
    }
}

class BudgetManager {
    private Budget myBudget = new Budget();

    //main menu method for selecting general action
    void startManager() throws IOException, ClassNotFoundException {
        boolean running = true;
        while (running) {
            System.out.println("Choose your action:\n" +
                    "1) Add income\n" +
                    "2) Add purchase\n" +
                    "3) Show list of purchases\n" +
                    "4) Balance\n" +
                    "5) Save\n" +
                    "6) Load\n" +
                    "7) Analyze (Sort)\n" +
                    "0) Exit");
            switch (Main.scanner.nextLine()) {
                case "1":
                    System.out.println("\nEnter Inncome:");
                    myBudget.addIncome(Double.parseDouble(Main.scanner.nextLine()));
                    System.out.println("Income was added!\n");
                    break;
                case "2":
                    System.out.println();
                    typeOfPurchaseCreate();
                    break;
                case "3":
                    System.out.println();
                    if (myBudget.getPurchases().isEmpty()) {
                        System.out.println("The purchase list is empty!\n");
                    } else {
                        typeOfPurchaseSelect();
                    }
                    break;
                case "4":
                    System.out.printf("\nBalance: $%.2f\n\n", +myBudget.getBallance());
                    break;
                case "5":
                    myBudget.saveBudget();
                    System.out.println("\nPurchases were Saved!\n");
                    break;
                case "6":
                    myBudget = myBudget.loadBudget();
                    System.out.println("\nPurchases were Loaded!\n");
                    break;
                case "7":
                    System.out.println();
                    sortPurchaseSelect();

                    break;
                case "0":
                    running = false;
                    break;
            }
        }
        System.out.println("\nBye!");
    }

    //submenu for selection the type of purchase to make
    void typeOfPurchaseCreate() {
        boolean selectPurchaseTypeMenu = true;
        while (selectPurchaseTypeMenu) {
            System.out.println("Choose the type of purchase\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) Back");
            String inputType = Main.scanner.nextLine();
            switch (inputType) {
                case "1":
                    myBudget.addPurchase(new Purchase(PurchaseType.FOOD));
                    System.out.println("Purchase was added!\n");
                    break;
                case "2":
                    myBudget.addPurchase(new Purchase(PurchaseType.CLOTHES));
                    System.out.println("Purchase was added!\n");
                    break;
                case "3":
                    myBudget.addPurchase(new Purchase(PurchaseType.ENTERTAINMENT));
                    System.out.println("Purchase was added!\n");
                    break;
                case "4":
                    myBudget.addPurchase(new Purchase(PurchaseType.OTHER));
                    System.out.println("Purchase was added!\n");
                    break;
                case "5":
                    selectPurchaseTypeMenu = false;
                    System.out.println();
                    break;
                default:
                    System.out.println("No such type!");
                    break;
            }
        }
    }

    //menu for selecting type of purchase information to print
    void typeOfPurchaseSelect() {
        boolean selectPurchaseTypeMenu = true;
        while (selectPurchaseTypeMenu) {
            System.out.println("Choose the type of purchase\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) All\n" +
                    "6) Back");
            switch (Main.scanner.nextLine()) {
                case "1":
                    System.out.println("\nFood:");
                    printPurchases(PurchaseType.FOOD);
                    break;
                case "2":
                    System.out.println("\nClothes:");
                    printPurchases(PurchaseType.CLOTHES);
                    break;
                case "3":
                    System.out.println("\nEntertainment:");
                    printPurchases(PurchaseType.ENTERTAINMENT);
                    break;
                case "4":
                    System.out.println("\nOther:");
                    printPurchases(PurchaseType.OTHER);
                    break;
                case "5":
                    System.out.println("\nAll:");
                    printPurchases();
                    break;
                case "6":
                    selectPurchaseTypeMenu = false;
                    System.out.println();
                    break;
                default:
                    System.out.println("No such type!");
                    break;
            }
        }
    }

    //method for sort selection menu
    void sortPurchaseSelect(){
        boolean selectPurchaseSortMenu = true;
        while (selectPurchaseSortMenu) {
            System.out.println("How do you want to sort?\n" +
                    "1) Sort all purchases\n" +
                    "2) Sort by type\n" +
                    "3) Sort certain type\n" +
                    "4) Back");
            switch (Main.scanner.nextLine()) {
                case "1":
                    sortAndPrintPurchases(myBudget.getPurchases(),"All");
                    break;
                case "2":
                    System.out.println("\nTypes:");
                    sortedPurchase(1);
                    break;
                case "3":
                    System.out.println("\nChoose the type of purchase\n" +
                            "1) Food\n" +
                            "2) Clothes\n" +
                            "3) Entertainment\n" +
                            "4) Other");
                    String input = Main.scanner.nextLine();
                    if (input.equals("1")){
                        sortAndPrintPurchases(filterPurchaseTypes(PurchaseType.FOOD, myBudget.getPurchases()),"Food");
                    } else if (input.equals("2")) {
                        sortAndPrintPurchases(filterPurchaseTypes(PurchaseType.CLOTHES, myBudget.getPurchases()),"Clothes");
                    } else if (input.equals("3")) {
                        sortAndPrintPurchases(filterPurchaseTypes(PurchaseType.ENTERTAINMENT, myBudget.getPurchases()),"Entertainment");
                    } else if (input.equals("4")) {
                        sortAndPrintPurchases(filterPurchaseTypes(PurchaseType.OTHER, myBudget.getPurchases()),"Other");
                    } else {
                        System.out.println("\nInvalid type selected\n");
                    }
                    break;
                case "4":
                    selectPurchaseSortMenu = false;
                    System.out.println();
                    break;
                default:
                    System.out.println("\nNo such type!\n");
                    break;
            }
        }
    }

    List<Purchase> filterPurchaseTypes(PurchaseType purchaseType, List<Purchase> listToFilter){
        return listToFilter
                .stream()
                .filter(n -> n.getPurchaseType() == purchaseType)
                .collect(Collectors.toList());
    }

    void sortAndPrintPurchases(List<Purchase> purchasesToSort, String title){
        if(purchasesToSort.isEmpty()){
            System.out.println("\nThe purchase list is empty!\n");
        }else{
            System.out.printf("\n%s:\n", title);
            purchasesToSort.stream().sorted().forEach(System.out::println);
            double sum = purchasesToSort.stream().mapToDouble(n->n.getPrice()).sum();
            System.out.printf("Total sum: $%.2f\n\n", sum);
        }
    }

    void sortedPurchase(int type ){
            double sumFood = myBudget.getPurchases().stream()
                    .filter(x -> x.getPurchaseType() == PurchaseType.FOOD)
                    .mapToDouble(n->n.getPrice()).sum();
            double sumClothes = myBudget.getPurchases().stream()
                    .filter(x -> x.getPurchaseType() == PurchaseType.CLOTHES)
                    .mapToDouble(n->n.getPrice()).sum();
            double sumEntertainment = myBudget.getPurchases().stream()
                    .filter(x -> x.getPurchaseType() == PurchaseType.ENTERTAINMENT)
                    .mapToDouble(n->n.getPrice()).sum();
            double sumOther = myBudget.getPurchases().stream()
                    .filter(x -> x.getPurchaseType() == PurchaseType.OTHER)
                    .mapToDouble(n->n.getPrice()).sum();
            double sum = myBudget.getPurchases().stream().mapToDouble(n->n.getPrice()).sum();
            System.out.printf("Food - $%.2f\n" +
                    "Entertainment - $%.2f\n" +
                    "Clothes - $%.2f\n" +
                    "Other - $%.2f\n" +
                    "Total sum: $%.2f\n\n", sumFood,sumEntertainment,sumClothes,sumOther,sum);
    }

    void printPurchases(PurchaseType type) {
        double totalPurchaseCost = 0;
        int count = 0;
        for (Purchase purchase : myBudget.getPurchases()) {
            if (purchase.getPurchaseType() == type) {
                System.out.println(purchase);
                count++;
                totalPurchaseCost += purchase.getPrice();
            }
        }
        System.out.printf("%s", count > 0
                ? String.format("Total sum: $%.2f\n\n", totalPurchaseCost)
                : "The purchase list is empty!\n\n");
    }

    void printPurchases() {
        double totalPurchaseCost = 0;
        for (Purchase purchase : myBudget.getPurchases()) {
            System.out.println(purchase);
            totalPurchaseCost += purchase.getPrice();
        }
        System.out.printf("Total sum: $%.2f\n\n", totalPurchaseCost);
    }

}

class Budget implements Serializable {
    private double ballance;
    private List<Purchase> purchases = new ArrayList<>();

    void addIncome(double additionalIncome){
        this.ballance += additionalIncome;
    }

    public double getBallance() {
        return ballance;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    void addPurchase(Purchase purchase){
        if(this.ballance >= purchase.getPrice()) {
            purchases.add(purchase);
            this.ballance -= purchase.getPrice();
        } else {
            System.out.println("Not enough money! Work harder, lazya**!");
        }
    }

    public void saveBudget () throws IOException {
        String fileName= "purchases.txt";
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
    }

    public Budget loadBudget () throws IOException, ClassNotFoundException {
        String fileName= "purchases.txt";
        FileInputStream fin = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fin);
        Budget loadedBudget= (Budget) ois.readObject();
        ois.close();
        return loadedBudget;
    }
}

enum PurchaseType{
    FOOD, CLOTHES, ENTERTAINMENT, OTHER
}

class Purchase implements Serializable, Comparable<Purchase> {

    private double price;
    private String name;
    private PurchaseType purchaseType;

    public Purchase(PurchaseType purchaseType){
        this.purchaseType = purchaseType;

        System.out.println("\nEnter purchase name:");
        setName(Main.scanner.nextLine());
        System.out.println("Enter its price:");
        setPrice(Double.parseDouble(Main.scanner.nextLine()));

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public PurchaseType getPurchaseType() {
        return purchaseType;
    }

    @Override
    public String toString() {
        return name + " $" + String.format("%.2f",price);
    }

    //for sorting by price
    @Override
    public int compareTo(Purchase o) {
        return Double.compare(o.getPrice(), getPrice());
    }
}

