package pl.edu.agh.ftpmock;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Attempt {

    public static final Logger LOGGER = Logger.getLogger(Attempt.class);

    public static void main(String[] args) {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory listenerFactory = new ListenerFactory();

        listenerFactory.setPort(2221);
        serverFactory.addListener("default", listenerFactory.createListener());

        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        File propFile = new File("users.properties");
        propFile.delete();

        try {
            if (!propFile.createNewFile()) {
                LOGGER.error("Cannot create file: " + propFile.getName());
                System.exit(1);
            }
        } catch (IOException e) {
            LOGGER.error("IOException while creating file: " + propFile.getName());
            System.exit(2);
        }

        userManagerFactory.setFile(propFile);
        BaseUser user = new BaseUser();
        user.setName("reporting");
        user.setPassword("report1234");
        user.setHomeDirectory("./");
        user.setAuthorities(Collections.singletonList(new WritePermission()));

        UserManager um = userManagerFactory.createUserManager();
        try {
            um.save(user);//Save the user to the user list on the filesystem
        }
        catch (FtpException e1) {
            e1.printStackTrace();
        }

        serverFactory.setUserManager(um);


        Map<String, Ftplet> ftplets = new HashMap<String, Ftplet>();
        ftplets.put("entertainmentFtplet", new MofFtplet());
        serverFactory.setFtplets(ftplets);
        try {
            FtpServer server = serverFactory.createServer();
            server.start();
        } catch (FtpException e) {
            e.printStackTrace();
        }
    }
}
