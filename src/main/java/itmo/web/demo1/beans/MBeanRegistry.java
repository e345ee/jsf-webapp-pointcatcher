package itmo.web.demo1.beans;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

public class MBeanRegistry {
    private static final Map<Class<?>, ObjectName> beans = new HashMap<>();

    public static void registerBean(Object bean, String name) {
        try {
            String domain = bean.getClass().getPackageName();  // пример: itmo.web.demo1.beans
            String type = bean.getClass().getSimpleName();     // пример: PointStats
            ObjectName objectName = new ObjectName(String.format("%s:type=%s,name=%s", domain, type, name));

            ManagementFactory.getPlatformMBeanServer().registerMBean(bean, objectName);
            beans.put(bean.getClass(), objectName);
            System.out.println("Registered MBean: " + objectName);
        } catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException |
                 MalformedObjectNameException e) {
            e.printStackTrace();
        }
    }

    public static void unregisterBean(Object bean) {
        try {
            ObjectName name = beans.remove(bean.getClass());
            if (name != null) {
                ManagementFactory.getPlatformMBeanServer().unregisterMBean(name);
                System.out.println("Unregistered MBean: " + name);
            }
        } catch (InstanceNotFoundException | MBeanRegistrationException e) {
            e.printStackTrace();
        }
    }
}
