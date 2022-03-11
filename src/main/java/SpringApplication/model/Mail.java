package SpringApplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idM;

    private String libelleM;

    public Mail(String libelleM, Contact contact) {
        this.libelleM = libelleM;
        this.contact = contact;
    }

    public Mail(String libelleM) {
        this.libelleM = libelleM;
    }

    public Mail() {

    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "contact_fk")
    private Contact contact;

    public long getIdM() {
        return idM;
    }

    public void setIdM(long idM) {
        this.idM = idM;
    }

    public String getLibelleM() {
        return libelleM;
    }

    public void setLibelleM(String libelleM) {
        this.libelleM = libelleM;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "idM=" + idM +
                ", libelleM='" + libelleM + '\'' +
                ", contact=" + contact +
                '}';
    }
}
