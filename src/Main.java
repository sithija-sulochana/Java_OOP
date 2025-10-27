
class Payment{
    public double amount;
    public String currency;
    public String status;

    public Payment(double amount,String currency, String status){
        this.amount = amount;
        this.currency = currency;
        this.status = status;
    }


    public void processpayment(){
        System.out.println("Processing generic payment");
    }
    public void genericReceipt(){
        System.out.println("Receipt for amount "+this.amount+" Currency :"+this.currency);
    }
    public String markAsCompleated(){
        this.status = "COMPLEATED";
        return this.status;
    }
}

class CahsOnDelivery extends Payment{
    public String deliveryAddress;
    public CahsOnDelivery(double amount,String currency, String status, String address){
        super(amount,currency,status);
        this.deliveryAddress = address;
    }


    @Override
    public void processpayment(){
        System.out.println("Cash will be collected at dellivery address : "+this.deliveryAddress);

    }

}

 class BankTransfer extends Payment{
    public String bankName;
    public String accountNumber;
    public String referenceCode;

    public BankTransfer(double amount,String currency, String status,String bankName,String accountNumber,String referenceCode){
        super(amount, currency, status);
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.referenceCode = referenceCode;
    }
    @Override
     public void processpayment(){
        System.out.println("Initiating bank transfer to  "+this.bankName+ " using account "+this.accountNumber+ " with reference "+ this.referenceCode);
        this.markAsCompleated();
    }

}
class CardPayment extends Payment{
    public String cardNumber;
    public String cardHolderName;
    public String expireDate;


    public CardPayment(double amount,String currency, String status,String number,String name,String expDate){
        super(amount, currency, status);
        this.cardNumber = number;
        this.cardHolderName = name;
        this.expireDate = expDate;
    }
    @Override
    public void processpayment(){
        System.out.println("Authorizing card payment for "+ this.cardHolderName);

    }
    public void validCard(){
        System.out.println("Validating card number format ...");

    }
    class DebitCardPayment extends CardPayment{
        public double avilableBalance;

        public DebitCardPayment(double amount,String currency, String status,String number,String name,String expDate ,double balance){
            super(amount, currency, status, number, name, expDate);
            this.avilableBalance = balance;
        }

        @Override
        public void processpayment(){
            System.out.println("Debiting from account balnce "+ this.avilableBalance+ " for amount"+ this.amount);


        }
        public void checkSufficientBalance(){
            System.out.println("Checking if balance covers amount ");
        }
    }
    class CreditCardPayment extends CardPayment{
        public double creditLimit;
        public CreditCardPayment(double amount,String currency, String status,String number,String name,String expDate ,double creditLimit){
            super(amount, currency, status, number, name, expDate);
            this.creditLimit = creditLimit;

        }

        @Override
        public void processpayment(){
            System.out.println("Charging credit card with limit "+this.creditLimit+ " for amount "+this.amount);

        }
        public void applyInterest(){
            System.out.println("Applying interest if payment is settleed by due date");

        }
    }
}


public class Main {
    public static void main(String[] args) {
        CahsOnDelivery cod1 = new CahsOnDelivery(1500, "USD", "PENDING", "123 Main Street");
        BankTransfer bt1 = new BankTransfer(2000, "USD", "PENDING", "ABC Bank", "12345678", "REF001");

        CardPayment outer = new CardPayment(1000, "USD", "PENDING", "1111-2222-3333-4444", "Sithija", "12/25");
        CardPayment.CreditCardPayment cp = outer.new CreditCardPayment(1000, "USD", "PENDING", "1111-2222-3333-4444", "Sithija", "12/25", 5000);
        CardPayment.DebitCardPayment dp = outer.new DebitCardPayment(500, "USD", "PENDING", "1111-2222-3333-4444", "Sithija", "12/25", 2000);

        cod1.processpayment();
        bt1.processpayment();
        cp.processpayment();
        dp.processpayment();

        cod1.genericReceipt();
        bt1.genericReceipt();
        cp.genericReceipt();
        dp.genericReceipt();
    }
}

