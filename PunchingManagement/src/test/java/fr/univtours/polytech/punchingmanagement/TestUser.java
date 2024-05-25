package fr.univtours.polytech.punchingmanagement;

 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.univtours.polytech.punchingmanagement.model.User;

public class TestUser {

    private User user;

    @Before 
    public void setUp() {
        user = new User();
    }

    @Test
    public void testGetUuid() {
        assertNotNull(user.getUuid());
    }

    @Test
    public void testSetUuid() {
        UUID uuid = UUID.randomUUID();
        user.setUuid(uuid);
        assertEquals(uuid, user.getUuid());
    }

    @Test
    public void testGetFirstName() {
        assertNull(user.getFirstName());
    }

    @Test
    public void testSetFirstName() {
        String firstName = "John";
        user.setFirstName(firstName);
        assertEquals(firstName, user.getFirstName());
    }

    @Test
    public void testGetName() {
        assertNull(user.getName());
    }

    @Test
    public void testSetName() {
        String name = "Doe";
        user.setName(name);
        assertEquals(name, user.getName());
    }
}

