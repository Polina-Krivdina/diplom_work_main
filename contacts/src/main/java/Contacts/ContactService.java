package Contacts;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ContactService {

    private List<Contact> contacts = new ArrayList<>();

    public class ContactIdGenerator {
        private final AtomicLong idCounter = new AtomicLong(0);

        public long generateId() {
            return idCounter.incrementAndGet();
        }
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public Contact getContactById(Integer contactId) {
        long id = contactId != null ? contactId.longValue() : 0;
        try {
            return contacts.stream()
                    .filter(contact -> contact.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new ContactNotFoundException("Contact not found with id: " + contactId));
        } catch (ContactNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Contact addContact(Contact contact) {
        contact.setId(Math.toIntExact(ContactIdGenerator.class.getModifiers()));
        contacts.add(contact);
        return contact;
    }

    public Contact updateContact(Integer contactId, Contact updatedContact) {
        Contact existingContact = getContactById(contactId);
        existingContact.setFirstName(updatedContact.getFirstName());
        existingContact.setLastName(updatedContact.getLastName());
        existingContact.setPhoneNumber(updatedContact.getPhoneNumber());
        existingContact.setEmail(updatedContact.getEmail());
        return existingContact;
    }
}

