package com.cleverdev.patientservice.repositories;

import com.cleverdev.patientservice.entities.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {

    @Query("SELECT pp FROM PatientProfile pp WHERE pp.oldClientGuid LIKE %:guid%")
    PatientProfile findByGuid(@Param(value = "guid") String guid);
}
