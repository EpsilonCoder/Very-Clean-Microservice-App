package sn.ssi.sigmap.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link sn.ssi.sigmap.domain.Historique} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HistoriqueDTO implements Serializable {

    private Long id;

    private String etat;

    private LocalDate dateRejet;

    private LocalDate dateRejet2;

    private LocalDate dateMiseValidation;

    private String commentaireRejet;

    private String commentaireMiseValidation;

    @Lob
    private byte[] fichierMiseValidation;

    private String fichierMiseValidationContentType;

    @Lob
    private byte[] fichierRejet;

    private String fichierRejetContentType;
    private String etat2;

    private String commentaireRejet2;

    @Lob
    private byte[] fichierRejet2;

    private String fichierRejet2ContentType;
    private PlanPassationDTO planPassation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public LocalDate getDateRejet() {
        return dateRejet;
    }

    public void setDateRejet(LocalDate dateRejet) {
        this.dateRejet = dateRejet;
    }

    public LocalDate getDateRejet2() {
        return dateRejet2;
    }

    public void setDateRejet2(LocalDate dateRejet2) {
        this.dateRejet2 = dateRejet2;
    }

    public LocalDate getDateMiseValidation() {
        return dateMiseValidation;
    }

    public void setDateMiseValidation(LocalDate dateMiseValidation) {
        this.dateMiseValidation = dateMiseValidation;
    }

    public String getCommentaireRejet() {
        return commentaireRejet;
    }

    public void setCommentaireRejet(String commentaireRejet) {
        this.commentaireRejet = commentaireRejet;
    }

    public String getCommentaireMiseValidation() {
        return commentaireMiseValidation;
    }

    public void setCommentaireMiseValidation(String commentaireMiseValidation) {
        this.commentaireMiseValidation = commentaireMiseValidation;
    }

    public byte[] getFichierMiseValidation() {
        return fichierMiseValidation;
    }

    public void setFichierMiseValidation(byte[] fichierMiseValidation) {
        this.fichierMiseValidation = fichierMiseValidation;
    }

    public String getFichierMiseValidationContentType() {
        return fichierMiseValidationContentType;
    }

    public void setFichierMiseValidationContentType(String fichierMiseValidationContentType) {
        this.fichierMiseValidationContentType = fichierMiseValidationContentType;
    }

    public byte[] getFichierRejet() {
        return fichierRejet;
    }

    public void setFichierRejet(byte[] fichierRejet) {
        this.fichierRejet = fichierRejet;
    }

    public String getFichierRejetContentType() {
        return fichierRejetContentType;
    }

    public void setFichierRejetContentType(String fichierRejetContentType) {
        this.fichierRejetContentType = fichierRejetContentType;
    }

    public String getEtat2() {
        return etat2;
    }

    public void setEtat2(String etat2) {
        this.etat2 = etat2;
    }

    public String getCommentaireRejet2() {
        return commentaireRejet2;
    }

    public void setCommentaireRejet2(String commentaireRejet2) {
        this.commentaireRejet2 = commentaireRejet2;
    }

    public byte[] getFichierRejet2() {
        return fichierRejet2;
    }

    public void setFichierRejet2(byte[] fichierRejet2) {
        this.fichierRejet2 = fichierRejet2;
    }

    public String getFichierRejet2ContentType() {
        return fichierRejet2ContentType;
    }

    public void setFichierRejet2ContentType(String fichierRejet2ContentType) {
        this.fichierRejet2ContentType = fichierRejet2ContentType;
    }

    public PlanPassationDTO getPlanPassation() {
        return planPassation;
    }

    public void setPlanPassation(PlanPassationDTO planPassation) {
        this.planPassation = planPassation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistoriqueDTO)) {
            return false;
        }

        HistoriqueDTO historiqueDTO = (HistoriqueDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, historiqueDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HistoriqueDTO{" +
            "id=" + getId() +
            ", etat='" + getEtat() + "'" +
            ", dateRejet='" + getDateRejet() + "'" +
            ", dateRejet2='" + getDateRejet2() + "'" +
            ", dateMiseValidation='" + getDateMiseValidation() + "'" +
            ", commentaireRejet='" + getCommentaireRejet() + "'" +
            ", commentaireMiseValidation='" + getCommentaireMiseValidation() + "'" +
            ", fichierMiseValidation='" + getFichierMiseValidation() + "'" +
            ", fichierRejet='" + getFichierRejet() + "'" +
            ", etat2='" + getEtat2() + "'" +
            ", commentaireRejet2='" + getCommentaireRejet2() + "'" +
            ", fichierRejet2='" + getFichierRejet2() + "'" +
            ", planPassation=" + getPlanPassation() +
            "}";
    }
}
