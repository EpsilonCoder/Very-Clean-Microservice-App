package sn.ssi.sigmap.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AutoriteContractante.
 */
@Entity
@Table(name = "autorite_contractante")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AutoriteContractante implements Serializable {

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

    @NotNull
    @Column(name = "approbation", nullable = false)
    private String approbation;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @ManyToOne(optional = false)
    @NotNull
    private TypeAutoriteContractante type;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AutoriteContractante id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdre() {
        return this.ordre;
    }

    public AutoriteContractante ordre(Integer ordre) {
        this.setOrdre(ordre);
        return this;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public String getDenomination() {
        return this.denomination;
    }

    public AutoriteContractante denomination(String denomination) {
        this.setDenomination(denomination);
        return this;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getResponsable() {
        return this.responsable;
    }

    public AutoriteContractante responsable(String responsable) {
        this.setResponsable(responsable);
        return this;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public AutoriteContractante adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public AutoriteContractante telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return this.fax;
    }

    public AutoriteContractante fax(String fax) {
        this.setFax(fax);
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return this.email;
    }

    public AutoriteContractante email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSigle() {
        return this.sigle;
    }

    public AutoriteContractante sigle(String sigle) {
        this.setSigle(sigle);
        return this;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public String getUrlsiteweb() {
        return this.urlsiteweb;
    }

    public AutoriteContractante urlsiteweb(String urlsiteweb) {
        this.setUrlsiteweb(urlsiteweb);
        return this;
    }

    public void setUrlsiteweb(String urlsiteweb) {
        this.urlsiteweb = urlsiteweb;
    }

    public String getApprobation() {
        return this.approbation;
    }

    public AutoriteContractante approbation(String approbation) {
        this.setApprobation(approbation);
        return this;
    }

    public void setApprobation(String approbation) {
        this.approbation = approbation;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public AutoriteContractante logo(byte[] logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return this.logoContentType;
    }

    public AutoriteContractante logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public TypeAutoriteContractante getType() {
        return this.type;
    }

    public void setType(TypeAutoriteContractante typeAutoriteContractante) {
        this.type = typeAutoriteContractante;
    }

    public AutoriteContractante type(TypeAutoriteContractante typeAutoriteContractante) {
        this.setType(typeAutoriteContractante);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AutoriteContractante)) {
            return false;
        }
        return id != null && id.equals(((AutoriteContractante) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutoriteContractante{" +
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
