package pl.edu.agh.ftpmock;

import org.apache.ftpserver.ftplet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MofFtplet implements Ftplet {

	private static final Logger LOGGER = LoggerFactory.getLogger(MofFtplet.class);

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

	@Override
	public FtpletResult afterCommand(FtpSession session, FtpRequest request, FtpReply reply) {
		logFtpRequest(session, request);
		return FtpletResult.DEFAULT;
	}

	@Override
	public FtpletResult onConnect(FtpSession session) {
		logFtpRequest(session, null);
		return FtpletResult.DEFAULT;
	}

	@Override
	public FtpletResult onDisconnect(FtpSession session) {
		logFtpRequest(session, null);
		return FtpletResult.DEFAULT;
	}

	private void logFtpRequest(FtpSession session, FtpRequest request) {
		LOGGER.debug("User argument: " + session.getUserArgument());
		LOGGER.debug("Session: " + session.toString());
		if (request != null) {
			if (request.hasArgument()) {
				LOGGER.info("Command: " + request.getCommand());
				LOGGER.info("Request argument: " + request.getArgument());
			}
		}
	}

}
