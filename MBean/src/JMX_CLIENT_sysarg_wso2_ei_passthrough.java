import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;

public class JMX_CLIENT_sysarg_wso2_ei_passthrough {


//    public static final String HOST = "192.168.32.9";
    public static final String HOST = "127.0.0.1";
    public static final String PORT = "3346";

    public static void main(String[] args) throws IOException, MalformedObjectNameException {

        JMXServiceURL url =
                new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + HOST + ":" + PORT + "/jmxrmi");

        JMXConnector jmxConnector = JMXConnectorFactory.connect(url);
        MBeanServerConnection mbeanServerConnection = jmxConnector.getMBeanServerConnection();
        //ObjectName should be same as your MBean name
        //ObjectName mbeanName = new ObjectName("com.journaldev.jmx:type=SystemConfig");
        ObjectName mbeanName = new ObjectName("com.ei.autotuningBaseConf:type=basic,name=dynamicTuning");

        //Get MBean proxy instance that will be used to make calls to registered MBean
        Axis2SynapseEnvironmentMBean mbeanProxy =
                 MBeanServerInvocationHandler.newProxyInstance(
                        mbeanServerConnection, mbeanName, Axis2SynapseEnvironmentMBean.class, true);


            if( args[0].equals("set")){
                mbeanProxy.setCoreThreadPoolSize(Integer.parseInt(args[1]));
                mbeanProxy.setMaxThreadPoolSize(Integer.parseInt(args[1]));
                System.out.println("Core Pool Size = " + mbeanProxy.getCoreThreadPoolSize());
                System.out.println("Max Pool Size = "+mbeanProxy.getMaxThreadPoolSize());
                System.out.println("Keep Alive Time = "+mbeanProxy.getKeepAliveTime());
            }

        //close the connection
        jmxConnector.close();
    }
}


