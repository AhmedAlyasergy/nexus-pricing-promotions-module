package com.nexus.shipping;

import com.google.cloud.firestore.Firestore;
import com.nexus.services.FirebaseService;
import com.nexus.shipping.bridge.ShippableProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShippingService {

    private static final Logger LOGGER = Logger.getLogger(ShippingService.class.getName());
    private final Firestore db = FirebaseService.getInstance().getDb();

    public ShipmentRecord dispatch(ShippableProduct product, String destination) {
        ShipmentRecord record = product.ship(destination);
        persistShipment(record);
        return record;
    }

    public List<ShipmentRecord> listShipments() {
        List<ShipmentRecord> records = new ArrayList<>();
        try {
            var docs = db.collection("shipments").get().get().getDocuments();
            for (var doc : docs) {
                records.add(doc.toObject(ShipmentRecord.class));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching shipments from Firestore", e);
        }
        return records;
    }

    public boolean updateShipmentStatus(String shipmentId, String newStatus) {
        try {
            db.collection("shipments").document(shipmentId).update("status", newStatus).get();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating shipment status for " + shipmentId, e);
            return false;
        }
    }

    private void persistShipment(ShipmentRecord record) {
        try {
            db.collection("shipments").document(record.getShipmentId()).set(record).get();
            System.out.println("Cloud Sync: Shipment " + record.getShipmentId() + " persisted.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error persisting shipment record", e);
        }
    }
}
