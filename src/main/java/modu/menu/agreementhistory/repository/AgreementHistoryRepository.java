package modu.menu.agreementhistory.repository;

import modu.menu.agreementhistory.domain.AgreementHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementHistoryRepository extends JpaRepository<AgreementHistory, Long> {
}
