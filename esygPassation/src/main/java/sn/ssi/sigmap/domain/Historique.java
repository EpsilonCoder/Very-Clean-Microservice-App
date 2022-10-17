package sn.ssi.sigmap.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Historique.
 */
@Table("historique")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Historique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("etat")
    private String etat;

    @Column("date_rejet")
    private LocalDate dateRejet;

    @Column("date_rejet_2")
    private LocalDate dateRejet2;

    @Column("date_mise_validation")
    private LocalDate dateMiseValidation;

    @Column("commentaire_rejet")
    private String commentaireRejet;

    @Column("commentaire_mise_validation")
    private String commentaireMiseValidation;

    @Column("fichier_mise_validation")
    private byte[] fichierMiseValidation;

    @Column("fichier_mise_validation_content_type")
    private String fichierMiseValidationContentType;

    @Column("fichier_rejet")
    private byte[] fichierRejet;

    @Column("fichier_rejet_content_type")
    private String fichierRejetContentType;

    @Column("etat_2")
    private String etat2;

    @Column("commentaire_rejet_2")
    private String commentaireRejet2;

    @Column("fichier_rejet_2")
    private byte[] fichierRejet2;

    @Column("fichier_rejet_2_content_type")
    private String fichierRejet2ContentType;

    @Transient
    private PlanPassation planPassation;

    @Column("plan_passation_id")
    private Long planPassationId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Historique id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtat() {
        return this.etat;
    }

    public Historique etat(String etat) {
        this.setEtat(etat);
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public LocalDate getDateRejet() {
        return this.dateRejet;
    }

    public Historique dateRejet(LocalDate dateRejet) {
        this.setDateRejet(dateRejet);
        return this;
    }

    public void setDateRejet(LocalDate dateRejet) {
        this.dateRejet = dateRejet;
    }

    public LocalDate getDateRejet2() {
        return this.dateRejet2;
    }

    public Historique dateRejet2(LocalDate dateRejet2) {
        this.setDateRejet2(dateRejet2);
        return this;
    }

    public void setDateRejet2(LocalDate dateRejet2) {
        this.dateRejet2 = dateRejet2;
    }

    public LocalDate getDateMiseValidation() {
        return this.dateMiseValidation;
    }

    public Historique dateMiseValidation(LocalDate dateMiseValidation) {
        this.setDateMiseValidation(dateMiseValidation);
        return this;
    }

    public void setDateMiseValidation(LocalDate dateMiseValidation) {
        this.dateMiseValidation = dateMiseValidation;
    }

    public String getCommentaireRejet() {
        return this.commentaireRejet;
    }

    public Historique commentaireRejet(String commentaireRejet) {
        this.setCommentaireRejet(commentaireRejet);
        return this;
    }

    public void setCommentaireRejet(String commentaireRejet) {
        this.commentaireRejet = commentaireRejet;
    }

    public String getCommentaireMiseValidation() {
        return this.commentaireMiseValidation;
    }

    public Historique commentaireMiseValidation(String commentaireMiseValidation) {
        this.setCommentaireMiseValidation(commentaireMiseValidation);
        return this;
    }

    public void setCommentaireMiseValidation(String commentaireMiseValidation) {
        this.commentaireMiseValidation = commentaireMiseValidation;
    }

    public byte[] getFichierMiseValidation() {
        return this.fichierMiseValidation;
    }

    public Historique fichierMiseValidation(byte[] fichierMiseValidation) {
        this.setFichierMiseValidation(fichierMiseValidation);
        return this;
    }

    public void setFichierMiseValidation(byte[] fichierMiseValidation) {
        this.fichierMiseValidation = fichierMiseValidation;
    }

    public String getFichierMiseValidationContentType() {
        return this.fichierMiseValidationContentType;
    }

    public Historique fichierMiseValidationContentType(String fichierMiseValidationContentType) {
        this.fichierMiseValidationContentType = fichierMiseValidationContentType;
        return this;
    }

    public void setFichierMiseValidationContentType(String fichierMiseValidationContentType) {
        this.fichierMiseValidationContentType = fichierMiseValidationContentType;
    }

    public byte[] getFichierRejet() {
        return this.fichierRejet;
    }

    public Historique fichierRejet(byte[] fichierRejet) {
        this.setFichierRejet(fichierRejet);
        return this;
    }

    public void setFichierRejet(byte[] fichierRejet) {
        this.fichierRejet = fichierRejet;
    }

    public String getFichierRejetContentType() {
        return this.fichierRejetContentType;
    }

    public Historique fichierRejetContentType(String fichierRejetContentType) {
        this.fichierRejetContentType = fichierRejetContentType;
        return this;
    }

    public void setFichierRejetContentType(String fichierRejetContentType) {
        this.fichierRejetContentType = fichierRejetContentType;
    }

    public String getEtat2() {
        return this.etat2;
    }

    public Historique etat2(String etat2) {
        this.setEtat2(etat2);
        return this;
    }

    public void setEtat2(String etat2) {
        this.etat2 = etat2;
    }

    public String getCommentaireRejet2() {
        return this.commentaireRejet2;
    }

    public Historique commentaireRejet2(String commentaireRejet2) {
        this.setCommentaireRejet2(commentaireRejet2);
        return this;
    }

    public void setCommentaireRejet2(String commentaireRejet2) {
        this.commentaireRejet2 = commentaireRejet2;
    }

    public byte[] getFichierRejet2() {
        return this.fichierRejet2;
    }

    public Historique fichierRejet2(byte[] fichierRejet2) {
        this.setFichierRejet2(fichierRejet2);
        return this;
    }

    public void setFichierRejet2(byte[] fichierRejet2) {
        this.fichierRejet2 = fichierRejet2;
    }

    public String getFichierRejet2ContentType() {
        return this.fichierRejet2ContentType;
    }

    public Historique fichierRejet2ContentType(String fichierRejet2ContentType) {
        this.fichierRejet2ContentType = fichierRejet2ContentType;
        return this;
    }

    public void setFichierRejet2ContentType(String fichierRejet2ContentType) {
        this.fichierRejet2ContentType = fichierRejet2ContentType;
    }

    public PlanPassation getPlanPassation() {
        return this.planPassation;
    }

    public void setPlanPassation(PlanPassation planPassation) {
        this.planPassation = planPassation;
        this.planPassationId = planPassation != null ? planPassation.getId() : null;
    }

    public Historique planPassation(PlanPassation planPassation) {
        this.setPlanPassation(planPassation);
        return this;
    }

    public Long getPlanPassationId() {
        return this.planPassationId;
    }

    public void setPlanPassationId(Long planPassation) {
        this.planPassationId = planPassation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Historique)) {
            return false;
        }
        return id != null && id.equals(((Historique) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Historique{" +
            "id=" + getId() +
            ", etat='" + getEtat() + "'" +
            ", dateRejet='" + getDateRejet() + "'" +
            ", dateRejet2='" + getDateRejet2() + "'" +
            ", dateMiseValidation='" + getDateMiseValidation() + "'" +
            ", commentaireRejet='" + getCommentaireRejet() + "'" +
            ", commentaireMiseValidation='" + getCommentaireMiseValidation() + "'" +
            ", fichierMiseValidation='" + getFichierMiseValidation() + "'" +
            ", fichierMiseValidationContentType='" + getFichierMiseValidationContentType() + "'" +
            ", fichierRejet='" + getFichierRejet() + "'" +
            ", fichierRejetContentType='" + getFichierRejetContentType() + "'" +
            ", etat2='" + getEtat2() + "'" +
            ", commentaireRejet2='" + getCommentaireRejet2() + "'" +
            ", fichierRejet2='" + getFichierRejet2() + "'" +
            ", fichierRejet2ContentType='" + getFichierRejet2ContentType() + "'" +
            "}";
    }
}
