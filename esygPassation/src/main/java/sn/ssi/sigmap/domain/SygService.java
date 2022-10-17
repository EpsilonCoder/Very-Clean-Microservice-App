package sn.ssi.sigmap.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A SygService.
 */
@Table("syg_service")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SygService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("code")
    private String code;

    @NotNull(message = "must not be null")
    @Column("libelle")
    private String libelle;

    @Column("description")
    private String description;

    @Transient
    private SygTypeService sygTypeService;

    @Column("syg_type_service_id")
    private Long sygTypeServiceId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SygService id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public SygService code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public SygService libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return this.description;
    }

    public SygService description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SygTypeService getSygTypeService() {
        return this.sygTypeService;
    }

    public void setSygTypeService(SygTypeService sygTypeService) {
        this.sygTypeService = sygTypeService;
        this.sygTypeServiceId = sygTypeService != null ? sygTypeService.getId() : null;
    }

    public SygService sygTypeService(SygTypeService sygTypeService) {
        this.setSygTypeService(sygTypeService);
        return this;
    }

    public Long getSygTypeServiceId() {
        return this.sygTypeServiceId;
    }

    public void setSygTypeServiceId(Long sygTypeService) {
        this.sygTypeServiceId = sygTypeService;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SygService)) {
            return false;
        }
        return id != null && id.equals(((SygService) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SygService{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
