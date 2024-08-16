package com.mycompany.company.web.rest;

import com.mycompany.company.repository.UserRepository;
import com.mycompany.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class CompanyResource {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/check-company")
    public Mono<ResponseEntity<String>> checkCompanyName(@RequestParam String companyName) {
        return userRepository
            .existsByCompanyName(companyName)
            .map(exists -> exists ? ResponseEntity.ok("redirect:/login") : ResponseEntity.ok("redirect:/account/register"))
            .onErrorReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error checking company name."));
    }
}
