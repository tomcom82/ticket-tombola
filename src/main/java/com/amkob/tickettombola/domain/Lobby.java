package com.amkob.tickettombola.domain;

import com.amkob.tickettombola.domain.event.UserEnteredLobby;
import com.amkob.tickettombola.exception.LobbyIsFullException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class Lobby extends AbstractAggregateRoot {

    private final int maxLobbyUsers;
    private Queue<User> users = new ConcurrentLinkedQueue<>();

    public Lobby(int maxLobbyUsers) {
        this.maxLobbyUsers = maxLobbyUsers;
    }

    public void enter(User user) {
        try {
            if (!isFull()) {
                throw new LobbyIsFullException();
            }
            if (users.offer(user)) {
                registerEvent(new UserEnteredLobby());
            }

        } catch (LobbyIsFullException e) {
            log.error("Oopsie dont bother user", e);
        }
    }

    public Queue<User> getUsers() {
        return users;
    }

    public boolean isFull() {
        return users.size() >= maxLobbyUsers;
    }

    @Override
    protected Collection<Object> domainEvents() {
        return super.domainEvents();
    }
}