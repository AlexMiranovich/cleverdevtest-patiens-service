package com.cleverdev.patientservice.scheduled;

import com.cleverdev.patientservice.entities.CompanyUser;
import com.cleverdev.patientservice.entities.PatientNote;
import com.cleverdev.patientservice.entities.PatientProfile;
import com.cleverdev.patientservice.entities.dto.ClientDto;
import com.cleverdev.patientservice.entities.dto.ClientNoteDto;
import com.cleverdev.patientservice.entities.dto.ClientNoteRequestDto;
import com.cleverdev.patientservice.services.ClientService;
import com.cleverdev.patientservice.services.CompanyUserService;
import com.cleverdev.patientservice.services.PatientNoteService;
import com.cleverdev.patientservice.services.PatientProfileService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;

@Slf4j
@Component
public class ImportDataProcessing implements Runnable {

    private final static ForkJoinPool FORK_JOIN_POOL = ForkJoinPool.commonPool();

    private final ClientService clientService;
    private final CompanyUserService companyUserService;
    private final PatientNoteService patientNoteService;
    private final PatientProfileService patientProfileService;

    public ImportDataProcessing(ClientService clientService,
                                CompanyUserService companyUserService,
                                PatientNoteService patientNoteService,
                                PatientProfileService patientProfileService) {
        this.clientService = clientService;
        this.companyUserService = companyUserService;
        this.patientNoteService = patientNoteService;
        this.patientProfileService = patientProfileService;
    }

    @SneakyThrows
    @Override
    public void run() {
        List<ClientDto> clients = clientService.getClients();
        if (CollectionUtils.isEmpty(clients)) return;
        FORK_JOIN_POOL.submit(() -> clients.parallelStream().forEach(this::process)).join();
    }

    private void process(ClientDto clientDto) {
        try {
            log.info("Start processing of the client with guid {}", clientDto.getGuid());
            if (clientDto.getStatus() == null || clientDto.getStatus().getCode() != 200) {
                log.info("Processing of the client was finished, because client have status not ACTIVE - {}", clientDto.getGuid());
                return;
            }
            ClientNoteRequestDto requestDto = ClientNoteRequestDto.builder()
                .agency(clientDto.getAgency())
                .clientGuid(clientDto.getGuid())
                .dateFrom(LocalDate.now().minus(20, ChronoUnit.YEARS))
                .dateTo(LocalDate.now())
                .build();
            List<ClientNoteDto> clientNotes = clientService.getNotes(requestDto);
            if (CollectionUtils.isEmpty(clientNotes)) {
                log.info("Processing client's notes was finished, because client don't have client's notes - {}", clientDto.getGuid());
                return;
            }
            processUsers(clientNotes, clientDto);
        } catch (Exception exception) {
            log.error("Failed to process client - {}. Error - {}", clientDto.getGuid(), exception.getMessage());
        } finally {
            log.info("Processing of the client with guid {} was finished", clientDto.getGuid());
        }
    }

    private void processUsers(List<ClientNoteDto> clientNotes, ClientDto clientDto) {
        clientNotes.forEach(note -> {
            String login = note.getLoggedUser();
            CompanyUser createdUser = null;
            CompanyUser existedUser = companyUserService.findByLogin(login);
            if (existedUser == null) createdUser = companyUserService.save(CompanyUser.builder().login(login).build());
            CompanyUser userForNotes = Optional.ofNullable(createdUser).orElse(existedUser);
            processNote(note, userForNotes, clientDto);
        });
    }

    private void processNote(ClientNoteDto clientNote, CompanyUser user, ClientDto clientDto) {
        PatientProfile patientProfile = patientProfileService.findByGuid(clientDto.getGuid());
        //stub
        Long patientId = Optional.ofNullable(patientProfile)
            .map(PatientProfile::getId)
            .orElse(1L);

        PatientNote newPatientNote = PatientNote.builder()
            .oldNoteGuid(clientNote.getGuid())
            .createdDateTime(clientNote.getCreatedDateTime())
            .lastModifiedDateTime(clientNote.getModifiedDateTime())
            .createdByUserId(user.getId())
            .lastModifiedByUserId(user.getId())
            .note(clientNote.getComments())
            .patientId(patientId)
            .build();
        PatientNote oldPatientNote = patientNoteService.findByOldNoteGuid(clientNote.getGuid());
        if (oldPatientNote != null) {
            if (clientNote.getModifiedDateTime().isAfter(oldPatientNote.getLastModifiedDateTime()) &&
                    !clientNote.getComments().equals(oldPatientNote.getNote())) {
                newPatientNote.setId(oldPatientNote.getId());
                patientNoteService.update(newPatientNote);
            }
        } else {
            patientNoteService.save(newPatientNote);
        }
    }
}
