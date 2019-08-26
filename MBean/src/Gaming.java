import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Gaming {

    public static void main(String[] args) throws IOException {

        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        Game gameBean = new Game();


        ObjectName objectName = null;
        try {
            objectName = new ObjectName("com.ballerina.autotuning:type=basic,name=dynamicTuning");
            server.registerMBean(gameBean,objectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LocateRegistry.createRegistry(1234);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://localhost/jndi/rmi://localhost:1234/jmxrmi");
        JMXConnectorServer svr = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
        svr.start();

        while (true) {
        }



    }
}
