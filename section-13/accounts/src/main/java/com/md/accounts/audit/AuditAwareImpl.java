package com.md.accounts.audit;

import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNTS_MS");
    }
}
