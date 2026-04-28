# NexuesLogistics

NexuesLogistics is a Spring Boot + Firebase inventory dashboard for managing branch-based stock for Cairo, Alexandria, and Giza.

## How to run

### Prerequisites

- Java 17+
- Maven
- A Firebase service account file named `serviceAccountKey.json` in the project root

### Start the application

```bash
mvn -q -DskipTests compile
mvn spring-boot:run
```

### Open the app

Open the login page in your browser:

```text
http://localhost:8080/login.html
```

### Login IDs

- `M00` → Global Manager (`All`)
- `M01` → Nour Maatouk (`Cairo`)
- `M02` → Dania Elbakri (`Alexandria`)
- `M03` → Ahmed Atef (`Giza`)

## File guide

### `src/main/java/com/nexus/MainApp.java`

Application entry point. It seeds Firebase with managers and inventory, starts the web server, and wires the stock observers.

### `src/main/java/com/nexus/models/Manager.java`

Manager model used for login and branch assignment.

### `src/main/java/com/nexus/models/Product.java`

Product model used for inventory items stored in Firebase.

### `src/main/java/com/nexus/services/AuthController.java`

Authentication API. It checks a manager ID in Firebase and stores the logged-in manager in the singleton session.

### `src/main/java/com/nexus/services/SessionManager.java`

Singleton session store for the current manager.

### `src/main/java/com/nexus/services/InventoryController.java`

Inventory API. It returns products from Firebase for the current branch, or all branches for `M00`.

### `src/main/java/com/nexus/services/InventoryService.java`

Updates inventory in Firebase and triggers observer notifications when stock changes.

### `src/main/java/com/nexus/services/InventoryManager.java`

Observer subject. It keeps a list of stock observers and notifies them when a product changes.

### `src/main/java/com/nexus/services/StockObserver.java`

Observer interface implemented by stock listeners.

### `src/main/java/com/nexus/services/EmailAlertSystem.java`

Observer implementation that prints a critical alert when stock falls to or below the threshold.

### `src/main/java/com/nexus/services/WarehouseLogger.java`

Observer implementation that logs stock updates.

### `src/main/java/com/nexus/services/Warehouse.java`

Iterator example. It exposes products through `Enumeration<Product>` without exposing the internal collection directly.

### `src/main/java/com/nexus/services/FirebaseService.java`

Singleton wrapper that initializes Firebase and returns Firestore access.

### `src/main/java/com/nexus/services/DatabaseSeeder.java`

Reads the CSV files from `src/main/resources` and uploads managers and inventory to Firebase.

### `src/main/resources/managers.csv`

Seed data for manager accounts and their branch/section.

### `src/main/resources/inventory.csv`

Seed data for inventory items and their warehouse assignment.

### `src/main/resources/static/index.html`

Main dashboard page that shows inventory, analytics, and branch information.

### `src/main/resources/static/login.html`

Login page used to authenticate a manager by ID.

### `src/main/resources/static/style.css`

Shared styling for the dashboard and login pages.

## Design patterns used

### Singleton

Used to keep one shared instance for session state and Firebase access.

- [`SessionManager`](src/main/java/com/nexus/services/SessionManager.java) keeps the current logged-in manager in one shared object.
- [`FirebaseService`](src/main/java/com/nexus/services/FirebaseService.java) initializes Firebase once and reuses the same Firestore connection.

### Observer

Used for stock notifications.

- Subject: [`InventoryManager`](src/main/java/com/nexus/services/InventoryManager.java)
- Interface: [`StockObserver`](src/main/java/com/nexus/services/StockObserver.java)
- Observers: [`EmailAlertSystem`](src/main/java/com/nexus/services/EmailAlertSystem.java), [`WarehouseLogger`](src/main/java/com/nexus/services/WarehouseLogger.java)
- Trigger: [`InventoryService`](src/main/java/com/nexus/services/InventoryService.java) calls `notifyObservers(...)`

### Iterator

Used to traverse products in a warehouse without exposing the internal list.

- Example class: [`Warehouse`](src/main/java/com/nexus/services/Warehouse.java)
- Iterator style: `getProducts()` returns `Enumeration<Product>`

## SOLID principles in the project

### Single Responsibility Principle

The project is partially aligned with SRP, but some classes still do more than one job.

- [`DatabaseSeeder`](src/main/java/com/nexus/services/DatabaseSeeder.java) focuses on seeding Firebase from CSV files.
- [`AuthController`](src/main/java/com/nexus/services/AuthController.java) handles login and session retrieval.
- [`MainApp`](src/main/java/com/nexus/MainApp.java) still seeds data, starts Spring Boot, and wires observers, so it has multiple responsibilities.

### Open/Closed Principle

The stock notification flow is open for extension through new observers.

- [`InventoryManager`](src/main/java/com/nexus/services/InventoryManager.java) can accept new `StockObserver` implementations without changing its internal notification loop.
- [`EmailAlertSystem`](src/main/java/com/nexus/services/EmailAlertSystem.java) and [`WarehouseLogger`](src/main/java/com/nexus/services/WarehouseLogger.java) are added as separate observer classes.

### Liskov Substitution Principle

This is not strongly demonstrated because the project does not use a deep inheritance hierarchy.

- The code mainly uses composition and interfaces instead of subclass chains.

### Interface Segregation Principle

The observer interface is small and focused.

- [`StockObserver`](src/main/java/com/nexus/services/StockObserver.java) exposes only `onStockChange(Product product)`.
- This keeps observer implementations simple and avoids forcing extra methods.

### Dependency Inversion Principle

This principle is only partially followed.

- [`InventoryService`](src/main/java/com/nexus/services/InventoryService.java) depends on the observer abstraction through `InventoryManager` and `StockObserver`.
- Other classes still depend directly on concrete services like [`FirebaseService`](src/main/java/com/nexus/services/FirebaseService.java), so the abstraction layer is limited.

## Notes

- `M00` can view all branches.
- Branch managers only see inventory for their branch.
- The app reads its Firebase credentials from `serviceAccountKey.json` in the project root.