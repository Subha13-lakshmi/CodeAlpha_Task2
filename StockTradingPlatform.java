import java.util.*;

class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    @Override
    public String toString() {
        return symbol + " - $" + price;
    }
}

class Trade {
    String type; // "BUY" or "SELL"
    Stock stock;
    int quantity;
    Date date;

    Trade(String type, Stock stock, int quantity) {
        this.type = type;
        this.stock = stock;
        this.quantity = quantity;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return type + " " + quantity + " of " + stock.symbol + " @ $" + stock.price + " on " + date;
    }
}

class User {
    String name;
    double balance;
    Map<String, Integer> portfolio;
    List<Trade> history;

    User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.portfolio = new HashMap<>();
        this.history = new ArrayList<>();
    }

    void buyStock(Stock stock, int quantity) {
        double cost = stock.price * quantity;
        if (balance >= cost) {
            balance -= cost;
            portfolio.put(stock.symbol, portfolio.getOrDefault(stock.symbol, 0) + quantity);
            history.add(new Trade("BUY", stock, quantity));
            System.out.println("✅ Bought " + quantity + " of " + stock.symbol);
        } else {
            System.out.println("❌ Insufficient balance!");
        }
    }

    void sellStock(Stock stock, int quantity) {
        int owned = portfolio.getOrDefault(stock.symbol, 0);
        if (owned >= quantity) {
            balance += stock.price * quantity;
            portfolio.put(stock.symbol, owned - quantity);
            history.add(new Trade("SELL", stock, quantity));
            System.out.println("✅ Sold " + quantity + " of " + stock.symbol);
        } else {
            System.out.println("❌ Not enough shares to sell!");
        }
    }

    void showPortfolio() {
        System.out.println("\n📊 Portfolio of " + name + ":");
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            if (entry.getValue() > 0)
                System.out.println(entry.getKey() + " : " + entry.getValue() + " shares");
        }
        System.out.printf("💰 Available Balance: $%.2f\n", balance);
    }

    void showHistory() {
        System.out.println("\n📁 Trade History:");
        for (Trade t : history)
            System.out.println(t);
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Sample market
        List<Stock> market = Arrays.asList(
            new Stock("AAPL", 150.00),
            new Stock("GOOG", 2800.00),
            new Stock("TSLA", 750.00),
            new Stock("AMZN", 3400.00)
        );

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        User user = new User(name, 10000); // Starting balance

        int choice;
        do {
            System.out.println("\n📈 STOCK TRADING MENU");
            System.out.println("1. View Market Stocks");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Trade History");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n📋 Market Stocks:");
                    for (int i = 0; i < market.size(); i++) {
                        System.out.println(i + ". " + market.get(i));
                    }
                    break;

                case 2:
                    System.out.print("Enter stock index to BUY: ");
                    int buyIndex = scanner.nextInt();
                    System.out.print("Enter quantity: ");
                    int buyQty = scanner.nextInt();
                    if (buyIndex >= 0 && buyIndex < market.size()) {
                        user.buyStock(market.get(buyIndex), buyQty);
                    } else {
                        System.out.println("❌ Invalid stock index.");
                    }
                    break;

                case 3:
                    System.out.print("Enter stock index to SELL: ");
                    int sellIndex = scanner.nextInt();
                    System.out.print("Enter quantity: ");
                    int sellQty = scanner.nextInt();
                    if (sellIndex >= 0 && sellIndex < market.size()) {
                        user.sellStock(market.get(sellIndex), sellQty);
                    } else {
                        System.out.println("❌ Invalid stock index.");
                    }
                    break;

                case 4:
                    user.showPortfolio();
                    break;

                case 5:
                    user.showHistory();
                    break;

                case 0:
                    System.out.println("👋 Exiting. Thank you for trading!");
                    break;

                default:
                    System.out.println("❌ Invalid option.");
            }

        } while (choice != 0);

        scanner.close();
    }
}
