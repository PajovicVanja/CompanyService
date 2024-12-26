package org.soa.companyService.service;

import org.junit.jupiter.api.Test;
import org.soa.companyService.model.Location;
import org.soa.companyService.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void testCreateLocation() {
        Location location = new Location();
        location.setName("Main Office");
        location.setNumber(101);
        Location savedLocation = locationService.createLocation(location);

        assertThat(savedLocation.getId()).isNotNull();
        assertThat(savedLocation.getName()).isEqualTo("Main Office");
    }

    @Test
    public void testGetLocationById() {
        Location location = new Location();
        location.setName("Branch Office");
        location.setNumber(202);
        Location savedLocation = locationService.createLocation(location);

        Optional<Location> retrievedLocation = locationService.getLocationById(savedLocation.getId());
        assertThat(retrievedLocation).isPresent();
        assertThat(retrievedLocation.get().getName()).isEqualTo("Branch Office");
    }

    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("Old Location");
        location.setNumber(303);
        Location savedLocation = locationService.createLocation(location);

        savedLocation.setName("Updated Location");
        savedLocation.setNumber(404);
        Location updatedLocation = locationService.updateLocation(savedLocation.getId(), savedLocation);

        assertThat(updatedLocation.getName()).isEqualTo("Updated Location");
        assertThat(updatedLocation.getNumber()).isEqualTo(404);
    }

    @Test
    public void testDeleteLocation() {
        Location location = new Location();
        location.setName("Temporary Location");
        location.setNumber(505);
        Location savedLocation = locationService.createLocation(location);

        locationService.deleteLocation(savedLocation.getId());

        Optional<Location> retrievedLocation = locationService.getLocationById(savedLocation.getId());
        assertThat(retrievedLocation).isEmpty();
    }
}
