package sn.ssi.sigmap.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PersonnesRessources.
 */
@Entity
@Table(name = "personnes_ressources")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonnesRessources implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private Long telephone;

    @NotNull
    @Column(name = "fonction", nullable = false)
    private String fonction;

    @Column(name = "commentaires")
    private String commentaires;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PersonnesRessources id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public PersonnesRessources prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public PersonnesRessources nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return this.email;
    }

    public PersonnesRessources email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTelephone() {
        return this.telephone;
    }

    public PersonnesRessources telephone(Long telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getFonction() {
        return this.fonction;
    }

    public PersonnesRessources fonction(String fonction) {
        this.setFonction(fonction);
        return this;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getCommentaires() {
        return this.commentaires;
    }

    public PersonnesRessources commentaires(String commentaires) {
        this.setCommentaires(commentaires);
        return this;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonnesRessources)) {
            return false;
        }
        return id != null && id.equals(((PersonnesRessources) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonnesRessources{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone=" + getTelephone() +
            ", fonction='" + getFonction() + "'" +
            ", commentaires='" + getCommentaires() + "'" +
            "}";
    }
}
