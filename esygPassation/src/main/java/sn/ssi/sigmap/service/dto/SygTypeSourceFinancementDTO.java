package sn.ssi.sigmap.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link sn.ssi.sigmap.domain.SygTypeSourceFinancement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SygTypeSourceFinancementDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private String libelle;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SygTypeSourceFinancementDTO)) {
            return false;
        }

        SygTypeSourceFinancementDTO sygTypeSourceFinancementDTO = (SygTypeSourceFinancementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sygTypeSourceFinancementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SygTypeSourceFinancementDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
