package sn.ssi.sigmap.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link sn.ssi.sigmap.domain.Realisation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RealisationDTO implements Serializable {

    private Long id;

    private String libelle;

    @NotNull(message = "must not be null")
    private LocalDate dateAttribution;

    @NotNull(message = "must not be null")
    private Integer delaiexecution;

    private String objet;

    private BigDecimal montant;

    @NotNull(message = "must not be null")
    private Integer examenDncmp;

    @NotNull(message = "must not be null")
    private Integer examenPtf;

    @NotNull(message = "must not be null")
    private String chapitreImputation;

    @NotNull(message = "must not be null")
    private String autorisationEngagement;

    @NotNull(message = "must not be null")
    private LocalDate dateReceptionDossier;

    @NotNull(message = "must not be null")
    private LocalDate dateNonObjection;

    @NotNull(message = "must not be null")
    private LocalDate dateAutorisation;

    @NotNull(message = "must not be null")
    private LocalDate dateRecepNonObjection;

    @NotNull(message = "must not be null")
    private LocalDate dateRecepDossCorrige;

    @NotNull(message = "must not be null")
    private LocalDate datePubParPrmp;

    @NotNull(message = "must not be null")
    private LocalDate dateOuverturePlis;

    @NotNull(message = "must not be null")
    private LocalDate dateRecepNonObjectOcmp;

    @NotNull(message = "must not be null")
    private LocalDate dateRecepRapportEva;

    @NotNull(message = "must not be null")
    private LocalDate dateRecepNonObjectPtf;

    @NotNull(message = "must not be null")
    private LocalDate dateExamenJuridique;

    @NotNull(message = "must not be null")
    private LocalDate dateNotifContrat;

    @NotNull(message = "must not be null")
    private LocalDate dateApprobationContrat;

    private PlanPassationDTO planPassation;

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

    public LocalDate getDateAttribution() {
        return dateAttribution;
    }

    public void setDateAttribution(LocalDate dateAttribution) {
        this.dateAttribution = dateAttribution;
    }

    public Integer getDelaiexecution() {
        return delaiexecution;
    }

    public void setDelaiexecution(Integer delaiexecution) {
        this.delaiexecution = delaiexecution;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public Integer getExamenDncmp() {
        return examenDncmp;
    }

    public void setExamenDncmp(Integer examenDncmp) {
        this.examenDncmp = examenDncmp;
    }

    public Integer getExamenPtf() {
        return examenPtf;
    }

    public void setExamenPtf(Integer examenPtf) {
        this.examenPtf = examenPtf;
    }

    public String getChapitreImputation() {
        return chapitreImputation;
    }

    public void setChapitreImputation(String chapitreImputation) {
        this.chapitreImputation = chapitreImputation;
    }

    public String getAutorisationEngagement() {
        return autorisationEngagement;
    }

    public void setAutorisationEngagement(String autorisationEngagement) {
        this.autorisationEngagement = autorisationEngagement;
    }

    public LocalDate getDateReceptionDossier() {
        return dateReceptionDossier;
    }

    public void setDateReceptionDossier(LocalDate dateReceptionDossier) {
        this.dateReceptionDossier = dateReceptionDossier;
    }

    public LocalDate getDateNonObjection() {
        return dateNonObjection;
    }

    public void setDateNonObjection(LocalDate dateNonObjection) {
        this.dateNonObjection = dateNonObjection;
    }

    public LocalDate getDateAutorisation() {
        return dateAutorisation;
    }

    public void setDateAutorisation(LocalDate dateAutorisation) {
        this.dateAutorisation = dateAutorisation;
    }

    public LocalDate getDateRecepNonObjection() {
        return dateRecepNonObjection;
    }

    public void setDateRecepNonObjection(LocalDate dateRecepNonObjection) {
        this.dateRecepNonObjection = dateRecepNonObjection;
    }

    public LocalDate getDateRecepDossCorrige() {
        return dateRecepDossCorrige;
    }

    public void setDateRecepDossCorrige(LocalDate dateRecepDossCorrige) {
        this.dateRecepDossCorrige = dateRecepDossCorrige;
    }

    public LocalDate getDatePubParPrmp() {
        return datePubParPrmp;
    }

    public void setDatePubParPrmp(LocalDate datePubParPrmp) {
        this.datePubParPrmp = datePubParPrmp;
    }

    public LocalDate getDateOuverturePlis() {
        return dateOuverturePlis;
    }

    public void setDateOuverturePlis(LocalDate dateOuverturePlis) {
        this.dateOuverturePlis = dateOuverturePlis;
    }

    public LocalDate getDateRecepNonObjectOcmp() {
        return dateRecepNonObjectOcmp;
    }

    public void setDateRecepNonObjectOcmp(LocalDate dateRecepNonObjectOcmp) {
        this.dateRecepNonObjectOcmp = dateRecepNonObjectOcmp;
    }

    public LocalDate getDateRecepRapportEva() {
        return dateRecepRapportEva;
    }

    public void setDateRecepRapportEva(LocalDate dateRecepRapportEva) {
        this.dateRecepRapportEva = dateRecepRapportEva;
    }

    public LocalDate getDateRecepNonObjectPtf() {
        return dateRecepNonObjectPtf;
    }

    public void setDateRecepNonObjectPtf(LocalDate dateRecepNonObjectPtf) {
        this.dateRecepNonObjectPtf = dateRecepNonObjectPtf;
    }

    public LocalDate getDateExamenJuridique() {
        return dateExamenJuridique;
    }

    public void setDateExamenJuridique(LocalDate dateExamenJuridique) {
        this.dateExamenJuridique = dateExamenJuridique;
    }

    public LocalDate getDateNotifContrat() {
        return dateNotifContrat;
    }

    public void setDateNotifContrat(LocalDate dateNotifContrat) {
        this.dateNotifContrat = dateNotifContrat;
    }

    public LocalDate getDateApprobationContrat() {
        return dateApprobationContrat;
    }

    public void setDateApprobationContrat(LocalDate dateApprobationContrat) {
        this.dateApprobationContrat = dateApprobationContrat;
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
        if (!(o instanceof RealisationDTO)) {
            return false;
        }

        RealisationDTO realisationDTO = (RealisationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, realisationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RealisationDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", dateAttribution='" + getDateAttribution() + "'" +
            ", delaiexecution=" + getDelaiexecution() +
            ", objet='" + getObjet() + "'" +
            ", montant=" + getMontant() +
            ", examenDncmp=" + getExamenDncmp() +
            ", examenPtf=" + getExamenPtf() +
            ", chapitreImputation='" + getChapitreImputation() + "'" +
            ", autorisationEngagement='" + getAutorisationEngagement() + "'" +
            ", dateReceptionDossier='" + getDateReceptionDossier() + "'" +
            ", dateNonObjection='" + getDateNonObjection() + "'" +
            ", dateAutorisation='" + getDateAutorisation() + "'" +
            ", dateRecepNonObjection='" + getDateRecepNonObjection() + "'" +
            ", dateRecepDossCorrige='" + getDateRecepDossCorrige() + "'" +
            ", datePubParPrmp='" + getDatePubParPrmp() + "'" +
            ", dateOuverturePlis='" + getDateOuverturePlis() + "'" +
            ", dateRecepNonObjectOcmp='" + getDateRecepNonObjectOcmp() + "'" +
            ", dateRecepRapportEva='" + getDateRecepRapportEva() + "'" +
            ", dateRecepNonObjectPtf='" + getDateRecepNonObjectPtf() + "'" +
            ", dateExamenJuridique='" + getDateExamenJuridique() + "'" +
            ", dateNotifContrat='" + getDateNotifContrat() + "'" +
            ", dateApprobationContrat='" + getDateApprobationContrat() + "'" +
            ", planPassation=" + getPlanPassation() +
            "}";
    }
}
