package SpringApplication.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "CONTACT")
public class Contact implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private long idC;

    @NotNull
    @Size(min=2, max=30)
    @Column(name = "nomC", length = 30, nullable = false)
    private String nomC;

    @NotNull
    @Size(min=2, max=30)
    @Column(name = "prenomC", length = 30, nullable = false)
    private String prenomC;

    @NotNull
    @Size(min=10, max=100)
    @Column(name = "adresseC", length = 100, nullable = false)
    private String adresseC;

    public Contact() {
    }

    public Contact(String nomC, String prenomC, String adresseC) {
        this.nomC = nomC;
        this.prenomC = prenomC;
        this.adresseC = adresseC;
    }

    @OneToMany(mappedBy = "contact")
    private Collection<Mail> mails;

    public long getIdC() {
        return idC;
    }

    public void setIdC(long idC) {
        this.idC = idC;
    }

    public String getNomC() {
        return nomC;
    }

    public void setNomC(String nomC) {
        this.nomC = nomC;
    }

    public String getPrenomC() {
        return prenomC;
    }

    public void setPrenomC(String prenomC) {
        this.prenomC = prenomC;
    }

    public String getAdresseC() {
        return adresseC;
    }

    public void setAdresseC(String adresseC) {
        this.adresseC = adresseC;
    }

    public Collection<Mail> getMails() {
        if (mails==null)
            mails=new ArrayList<>();
        return mails;
    }

    public void setMails(Collection<Mail> mails) {
        this.mails = mails;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "idC=" + idC +
                ", nomC='" + nomC + '\'' +
                ", prenomC='" + prenomC + '\'' +
                ", adresseC='" + adresseC + '\'' +
                ", mails=" + mails +
                '}';
    }
}