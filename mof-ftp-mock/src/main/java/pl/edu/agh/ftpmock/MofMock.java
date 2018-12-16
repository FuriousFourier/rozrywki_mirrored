package pl.edu.agh.ftpmock;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MofMock {

	private static final Logger LOGGER = LoggerFactory.getLogger(MofMock.class);

	public static void main(String[] args) {
		FtpServerFactory serverFactory = new FtpServerFactory();
		ListenerFactory listenerFactory = new ListenerFactory();

		listenerFactory.setPort(2221);
		serverFactory.addListener("default", listenerFactory.createListener());

		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
		File propFile = new File("default_users.properties");

		userManagerFactory.setFile(propFile);

		UserManager um = userManagerFactory.createUserManager();
		LOGGER.debug("User names:");
		try {
			for (String name : um.getAllUserNames()) {
				User user = um.getUserByName(name);
                if (!handleHomeDir(user)) {
                    LOGGER.error("Error while creating home directory for " + name);
                    System.exit(1);
                }
				LOGGER.debug("\t" + name + ": " + user.getHomeDirectory());

			}
		} catch (FtpException e) {
			LOGGER.error("ERROR", e);
		}
		serverFactory.setUserManager(um);

		Map<String, Ftplet> ftplets = new HashMap<>();
		ftplets.put("entertainmentFtplet", new MofFtplet());
		serverFactory.setFtplets(ftplets);
		try {
			FtpServer server = serverFactory.createServer();
			server.start();
		} catch (FtpException e) {
			e.printStackTrace();
		}
		LOGGER.info("Everything started");
	}

	private static boolean handleHomeDir(User user) {
		File file = new File(user.getHomeDirectory());
		if (!file.exists()) {
            return file.mkdirs();
        }
        return file.isDirectory();
    }
}
