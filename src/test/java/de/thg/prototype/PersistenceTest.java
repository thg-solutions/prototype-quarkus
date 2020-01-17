package de.thg.prototype;

import de.thg.prototype.persistence.Address;
import de.thg.prototype.persistence.Person;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class PersistenceTest {

    @Inject
    UserTransaction transaction;

    @Test
    void testPersistDelete() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        transaction.begin();
        assertEquals(3, Person.findAll().list().size());
        Person person = new Person("Piet", "Klocke", new Address("a", "b", "c"));
        person.persistAndFlush();
        assertEquals(4, Person.findAll().list().size());
        person.delete();
        assertEquals(3, Person.findAll().list().size());
        transaction.rollback();
    }

    @Test
    void testFindByLastname() {
        Person person = Person.findByLastname("Bond").get(0);
        assertEquals(person.firstname, "James");
        assertNotNull(person.address);
    }

    @Test
    void testFindByLastnameStartingWith() {
        List<Person> personList = Person.findByLastnameStartingWith("B");
        assertEquals(3, personList.size());
    }
}
