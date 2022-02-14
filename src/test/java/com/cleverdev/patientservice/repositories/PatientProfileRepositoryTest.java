package com.cleverdev.patientservice.repositories;

import com.cleverdev.patientservice.entities.PatientProfile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PatientProfileRepositoryTest {

    @Autowired
    private PatientProfileRepository patientProfileRepository;

    @Test
    @Rollback
    public void savePatientProfileTest() {
        PatientProfile patientProfile = PatientProfile.builder()
            .firstName("Alex")
            .lastName("Miranovich")
            .oldClientGuid("1")
            .statusId(200)
            .build();
        PatientProfile newPatientNote = patientProfileRepository.save(patientProfile);
        Assertions.assertThat(newPatientNote.getId()).isGreaterThan(0);
    }

    @Test
    @Rollback
    public void findByGuidTest() {
        PatientProfile patientProfile = PatientProfile.builder()
            .firstName("Alex")
            .lastName("Miranovich")
            .oldClientGuid("1")
            .statusId(200)
            .build();
        patientProfileRepository.save(patientProfile);
        PatientProfile newPatientProfile = patientProfileRepository.findByGuid(patientProfile.getOldClientGuid());
        Assertions.assertThat(patientProfile.getOldClientGuid()).isEqualTo(newPatientProfile.getOldClientGuid());
    }
}
