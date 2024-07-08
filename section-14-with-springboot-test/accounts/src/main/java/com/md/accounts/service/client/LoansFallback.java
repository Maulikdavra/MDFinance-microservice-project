package com.md.accounts.service.client;

import com.md.accounts.dto.LoansDto;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class LoansFallback implements LoansFeignClient{

    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String correlationId, String mobileNumber) {
        // Help me return a default LoansDto object that can be returned in case of a fallback
        LoansDto defaultLoansDto = new LoansDto();
        defaultLoansDto.setMobileNumber("0000000000");
        defaultLoansDto.setLoanNumber("000000000000");
        defaultLoansDto.setLoanType("Default Loan");
        defaultLoansDto.setTotalLoan(0);
        defaultLoansDto.setAmountPaid(0);
        defaultLoansDto.setOutstandingAmount(0);

        return ResponseEntity.ok(defaultLoansDto);

    }
}
