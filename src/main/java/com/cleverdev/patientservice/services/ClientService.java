package com.cleverdev.patientservice.services;

import com.cleverdev.patientservice.services.dto.ClientDto;
import com.cleverdev.patientservice.services.dto.ClientNoteDto;
import com.cleverdev.patientservice.services.dto.ClientNoteRequestDto;

import java.util.List;

public interface ClientService {

    List<ClientDto> getClients();

    List<ClientNoteDto> getNotes(ClientNoteRequestDto clientNoteRequestDto);
}
