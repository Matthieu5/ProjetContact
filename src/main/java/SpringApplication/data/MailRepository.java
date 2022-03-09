package SpringApplication.data;

import SpringApplication.model.Contact;
import SpringApplication.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface MailRepository extends JpaRepository<Mail, Long> {

}