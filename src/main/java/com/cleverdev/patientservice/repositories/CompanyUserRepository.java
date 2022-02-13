package com.cleverdev.patientservice.repositories;

import com.cleverdev.patientservice.entities.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {

    CompanyUser findByLogin(String login);
}
