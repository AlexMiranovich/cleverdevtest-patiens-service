package com.cleverdev.patientservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String oldNoteGuid;
    @Column(nullable = false)
    private LocalDateTime createdDateTime;
    @Column(nullable = false)
    private LocalDateTime lastModifiedDateTime;
    private Long createdByUserId;
    private Long lastModifiedByUserId;
    @Column(length = 4000)
    private String note;
    @Column(nullable = false)
    private Long patientId;
}
