package org.soa.companyService.controller;

import org.soa.companyService.dto.CreateSmsNotificationConfigRequest;
import org.soa.companyService.dto.UpdateSmsNotificationConfigRequest;
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

    @GetMapping
    public List<SmsNotificationConfig> getAllSmsNotificationConfigs() {
        return smsNotificationConfigService.getAllSmsNotificationConfigs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SmsNotificationConfig> getSmsNotificationConfigById(@PathVariable Long id) {
        return smsNotificationConfigService.getSmsNotificationConfigById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SmsNotificationConfig> createSmsNotificationConfig(
            @RequestBody CreateSmsNotificationConfigRequest request) {
        SmsNotificationConfig smsNotificationConfig = new SmsNotificationConfig();
        smsNotificationConfig.setName(request.getName());

        SmsNotificationConfig createdSmsNotificationConfig = smsNotificationConfigService.createSmsNotificationConfig(smsNotificationConfig);
        return ResponseEntity.ok(createdSmsNotificationConfig);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SmsNotificationConfig> updateSmsNotificationConfig(
            @PathVariable Long id,
            @RequestBody UpdateSmsNotificationConfigRequest request) {
        try {
            SmsNotificationConfig smsNotificationConfig = new SmsNotificationConfig();
            smsNotificationConfig.setName(request.getName());

            SmsNotificationConfig updatedSmsNotificationConfig = smsNotificationConfigService.updateSmsNotificationConfig(id, smsNotificationConfig);
            return ResponseEntity.ok(updatedSmsNotificationConfig);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSmsNotificationConfig(@PathVariable Long id) {
        smsNotificationConfigService.deleteSmsNotificationConfig(id);
        return ResponseEntity.noContent().build();
    }
}
