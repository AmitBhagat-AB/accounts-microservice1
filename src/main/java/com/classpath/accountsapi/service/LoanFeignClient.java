package com.classpath.accountsapi.service;

import com.classpath.accountsapi.model.Loan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "LOANSERVICE")
public interface LoanFeignClient {
    @PostMapping("/api/customer/{customerId}/loans")
    ResponseEntity<Loan> applyForLoan(@PathVariable("customerId") long customerId, @RequestBody Loan loan);
}
