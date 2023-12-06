package net.jnjmx.todd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXServiceURL;

public class JMXToddServerStatus {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Connect to a remote MBean Server
			JMXConnector c = javax.management.remote.JMXConnectorFactory
					.connect(new JMXServiceURL(
							"service:jmx:rmi:///jndi/rmi://192.168.30.101:10500/jmxrmi"));

			MBeanServerConnection mbs = c.getMBeanServerConnection();

			// Lets try to access the MBean net.jnjmx.todd.Server:
			ObjectName son = new ObjectName("todd:id=Server");
			ObjectInstance ob=mbs.getObjectInstance(son);
			    	
			System.out.println("Todd server is UP");						
			
			c.close();
			
			System.exit(0);
		} catch (Exception ex) {
			System.out.println("Error: unable to connect to MBean Server");
			System.exit(2);
		}

	}

}
