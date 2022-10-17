package sn.ssi.sigmap.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ConfigurationTaux.
 */
@Entity
@Table(name = "configuration_taux")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConfigurationTaux implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "taux")
    private Double taux;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private ZonedDateTime dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private ZonedDateTime dateFin;

    @NotNull
    @Column(name = "invalid", nullable = false)
    private Boolean invalid;

    @ManyToOne
    private Pays pays;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ConfigurationTaux id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public ConfigurationTaux code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public ConfigurationTaux libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getTaux() {
        return this.taux;
    }

    public ConfigurationTaux taux(Double taux) {
        this.setTaux(taux);
        return this;
    }

    public void setTaux(Double taux) {
        this.taux = taux;
    }

    public ZonedDateTime getDateDebut() {
        return this.dateDebut;
    }

    public ConfigurationTaux dateDebut(ZonedDateTime dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDateFin() {
        return this.dateFin;
    }

    public ConfigurationTaux dateFin(ZonedDateTime dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public Boolean getInvalid() {
        return this.invalid;
    }

    public ConfigurationTaux invalid(Boolean invalid) {
        this.setInvalid(invalid);
        return this;
    }

    public void setInvalid(Boolean invalid) {
        this.invalid = invalid;
    }

    public Pays getPays() {
        return this.pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public ConfigurationTaux pays(Pays pays) {
        this.setPays(pays);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfigurationTaux)) {
            return false;
        }
        return id != null && id.equals(((ConfigurationTaux) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConfigurationTaux{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", taux=" + getTaux() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", invalid='" + getInvalid() + "'" +
            "}";
    }
}
