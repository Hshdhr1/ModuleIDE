package org.apache.http.impl.conn.tsccm;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;

@Deprecated
/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes.dex */
public class ConnPoolByRoute extends AbstractConnPool {
    protected Queue freeConnections;
    protected final int maxTotalConnections;
    protected final ClientConnectionOperator operator;
    protected final Map routeToPool;
    protected Queue waitingThreads;

    public ConnPoolByRoute(ClientConnectionOperator operator, HttpParams params) {
        throw new RuntimeException("Stub!");
    }

    protected Queue createFreeConnQueue() {
        throw new RuntimeException("Stub!");
    }

    protected Queue createWaitingThreadQueue() {
        throw new RuntimeException("Stub!");
    }

    protected Map createRouteToPoolMap() {
        throw new RuntimeException("Stub!");
    }

    protected RouteSpecificPool newRouteSpecificPool(HttpRoute route) {
        throw new RuntimeException("Stub!");
    }

    protected WaitingThread newWaitingThread(Condition cond, RouteSpecificPool rospl) {
        throw new RuntimeException("Stub!");
    }

    protected RouteSpecificPool getRoutePool(HttpRoute route, boolean create) {
        throw new RuntimeException("Stub!");
    }

    public int getConnectionsInPool(HttpRoute route) {
        throw new RuntimeException("Stub!");
    }

    public PoolEntryRequest requestPoolEntry(HttpRoute route, Object state) {
        throw new RuntimeException("Stub!");
    }

    protected BasicPoolEntry getEntryBlocking(HttpRoute route, Object state, long timeout, TimeUnit tunit, WaitingThreadAborter aborter) throws ConnectionPoolTimeoutException, InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public void freeEntry(BasicPoolEntry entry, boolean reusable, long validDuration, TimeUnit timeUnit) {
        throw new RuntimeException("Stub!");
    }

    protected BasicPoolEntry getFreeEntry(RouteSpecificPool rospl, Object state) {
        throw new RuntimeException("Stub!");
    }

    protected BasicPoolEntry createEntry(RouteSpecificPool rospl, ClientConnectionOperator op) {
        throw new RuntimeException("Stub!");
    }

    protected void deleteEntry(BasicPoolEntry entry) {
        throw new RuntimeException("Stub!");
    }

    protected void deleteLeastUsedEntry() {
        throw new RuntimeException("Stub!");
    }

    protected void handleLostEntry(HttpRoute route) {
        throw new RuntimeException("Stub!");
    }

    protected void notifyWaitingThread(RouteSpecificPool rospl) {
        throw new RuntimeException("Stub!");
    }

    public void deleteClosedConnections() {
        throw new RuntimeException("Stub!");
    }

    public void shutdown() {
        throw new RuntimeException("Stub!");
    }
}
