# PIZZAlicious - Point of Sale Application

A Java console-based (CLI) point of sale application for PIZZA-licious, a client requested pizza shop.
Customers can fully customize their pizza orders, add drinks and garlic knots,
and receive a timestamped receipt for every order.

---

## Application Screens

### Home Screen
```
  ==============================
       PIZZA-licious
  ==============================

  1) New Order
  0) Exit

  Enter your choice:
```

### Order Screen
```
  ------------------------------
  YOUR ORDER
  ------------------------------
  (No items yet)

  1) Add Pizza
  2) Add Drink
  3) Add Garlic Knots  ($1.50)
  4) Checkout
  0) Cancel Order
```

### Building Your Pizza Screen
```
  ------------------------------
  BUILD YOUR PIZZA
  ------------------------------
  Start from:
  1) Build from scratch
  2) Margherita - 12" Regular, Mozzarella, Tomatoes, Basil
  3) Veggie     - 8"  Regular, Mozzarella, Bell Peppers, Spinach, Olives, Onions
  Choice:

  Select crust type:
  1) Thin  2) Regular  3) Thick  4) Cauliflower

  Select size:
  1)  8"  Personal  - $8.50
  2) 12"  Medium    - $12.00
  3) 16"  Large     - $16.50
```

### Checkout Screen
```
  ==============================
       PIZZA-licious Order
  ==============================

  --- PIZZAS ---
  Pizza (12" regular crust)  -  $14.75
    Meats:    pepperoni
    Cheese:   mozzarella
    Sauces:   marinara

  --- DRINKS ---
  Drink  -  Medium Coke  -  $2.50

  ==============================
    TOTAL:  $17.25
  ==============================

  1) Confirm Order
  0) Cancel Order
```

---

## Pricing

| Item | 8" Personal | 12" Medium | 16" Large |
|---|---|---|---|
| Pizza (any crust) | $8.50 | $12.00 | $16.50 |
| Meat topping | $1.00 | $2.00 | $3.00 |
| Extra meat | $0.50 | $1.00 | $1.50 |
| Cheese topping | $0.75 | $1.50 | $2.25 |
| Extra cheese | $0.30 | $0.60 | $0.90 |
| Regular toppings | Free | Free | Free |
| Sauces | Free | Free | Free |

| Other Items | Price |
|---|---|
| Small Drink | $2.00 |
| Medium Drink | $2.50 |
| Large Drink | $3.00 |
| Garlic Knots | $1.50 |

---

## Project Structure

```
src/main/java/com/pluralsight/
    Main.java                        - Application entry point
    models/
        Pizza.java                   - Core product with pricing logic
        Topping.java                 - Premium topping (meat/cheese) with extra flag
        Drink.java                   - Beverage with size-based pricing
        GarlicKnots.java             - Side item at flat $1.50
        Order.java                   - Manages all items and calculates total
        MargheritaPizza.java         - Signature pizza (extends Pizza)
        VeggiePizza.java             - Signature pizza (extends Pizza)
        SignaturePizzaFactory.java   - Creates signature pizza instances
    ui/
        UserInterface.java           - All console screens and input handling
    util/
        ReceiptWriter.java           - Saves orders to timestamped .txt files
```

---

## Interesting Code: Polymorphism in getTotal()

The `Order.getTotal()` method is a great example of polymorphism.
Each item type (Pizza, Drink, GarlicKnots) has its own `getPrice()` method
with its own pricing logic. Order just loops through each list and asks
every item "what do you cost?" without needing to know how each one calculates it.

```java
public double getTotal() {
    double total = 0.0;
    for (Pizza p       : pizzas)      total += p.getPrice();
    for (Drink d       : drinks)      total += d.getPrice();
    for (GarlicKnots g : garlicKnots) total += g.getPrice();
    return total;
}
```

Pizza.getPrice() adds up base price + meat costs + cheese costs + stuffed crust.
Drink.getPrice() looks up the price from the size (Small/Medium/Large).
GarlicKnots.getPrice() always returns the constant $1.50.
Same method call, completely different behavior -- that is polymorphism.

---

## OOP Concepts Used

- Encapsulation: all fields are private with public getters/setters
- Inheritance: MargheritaPizza and VeggiePizza extend Pizza
- Polymorphism: each class implements getPrice() differently
- Abstraction: callers use getPrice() without knowing internal pricing logic
- Composition: Order "has-a" list of Pizza, Drink, and GarlicKnots
- Factory Pattern: SignaturePizzaFactory creates pre-configured pizza objects

---

## How to Run

1. Open the project in IntelliJ IDEA
2. Right-click `Main.java` in the Project panel
3. Select **Run 'Main.main()'**
4. The console will display the PIZZA-licious home screen
5. Receipts are saved to the `receipts/` folder at the project root

---

## Class Diagram


```
Pizza  <--- MargheritaPizza
       <--- VeggiePizza

Order  <>--- Pizza
       <>--- Drink
       <>--- GarlicKnots

SignaturePizzaFactory ---> Pizza

UserInterface ---> Order
              ---> SignaturePizzaFactory
              ---> ReceiptWriter

ReceiptWriter ---> Order
```
