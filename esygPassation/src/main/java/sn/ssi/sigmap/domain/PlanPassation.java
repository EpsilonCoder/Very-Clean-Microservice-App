package sn.ssi.sigmap.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A PlanPassation.
 */
@Table("plan_passation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanPassation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("statut")
    private String statut;

    @Column("date_debut")
    private LocalDate dateDebut;

    @Column("date_fin")
    private LocalDate dateFin;

    @Column("commentaire")
    private String commentaire;

    @NotNull(message = "must not be null")
    @Column("annee")
    private Integer annee;

    @NotNull(message = "must not be null")
    @Column("date_creation")
    private LocalDate dateCreation;

    @Column("date_mise_validation")
    private LocalDate dateMiseValidation;

    @Column("date_validation")
    private LocalDate dateValidation;

    @Column("commentaire_mise_en_validation_ac")
    private String commentaireMiseEnValidationAC;

    @Column("reference_mise_validation_ac")
    private String referenceMiseValidationAC;

    @Column("fichier_mise_validation_ac")
    private byte[] fichierMiseValidationAC;

    @Column("fichier_mise_validation_ac_content_type")
    private String fichierMiseValidationACContentType;

    @Column("date_mise_en_validation_ccmp")
    private LocalDate dateMiseEnValidationCcmp;

    @Column("fichier_mise_validation_ccmp")
    private byte[] fichierMiseValidationCcmp;

    @Column("fichier_mise_validation_ccmp_content_type")
    private String fichierMiseValidationCcmpContentType;

    @Column("reference_mise_validation_ccmp")
    private String referenceMiseValidationCcmp;

    @Column("date_validation_1")
    private LocalDate dateValidation1;

    @Column("commentaire_validation")
    private String commentaireValidation;

    @Column("reference_validation")
    private String referenceValidation;

    @Column("fichier_validation")
    private byte[] fichierValidation;

    @Column("fichier_validation_content_type")
    private String fichierValidationContentType;

    @Column("date_validation_2")
    private LocalDate dateValidation2;

    @Column("date_rejet")
    private LocalDate dateRejet;

    @Column("date_publication")
    private LocalDate datePublication;

    @Column("commentaire_publication")
    private String commentairePublication;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlanPassation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatut() {
        return this.statut;
    }

    public PlanPassation statut(String statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public PlanPassation dateDebut(LocalDate dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public PlanPassation dateFin(LocalDate dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public PlanPassation commentaire(String commentaire) {
        this.setCommentaire(commentaire);
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Integer getAnnee() {
        return this.annee;
    }

    public PlanPassation annee(Integer annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public LocalDate getDateCreation() {
        return this.dateCreation;
    }

    public PlanPassation dateCreation(LocalDate dateCreation) {
        this.setDateCreation(dateCreation);
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateMiseValidation() {
        return this.dateMiseValidation;
    }

    public PlanPassation dateMiseValidation(LocalDate dateMiseValidation) {
        this.setDateMiseValidation(dateMiseValidation);
        return this;
    }

    public void setDateMiseValidation(LocalDate dateMiseValidation) {
        this.dateMiseValidation = dateMiseValidation;
    }

    public LocalDate getDateValidation() {
        return this.dateValidation;
    }

    public PlanPassation dateValidation(LocalDate dateValidation) {
        this.setDateValidation(dateValidation);
        return this;
    }

    public void setDateValidation(LocalDate dateValidation) {
        this.dateValidation = dateValidation;
    }

    public String getCommentaireMiseEnValidationAC() {
        return this.commentaireMiseEnValidationAC;
    }

    public PlanPassation commentaireMiseEnValidationAC(String commentaireMiseEnValidationAC) {
        this.setCommentaireMiseEnValidationAC(commentaireMiseEnValidationAC);
        return this;
    }

    public void setCommentaireMiseEnValidationAC(String commentaireMiseEnValidationAC) {
        this.commentaireMiseEnValidationAC = commentaireMiseEnValidationAC;
    }

    public String getReferenceMiseValidationAC() {
        return this.referenceMiseValidationAC;
    }

    public PlanPassation referenceMiseValidationAC(String referenceMiseValidationAC) {
        this.setReferenceMiseValidationAC(referenceMiseValidationAC);
        return this;
    }

    public void setReferenceMiseValidationAC(String referenceMiseValidationAC) {
        this.referenceMiseValidationAC = referenceMiseValidationAC;
    }

    public byte[] getFichierMiseValidationAC() {
        return this.fichierMiseValidationAC;
    }

    public PlanPassation fichierMiseValidationAC(byte[] fichierMiseValidationAC) {
        this.setFichierMiseValidationAC(fichierMiseValidationAC);
        return this;
    }

    public void setFichierMiseValidationAC(byte[] fichierMiseValidationAC) {
        this.fichierMiseValidationAC = fichierMiseValidationAC;
    }

    public String getFichierMiseValidationACContentType() {
        return this.fichierMiseValidationACContentType;
    }

    public PlanPassation fichierMiseValidationACContentType(String fichierMiseValidationACContentType) {
        this.fichierMiseValidationACContentType = fichierMiseValidationACContentType;
        return this;
    }

    public void setFichierMiseValidationACContentType(String fichierMiseValidationACContentType) {
        this.fichierMiseValidationACContentType = fichierMiseValidationACContentType;
    }

    public LocalDate getDateMiseEnValidationCcmp() {
        return this.dateMiseEnValidationCcmp;
    }

    public PlanPassation dateMiseEnValidationCcmp(LocalDate dateMiseEnValidationCcmp) {
        this.setDateMiseEnValidationCcmp(dateMiseEnValidationCcmp);
        return this;
    }

    public void setDateMiseEnValidationCcmp(LocalDate dateMiseEnValidationCcmp) {
        this.dateMiseEnValidationCcmp = dateMiseEnValidationCcmp;
    }

    public byte[] getFichierMiseValidationCcmp() {
        return this.fichierMiseValidationCcmp;
    }

    public PlanPassation fichierMiseValidationCcmp(byte[] fichierMiseValidationCcmp) {
        this.setFichierMiseValidationCcmp(fichierMiseValidationCcmp);
        return this;
    }

    public void setFichierMiseValidationCcmp(byte[] fichierMiseValidationCcmp) {
        this.fichierMiseValidationCcmp = fichierMiseValidationCcmp;
    }

    public String getFichierMiseValidationCcmpContentType() {
        return this.fichierMiseValidationCcmpContentType;
    }

    public PlanPassation fichierMiseValidationCcmpContentType(String fichierMiseValidationCcmpContentType) {
        this.fichierMiseValidationCcmpContentType = fichierMiseValidationCcmpContentType;
        return this;
    }

    public void setFichierMiseValidationCcmpContentType(String fichierMiseValidationCcmpContentType) {
        this.fichierMiseValidationCcmpContentType = fichierMiseValidationCcmpContentType;
    }

    public String getReferenceMiseValidationCcmp() {
        return this.referenceMiseValidationCcmp;
    }

    public PlanPassation referenceMiseValidationCcmp(String referenceMiseValidationCcmp) {
        this.setReferenceMiseValidationCcmp(referenceMiseValidationCcmp);
        return this;
    }

    public void setReferenceMiseValidationCcmp(String referenceMiseValidationCcmp) {
        this.referenceMiseValidationCcmp = referenceMiseValidationCcmp;
    }

    public LocalDate getDateValidation1() {
        return this.dateValidation1;
    }

    public PlanPassation dateValidation1(LocalDate dateValidation1) {
        this.setDateValidation1(dateValidation1);
        return this;
    }

    public void setDateValidation1(LocalDate dateValidation1) {
        this.dateValidation1 = dateValidation1;
    }

    public String getCommentaireValidation() {
        return this.commentaireValidation;
    }

    public PlanPassation commentaireValidation(String commentaireValidation) {
        this.setCommentaireValidation(commentaireValidation);
        return this;
    }

    public void setCommentaireValidation(String commentaireValidation) {
        this.commentaireValidation = commentaireValidation;
    }

    public String getReferenceValidation() {
        return this.referenceValidation;
    }

    public PlanPassation referenceValidation(String referenceValidation) {
        this.setReferenceValidation(referenceValidation);
        return this;
    }

    public void setReferenceValidation(String referenceValidation) {
        this.referenceValidation = referenceValidation;
    }

    public byte[] getFichierValidation() {
        return this.fichierValidation;
    }

    public PlanPassation fichierValidation(byte[] fichierValidation) {
        this.setFichierValidation(fichierValidation);
        return this;
    }

    public void setFichierValidation(byte[] fichierValidation) {
        this.fichierValidation = fichierValidation;
    }

    public String getFichierValidationContentType() {
        return this.fichierValidationContentType;
    }

    public PlanPassation fichierValidationContentType(String fichierValidationContentType) {
        this.fichierValidationContentType = fichierValidationContentType;
        return this;
    }

    public void setFichierValidationContentType(String fichierValidationContentType) {
        this.fichierValidationContentType = fichierValidationContentType;
    }

    public LocalDate getDateValidation2() {
        return this.dateValidation2;
    }

    public PlanPassation dateValidation2(LocalDate dateValidation2) {
        this.setDateValidation2(dateValidation2);
        return this;
    }

    public void setDateValidation2(LocalDate dateValidation2) {
        this.dateValidation2 = dateValidation2;
    }

    public LocalDate getDateRejet() {
        return this.dateRejet;
    }

    public PlanPassation dateRejet(LocalDate dateRejet) {
        this.setDateRejet(dateRejet);
        return this;
    }

    public void setDateRejet(LocalDate dateRejet) {
        this.dateRejet = dateRejet;
    }

    public LocalDate getDatePublication() {
        return this.datePublication;
    }

    public PlanPassation datePublication(LocalDate datePublication) {
        this.setDatePublication(datePublication);
        return this;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    public String getCommentairePublication() {
        return this.commentairePublication;
    }

    public PlanPassation commentairePublication(String commentairePublication) {
        this.setCommentairePublication(commentairePublication);
        return this;
    }

    public void setCommentairePublication(String commentairePublication) {
        this.commentairePublication = commentairePublication;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanPassation)) {
            return false;
        }
        return id != null && id.equals(((PlanPassation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanPassation{" +
            "id=" + getId() +
            ", statut='" + getStatut() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", annee=" + getAnnee() +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateMiseValidation='" + getDateMiseValidation() + "'" +
            ", dateValidation='" + getDateValidation() + "'" +
            ", commentaireMiseEnValidationAC='" + getCommentaireMiseEnValidationAC() + "'" +
            ", referenceMiseValidationAC='" + getReferenceMiseValidationAC() + "'" +
            ", fichierMiseValidationAC='" + getFichierMiseValidationAC() + "'" +
            ", fichierMiseValidationACContentType='" + getFichierMiseValidationACContentType() + "'" +
            ", dateMiseEnValidationCcmp='" + getDateMiseEnValidationCcmp() + "'" +
            ", fichierMiseValidationCcmp='" + getFichierMiseValidationCcmp() + "'" +
            ", fichierMiseValidationCcmpContentType='" + getFichierMiseValidationCcmpContentType() + "'" +
            ", referenceMiseValidationCcmp='" + getReferenceMiseValidationCcmp() + "'" +
            ", dateValidation1='" + getDateValidation1() + "'" +
            ", commentaireValidation='" + getCommentaireValidation() + "'" +
            ", referenceValidation='" + getReferenceValidation() + "'" +
            ", fichierValidation='" + getFichierValidation() + "'" +
            ", fichierValidationContentType='" + getFichierValidationContentType() + "'" +
            ", dateValidation2='" + getDateValidation2() + "'" +
            ", dateRejet='" + getDateRejet() + "'" +
            ", datePublication='" + getDatePublication() + "'" +
            ", commentairePublication='" + getCommentairePublication() + "'" +
            "}";
    }
}
