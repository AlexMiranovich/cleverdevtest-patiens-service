package com.cleverdev.patientservice.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ClientProperties {

    private String url = "http://localhost:8081";
    public static String CLIENTS_URL = "/clients";
    public static String CLIENT_NOTES_URL = "/notes";
}
