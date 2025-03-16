import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000.0);
        TransactionHistory history = new TransactionHistory();

        // Начальное состояние
        account.showStatus();
        history.save(account.getSnapshot());  // Снимок 0: начальное состояние

        // Операция 1: Внесение депозита
        account.deposit(500.0, "USD", "Main");
        account.setInterestRate(3.5);
        account.showStatus();
        history.save(account.getSnapshot());  // Снимок 1: после депозита

        // Операция 2: Снятие средств
        account.withdraw(200.0, "USD", "Main");
        account.setAccountStatus("Pending");
        account.showStatus();
        history.save(account.getSnapshot());  // Снимок 2: после снятия

        // Операция 3: Большой депозит и смена филиала
        account.deposit(1500.0, "EUR", "Central");
        account.setInterestRate(4.0);
        account.showStatus();
        history.save(account.getSnapshot());  // Снимок 3: после большого депозита

        // Операция 4: Снятие с использованием кредитного лимита
        account.withdraw(2200.0, "EUR", "Central");
        account.setAccountStatus("Active");
        account.showStatus();
        history.save(account.getSnapshot());  // Снимок 4: после снятия с кредитом

        // Операция 5: Изменение лимита и статуса
        account.deposit(300.0, "EUR", "Central");
        account.setInterestRate(4.5);
        account.setAccountStatus("VIP");
        account.showStatus();
        history.save(account.getSnapshot());  // Снимок 5: после финального депозита

        // Показываем историю
        System.out.println("Transaction History:");
        history.showHistory();

        // Восстановление к разным состояниям
        System.out.println("\nRestoring to snapshot 2:");
        account.restore(history.getSnapshot(2));
        account.showStatus();

        System.out.println("\nRestoring to last snapshot:");
        account.restore(history.getLastSnapshot());
        account.showStatus();

        System.out.println("\nRestoring to initial state (snapshot 0):");
        account.restore(history.getSnapshot(0));
        account.showStatus();
    }
}

// MEMENTO FOR BANK ACCOUNT
class AccountMemento {
    private final double balance;
    private final String lastTransaction;
    private final String accountNumber;
    private final String currency;
    private final double interestRate;
    private final String accountStatus;
    private final int transactionCount;
    private final String branch;
    private final double creditLimit;

    public AccountMemento(double balance,
                          String lastTransaction,
                          String accountNumber,
                          String currency,
                          double interestRate,
                          String accountStatus,
                          int transactionCount,
                          String branch,
                          double creditLimit
    ) {
        this.balance = balance;
        this.lastTransaction = lastTransaction;
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.interestRate = interestRate;
        this.accountStatus = accountStatus;
        this.transactionCount = transactionCount;
        this.branch = branch;
        this.creditLimit = creditLimit;
    }

    public double getBalance() { return balance; }
    public String getLastTransaction() { return lastTransaction; }
    public String getAccountNumber() { return accountNumber; }
    public String getCurrency() { return currency; }
    public double getInterestRate() { return interestRate; }
    public String getAccountStatus() { return accountStatus; }
    public int getTransactionCount() { return transactionCount; }
    public String getBranch() { return branch; }
    public double getCreditLimit() { return creditLimit; }
}

// ORIGINATOR FOR BANK ACCOUNT
class BankAccount {
    private double balance;
    private String lastTransaction;
    private String accountNumber;
    private String currency;
    private double interestRate;
    private String accountStatus;
    private int transactionCount;
    private String branch;
    private double creditLimit;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
        this.lastTransaction = "Initial deposit";
        this.accountNumber = "ACC" + (int)(Math.random() * 10000);
        this.currency = "USD";
        this.interestRate = 2.0;
        this.accountStatus = "Active";
        this.transactionCount = 0;
        this.branch = "Main";
        this.creditLimit = 1000.0;
    }

    public void deposit(double amount, String currency, String branch) {
        balance += amount;
        lastTransaction = "Deposit: +" + amount;
        this.currency = currency;
        this.branch = branch;
        transactionCount++;
    }

    public void withdraw(double amount, String currency, String branch) {
        if (amount <= balance + creditLimit) {
            balance -= amount;
            lastTransaction = "Withdrawal: -" + amount;
            this.currency = currency;
            this.branch = branch;
            transactionCount++;
        }
    }

    public void setInterestRate(double rate) {
        this.interestRate = rate;
    }

    public void setAccountStatus(String status) {
        this.accountStatus = status;
    }

    public AccountMemento getSnapshot() {
        return new AccountMemento(balance, lastTransaction, accountNumber,
                currency, interestRate, accountStatus,
                transactionCount, branch, creditLimit);
    }

    public void restore(AccountMemento memento) {
        this.balance = memento.getBalance();
        this.lastTransaction = memento.getLastTransaction();
        this.accountNumber = memento.getAccountNumber();
        this.currency = memento.getCurrency();
        this.interestRate = memento.getInterestRate();
        this.accountStatus = memento.getAccountStatus();
        this.transactionCount = memento.getTransactionCount();
        this.branch = memento.getBranch();
        this.creditLimit = memento.getCreditLimit();
    }

    public void showStatus() {
        System.out.printf("Account: %s, Balance: %.2f %s, Status: %s%n" +
                        "Last transaction: %s, Transactions: %d%n" +
                        "Interest Rate: %.1f%%, Branch: %s, Credit Limit: %.2f%n%n",
                accountNumber, balance, currency, accountStatus,
                lastTransaction, transactionCount,
                interestRate, branch, creditLimit);
    }
}

// CARETAKER OF HISTORY
class TransactionHistory {
    private ArrayList<AccountMemento> operationsSnapshots;

    TransactionHistory() {
        operationsSnapshots = new ArrayList<>();
    }

    public void save(AccountMemento memento) {
        operationsSnapshots.add(memento);
    }

    public void showHistory() {
        for (int i = 0; i < operationsSnapshots.size(); i++) {
            System.out.println("Snapshot " + i + ": Balance " +
                    operationsSnapshots.get(i).getBalance() + " " +
                    operationsSnapshots.get(i).getLastTransaction());
        }
    }

    public AccountMemento getSnapshot(int id) {
        return operationsSnapshots.get(id);
    }

    public AccountMemento getLastSnapshot() {
        if (operationsSnapshots.isEmpty()) return null;
        var res = operationsSnapshots.get(operationsSnapshots.size() - 1);
        operationsSnapshots.remove(operationsSnapshots.size() - 1);
        return res;
    }
}