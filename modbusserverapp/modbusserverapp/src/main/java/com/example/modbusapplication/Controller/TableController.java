package com.example.modbusapplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import com.example.modbusapplication.Model.RawRecordDTO;
import com.example.modbusapplication.Model.TableRequest;
import com.example.modbusapplication.Service.ModbusRecordService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TableController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
       @Autowired
    private ModbusRecordService modbusRecordService;

      // Modified to accept List<RawRecordDTO>
    @PostMapping("/upload-bytes")
    public ResponseEntity<String> uploadRawBase64DTOs(@RequestBody List<RawRecordDTO> rawRecordDTOList) {
        System.out.println("Received DTO records: " + rawRecordDTOList.size());

        int successCount = 0;

        for (RawRecordDTO dto : rawRecordDTOList) {
            boolean success = modbusRecordService.decodeAndStore(dto.getEncByteString());
            if (success) {
                successCount++;
            }
        }

        if (successCount == rawRecordDTOList.size()) {
            return ResponseEntity.ok("All records decoded and stored.");
        } else if (successCount > 0) {
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .body("Some records were stored successfully. " + successCount + "/" + rawRecordDTOList.size());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to store any records.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTable(@RequestBody TableRequest request) {
        String tableName = request.getTableName();

        // Sanitize: allow only alphanumeric and underscore
        if (!tableName.matches("[a-zA-Z0-9_]+")) {
            return ResponseEntity.badRequest().body("Invalid table name.");
        }

        // Check if table exists (for SQLite, adjust for other DBs)
        String checkSql = "SELECT table_name FROM information_schema.tables WHERE table_schema = ? AND table_name = ?";
        String schemaName = "modbusdb"; // or whatever your DB name is
              
        List<String> tables = jdbcTemplate.queryForList(checkSql, String.class, schemaName, tableName);


        if (!tables.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Table '" + tableName + "' already exists.");
        }

        String createSql = "CREATE TABLE " + tableName + " (" +
        "id INT PRIMARY KEY AUTO_INCREMENT," +
        "timestamp DATETIME NOT NULL," +
        "machine_name VARCHAR(255) NOT NULL," +
        "set_weight INT NOT NULL," +
        "actual_weight INT NOT NULL," +
        "device_id VARCHAR(255)" +
        ")";


        try {
            jdbcTemplate.execute(createSql);
            return ResponseEntity.ok("Table '" + tableName + "' created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating table: " + e.getMessage());
        }
    }
}
 