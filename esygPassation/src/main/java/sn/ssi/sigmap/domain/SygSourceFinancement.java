package sn.ssi.sigmap.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A SygSourceFinancement.
 */
@Table("syg_source_financement")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SygSourceFinancement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("libelle")
    private String libelle;

    @Transient
    private SygTypeSourceFinancement sygTypeSourceFinancement;

    @Column("syg_type_source_financement_id")
    private Long sygTypeSourceFinancementId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SygSourceFinancement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public SygSourceFinancement libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public SygTypeSourceFinancement getSygTypeSourceFinancement() {
        return this.sygTypeSourceFinancement;
    }

    public void setSygTypeSourceFinancement(SygTypeSourceFinancement sygTypeSourceFinancement) {
        this.sygTypeSourceFinancement = sygTypeSourceFinancement;
        this.sygTypeSourceFinancementId = sygTypeSourceFinancement != null ? sygTypeSourceFinancement.getId() : null;
    }

    public SygSourceFinancement sygTypeSourceFinancement(SygTypeSourceFinancement sygTypeSourceFinancement) {
        this.setSygTypeSourceFinancement(sygTypeSourceFinancement);
        return this;
    }

    public Long getSygTypeSourceFinancementId() {
        return this.sygTypeSourceFinancementId;
    }

    public void setSygTypeSourceFinancementId(Long sygTypeSourceFinancement) {
        this.sygTypeSourceFinancementId = sygTypeSourceFinancement;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SygSourceFinancement)) {
            return false;
        }
        return id != null && id.equals(((SygSourceFinancement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SygSourceFinancement{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
