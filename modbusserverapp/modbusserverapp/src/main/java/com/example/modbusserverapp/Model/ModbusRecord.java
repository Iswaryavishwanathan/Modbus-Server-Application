package com.example.modbusserverapp.Model;

public class ModbusRecord {
    private long timestamp;
    private String name;
    private Registers registers;

    public ModbusRecord(long timestamp, String name, Registers registers) {
        this.timestamp = timestamp;
        this.name = name;
        this.registers = registers;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getName() {
        return name;
    }

    public Registers getRegisters() {
        return registers;
    }

    // New getters for set weight and actual weight
    public int getSetWeight() {
        return registers.getSetWeight();
    }

    public int getActualWeight() {
        return registers.getActualWeight();
    }
}
