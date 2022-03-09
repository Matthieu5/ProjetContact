package SpringApplication.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MailForm {
    @NotNull
    private long idContact;
    @NotNull
    @Size(min = 5, max = 100)
    private String libelleM;

    public MailForm() {
    }

    public long getIdContact() {
        return idContact;
    }

    public void setIdContact(long idContact) {
        this.idContact = idContact;
    }

    public String getLibelleM() {
        return libelleM;
    }

    public void setLibelleM(String libelleM) {
        this.libelleM = libelleM;
    }
}
