package de.thg.prototype.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class Person extends PanacheEntity {

    public Person() {
    }

    public Person(String firstname, String lastname) {
        this(firstname, lastname, null);
    }

    public Person(String firstname, String lastname, Address address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }

    public String firstname;
    public String lastname;
    @OneToOne(cascade = CascadeType.PERSIST)
    public Address address;

    public static List<Person> findByFirstname(String firstname) {
        return find("firstname", firstname).list();
    };

    public static List<Person> findByLastname(String lastname){
        return find("lastname", lastname).list();
    };

    public static List<Person> findByLastnameStartingWith(String startsWith) {
        return find("lastname like '" + startsWith + "%'").list();
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
