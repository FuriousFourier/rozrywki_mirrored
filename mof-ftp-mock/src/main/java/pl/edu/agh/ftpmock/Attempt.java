package pl.edu.agh.ftpmock;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Attempt {

    private static final Logger LOGGER = LoggerFactory.getLogger(Attempt.class);

    public static void main(String[] args) {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory listenerFactory = new ListenerFactory();

        listenerFactory.setPort(2221);
        serverFactory.addListener("default", listenerFactory.createListener());

        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        File propFile = new File("default_users.properties");

        userManagerFactory.setFile(propFile);

        UserManager um = userManagerFactory.createUserManager();
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
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String s = scanner.nextLine();
                if (s.equalsIgnoreCase("q")) {
                    LOGGER.info("BYE");
                    System.exit(0);
                }
            }
        }).start();
        LOGGER.info("Everything started");
    }
}
