package com.example.modbusserverapp.Model;

public class RawRecordDTO {
    private String data;

    public RawRecordDTO() {}

    public RawRecordDTO(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
