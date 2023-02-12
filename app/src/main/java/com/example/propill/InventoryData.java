package com.example.propill;

public class InventoryData {
    private String pillname;
    private String inventory;
    public InventoryData(String pillname, String inventory){
        this.pillname = pillname;
        this.inventory = inventory;
    }

    public String getPillname()
    {
        return this.pillname;
    }

    public String getInventory()
    {
        return this.inventory;
    }
}
