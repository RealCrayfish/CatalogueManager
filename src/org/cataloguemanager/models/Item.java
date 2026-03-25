package org.cataloguemanager.models;

import java.time.LocalDate;

public class Item {
    private String assetID; // Note: Useful for QR-Code generating and tracking
    private String name;
    private String manufacturer;
    private LocalDate acquisitionDate;
    private String category;
    private Boolean isAvailable;

    public Item(String assetID, String name, String manufacturer, LocalDate acquisitionDate, String category, Boolean isAvailable) {
        this.assetID = assetID;
        this.name = name;
        this.manufacturer = manufacturer;
        this.acquisitionDate = acquisitionDate;
        this.category = category;
        this.isAvailable = isAvailable;
    }


    public String getAssetID() { return this.assetID; }

    public void setAssetID(String assetID) { this.assetID = assetID; } // TODO: Implement id validation using regex.


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    } // NOTE: Not implementing name validation as it would bbe too complex for the assignment.


    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    } // NOTE: Not implementing name validation as it would bbe too complex for the assignment.


    public LocalDate getAcquisitionDate() {
        return this.acquisitionDate;
    }

    public void setAcquisitionDate(LocalDate date) {
        this.acquisitionDate = date;
    }


    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public Boolean getAvailability() {
        return this.isAvailable;
    }

    public void setAvailability(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
