package ua.kpi.fict.multinewscw.entities;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class ArticleInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleInfoId;

    @Column(length = 1000)
    private String articleLink;

    @Column(length = 1000)
    private String articleSource;

    private Date articleDate;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn
    private Customer customer;
}
