package ua.kpi.fict.multinewscw.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import ua.kpi.fict.multinewscw.entities.helper.Indices;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Document(indexName = Indices.ARTICLE_INDEX, shards = 2)
//@Setting(settingPath = "static/es-settings.json")
public class Article { // TODO add enum for categories
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
    @ManyToOne(cascade = CascadeType.REFRESH) @JoinColumn
    private Customer customer;


    @Transient @ToString.Exclude
    private Long customerId;

}
