package com.cleverdev.patientservice.repositories;

import com.cleverdev.patientservice.entities.PatientNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientNoteRepository extends JpaRepository<PatientNote, Long> {
}
