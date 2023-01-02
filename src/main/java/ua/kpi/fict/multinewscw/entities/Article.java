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

    @Column(length = 2048)
    private String articleDescription;

    private Date articleDate;

    private String articleSource;

    private String articleTitleEn;

    @Column(length = 2048)
    private String articleDescriptionEn;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn
    private Customer customer;

}
