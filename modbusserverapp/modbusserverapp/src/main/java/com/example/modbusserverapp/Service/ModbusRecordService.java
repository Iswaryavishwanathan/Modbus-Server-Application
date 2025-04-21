package com.example.modbusserverapp.Service;

import com.example.modbusserverapp.Entity.ModbusRecordEntity;
// import com.example.modbusserverapp.Model.ModbusRecord;
// import com.example.modbusserverapp.Model.Registers;
import com.example.modbusserverapp.Repository.ModbusRecordRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Base64;

@Service
public class ModbusRecordService {

    private final ModbusRecordRepository repository;

    public ModbusRecordService(ModbusRecordRepository repository) {
        this.repository = repository;
    }

    // Decode a Base64 string, extract values, and store in the DB
    public boolean decodeAndStore(String base64Data) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);

            try (ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes);
                 DataInputStream dis = new DataInputStream(bais)) {

                // Decode timestamp (8 bytes)
                long timestamp = dis.readLong();

                // Decode machine name
                int nameLength = dis.readInt(); // 4 bytes for the machine name length
                byte[] nameBytes = new byte[nameLength];
                dis.readFully(nameBytes);
                String machineName = new String(nameBytes, StandardCharsets.UTF_8);

                // Only read first 2 registers (Set Weight, Actual Weight)
                int setWeight = dis.readShort() & 0xFFFF;
                int actualWeight = dis.readShort() & 0xFFFF;

                // Optional: Log the extracted values
                System.out.printf("📦 Decoded - Timestamp: %d, Name: %s, Set: %d, Actual: %d%n",
                        timestamp, machineName, setWeight, actualWeight);
                        // Registers registers = new Registers(setWeight, actualWeight, machineName);
                        // ModbusRecord record = new ModbusRecord(timestamp, machineName, registers);


                // Convert and save entity
                ModbusRecordEntity entity = new ModbusRecordEntity(
                        Instant.ofEpochMilli(timestamp).atOffset(ZoneOffset.UTC).toLocalDateTime(),
                        machineName,
                        setWeight,
                        actualWeight
                );

                repository.save(entity);
                return true;

            }
        } catch (Exception e) {
            System.err.println("❌ Error decoding/storing record: " + e.getMessage());
            return false;
        }
    }
}
