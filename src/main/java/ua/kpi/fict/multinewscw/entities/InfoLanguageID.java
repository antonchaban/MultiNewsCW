package ua.kpi.fict.multinewscw.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Embeddable
public class InfoLanguageID implements Serializable {

    @ManyToOne
    @JoinColumn(name = "article_info_id")
    private ArticleInfo articleInfo;

    @ManyToOne @JoinColumn(name = "language_id")
    private Language language;
}
