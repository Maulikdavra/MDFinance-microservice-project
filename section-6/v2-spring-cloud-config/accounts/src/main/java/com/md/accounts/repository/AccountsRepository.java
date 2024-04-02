package com.md.accounts.repository;

import com.md.accounts.entity.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    /**
     * This method is used to find the account details by customer id
     * @param customerId - customer id
     * @return Optional<Accounts> - account details
     */
    Optional<Accounts> findByCustomerId(Long customerId);

    /**
     * This method is used to delete the account details by customer id
     * @param customerId customer id
     * @Transactional - to perform the transaction,
     * @Modifying - to perform the modification
     */
    @Transactional
    @Modifying
    void deleteByCustomerId(Long customerId);
}
