 #Nexus Pricing & Promotions Module

Nexus Pricing & Promotions Module is a Spring Boot pricing system integrated with the Inventory Module to calculate dynamic selling prices using pricing strategies, promotional add-ons, and shared tax rules.

---

## How to Run

### Prerequisites

- Java 17+
- Maven
- Firebase credentials file named `serviceAccountKey.json`

### Start the Application

```bash
mvn -q -DskipTests compile
mvn spring-boot:run
Open the App

Open in browser:

http://localhost:8080/login.html

Then access Pricing Engine from the dashboard sidebar.

Module Features
Load products dynamically from Inventory Module
Display original product price
Apply multiple pricing strategies
Add optional promotions
Calculate final price with tax included
Reset pricing form instantly
Pricing Strategies
Normal Pricing

Uses standard product price.

VIP Pricing

Applies 10% discount.

Black Friday Pricing

Applies 20% discount.

Promotions
Gift Wrap

Adds +50 to final cost.

Insurance

Adds +100 to final cost.

Final Pricing Formula
Get base product price
Apply selected pricing strategy
Add selected promotional services
Apply 14% tax
File Guide
src/main/java/com/nexus/pricing/controller/PricingController.java

REST API controller for pricing calculations.

src/main/java/com/nexus/pricing/controller/PricingModule.java

Main pricing module execution class.

src/main/java/com/nexus/pricing/manager/PricingManager.java

Handles final price calculation flow.

src/main/java/com/nexus/pricing/strategies/PricingStrategy.java

Strategy interface for pricing behaviors.

src/main/java/com/nexus/pricing/strategies/NormalPricing.java

Standard pricing logic.

src/main/java/com/nexus/pricing/strategies/VipPricing.java

VIP discount pricing logic.

src/main/java/com/nexus/pricing/strategies/BlackFridayPricing.java

Black Friday discount logic.

src/main/java/com/nexus/pricing/decorators/GiftWrapDecorator.java

Adds gift wrap service cost.

src/main/java/com/nexus/pricing/decorators/InsuranceDecorator.java

Adds insurance service cost.

src/main/resources/static/index.html

Dashboard UI integrated with Pricing Engine.

Design Patterns Used
Strategy Pattern

Used to switch pricing algorithms dynamically.

NormalPricing
VipPricing
BlackFridayPricing
Decorator Pattern

Used to add optional promotions without changing core pricing logic.

GiftWrapDecorator
InsuranceDecorator
Singleton Pattern

Used for shared system configuration such as tax values.

SOLID Principles
Single Responsibility Principle

Each class handles one clear responsibility.

Open/Closed Principle

New strategies and promotions can be added without modifying existing code.

Liskov Substitution Principle

All pricing strategies can replace each other through the same interface.

Interface Segregation Principle

Small focused interfaces used for pricing logic.

Dependency Inversion Principle

PricingManager depends on PricingStrategy abstraction.

Integration Notes
Products are loaded from Inventory Module
Pricing module runs inside the main dashboard
Connected with the full Nexus Logistics system
Author

Ahmed Alyasergy

Developer of Pricing & Promotions Module

Future Enhancements
Coupon codes
Loyalty discounts
Seasonal campaigns
Multi-currency pricing
Promotion analytics
