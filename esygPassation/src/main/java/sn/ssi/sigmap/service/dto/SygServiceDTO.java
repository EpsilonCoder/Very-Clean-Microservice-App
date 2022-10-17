package sn.ssi.sigmap.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link sn.ssi.sigmap.domain.SygService} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SygServiceDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private String code;

    @NotNull(message = "must not be null")
    private String libelle;

    private String description;

    private SygTypeServiceDTO sygTypeService;

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

    public SygTypeServiceDTO getSygTypeService() {
        return sygTypeService;
    }

    public void setSygTypeService(SygTypeServiceDTO sygTypeService) {
        this.sygTypeService = sygTypeService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SygServiceDTO)) {
            return false;
        }

        SygServiceDTO sygServiceDTO = (SygServiceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sygServiceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SygServiceDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", sygTypeService=" + getSygTypeService() +
            "}";
    }
}
