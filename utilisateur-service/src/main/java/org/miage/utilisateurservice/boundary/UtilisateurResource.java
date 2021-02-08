package org.miage.utilisateurservice.boundary;

import org.miage.utilisateurservice.entity.Statut;
import org.miage.utilisateurservice.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurResource extends JpaRepository<Utilisateur, String> {

    List<Utilisateur> findAllByStatut(Statut statut);

    Optional<Utilisateur> findByIdAndStatut(String id, Statut statut);

    boolean existsByMail(String mail);
}