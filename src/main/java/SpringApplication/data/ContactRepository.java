package SpringApplication.data;

import SpringApplication.model.Contact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ContactRepository extends CrudRepository<Contact, Long> {
    public Optional<Contact> findById(Long id);
    public List<Contact> findByNomC(String nomC);
    public List<Contact> findByPrenomC(String prenomC);
    public List<Contact> findByAdresseC(String adresseC);

    Object findAll(Pageable monContact);

}
