package modu.menu.agreementhistory.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.BaseTime;
import modu.menu.agreement.domain.Agreement;
import modu.menu.user.domain.User;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "agreement_history_tb")
@Entity
public class AgreementHistory extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agreement_id")
    private Agreement agreement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
