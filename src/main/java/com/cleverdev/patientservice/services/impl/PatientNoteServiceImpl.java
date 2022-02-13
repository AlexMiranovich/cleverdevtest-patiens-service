package com.cleverdev.patientservice.services.impl;

import com.cleverdev.patientservice.entities.PatientNote;
import com.cleverdev.patientservice.repositories.PatientNoteRepository;
import com.cleverdev.patientservice.services.PatientNoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientNoteServiceImpl implements PatientNoteService {

    private final PatientNoteRepository patientNoteRepository;

    @Override
    public PatientNote save(PatientNote patientNote) { return patientNoteRepository.save(patientNote); }
}
