package com.nexus.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.cloud.firestore.Firestore;
import com.nexus.models.Manager;
import com.nexus.models.Product;

public class DatabaseSeeder {
    public static void uploadManagers() {
        Firestore db = FirebaseService.getInstance().getDb();

        try {
            InputStream is = DatabaseSeeder.class.getClassLoader().getResourceAsStream("managers.csv");
            if (is == null) {
                System.err.println("Seeding Failed: managers.csv not found");
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Manager manager = new Manager(data[0], data[1], data[2]);
                db.collection("managers").document(manager.getId()).set(manager);
                System.out.println("Cloud Sync SUCCESS: Manager " + manager.getId());
            }
            System.out.println(">>> ALL MANAGERS UPLOADED TO FIREBASE <<<");
        } catch (Exception e) {
            System.err.println("Manager seeding failed: " + e.getMessage());
        }
    }

    public static void uploadInventory() {
        Firestore db = FirebaseService.getInstance().getDb();
        
        try {
            // Locate the file you just created in the resources folder
            InputStream is = DatabaseSeeder.class.getClassLoader().getResourceAsStream("inventory.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            br.readLine(); // Skip the first line (the headers: id, name, etc.)
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                
                // Create a Product object using the data from the CSV row
                Product p = new Product(
                    data[0], data[1], data[2], 
                    Double.parseDouble(data[3]), 
                    Integer.parseInt(data[4]), 
                    Integer.parseInt(data[5]), 
                    data[6], data[7]
                );

                // Push to Firebase! 
                // We use the ID from the CSV as the document name
                db.collection("inventory").document(p.getId()).set(p);
                System.out.println("Cloud Sync SUCCESS: " + p.getName());
            }
            System.out.println(">>> ALL DATA UPLOADED TO FIREBASE <<<");
        } catch (Exception e) {
            System.err.println("Seeding Failed: " + e.getMessage());
        }
    }
}