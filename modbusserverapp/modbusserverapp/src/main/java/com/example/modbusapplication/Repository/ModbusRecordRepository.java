package com.example.modbusapplication.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.modbusapplication.Entity.ModbusRecordEntity;

public interface ModbusRecordRepository extends JpaRepository<ModbusRecordEntity, Long> {
}

