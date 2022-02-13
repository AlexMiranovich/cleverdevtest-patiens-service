package com.cleverdev.patientservice.services.impl;

import com.cleverdev.patientservice.entities.PatientProfile;
import com.cleverdev.patientservice.repositories.PatientProfileRepository;
import com.cleverdev.patientservice.services.PatientProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientProfileServiceImpl implements PatientProfileService {

    private final PatientProfileRepository patientProfileRepository;

    @Override
    public PatientProfile save(PatientProfile patientProfile) { return patientProfileRepository.save(patientProfile); }
}
