package ua.kpi.fict.multinewscw.entities;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    private String articleTitle;

    private String articleLink;

    private String articleDescription;

    private Date articleDate;

    private String articleSource;

    @ToString.Exclude
    @ManyToOne
//    @JoinColumn(name = "customer_id")
    private Customer customer;

}
