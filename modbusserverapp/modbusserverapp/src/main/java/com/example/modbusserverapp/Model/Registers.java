package com.example.modbusserverapp.Model;

public class Registers {
    private int setWeight;      // Register 0 (Set Weight)
    private int actualWeight;   // Register 1 (Actual Weight)
    private String machineName; // Machine name stored as string from Register 3 to 10

    // Constructor for Set Weight, Actual Weight, and Machine Name
    public Registers(int setWeight, int actualWeight, String machineName) {
        this.setWeight = setWeight;
        this.actualWeight = actualWeight;
        this.machineName = machineName;
    }

    // Getters and Setters
    public int getSetWeight() {
        return setWeight;
    }

    public void setSetWeight(int setWeight) {
        this.setWeight = setWeight;
    }

    public int getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(int actualWeight) {
        this.actualWeight = actualWeight;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }
}
