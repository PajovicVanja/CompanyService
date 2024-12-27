package org.soa.companyService.controller;

import org.soa.companyService.dto.CreateLocationRequest;
import org.soa.companyService.dto.UpdateLocationRequest;
import org.soa.companyService.model.Location;
import org.soa.companyService.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody CreateLocationRequest request) {
        Location location = new Location();
        location.setName(request.getName());
        location.setNumber(request.getNumber());

        // Fetch and set Parent Location if provided
        if (request.getParentLocationId() != null) {
            Location parentLocation = locationService.getLocationById(request.getParentLocationId())
                    .orElseThrow(() -> new RuntimeException("Parent Location not found with id " + request.getParentLocationId()));
            location.setParentLocation(parentLocation);
        }

        Location createdLocation = locationService.createLocation(location);
        return ResponseEntity.ok(createdLocation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody UpdateLocationRequest request) {
        try {
            Location location = new Location();
            location.setName(request.getName());
            location.setNumber(request.getNumber());

            // Fetch and set Parent Location if provided
            if (request.getParentLocationId() != null) {
                Location parentLocation = locationService.getLocationById(request.getParentLocationId())
                        .orElseThrow(() -> new RuntimeException("Parent Location not found with id " + request.getParentLocationId()));
                location.setParentLocation(parentLocation);
            }

            Location updatedLocation = locationService.updateLocation(id, location);
            return ResponseEntity.ok(updatedLocation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
