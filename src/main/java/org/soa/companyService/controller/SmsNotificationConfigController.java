package org.soa.companyService.controller;

import org.soa.companyService.model.SmsNotificationConfig;
import org.soa.companyService.service.SmsNotificationConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sms-notification-configs")
public class SmsNotificationConfigController {

    @Autowired
    private SmsNotificationConfigService smsNotificationConfigService;

    // Get all SMS notification configurations
    @GetMapping
    public List<SmsNotificationConfig> getAllSmsNotificationConfigs() {
        return smsNotificationConfigService.getAllSmsNotificationConfigs();
    }

    // Get an SMS notification configuration by ID
    @GetMapping("/{id}")
    public ResponseEntity<SmsNotificationConfig> getSmsNotificationConfigById(@PathVariable Long id) {
        return smsNotificationConfigService.getSmsNotificationConfigById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new SMS notification configuration
    @PostMapping
    public SmsNotificationConfig createSmsNotificationConfig(@RequestBody SmsNotificationConfig smsNotificationConfig) {
        return smsNotificationConfigService.createSmsNotificationConfig(smsNotificationConfig);
    }

    // Update an SMS notification configuration
    @PutMapping("/{id}")
    public ResponseEntity<SmsNotificationConfig> updateSmsNotificationConfig(
            @PathVariable Long id,
            @RequestBody SmsNotificationConfig smsNotificationConfig) {
        try {
            return ResponseEntity.ok(smsNotificationConfigService.updateSmsNotificationConfig(id, smsNotificationConfig));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an SMS notification configuration
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSmsNotificationConfig(@PathVariable Long id) {
        smsNotificationConfigService.deleteSmsNotificationConfig(id);
        return ResponseEntity.noContent().build();
    }
}
