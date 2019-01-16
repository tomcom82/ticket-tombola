package com.amkob.tickettombola.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ExecutorsTest {

    private Set<User> users = new HashSet<>();

    @Before
    public void setUp() {
        users.clear();
    }

    @Test
    public void scheduleAtFixedRate() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        LobbyTimer lobbyTimer = new LobbyTimer(1);

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(lobbyTimer, 0, 1, TimeUnit.SECONDS);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(4, lobbyTimer.getNumberOfRuns());
        assertFalse(scheduledFuture.isDone());
    }

    @Test
    public void scheduleAtFixedRateMulti() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        LobbyTimer lobbyTimer = new LobbyTimer(1);
        LobbyTimer lobbyTimer2 = new LobbyTimer(2);
        LobbyTimer lobbyTimer3 = new LobbyTimer(3);
        LobbyTimer lobbyTimer4 = new LobbyTimer(4);
        LobbyTimer lobbyTimer5 = new LobbyTimer(5);

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(lobbyTimer, 0, 1, TimeUnit.SECONDS);
        ScheduledFuture<?> scheduledFuture2 = scheduledExecutorService.scheduleAtFixedRate(lobbyTimer2, 1, 1, TimeUnit.SECONDS);
        ScheduledFuture<?> scheduledFuture3 = scheduledExecutorService.scheduleAtFixedRate(lobbyTimer3, 1, 1, TimeUnit.SECONDS);
        ScheduledFuture<?> scheduledFuture4 = scheduledExecutorService.scheduleAtFixedRate(lobbyTimer4, 1, 1, TimeUnit.SECONDS);
        ScheduledFuture<?> scheduledFuture5 = scheduledExecutorService.scheduleAtFixedRate(lobbyTimer5, 1, 1, TimeUnit.SECONDS);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(4, lobbyTimer.getNumberOfRuns());
        scheduledFuture.cancel(true);
        assertTrue(scheduledFuture.isDone());
    }

    @Test
    public void scheduleAtFixedRateMulti2() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
        ScheduledExecutorService scheduledExecutorService = Executors.unconfigurableScheduledExecutorService(scheduledThreadPoolExecutor);

        LobbyTimer lobbyTimer = new LobbyTimer(1);
        LobbyTimer lobbyTimer2 = new LobbyTimer(2);
        LobbyTimer lobbyTimer3 = new LobbyTimer(3);
        LobbyTimer lobbyTimer4 = new LobbyTimer(4);
        LobbyTimer lobbyTimer5 = new LobbyTimer(5);

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(lobbyTimer, 0, 1, TimeUnit.SECONDS);
        ScheduledFuture<?> scheduledFuture2 = scheduledExecutorService.scheduleAtFixedRate(lobbyTimer2, 1, 1, TimeUnit.SECONDS);
        ScheduledFuture<?> scheduledFuture3 = scheduledExecutorService.scheduleAtFixedRate(lobbyTimer3, 1, 1, TimeUnit.SECONDS);
        ScheduledFuture<?> scheduledFuture4 = scheduledExecutorService.scheduleAtFixedRate(lobbyTimer4, 1, 1, TimeUnit.SECONDS);
        ScheduledFuture<?> scheduledFuture5 = scheduledExecutorService.scheduleAtFixedRate(lobbyTimer5, 1, 1, TimeUnit.SECONDS);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(4, lobbyTimer.getNumberOfRuns());
        assertFalse(scheduledFuture.isDone());
    }

    @Test
    public void scheduleAtFixedRateMulti3() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
        ScheduledExecutorService scheduledExecutorService = Executors.unconfigurableScheduledExecutorService(scheduledThreadPoolExecutor);

        LobbyTimer lobbyTimer = new LobbyTimer(1);
        LobbyTimer lobbyTimer2 = new LobbyTimer(2);

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(lobbyTimer, 0, 1, TimeUnit.SECONDS);
        ScheduledFuture<?> scheduledFuture2 = scheduledExecutorService.scheduleAtFixedRate(lobbyTimer2, 5, 1, TimeUnit.SECONDS);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(4, lobbyTimer.getNumberOfRuns());
        assertFalse(scheduledFuture.isDone());

        //Cancel
        scheduledFuture.cancel(true);
        assertTrue(scheduledFuture.isDone());

        //Assert threadPool stats
        assertEquals(lobbyTimer.getNumberOfRuns(), scheduledThreadPoolExecutor.getCompletedTaskCount());
        assertEquals(0, scheduledThreadPoolExecutor.getActiveCount());
        assertEquals(1, scheduledThreadPoolExecutor.getQueue().size());//Timer 2 in queue
        //assertTrue(scheduledThreadPoolExecutor.getQueue().contains(lobbyTimer2));//Timer 2 in queue

    }

}