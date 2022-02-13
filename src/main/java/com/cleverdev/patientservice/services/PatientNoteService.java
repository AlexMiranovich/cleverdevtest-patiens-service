package com.cleverdev.patientservice.services;

import com.cleverdev.patientservice.entities.PatientNote;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

public interface PatientNoteService {

    PatientNote findByOldNoteGuid(String oldNoteGuid);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional(propagation = Propagation.NESTED)
    PatientNote save(PatientNote patientNote);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional(propagation = Propagation.NESTED)
    void update(PatientNote patientNote);
}
