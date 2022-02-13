package com.cleverdev.patientservice.services.impl;

import com.cleverdev.patientservice.entities.CompanyUser;
import com.cleverdev.patientservice.repositories.CompanyUserRepository;
import com.cleverdev.patientservice.services.CompanyUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyUserServiceImpl implements CompanyUserService {

    private final CompanyUserRepository companyUserRepository;

    @Override
    public CompanyUser findByLogin(String login) {
        return companyUserRepository.findByLogin(login);
    }

    @Override
    public CompanyUser save(CompanyUser companyUser) { return companyUserRepository.save(companyUser); }
}
