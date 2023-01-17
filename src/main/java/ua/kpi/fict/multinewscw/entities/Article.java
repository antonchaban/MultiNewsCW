package ua.kpi.fict.multinewscw.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import ua.kpi.fict.multinewscw.entities.enums.Category;
import ua.kpi.fict.multinewscw.entities.enums.Role;
import ua.kpi.fict.multinewscw.entities.helper.Indices;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Document(indexName = Indices.ARTICLE_INDEX, shards = 2)
public class Article {
    @Id
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Field(type = FieldType.Keyword)
    private Long articleId;

    @Field(type = FieldType.Text)
    private String articleTitle;

    private String articleLink;

    @Column(length = 2048)
    @Field(type = FieldType.Text)
    private String articleDescription;

    private Date articleDate;

    private String articleSource;

    @Field(type = FieldType.Text)
    private String articleTitleEn;

    @Column(length = 2048)
    @Field(type = FieldType.Text)
    private String articleDescriptionEn;

    @ToString.Exclude
    @ElementCollection(targetClass = Category.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "article_category"
            , joinColumns = @JoinColumn(name = "article_id"))
    @Enumerated(EnumType.STRING)
    private Set<Category> categories = new HashSet<>();

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.REFRESH) @JoinColumn
    private Customer customer;


    @Transient @ToString.Exclude
    private Long customerId;

}
