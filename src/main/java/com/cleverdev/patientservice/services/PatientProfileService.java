package com.cleverdev.patientservice.services;

import com.cleverdev.patientservice.entities.PatientProfile;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

public interface PatientProfileService  {

    PatientProfile findByGuid(String guid);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional(propagation = Propagation.NESTED)
    PatientProfile save(PatientProfile patientProfile);
}
