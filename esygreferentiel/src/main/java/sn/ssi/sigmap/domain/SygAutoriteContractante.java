package sn.ssi.sigmap.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SygAutoriteContractante.
 */
@Entity
@Table(name = "syg_autorite_contractante")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SygAutoriteContractante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ordre", nullable = false)
    private Integer ordre;

    @NotNull
    @Column(name = "denomination", nullable = false)
    private String denomination;

    @NotNull
    @Column(name = "responsable", nullable = false)
    private String responsable;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "fax")
    private String fax;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "sigle", nullable = false)
    private String sigle;

    @Column(name = "urlsiteweb")
    private String urlsiteweb;

    @Column(name = "approbation")
    private String approbation;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @ManyToOne(optional = false)
    @NotNull
    private TypeAutoriteContractante typeAutoriteContractante;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SygAutoriteContractante id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdre() {
        return this.ordre;
    }

    public SygAutoriteContractante ordre(Integer ordre) {
        this.setOrdre(ordre);
        return this;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public String getDenomination() {
        return this.denomination;
    }

    public SygAutoriteContractante denomination(String denomination) {
        this.setDenomination(denomination);
        return this;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getResponsable() {
        return this.responsable;
    }

    public SygAutoriteContractante responsable(String responsable) {
        this.setResponsable(responsable);
        return this;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public SygAutoriteContractante adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public SygAutoriteContractante telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return this.fax;
    }

    public SygAutoriteContractante fax(String fax) {
        this.setFax(fax);
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return this.email;
    }

    public SygAutoriteContractante email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSigle() {
        return this.sigle;
    }

    public SygAutoriteContractante sigle(String sigle) {
        this.setSigle(sigle);
        return this;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public String getUrlsiteweb() {
        return this.urlsiteweb;
    }

    public SygAutoriteContractante urlsiteweb(String urlsiteweb) {
        this.setUrlsiteweb(urlsiteweb);
        return this;
    }

    public void setUrlsiteweb(String urlsiteweb) {
        this.urlsiteweb = urlsiteweb;
    }

    public String getApprobation() {
        return this.approbation;
    }

    public SygAutoriteContractante approbation(String approbation) {
        this.setApprobation(approbation);
        return this;
    }

    public void setApprobation(String approbation) {
        this.approbation = approbation;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public SygAutoriteContractante logo(byte[] logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return this.logoContentType;
    }

    public SygAutoriteContractante logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public TypeAutoriteContractante getTypeAutoriteContractante() {
        return this.typeAutoriteContractante;
    }

    public void setTypeAutoriteContractante(TypeAutoriteContractante typeAutoriteContractante) {
        this.typeAutoriteContractante = typeAutoriteContractante;
    }

    public SygAutoriteContractante typeAutoriteContractante(TypeAutoriteContractante typeAutoriteContractante) {
        this.setTypeAutoriteContractante(typeAutoriteContractante);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SygAutoriteContractante)) {
            return false;
        }
        return id != null && id.equals(((SygAutoriteContractante) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SygAutoriteContractante{" +
            "id=" + getId() +
            ", ordre=" + getOrdre() +
            ", denomination='" + getDenomination() + "'" +
            ", responsable='" + getResponsable() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", fax='" + getFax() + "'" +
            ", email='" + getEmail() + "'" +
            ", sigle='" + getSigle() + "'" +
            ", urlsiteweb='" + getUrlsiteweb() + "'" +
            ", approbation='" + getApprobation() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            "}";
    }
}
