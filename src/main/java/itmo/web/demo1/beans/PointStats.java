package itmo.web.demo1.beans;

import javax.management.*;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class PointStats implements PointStatsMBean, NotificationBroadcaster, Serializable {
    private final AtomicInteger totalPoints = new AtomicInteger();
    private final AtomicInteger hits = new AtomicInteger();

    private final NotificationBroadcasterSupport broadcaster = new NotificationBroadcasterSupport();

    @Override
    public int getTotalPoints() {
        return totalPoints.get();
    }

    @Override
    public int getHits() {
        return hits.get();
    }

    public void update(boolean isHit) {
        totalPoints.incrementAndGet();
        if (isHit) hits.incrementAndGet();

        if (totalPoints.get() % 10 == 0) {
            broadcaster.sendNotification(new Notification(
                    "point.count.threshold",
                    this,
                    System.currentTimeMillis(),
                    "User placed " + totalPoints.get() + " points (multiple of 10)"
            ));
        }
    }

    @Override
    public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) {
        broadcaster.addNotificationListener(listener, filter, handback);
    }

    @Override
    public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
        broadcaster.removeNotificationListener(listener);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] { "point.count.threshold" };
        return new MBeanNotificationInfo[] {
                new MBeanNotificationInfo(types, Notification.class.getName(), "Every 10 points placed")
        };
    }

    public void reset() {
        totalPoints.set(0);
        hits.set(0);
    }
}
