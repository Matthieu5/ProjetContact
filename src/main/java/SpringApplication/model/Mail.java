package SpringApplication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idM;
    @NotNull
    @Size(min=5, max=100)
    private String libelleM;

    public Mail() {
    }

    public Mail(String libelleM) {
        this.libelleM = libelleM;
    }

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
}
