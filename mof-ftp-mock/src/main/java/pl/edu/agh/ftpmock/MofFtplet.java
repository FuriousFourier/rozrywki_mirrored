package pl.edu.agh.ftpmock;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.ftpserver.ftplet.DataConnection;
import org.apache.ftpserver.ftplet.DataConnectionFactory;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.ftplet.FtpletContext;
import org.apache.ftpserver.ftplet.FtpletResult;
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
		LOGGER.info("User argument: " + session.getUserArgument());
		LOGGER.info("Session: " + session.toString());
		if (request != null) {
			LOGGER.info("Request argument: " + request.getArgument());
			LOGGER.info("Command: " + request.getCommand());
			LOGGER.info("Request line: " + request.getRequestLine());
		}
	}

	private void saveFile(FtpSession session) {
		OutputStream outputStream = null;
		try {
			File file = new File("dummy");
			file.delete();
			file.createNewFile();
			outputStream = new FileOutputStream(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		DataConnectionFactory connectionFactory = session.getDataConnection();
		try {
			DataConnection dataConnection = connectionFactory.openConnection();
			dataConnection.transferFromClient(session, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionFactory.closeDataConnection();
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
