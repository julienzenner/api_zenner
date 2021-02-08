package org.miage.coursservice.entity.episode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.net.URL;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Episode implements Serializable {

    private static final long serialVersionUID = 674567895439L;

    @Id
    private String id;
    private String titre;
    private URL video;
}