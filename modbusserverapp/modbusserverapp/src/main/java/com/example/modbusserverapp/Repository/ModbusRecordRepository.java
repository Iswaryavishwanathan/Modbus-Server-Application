package com.example.modbusserverapp.Repository;


import com.example.modbusserverapp.Entity.ModbusRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModbusRecordRepository extends JpaRepository<ModbusRecordEntity, Long> {
}

