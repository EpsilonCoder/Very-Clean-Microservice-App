package sn.ssi.sigmap.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Realisation.
 */
@Table("realisation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Realisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("libelle")
    private String libelle;

    @NotNull(message = "must not be null")
    @Column("date_attribution")
    private LocalDate dateAttribution;

    @NotNull(message = "must not be null")
    @Column("delaiexecution")
    private Integer delaiexecution;

    @Column("objet")
    private String objet;

    @Column("montant")
    private BigDecimal montant;

    @NotNull(message = "must not be null")
    @Column("examen_dncmp")
    private Integer examenDncmp;

    @NotNull(message = "must not be null")
    @Column("examen_ptf")
    private Integer examenPtf;

    @NotNull(message = "must not be null")
    @Column("chapitre_imputation")
    private String chapitreImputation;

    @NotNull(message = "must not be null")
    @Column("autorisation_engagement")
    private String autorisationEngagement;

    @NotNull(message = "must not be null")
    @Column("date_reception_dossier")
    private LocalDate dateReceptionDossier;

    @NotNull(message = "must not be null")
    @Column("date_non_objection")
    private LocalDate dateNonObjection;

    @NotNull(message = "must not be null")
    @Column("date_autorisation")
    private LocalDate dateAutorisation;

    @NotNull(message = "must not be null")
    @Column("date_recep_non_objection")
    private LocalDate dateRecepNonObjection;

    @NotNull(message = "must not be null")
    @Column("date_recep_doss_corrige")
    private LocalDate dateRecepDossCorrige;

    @NotNull(message = "must not be null")
    @Column("date_pub_par_prmp")
    private LocalDate datePubParPrmp;

    @NotNull(message = "must not be null")
    @Column("date_ouverture_plis")
    private LocalDate dateOuverturePlis;

    @NotNull(message = "must not be null")
    @Column("date_recep_non_object_ocmp")
    private LocalDate dateRecepNonObjectOcmp;

    @NotNull(message = "must not be null")
    @Column("date_recep_rapport_eva")
    private LocalDate dateRecepRapportEva;

    @NotNull(message = "must not be null")
    @Column("date_recep_non_object_ptf")
    private LocalDate dateRecepNonObjectPtf;

    @NotNull(message = "must not be null")
    @Column("date_examen_juridique")
    private LocalDate dateExamenJuridique;

    @NotNull(message = "must not be null")
    @Column("date_notif_contrat")
    private LocalDate dateNotifContrat;

    @NotNull(message = "must not be null")
    @Column("date_approbation_contrat")
    private LocalDate dateApprobationContrat;

    @Transient
    private PlanPassation planPassation;

    @Column("plan_passation_id")
    private Long planPassationId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Realisation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Realisation libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public LocalDate getDateAttribution() {
        return this.dateAttribution;
    }

    public Realisation dateAttribution(LocalDate dateAttribution) {
        this.setDateAttribution(dateAttribution);
        return this;
    }

    public void setDateAttribution(LocalDate dateAttribution) {
        this.dateAttribution = dateAttribution;
    }

    public Integer getDelaiexecution() {
        return this.delaiexecution;
    }

    public Realisation delaiexecution(Integer delaiexecution) {
        this.setDelaiexecution(delaiexecution);
        return this;
    }

    public void setDelaiexecution(Integer delaiexecution) {
        this.delaiexecution = delaiexecution;
    }

    public String getObjet() {
        return this.objet;
    }

    public Realisation objet(String objet) {
        this.setObjet(objet);
        return this;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public BigDecimal getMontant() {
        return this.montant;
    }

    public Realisation montant(BigDecimal montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant != null ? montant.stripTrailingZeros() : null;
    }

    public Integer getExamenDncmp() {
        return this.examenDncmp;
    }

    public Realisation examenDncmp(Integer examenDncmp) {
        this.setExamenDncmp(examenDncmp);
        return this;
    }

    public void setExamenDncmp(Integer examenDncmp) {
        this.examenDncmp = examenDncmp;
    }

    public Integer getExamenPtf() {
        return this.examenPtf;
    }

    public Realisation examenPtf(Integer examenPtf) {
        this.setExamenPtf(examenPtf);
        return this;
    }

    public void setExamenPtf(Integer examenPtf) {
        this.examenPtf = examenPtf;
    }

    public String getChapitreImputation() {
        return this.chapitreImputation;
    }

    public Realisation chapitreImputation(String chapitreImputation) {
        this.setChapitreImputation(chapitreImputation);
        return this;
    }

    public void setChapitreImputation(String chapitreImputation) {
        this.chapitreImputation = chapitreImputation;
    }

    public String getAutorisationEngagement() {
        return this.autorisationEngagement;
    }

    public Realisation autorisationEngagement(String autorisationEngagement) {
        this.setAutorisationEngagement(autorisationEngagement);
        return this;
    }

    public void setAutorisationEngagement(String autorisationEngagement) {
        this.autorisationEngagement = autorisationEngagement;
    }

    public LocalDate getDateReceptionDossier() {
        return this.dateReceptionDossier;
    }

    public Realisation dateReceptionDossier(LocalDate dateReceptionDossier) {
        this.setDateReceptionDossier(dateReceptionDossier);
        return this;
    }

    public void setDateReceptionDossier(LocalDate dateReceptionDossier) {
        this.dateReceptionDossier = dateReceptionDossier;
    }

    public LocalDate getDateNonObjection() {
        return this.dateNonObjection;
    }

    public Realisation dateNonObjection(LocalDate dateNonObjection) {
        this.setDateNonObjection(dateNonObjection);
        return this;
    }

    public void setDateNonObjection(LocalDate dateNonObjection) {
        this.dateNonObjection = dateNonObjection;
    }

    public LocalDate getDateAutorisation() {
        return this.dateAutorisation;
    }

    public Realisation dateAutorisation(LocalDate dateAutorisation) {
        this.setDateAutorisation(dateAutorisation);
        return this;
    }

    public void setDateAutorisation(LocalDate dateAutorisation) {
        this.dateAutorisation = dateAutorisation;
    }

    public LocalDate getDateRecepNonObjection() {
        return this.dateRecepNonObjection;
    }

    public Realisation dateRecepNonObjection(LocalDate dateRecepNonObjection) {
        this.setDateRecepNonObjection(dateRecepNonObjection);
        return this;
    }

    public void setDateRecepNonObjection(LocalDate dateRecepNonObjection) {
        this.dateRecepNonObjection = dateRecepNonObjection;
    }

    public LocalDate getDateRecepDossCorrige() {
        return this.dateRecepDossCorrige;
    }

    public Realisation dateRecepDossCorrige(LocalDate dateRecepDossCorrige) {
        this.setDateRecepDossCorrige(dateRecepDossCorrige);
        return this;
    }

    public void setDateRecepDossCorrige(LocalDate dateRecepDossCorrige) {
        this.dateRecepDossCorrige = dateRecepDossCorrige;
    }

    public LocalDate getDatePubParPrmp() {
        return this.datePubParPrmp;
    }

    public Realisation datePubParPrmp(LocalDate datePubParPrmp) {
        this.setDatePubParPrmp(datePubParPrmp);
        return this;
    }

    public void setDatePubParPrmp(LocalDate datePubParPrmp) {
        this.datePubParPrmp = datePubParPrmp;
    }

    public LocalDate getDateOuverturePlis() {
        return this.dateOuverturePlis;
    }

    public Realisation dateOuverturePlis(LocalDate dateOuverturePlis) {
        this.setDateOuverturePlis(dateOuverturePlis);
        return this;
    }

    public void setDateOuverturePlis(LocalDate dateOuverturePlis) {
        this.dateOuverturePlis = dateOuverturePlis;
    }

    public LocalDate getDateRecepNonObjectOcmp() {
        return this.dateRecepNonObjectOcmp;
    }

    public Realisation dateRecepNonObjectOcmp(LocalDate dateRecepNonObjectOcmp) {
        this.setDateRecepNonObjectOcmp(dateRecepNonObjectOcmp);
        return this;
    }

    public void setDateRecepNonObjectOcmp(LocalDate dateRecepNonObjectOcmp) {
        this.dateRecepNonObjectOcmp = dateRecepNonObjectOcmp;
    }

    public LocalDate getDateRecepRapportEva() {
        return this.dateRecepRapportEva;
    }

    public Realisation dateRecepRapportEva(LocalDate dateRecepRapportEva) {
        this.setDateRecepRapportEva(dateRecepRapportEva);
        return this;
    }

    public void setDateRecepRapportEva(LocalDate dateRecepRapportEva) {
        this.dateRecepRapportEva = dateRecepRapportEva;
    }

    public LocalDate getDateRecepNonObjectPtf() {
        return this.dateRecepNonObjectPtf;
    }

    public Realisation dateRecepNonObjectPtf(LocalDate dateRecepNonObjectPtf) {
        this.setDateRecepNonObjectPtf(dateRecepNonObjectPtf);
        return this;
    }

    public void setDateRecepNonObjectPtf(LocalDate dateRecepNonObjectPtf) {
        this.dateRecepNonObjectPtf = dateRecepNonObjectPtf;
    }

    public LocalDate getDateExamenJuridique() {
        return this.dateExamenJuridique;
    }

    public Realisation dateExamenJuridique(LocalDate dateExamenJuridique) {
        this.setDateExamenJuridique(dateExamenJuridique);
        return this;
    }

    public void setDateExamenJuridique(LocalDate dateExamenJuridique) {
        this.dateExamenJuridique = dateExamenJuridique;
    }

    public LocalDate getDateNotifContrat() {
        return this.dateNotifContrat;
    }

    public Realisation dateNotifContrat(LocalDate dateNotifContrat) {
        this.setDateNotifContrat(dateNotifContrat);
        return this;
    }

    public void setDateNotifContrat(LocalDate dateNotifContrat) {
        this.dateNotifContrat = dateNotifContrat;
    }

    public LocalDate getDateApprobationContrat() {
        return this.dateApprobationContrat;
    }

    public Realisation dateApprobationContrat(LocalDate dateApprobationContrat) {
        this.setDateApprobationContrat(dateApprobationContrat);
        return this;
    }

    public void setDateApprobationContrat(LocalDate dateApprobationContrat) {
        this.dateApprobationContrat = dateApprobationContrat;
    }

    public PlanPassation getPlanPassation() {
        return this.planPassation;
    }

    public void setPlanPassation(PlanPassation planPassation) {
        this.planPassation = planPassation;
        this.planPassationId = planPassation != null ? planPassation.getId() : null;
    }

    public Realisation planPassation(PlanPassation planPassation) {
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
        if (!(o instanceof Realisation)) {
            return false;
        }
        return id != null && id.equals(((Realisation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Realisation{" +
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
            "}";
    }
}
