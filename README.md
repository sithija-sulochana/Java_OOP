# Abstraction 

 * Abstraction is process of hiding internal details and showing only essential functionalities.
 
****

There are two ways to implement abstraction in code level.
 01. Abstract Classes
 2. Interfaces

## Abstract Class

Suppose that you need to create a **Payment Management System** and you have to create **processingPayment()** method for two types of payment method (Credit and Debit) with two different business logics.

In that case, you can't create the actual method in the parent class (Payment) because we can't implement two business logic in one parent method.

To remedy this, we use **abstract class**. 


````
public abstract class Payment {
public double amount;
public String paymentId;

    public Payment(double amount,String paymentId){
        this.amount = amount;
        this.paymentId = paymentId;
    }

    //concrete method in an abstract class
    public void showPaymentInformation(){
        System.out.println("Payment Id : "+this.paymentId+ " Amount : "+this.amount);
    }

    //Abstract Class
    public abstract void ProcessPayment();


}
```` 

There are two different method in the payment class. There are,

1. Concrete Method : **Can implement(has a body) and can exist in any class**
2. Abstract Method : **Has no body(just for declaring the method) and can only exist in either another abstract class or an interface.**

Then we will consider the CreditPayment class (A child class of Payment).

````
//A child class of the payment (parent) class
public class CardPayment extends Payment  {
    public String cardNumnber;

    public CardPayment(double amount,String paymentId,String cardNumnber){
        super(amount,paymentId);

        this.cardNumnber = cardNumnber;
    }


    @Override
    public void ProcessPayment() {
        System.out.println("Processing Payment from Card of amount "+ this.amount);
    }
}
````

You can see that the processPayment() method has been overridden in the child class.

At the end of the day, **You can't create an object of the parent class in any place. Instead, you need to create an object of the child class and access the both parent and child method (without the abstract method in the parent class).**

---

## Upcasting 

**Upcasting** is a typecasting process where a child class object is referenced by a parent class variable. It allows access only to the parent class members (and any overridden methods from the child).

````
 Payment cp = new CardPayment(500,"DDD","sa1545");
 cp.ProcessPayment();
````

In that case, you should name the object's data type as the name of parent class.

Then you can access the parent class's elements through child object's variable name

---
## Downcasting
**Downcasting** is a typecasting process where a parent class reference (that actually points to a child object) is cast back to the child class type.


````
Payment cp = new CardPayment(500, "DDD", "sa1545"); // upcasting
CardPayment sa = (CardPayment) cp; // downcasting
````
Then you can access the child's element through "sa".

---

# interfaces 

* In java, classes can't extend multiple classes. In other hand, we can't do multiple inheritance in java.
* As a solution for this, Java introduced interfaces that are used to create multiple inheritance behavior in a safe and structured way.
