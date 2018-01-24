package com.pkm.snork.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

public class ServerChecker {

	private Logger logger;
	private String SERVER_EXISTS_FORMAT = "%s exists!";
	private String SERVER_DOES_NOT_EXIST_FORMAT = "'%s' does not exist!";

	/**
	 * 
	 */
	public ServerChecker() {
		logger = Logger.getLogger(this.getClass());
	}

	/**
	 * 
	 * @param serverName
	 * @return
	 */
	public boolean doesServerExist(String serverName) {
		boolean serverExists = true;

		try {

			InetAddress inet = InetAddress.getByName(serverName);
			logger.info(String.format(SERVER_EXISTS_FORMAT, inet.toString()));

		} catch (UnknownHostException uhe) {

			logger.warn(String.format(SERVER_DOES_NOT_EXIST_FORMAT, serverName), uhe);
			serverExists = false;
		}

		return (serverExists);
	}
}
