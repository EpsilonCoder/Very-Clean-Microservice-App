package sn.ssi.sigmap.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link sn.ssi.sigmap.domain.SygSourceFinancement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SygSourceFinancementDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private String libelle;

    private SygTypeSourceFinancementDTO sygTypeSourceFinancement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public SygTypeSourceFinancementDTO getSygTypeSourceFinancement() {
        return sygTypeSourceFinancement;
    }

    public void setSygTypeSourceFinancement(SygTypeSourceFinancementDTO sygTypeSourceFinancement) {
        this.sygTypeSourceFinancement = sygTypeSourceFinancement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SygSourceFinancementDTO)) {
            return false;
        }

        SygSourceFinancementDTO sygSourceFinancementDTO = (SygSourceFinancementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sygSourceFinancementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SygSourceFinancementDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", sygTypeSourceFinancement=" + getSygTypeSourceFinancement() +
            "}";
    }
}
