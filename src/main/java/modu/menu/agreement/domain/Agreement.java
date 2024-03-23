package modu.menu.agreement.domain;

import jakarta.persistence.*;
import lombok.*;
import modu.menu.BaseTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "agreement_tb")
@Entity
public class Agreement extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String content;
    private Boolean isNecessary;
}
