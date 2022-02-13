package com.cleverdev.patientservice.services.impl;

import com.cleverdev.patientservice.properties.ClientProperties;
import com.cleverdev.patientservice.services.ClientService;
import com.cleverdev.patientservice.entities.dto.ClientDto;
import com.cleverdev.patientservice.entities.dto.ClientNoteDto;
import com.cleverdev.patientservice.entities.dto.ClientNoteRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import static com.cleverdev.patientservice.properties.ClientProperties.*;

@Service
public class ClientServiceImpl implements ClientService {

    private final ObjectMapper objectMapper;
    private final ClientProperties clientProperties;
    private final HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .build();

    public ClientServiceImpl(ObjectMapper objectMapper, ClientProperties clientProperties) {
        this.objectMapper = objectMapper;
        this.clientProperties = clientProperties;
    }

    @SneakyThrows
    @Override
    public List<ClientDto> getClients() {
        String url = String.format("%s%s", clientProperties.getUrl(), CLIENTS_URL);
        HttpRequest request = HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.noBody())
            .uri(URI.create(url))
            .build();
        HttpResponse<String> response =  httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        ClientDto[] clients = objectMapper.readValue(response.body(), ClientDto[].class);
        return Optional.ofNullable(clients).map(Arrays::asList).orElse(Collections.emptyList());
    }

    @SneakyThrows
    @Override
    public List<ClientNoteDto> getNotes(ClientNoteRequestDto clientNoteRequestDto) {
        String url = String.format("%s%s", clientProperties.getUrl(), CLIENT_NOTES_URL);
        HttpRequest request = HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(clientNoteRequestDto)))
            .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .uri(URI.create(url))
            .build();
        HttpResponse<String> response =  httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        ClientNoteDto[] clientNotes = objectMapper.readValue(response.body(), ClientNoteDto[].class);
        return Optional.ofNullable(clientNotes).map(Arrays::asList).orElse(Collections.emptyList());
    }

}
