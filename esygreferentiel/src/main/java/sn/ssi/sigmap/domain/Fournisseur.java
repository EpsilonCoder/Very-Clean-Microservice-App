package sn.ssi.sigmap.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Fournisseur.
 */
@Entity
@Table(name = "fournisseur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "raison_sociale", nullable = false)
    private String raisonSociale;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "piece_jointe")
    private String pieceJointe;

    @Column(name = "numero_registre_commerce")
    private String numeroRegistreCommerce;

    @NotNull
    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @Column(name = "sigle")
    private String sigle;

    @Column(name = "numero_identite_fiscale")
    private String numeroIdentiteFiscale;

    @ManyToOne
    private CategorieFournisseur categorieFournisseur;

    @ManyToOne
    private Pays pays;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fournisseur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRaisonSociale() {
        return this.raisonSociale;
    }

    public Fournisseur raisonSociale(String raisonSociale) {
        this.setRaisonSociale(raisonSociale);
        return this;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Fournisseur adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return this.email;
    }

    public Fournisseur email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Fournisseur telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPieceJointe() {
        return this.pieceJointe;
    }

    public Fournisseur pieceJointe(String pieceJointe) {
        this.setPieceJointe(pieceJointe);
        return this;
    }

    public void setPieceJointe(String pieceJointe) {
        this.pieceJointe = pieceJointe;
    }

    public String getNumeroRegistreCommerce() {
        return this.numeroRegistreCommerce;
    }

    public Fournisseur numeroRegistreCommerce(String numeroRegistreCommerce) {
        this.setNumeroRegistreCommerce(numeroRegistreCommerce);
        return this;
    }

    public void setNumeroRegistreCommerce(String numeroRegistreCommerce) {
        this.numeroRegistreCommerce = numeroRegistreCommerce;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public Fournisseur date(ZonedDateTime date) {
        this.setDate(date);
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getSigle() {
        return this.sigle;
    }

    public Fournisseur sigle(String sigle) {
        this.setSigle(sigle);
        return this;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public String getNumeroIdentiteFiscale() {
        return this.numeroIdentiteFiscale;
    }

    public Fournisseur numeroIdentiteFiscale(String numeroIdentiteFiscale) {
        this.setNumeroIdentiteFiscale(numeroIdentiteFiscale);
        return this;
    }

    public void setNumeroIdentiteFiscale(String numeroIdentiteFiscale) {
        this.numeroIdentiteFiscale = numeroIdentiteFiscale;
    }

    public CategorieFournisseur getCategorieFournisseur() {
        return this.categorieFournisseur;
    }

    public void setCategorieFournisseur(CategorieFournisseur categorieFournisseur) {
        this.categorieFournisseur = categorieFournisseur;
    }

    public Fournisseur categorieFournisseur(CategorieFournisseur categorieFournisseur) {
        this.setCategorieFournisseur(categorieFournisseur);
        return this;
    }

    public Pays getPays() {
        return this.pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public Fournisseur pays(Pays pays) {
        this.setPays(pays);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fournisseur)) {
            return false;
        }
        return id != null && id.equals(((Fournisseur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fournisseur{" +
            "id=" + getId() +
            ", raisonSociale='" + getRaisonSociale() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", pieceJointe='" + getPieceJointe() + "'" +
            ", numeroRegistreCommerce='" + getNumeroRegistreCommerce() + "'" +
            ", date='" + getDate() + "'" +
            ", sigle='" + getSigle() + "'" +
            ", numeroIdentiteFiscale='" + getNumeroIdentiteFiscale() + "'" +
            "}";
    }
}
