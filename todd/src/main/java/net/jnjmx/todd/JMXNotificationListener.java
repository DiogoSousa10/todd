package net.jnjmx.todd;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class JMXNotificationListener implements NotificationListener {

	@Override
	public void handleNotification(Notification notification, Object handback) {
		System.out.println("Received Notification");
		System.out.println("======================================");
		System.out.println("Timestamp: " + notification.getTimeStamp());
		System.out.println("Type: " + notification.getType());
		System.out.println("Sequence Number: " + notification.getSequenceNumber());
		System.out.println("Message: " + notification.getMessage());
		System.out.println("User Data: " + notification.getUserData());
		System.out.println("Source: " + notification.getSource());

		System.out.println("======================================");
		try {
			// Define the Linux command you want to execute
			String linuxCommand = "/usr/bin/printf \"host1;Test;2;Critical: Sessions availability below 20\" | /usr/sbin/send_nsca -H 192.168.30.100 -p 5667 -d ';'";

			// Create a ProcessBuilder for the command
			ProcessBuilder processBuilder = new ProcessBuilder();
			processBuilder.command("bash", "-c", linuxCommand);

			// Start the process
			Process process = processBuilder.start();

			// Wait for the process to complete
			int exitCode = process.waitFor();

			if (exitCode == 0) {
				MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
				ObjectName son = new ObjectName("todd:id=SessionPool");
				ObjectInstance ob = mbs.getObjectInstance(son);
				mbs.invoke(son, "grow", new Object[]{2}, new String[]{int.class.toString()});
				System.out.println("Session growth by: " + 2);

				System.out.println(linuxCommand);
				System.out.println("Command executed successfully.");
			} else {
				System.err.println("Command execution failed with exit code: " + exitCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

