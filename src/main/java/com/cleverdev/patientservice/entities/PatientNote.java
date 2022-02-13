package com.cleverdev.patientservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PatientNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @Column(nullable = false)
    private Instant createdDateTime;
    @Column(nullable = false)
    private Instant lastModifiedDateTime;
    private Long createdByUserId;
    private Long lastModifiedByUserId;
    private String note;
    @Column(nullable = false)
    private Long patientId;
}
