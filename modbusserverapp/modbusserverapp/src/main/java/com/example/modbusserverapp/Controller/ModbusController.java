package com.example.modbusserverapp.Controller;

import com.example.modbusserverapp.Service.ModbusRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ModbusController {

    @Autowired
    private ModbusRecordService modbusRecordService;

    // This endpoint receives a list of Base64-encoded strings
    @PostMapping("/upload-bytes")
    public ResponseEntity<String> uploadRawBase64Strings(@RequestBody List<String> rawBase64List) {
        System.out.println("📥 Received base64 records: " + rawBase64List.size());
    
        int successCount = 0;
    
        for (String base64 : rawBase64List) {
            boolean success = modbusRecordService.decodeAndStore(base64);
            if (success) {
                successCount++;
            }
        }
    
        if (successCount == rawBase64List.size()) {
            return ResponseEntity.ok("✅ All records decoded and stored.");
        } else if (successCount > 0) {
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .body("⚠️ Some records were stored successfully. " + successCount + "/" + rawBase64List.size());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Failed to store any records.");
        }
    }
    }
