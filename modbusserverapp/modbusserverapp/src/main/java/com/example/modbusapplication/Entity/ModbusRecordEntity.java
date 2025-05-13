package com.example.modbusapplication.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "modbus_records")
public class ModbusRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Optional, but added for clarity
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "machine_name", nullable = false)
    private String name;

    @Column(name = "set_weight", nullable = false)
    private int setWeight;

    @Column(name = "actual_weight", nullable = false)
    private int actualWeight;

    @Column(name = "total_weight", nullable = false)
    private int totalWeight;

    @Column(name = "device_id") 
    private String deviceId;

    // Default constructor
    public ModbusRecordEntity() {}

    // Constructor to accept timestamp, name, set weight, and actual weight, deviceid
    public ModbusRecordEntity(LocalDateTime timestamp, String name, int setWeight, int actualWeight, int totalWeight, String deviceId) {
        this.timestamp = timestamp;
        this.name = name;
        this.setWeight = setWeight;
        this.actualWeight = actualWeight;
        this.totalWeight = totalWeight;
        this.deviceId = deviceId;
    }


    // Getters and setters
    public Long getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
        public int getTotalWeight() {
        return actualWeight;
    }

    public void setTotalWeight(int actualWeight) {
        this.actualWeight = actualWeight;
    }
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) { 
        this.deviceId = deviceId;
    }
}
