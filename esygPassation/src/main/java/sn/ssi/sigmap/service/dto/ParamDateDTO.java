package sn.ssi.sigmap.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link sn.ssi.sigmap.domain.ParamDate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParamDateDTO implements Serializable {

    private Long id;

    private LocalDate dateCreat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreat() {
        return dateCreat;
    }

    public void setDateCreat(LocalDate dateCreat) {
        this.dateCreat = dateCreat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParamDateDTO)) {
            return false;
        }

        ParamDateDTO paramDateDTO = (ParamDateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paramDateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParamDateDTO{" +
            "id=" + getId() +
            ", dateCreat='" + getDateCreat() + "'" +
            "}";
    }
}
