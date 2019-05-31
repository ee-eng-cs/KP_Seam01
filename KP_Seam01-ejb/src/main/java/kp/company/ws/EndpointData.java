package kp.company.ws;

import static kp.company.util.Constants.DEFAULT_HOST;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Web Services Endpoint Data.
 */
@SessionScoped
@Named
public class EndpointData implements Serializable {

	private static final long serialVersionUID = 1L;
	private String host = DEFAULT_HOST;

	/**
	 * Sets WS host.
	 * 
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets WS host.
	 * 
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

}
