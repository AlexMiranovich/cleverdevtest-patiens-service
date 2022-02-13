package com.cleverdev.patientservice.services;

import com.cleverdev.patientservice.entities.CompanyUser;

public interface CompanyUserService {

    CompanyUser findByLogin(String login);

    CompanyUser save(CompanyUser companyUser);
}
