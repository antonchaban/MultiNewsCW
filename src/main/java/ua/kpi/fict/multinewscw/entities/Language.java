package ua.kpi.fict.multinewscw.entities;

import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Language {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long languageId;

    @Column(length = 5)
    private String languageCode;
}
