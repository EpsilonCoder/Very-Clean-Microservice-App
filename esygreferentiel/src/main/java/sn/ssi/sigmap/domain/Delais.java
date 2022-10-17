package sn.ssi.sigmap.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Delais.
 */
@Entity
@Table(name = "delais")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Delais implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "unite", nullable = false)
    private String unite;

    @NotNull
    @Column(name = "valeur", nullable = false)
    private Integer valeur;

    @NotNull
    @Column(name = "debut_validite", nullable = false)
    private Instant debutValidite;

    @NotNull
    @Column(name = "fin_validite", nullable = false)
    private Instant finValidite;

    @Column(name = "commentaires")
    private String commentaires;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Delais id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Delais libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return this.code;
    }

    public Delais code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUnite() {
        return this.unite;
    }

    public Delais unite(String unite) {
        this.setUnite(unite);
        return this;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Integer getValeur() {
        return this.valeur;
    }

    public Delais valeur(Integer valeur) {
        this.setValeur(valeur);
        return this;
    }

    public void setValeur(Integer valeur) {
        this.valeur = valeur;
    }

    public Instant getDebutValidite() {
        return this.debutValidite;
    }

    public Delais debutValidite(Instant debutValidite) {
        this.setDebutValidite(debutValidite);
        return this;
    }

    public void setDebutValidite(Instant debutValidite) {
        this.debutValidite = debutValidite;
    }

    public Instant getFinValidite() {
        return this.finValidite;
    }

    public Delais finValidite(Instant finValidite) {
        this.setFinValidite(finValidite);
        return this;
    }

    public void setFinValidite(Instant finValidite) {
        this.finValidite = finValidite;
    }

    public String getCommentaires() {
        return this.commentaires;
    }

    public Delais commentaires(String commentaires) {
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
        if (!(o instanceof Delais)) {
            return false;
        }
        return id != null && id.equals(((Delais) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Delais{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", code='" + getCode() + "'" +
            ", unite='" + getUnite() + "'" +
            ", valeur=" + getValeur() +
            ", debutValidite='" + getDebutValidite() + "'" +
            ", finValidite='" + getFinValidite() + "'" +
            ", commentaires='" + getCommentaires() + "'" +
            "}";
    }
}
