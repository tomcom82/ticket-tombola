package com.amkob.tickettombola.domain;

import com.amkob.tickettombola.domain.event.UserEnteredLobby;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.*;

public class LobbyTest {

    private static final String USER1_FIRSTNAME = "John";
    private static final String USER1_LASTNAME = "Doe";
    private static final String USER2_FIRSTNAME = "Johnny";
    private static final String USER2_LASTNAME = "Doe";

    private Set<User> users = new HashSet<>();

    @Before
    public void setUp() {
        users.clear();
    }

    @Test
    public void Given_1User_When_EntersMaxOnePersonLobby_Then_LobbyIsFull() {

        users.add(newUser(USER1_FIRSTNAME, USER1_LASTNAME));

        Lobby lobby = new Lobby(1);

        for (User user : users) {
            lobby.enter(user);
        }

        assertTrue(lobby.isFull());

        Iterator<User> userIterator = lobby.getUsers().iterator();
        int i = 1;
        while (userIterator.hasNext()) {
            User user = userIterator.next();
            if (i == 1) {
                assertEquals(USER1_FIRSTNAME, user.getName().getFirstName());
                assertEquals(USER1_LASTNAME, user.getName().getLastName());
            }
            i++;
        }

        //Assert events
        assertEquals(1, lobby.domainEvents().size());
        assertTrue(lobby.domainEvents().stream().anyMatch(e -> e instanceof UserEnteredLobby));
    }

    @Test
    public void Given_2UsersEnter_When_MaxOnePersonLobby_Then_ExceptionIsThrown() {
        Lobby lobby = new Lobby(1);
        lobby.enter(newUser(USER1_FIRSTNAME, USER1_LASTNAME));
        lobby.enter(newUser(USER2_FIRSTNAME, USER2_LASTNAME));
        assertEquals(1, lobbySize(lobby));
    }

    private int lobbySize(Lobby lobby) {
        Iterator<User> userIterator = lobby.getUsers().iterator();
        int i = 0;
        while (userIterator.hasNext()) {
            userIterator.next();
            i++;
        }
        return i;
    }

    @Test
    public void Given_TwoNewUsersEnter_When_MaxThreePersonLobby_Then_LobbyIsNotFull() {

        users.add(newUser(USER1_FIRSTNAME, USER1_LASTNAME));
        users.add(newUser(USER2_FIRSTNAME, USER2_LASTNAME));

        Lobby lobby = new Lobby(3);

        for (User user : users) {
            lobby.enter(user);
        }

        assertFalse(lobby.isFull());

        Iterator<User> userIterator = lobby.getUsers().iterator();

        int i = 1;
        while (userIterator.hasNext()) {
            User user = userIterator.next();
            if (i == 1) {
                assertEquals(USER1_FIRSTNAME, user.getName().getFirstName());
                assertEquals(USER1_LASTNAME, user.getName().getLastName());
            } else if (i == 2) {
                assertEquals(USER2_FIRSTNAME, user.getName().getFirstName());
                assertEquals(USER2_LASTNAME, user.getName().getLastName());
            }
            i++;
        }

        assertEquals(2, lobby.domainEvents().size());
        assertTrue(lobby.domainEvents().stream().anyMatch(e -> e instanceof UserEnteredLobby));

    }

    private User newUser(String firstName, String lastName) {
        FullName fullName = FullName.builder().firstName(firstName).lastName(lastName).build();
        return User.builder().name(fullName).build();
    }
}