package com.cleverdev.patientservice.repositories;

import com.cleverdev.patientservice.entities.CompanyUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompanyUserRepositoryTest {

    @Autowired
    private CompanyUserRepository companyUserRepository;

    @Test
    @Rollback
    public void saveCompanyUserTest() {
        CompanyUser companyUser = CompanyUser.builder()
            .login("Alex")
            .build();
        CompanyUser newUser = companyUserRepository.save(companyUser);
        Assertions.assertThat(newUser.getId()).isGreaterThan(0);
    }

    @Test
    @Rollback
    public void findByLoginTest() {
        CompanyUser companyUser = CompanyUser.builder()
            .login("Alex")
            .build();
        companyUserRepository.save(companyUser);
        CompanyUser newCompanyUser = companyUserRepository.findByLogin(companyUser.getLogin());
        Assertions.assertThat(companyUser.getLogin()).isEqualTo(newCompanyUser.getLogin());
    }
}
