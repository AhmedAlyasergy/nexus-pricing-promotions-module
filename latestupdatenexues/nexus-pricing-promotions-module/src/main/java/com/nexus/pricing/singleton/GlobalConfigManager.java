package com.nexus.pricing.singleton;

public class GlobalConfigManager {

    private static volatile GlobalConfigManager instance;

    private double taxRate = 0.14;

    private GlobalConfigManager() {
    }

    public static GlobalConfigManager getInstance() {

        if (instance == null) {

            synchronized (GlobalConfigManager.class) {

                if (instance == null) {
                    instance = new GlobalConfigManager();
                }
            }
        }

        return instance;
    }

    public double getTaxRate() {
        return taxRate;
    }
}