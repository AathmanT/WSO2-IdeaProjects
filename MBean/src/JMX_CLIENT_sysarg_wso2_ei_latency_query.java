//import com.google.gson.JsonObject;

import com.google.gson.JsonObject;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class JMX_CLIENT_sysarg_wso2_ei_latency_query {

    public static void main(String[] args) throws IOException, MalformedObjectNameException {

        Map<String, String[]> env = new HashMap<>();
        String[] credentials = {"admin", "admin"}; // Login and password
        env.put(JMXConnector.CREDENTIALS, credentials);

        JMXServiceURL url =
                new JMXServiceURL("service:jmx:rmi://localhost:11111/jndi/rmi://localhost:9999/jmxrmi");

        JMXConnector jmxConnector = JMXConnectorFactory.connect(url, env);
        MBeanServerConnection mbeanServerConnection = jmxConnector.getMBeanServerConnection();
        //ObjectName should be same as your MBean name given in jconsole
        ObjectName mbeanName = new ObjectName("org.apache.synapse:Type=PassthroughLatencyView,Name=nio-http-http");

        //Get MBean proxy instance that will be used to make calls to registered MBean
        LatencyViewMBean mbeanProxy =
                (LatencyViewMBean) MBeanServerInvocationHandler.newProxyInstance(
                        mbeanServerConnection, mbeanName, LatencyViewMBean.class, true);

        JsonObject metricsJson = new JsonObject();
        metricsJson.addProperty("99per", mbeanProxy.get1m_99Per_Latency());
        metricsJson.addProperty("avg", mbeanProxy.get1m_Avg_Latency());
//        metricsJson.addProperty("Avg_BackEnd_To_Esb_ResponseReadTime", mbeanProxy.get1m_Avg_BackEnd_To_Esb_ResponseReadTime());
//        metricsJson.addProperty("Avg_Client_To_Esb_RequestReadTime",mbeanProxy.get1m_Avg_Client_To_Esb_RequestReadTime());
//        metricsJson.addProperty("Avg_ClientWorker_QueuedTime",mbeanProxy.get1m_Avg_ClientWorker_QueuedTime());
//        metricsJson.addProperty("Avg_Esb_To_BackEnd_RequestWriteTime",mbeanProxy.get1m_Avg_Esb_To_BackEnd_RequestWriteTime());
//        metricsJson.addProperty("Avg_request_Mediation_Latency",mbeanProxy.get1m_Avg_request_Mediation_Latency());
//        metricsJson.addProperty("Avg_ServerWorker_QueuedTime",mbeanProxy.get1m_Avg_ServerWorker_QueuedTime());
//        metricsJson.addProperty("Avg_Esb_To_Client_ResponseWriteTime",mbeanProxy.get1m_Avg_Esb_To_Client_ResponseWriteTime());
//        metricsJson.addProperty("Avg_Latency_BackEnd",mbeanProxy.get1m_Avg_Latency_BackEnd());
//        metricsJson.addProperty("Avg_response_Mediation_Latency",mbeanProxy.get1m_Avg_response_Mediation_Latency());


        System.out.println(metricsJson.toString());
        //close the connection

        jmxConnector.close();
    }
}


