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
public class ArticleMain {
    @EmbeddedId
    private InfoLanguageID infoLanguageID = new InfoLanguageID();

    private String articleTitle;

    private String articleDescription;

}
