package org.openuss.framework.deploy;

/**
 * @author Ingo Dueppe
 * 
 * Inspired by:
 * @author  Ivica Cardic
 * @author  Brian Wing Shun Chan
 *
 */
public class HotDeployException extends Exception {

	private static final long serialVersionUID = 8403349849228945386L;

	public HotDeployException() {
        super();
    }

    public HotDeployException(String msg) {
        super(msg);
    }

    public HotDeployException(Throwable cause) {
        super(cause);
    }

	public HotDeployException(String msg, Throwable cause) {
        super(msg, cause);
    }

}