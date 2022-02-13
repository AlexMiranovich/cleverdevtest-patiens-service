package com.cleverdev.patientservice.services;

import com.cleverdev.patientservice.entities.CompanyUser;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

public interface CompanyUserService {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Transactional(propagation = Propagation.NESTED)
    CompanyUser findByLogin(String login);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional(propagation = Propagation.NESTED)
    CompanyUser save(CompanyUser companyUser);
}
