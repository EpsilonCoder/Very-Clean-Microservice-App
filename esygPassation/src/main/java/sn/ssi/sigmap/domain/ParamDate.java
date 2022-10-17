package sn.ssi.sigmap.domain;

import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A ParamDate.
 */
@Table("param_date")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParamDate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("date_creat")
    private LocalDate dateCreat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ParamDate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreat() {
        return this.dateCreat;
    }

    public ParamDate dateCreat(LocalDate dateCreat) {
        this.setDateCreat(dateCreat);
        return this;
    }

    public void setDateCreat(LocalDate dateCreat) {
        this.dateCreat = dateCreat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParamDate)) {
            return false;
        }
        return id != null && id.equals(((ParamDate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParamDate{" +
            "id=" + getId() +
            ", dateCreat='" + getDateCreat() + "'" +
            "}";
    }
}
