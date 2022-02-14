package com.cleverdev.patientservice.repositories;

import com.cleverdev.patientservice.entities.PatientNote;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.MINUTES;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PatientNoteRepositoryTest {

    @Autowired
    private PatientNoteRepository patientNoteRepository;

    @Test
    @Rollback
    public void savePatientNoteTest() {
        PatientNote patientNote = PatientNote.builder()
            .oldNoteGuid("old_note_guid")
            .createdDateTime(LocalDateTime.now())
            .lastModifiedDateTime(LocalDateTime.now())
            .createdByUserId(1L)
            .lastModifiedByUserId(1L)
            .note("note")
            .patientId(1L)
            .build();
        PatientNote newPatientNote = patientNoteRepository.save(patientNote);
        Assertions.assertThat(newPatientNote.getId()).isGreaterThan(0);
    }

    @Test
    @Rollback
    public void findByOldNoteGuidTest() {
        PatientNote patientNote = PatientNote.builder()
            .oldNoteGuid("old_note_guid")
            .createdDateTime(LocalDateTime.now())
            .lastModifiedDateTime(LocalDateTime.now())
            .createdByUserId(1L)
            .lastModifiedByUserId(1L)
            .note("note")
            .patientId(1L)
            .build();
        patientNoteRepository.save(patientNote);
        PatientNote newPatientNote = patientNoteRepository.findByOldNoteGuid(patientNote.getOldNoteGuid());
        Assertions.assertThat(patientNote.getOldNoteGuid()).isEqualTo(newPatientNote.getOldNoteGuid());
    }

    @Test
    @Rollback
    public void updatePatientNoteTest() {
        PatientNote oldPatientNote = PatientNote.builder()
            .oldNoteGuid("old_note_guid")
            .createdDateTime(LocalDateTime.now())
            .lastModifiedDateTime(LocalDateTime.now())
            .createdByUserId(1L)
            .lastModifiedByUserId(1L)
            .note("note")
            .patientId(1L)
            .build();
        PatientNote newPatientNote = patientNoteRepository.save(oldPatientNote);
        newPatientNote.setOldNoteGuid("old_note_guid_2");
        newPatientNote.setCreatedDateTime(LocalDateTime.now().plus(1, MINUTES));
        newPatientNote.setLastModifiedDateTime(LocalDateTime.now().plus(1, MINUTES));
        newPatientNote.setCreatedByUserId(2L);
        newPatientNote.setLastModifiedByUserId(2L);
        newPatientNote.setNote("note_2");
        newPatientNote.setPatientId(2L);
        patientNoteRepository.update(newPatientNote.getCreatedDateTime(), newPatientNote.getLastModifiedDateTime(),
            newPatientNote.getCreatedByUserId(), newPatientNote.getLastModifiedByUserId(),
            newPatientNote.getNote(), newPatientNote.getPatientId(), newPatientNote.getId());
        PatientNote updatedPatientNote = Optional.of(patientNoteRepository.findById(newPatientNote.getId()))
            .get().orElse(null);
        Assertions.assertThat(newPatientNote).isEqualTo(updatedPatientNote);
    }

}
