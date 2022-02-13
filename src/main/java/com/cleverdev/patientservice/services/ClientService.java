package com.cleverdev.patientservice.services;

import com.cleverdev.patientservice.entities.dto.ClientDto;
import com.cleverdev.patientservice.entities.dto.ClientNoteDto;
import com.cleverdev.patientservice.entities.dto.ClientNoteRequestDto;

import java.util.List;

public interface ClientService {

    List<ClientDto> getClients();

    List<ClientNoteDto> getNotes(ClientNoteRequestDto clientNoteRequestDto);
}
