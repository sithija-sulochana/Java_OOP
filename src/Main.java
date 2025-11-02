
interface Payable {
    boolean validate();
    String getReference();
}

interface Discount {
    double applyDiscount(double percentage);
    double finalAmount();
}


abstract class Payment implements Payable {
    public int amount;
    public String currency;
    public String status;
    public String referenceId;

    public Payment(int amount, String currency, String status, String referenceId) {
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.referenceId = referenceId;
    }

    // Abstract method - must be implemented by subclasses
    public abstract void processPayment();

    // Concrete method - shared among all payment types
    public void generateReceipt() {
        System.out.println("------ Payment Receipt ------");
        System.out.println("Amount      : " + amount);
        System.out.println("Currency    : " + currency);
        System.out.println("Status      : " + status);
        System.out.println("ReferenceID : " + referenceId);
        System.out.println("-----------------------------");
    }

    @Override
    public String getReference() {
        return referenceId;
    }
}

// ===========================
// Subclasses
// ===========================

class CashOnDelivery extends Payment {
    public String address;

    public CashOnDelivery(int amount, String currency, String status, String referenceId, String address) {
        super(amount, currency, status, referenceId);
        this.address = address;
    }

    @Override
    public void processPayment() {
        System.out.println("Cash will be collected at delivery address: " + address);
        this.status = "Pending Collection";
    }

    @Override
    public boolean validate() {

        return address != null && !address.isEmpty();
    }
}

class BankTransfer extends Payment {
    public BankTransfer(int amount, String currency, String status, String referenceId) {
        super(amount, currency, status, referenceId);
    }

    @Override
    public void processPayment() {
        System.out.println("Processing bank transfer for Reference ID: " + referenceId);
        this.status = "Completed";
    }

    @Override
    public boolean validate() {
        return referenceId != null && referenceId.startsWith("BNK");
    }
}

// ===========================
// Abstract Card Payment
// ===========================

abstract class CardPayment extends Payment {
    public String cardNumber;
    public String holder;
    public String expiry;

    public CardPayment(int amount, String currency, String status, String referenceId,
                       String cardNumber, String holder, String expiry) {
        super(amount, currency, status, referenceId);
        this.cardNumber = cardNumber;
        this.holder = holder;
        this.expiry = expiry;
    }

    @Override
    public boolean validate() {
        return cardNumber != null && cardNumber.length() == 16;
    }
}

// ===========================
// Concrete Card Payments
// ===========================

class CreditCardPayment extends CardPayment implements Discount {

    public CreditCardPayment(int amount, String currency, String status, String referenceId,
                             String cardNumber, String holder, String expiry) {
        super(amount, currency, status, referenceId, cardNumber, holder, expiry);
    }

    @Override
    public void processPayment() {
        System.out.println("Processing credit card payment for cardholder: " + holder);
        this.status = "Paid";
    }

    @Override
    public double applyDiscount(double percentage) {
        return amount - (amount * (percentage / 100));
    }

    @Override
    public double finalAmount() {
        return amount;
    }
}

class DebitCardPayment extends CardPayment implements Discount {

    public DebitCardPayment(int amount, String currency, String status, String referenceId,
                            String cardNumber, String holder, String expiry) {
        super(amount, currency, status, referenceId, cardNumber, holder, expiry);
    }

    @Override
    public void processPayment() {
        System.out.println("Processing debit card payment for cardholder: " + holder);
        this.status = "Paid";
    }

    @Override
    public double applyDiscount(double percentage) {
        return amount - (amount * (percentage / 100));
    }

    @Override
    public double finalAmount() {
        return amount;
    }
}

// ===========================
// Main Class
// ===========================

public class Main {
    public static void main(String[] args) {

        Payment cod = new CashOnDelivery(5000, "LKR", "Pending", "COD123", "No.25, Colombo");
        cod.processPayment();
        cod.generateReceipt();

        Payment bank = new BankTransfer(8000, "LKR", "Pending", "BNK5467");
        bank.processPayment();
        bank.generateReceipt();

        CreditCardPayment credit = new CreditCardPayment(
                10000, "LKR", "Pending", "CRD789",
                "1234567812345678", "Sithi", "12/30");

        credit.processPayment();
        credit.generateReceipt();
        System.out.println("After Discount: " + credit.applyDiscount(10));


        DebitCardPayment debit = new DebitCardPayment(7000, "LKR", "Pending", "DBT321",
                "8765432187654321", "John", "11/29");


        debit.processPayment();
        debit.generateReceipt();
        System.out.println("After Discount: " + debit.applyDiscount(5));

    }
}
