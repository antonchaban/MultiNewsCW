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
    private Long infoId;

    @Column(length = 1000)
    private String link;

    @Column(length = 1000)
    private String source;

    private Date date;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
