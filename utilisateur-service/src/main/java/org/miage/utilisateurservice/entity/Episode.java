package org.miage.utilisateurservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.net.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Episode implements Serializable {

    private static final long serialVersionUID = 1905111041950251207L;

    private String id;
    private String titre;
    private URL video;
    private Boolean vision;
}