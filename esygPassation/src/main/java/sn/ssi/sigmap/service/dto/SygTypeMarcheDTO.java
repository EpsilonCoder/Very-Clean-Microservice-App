package sn.ssi.sigmap.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link sn.ssi.sigmap.domain.SygTypeMarche} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SygTypeMarcheDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private String code;

    @NotNull(message = "must not be null")
    private String libelle;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SygTypeMarcheDTO)) {
            return false;
        }

        SygTypeMarcheDTO sygTypeMarcheDTO = (SygTypeMarcheDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sygTypeMarcheDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SygTypeMarcheDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
