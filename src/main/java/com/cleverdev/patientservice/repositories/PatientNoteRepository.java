package com.cleverdev.patientservice.repositories;

import com.cleverdev.patientservice.entities.PatientNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PatientNoteRepository extends JpaRepository<PatientNote, Long> {

    PatientNote findByOldNoteGuid(String oldNoteGuid);

    @Modifying
    @Query("update PatientNote note set note.createdDateTime = :createdDateTime, note.lastModifiedDateTime = :lastModifiedDateTime, " +
            "note.createdByUserId = :createdByUserId, note.lastModifiedByUserId = :lastModifiedByUserId, " +
            "note.note = :note, note.patientId = :patientId where note.id = :id")
    void update(@Param(value = "createdDateTime") LocalDateTime createdDateTime,
                @Param(value = "lastModifiedDateTime") LocalDateTime lastModifiedDateTime,
                @Param(value = "createdByUserId") Long createdByUserId,
                @Param(value = "lastModifiedByUserId") Long lastModifiedByUserId,
                @Param(value = "note") String note,
                @Param(value = "patientId") Long patientId,
                @Param(value = "id") Long id);
}
