# Abstraction in Java — Clean & Friendly Guide

> Learn abstraction with clear examples, when to use abstract classes vs interfaces, and short demos of upcasting/downcasting.

---

[TOC]
- [What is Abstraction?](#what-is-abstraction)
- [When to Use Abstract Class vs Interface](#when-to-use-abstract-class-vs-interface)
- [Abstract Class Example — Payment System](#abstract-class-example---payment-system)
- [Upcasting & Downcasting](#upcasting--downcasting)
- [Interfaces — Quick Overview](#interfaces---quick-overview)
- [Best Practices & Takeaways](#best-practices--takeaways)

---

## What is Abstraction?
Abstraction is the process of hiding implementation details while exposing only the essential features. In Java this is provided by:
- abstract classes
- interfaces

Abstraction helps you design simpler APIs, separate concerns, and enforce contracts for subclasses/implementers.

---

## When to Use Abstract Class vs Interface

- Use an **abstract class** when:
  - You want to provide some common implementation (concrete methods + fields).
  - You expect subclasses to share state or protected helpers.
  - You want to add new methods with default behavior later (without breaking subclasses).

- Use an **interface** when:
  - You want a pure contract without instance state.
  - You need multiple inheritance of type (a class can implement many interfaces).
  - You want maximum flexibility for unrelated classes to conform to the same contract.

---

## Abstract Class Example — Payment System

This example shows an abstract Payment base class with a concrete helper method and an abstract method that specific payment types must implement.

Note: Java method names commonly use lowerCamelCase (`processPayment()`); adapt to your repo’s code style if needed.

```java
// Payment.java
public abstract class Payment {
    protected double amount;
    protected String paymentId;

    public Payment(double amount, String paymentId) {
        this.amount = amount;
        this.paymentId = paymentId;
    }

    // Concrete method available to all subclasses
    public void showPaymentInformation() {
        System.out.println("Payment Id : " + this.paymentId + " Amount : " + this.amount);
    }

    // Abstract method — subclasses must provide implementation
    public abstract void processPayment();
}
```

```java
// CardPayment.java
public class CardPayment extends Payment {
    private String cardNumber;

    public CardPayment(double amount, String paymentId, String cardNumber) {
        super(amount, paymentId);
        this.cardNumber = cardNumber;
    }

    @Override
    public void processPayment() {
        System.out.println("Processing card payment of amount " + this.amount + " using card " + this.cardNumber);
    }
}
```

Quick usage:

```java
Payment p = new CardPayment(500.0, "PAY-001", "4111-xxxx-xxxx-1111");
p.showPaymentInformation(); // Calls concrete parent method
p.processPayment();         // Calls overridden child method
```

---

## Upcasting & Downcasting

- Upcasting: treat a child instance as its parent type — safe and implicit.
  ```java
  Payment p = new CardPayment(500.0, "ID-100", "4111-xxxx");
  p.processPayment(); // dynamic dispatch: CardPayment.processPayment()
  ```

- Downcasting: cast a parent reference back to the child type — requires an explicit cast and may throw ClassCastException if incorrect.
  ```java
  Payment p = new CardPayment(500.0, "ID-100", "4111-xxxx");
  if (p instanceof CardPayment) {
      CardPayment card = (CardPayment) p;
      // access CardPayment-specific methods/fields here
  }
  ```

---

## Interfaces — Quick Overview

Interfaces define a contract for classes to implement. Since Java 8, interfaces can have:
- abstract methods (must be implemented)
- default methods (concrete)
- static methods

Example:

```java
public interface Refundable {
    void refund(double amount);

    default boolean isRefundAllowed() {
        return true;
    }
}
```

A class can implement many interfaces:
```java
public class CardPayment extends Payment implements Refundable {
    // implement processPayment() and refund()
}
```

---

## Best Practices & Takeaways
- Prefer interfaces to define capabilities and types; use abstract classes when you need shared state or helper code.
- Keep method names consistent with Java conventions (lowerCamelCase).
- Use upcasting to write flexible APIs; use downcasting sparingly and safely (with instanceof).
- Let abstractions express intent — clear contracts make code easier to understand and maintain.

---


