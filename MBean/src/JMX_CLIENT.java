import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMX_CLIENT {


        public static final String HOST = "localhost";
        public static final String PORT = "1234";

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
            ObjectName mbeanName = new ObjectName("com.ballerina.autotuning:type=basic,name=dynamicTuning");

            //Get MBean proxy instance that will be used to make calls to registered MBean
            ThreadPoolFactoryMBean mbeanProxy =
                    (ThreadPoolFactoryMBean) MBeanServerInvocationHandler.newProxyInstance(
                            mbeanServerConnection, mbeanName, ThreadPoolFactoryMBean.class, true);

            //let's make some calls to mbean through proxy and see the results.
            System.out.println("Core Pool Size = " + mbeanProxy.getCoreThreadPoolSize());
            System.out.println("Max Pool Size = "+mbeanProxy.getMaxThreadPoolSize());
            System.out.println("Keep Alive Time = "+mbeanProxy.getKeepAliveTime()+"\n");
            System.out.println("Waiting for input.....");
            while (true){
                size = input.nextLine();
                try{
                    arr = size.split(" ");
                }catch(Exception e){
                    arr[0]=size;
                }



                if(arr.length==2 && arr[0].equals("set")){
                    mbeanProxy.setMaxThreadPoolSize(Integer.parseInt(arr[1]));
                    System.out.println("Core Pool Size = " + mbeanProxy.getCoreThreadPoolSize());
                    System.out.println("Max Pool Size = "+mbeanProxy.getMaxThreadPoolSize());
                    System.out.println("Keep Alive Time = "+mbeanProxy.getKeepAliveTime()+"\n");
                    System.out.println("Waiting for input.....");
                }else if (arr[0].equals("get")){
                    System.out.println("Core Pool Size = " + mbeanProxy.getCoreThreadPoolSize());
                    System.out.println("Max Pool Size = "+mbeanProxy.getMaxThreadPoolSize());
                    System.out.println("Keep Alive Time = "+mbeanProxy.getKeepAliveTime()+"\n");
                    System.out.println("Waiting for input.....");
                }
            }



            //close the connection
           // jmxConnector.close();
        }
    }


