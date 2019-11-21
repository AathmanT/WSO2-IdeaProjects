import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.Scanner;

public class JMX_CLIENT_sysarg_wso2_ei_latency_query {


//    public static final String HOST = "192.168.32.9";
    public static final String HOST = "127.0.0.1";
    public static final String PORT = "9999";

    public static void main(String[] args) throws IOException, MalformedObjectNameException {
        Scanner input = new Scanner(System.in);
        String size;
        String[] arr = new String[2];
        JMXServiceURL url =
                new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + HOST + ":" + PORT + "/jmxrmi");

        JMXConnector jmxConnector = JMXConnectorFactory.connect(url);
        MBeanServerConnection mbeanServerConnection = jmxConnector.getMBeanServerConnection();
        //ObjectName should be same as your MBean name
        //ObjectName mbeanName = new ObjectName("com.journaldev.jmx:type=SystemConfig");
        ObjectName mbeanName = new ObjectName("org.apache.synapse:type=PassthroughLatencyView,name=nio-https-https");

        //Get MBean proxy instance that will be used to make calls to registered MBean
        LatencyViewMBean mbeanProxy =
                (LatencyViewMBean) MBeanServerInvocationHandler.newProxyInstance(
                        mbeanServerConnection, mbeanName, LatencyViewMBean.class, true);


                System.out.println("99% Latency = " + mbeanProxy.get1m_99Per_Latency());



        //close the connection
        jmxConnector.close();
    }
}


