package SpringApplication.Controller;

import SpringApplication.data.ContactRepository;
import SpringApplication.data.MailRepository;
import SpringApplication.model.Contact;
import SpringApplication.model.Mail;
import SpringApplication.model.MailForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MailController {

    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/mail")
    public String showMessage(Model model) {

        model.addAttribute("mailList", mailRepository.findAll());
        model.addAttribute("newMail", new MailForm());

        return "mailPage";
    }

    @GetMapping("/mail/modif/{id}")
    public String modifMail(Model model, @PathVariable long id) {

        model.addAttribute("mailList", mailRepository.findAll());
        model.addAttribute("newMail", mailRepository.findById(id).orElse(new Mail()));

        return "MailModif";
    }

    @PostMapping("/mail/alter")
    public String postMessage(@Valid @ModelAttribute MailForm mailForm, BindingResult bindingResult, Model model) {
        Optional<Contact> c = contactRepository.findById(mailForm.getIdContact());

        if (bindingResult.hasErrors() || c.isEmpty()) {
            model.addAttribute("mailList", mailRepository.findAll());
            model.addAttribute("newMail", mailForm);
            return "mailPage";
        }


        Mail m1 = new Mail(mailForm.getLibelleM(), c.get());
        c.get().getMails().add(m1);
        mailRepository.save(m1);
        return "redirect:/contact";
    }

    @GetMapping("/mail/delete/{id}")
    public String deleteMail(@PathVariable long id) {
        Optional<Mail> m = mailRepository.findById(id);
        mailRepository.deleteById(id);

        return "redirect:/mail";
    }
}