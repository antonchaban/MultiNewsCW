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
@Entity
public class ArticleMain implements Serializable {

    @EmbeddedId
    private InfoLanguageID id = new InfoLanguageID();

    private String title;

    private String description;

}
