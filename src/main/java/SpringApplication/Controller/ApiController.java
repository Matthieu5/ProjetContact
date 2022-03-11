package SpringApplication.Controller;

import SpringApplication.data.ContactRepository;
import SpringApplication.data.MailRepository;
import SpringApplication.model.Contact;
import SpringApplication.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}