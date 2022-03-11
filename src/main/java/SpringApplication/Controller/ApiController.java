package SpringApplication.Controller;

import SpringApplication.data.ContactRepository;
import SpringApplication.data.MailRepository;
import SpringApplication.model.Contact;
import SpringApplication.model.Mail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ApiController {

    @Autowired
    private ContactRepository contactRrepository;
    @Autowired
    private MailRepository mailRepository;

    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public Object index(@RequestParam(required = false) String action, @RequestParam(defaultValue = "0") long id, Model model) {

        if (action != null) {
            Optional<Contact> contact;
            switch (action) {
                case "listContacts":
                    return new ResponseEntity<>(contactRrepository.findAll(), HttpStatus.OK);
                case "getContact":
                    contact = contactRrepository.findById(id);
                    return contact.<ResponseEntity<Object>>map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
                case "delContact":
                    contact = contactRrepository.findById(id);
                    if (contact.get().getMails().size() == 0) {
                        contactRrepository.deleteById(id);
                    } else {
                        for (Mail m : contact.get().getMails()) {
                            mailRepository.delete(m);
                        }
                        contact.get().getMails().clear();
                        contactRrepository.deleteById(id);
                    }
            }
        }

        return null;
    }

    @PostMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Object> postInfo(@RequestParam String action, @RequestParam(defaultValue = "0") long id, @RequestBody String xml, Model model) {
        if (action != null) {
            Contact value = null;
            switch (action) {
                case "addContact":
                    try {
                        XmlMapper xmlMapper = new XmlMapper();
                        value = xmlMapper.readValue(xml, Contact.class);
                        contactRrepository.save(value);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return new ResponseEntity<>(HttpStatus.OK);
                case "editContact":
                    try {
                        XmlMapper xmlMapper = new XmlMapper();
                        value = xmlMapper.readValue(xml, Contact.class);
                        Contact oldContact = contactRrepository.getOne(id);
                        oldContact.setPrenomC(value.getPrenomC());
                        oldContact.setNomC(value.getNomC());
                        oldContact.setAdresseC(value.getAdresseC());
                        oldContact.setMails(value.getMails());
                        contactRrepository.flush();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return new ResponseEntity<>(HttpStatus.OK);
            }
        }

        return null;
    }

}