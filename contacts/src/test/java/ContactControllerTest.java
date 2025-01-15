import Contacts.Contact;
import Contacts.ContactNotFoundException;
import Contacts.ContactService;
import Contacts.controller.ContactController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ContactControllerTest {

    private ContactController contactController;
    private ContactService contactService;

    @BeforeEach
    void setUp() {
        contactService = mock(ContactService.class);
        contactController = new ContactController();
    }

    @Test
    void testGetAllContacts() {
        List<Contact> expectedContacts = List.of(
                new Contact(1L, "John", "Doe", "123456789", "john@example.com"),
                new Contact(2L, "Jane", "Smith", "987654321", "jane@example.com")
        );
        when(contactService.getAllContacts()).thenReturn(expectedContacts);

        List<Contact> actualContacts = contactController.getAllContacts();

        assertEquals(expectedContacts, actualContacts);
    }

    @Test
    void testGetContactById_ExistingId() {
        Integer contactId = Math.toIntExact(1L);
        Contact expectedContact = new Contact(contactId, "John", "Doe", "123456789", "john@example.com");
        when(contactService.getContactById(contactId)).thenReturn(expectedContact);

        ResponseEntity<Contact> response = contactController.getContactById(contactId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedContact, response.getBody());
    }

    @Test
    void testGetContactById_NonExistingId() {
        Integer contactId = Math.toIntExact(1L);
        when(contactService.getContactById(contactId)).thenThrow(ContactNotFoundException.class);

        ResponseEntity<Contact> response = contactController.getContactById(contactId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
