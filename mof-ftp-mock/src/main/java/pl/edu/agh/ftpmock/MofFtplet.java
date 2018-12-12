package pl.edu.agh.ftpmock;

import org.apache.ftpserver.ftplet.*;
import org.apache.log4j.Logger;

public class MofFtplet implements Ftplet {

    private static final Logger LOGGER = Logger.getLogger(MofFtplet.class);

    @Override
    public void init(FtpletContext ftpletContext) {
        LOGGER.info("Initializing");
    }

    @Override
    public void destroy() {
        LOGGER.info("Destroying MoF Ftplet");
    }

    @Override
    public FtpletResult beforeCommand(FtpSession session, FtpRequest request) {
        logFtpRequest(session, request);
        return FtpletResult.DEFAULT;
    }

    private void logFtpRequest(FtpSession session, FtpRequest request) {
        LOGGER.info("User argument: " + session.getUserArgument());
        LOGGER.info("Session: " + session.toString());
        LOGGER.info("Request argument: " + request.getArgument());
        LOGGER.info("Command: " + request.getCommand());
        LOGGER.info("Request line: " + request.getRequestLine());
    }

    @Override
    public FtpletResult afterCommand(FtpSession session, FtpRequest request, FtpReply reply) {
        logFtpRequest(session, request);
        return FtpletResult.DEFAULT;
    }

    @Override
    public FtpletResult onConnect(FtpSession session) {
        LOGGER.info("Connect");
        LOGGER.info("User argument: " + session.getUserArgument());
        LOGGER.info("Session: " + session.toString());
        return FtpletResult.DEFAULT;
    }

    @Override
    public FtpletResult onDisconnect(FtpSession session) {
        LOGGER.info("Disconnect");
        LOGGER.info("User argument: " + session.getUserArgument());
        LOGGER.info("Session: " + session.toString());
        return FtpletResult.DEFAULT;
    }
}
