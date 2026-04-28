package com.nexus.services;

import com.nexus.models.Product;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @GetMapping("/list")
    public List<Product> getLiveInventory() {
        List<Product> list = new ArrayList<>();
        try {
            var db = FirebaseService.getInstance().getDb();
            var future = db.collection("inventory").get();
            var documents = future.get().getDocuments();

            for (var doc : documents) {
                list.add(doc.toObject(Product.class));
            }
        } catch (Exception e) {
            System.err.println("Error fetching from Firebase: " + e.getMessage());
        }
        return list;
    }
}