package com.libs.util.security.cryptograph;

import com.libs.util.constant.ErrorCode;
import com.libs.util.constant.Global;
import com.libs.util.exception.AppException;
import com.libs.util.log.ILogger;
import com.libs.util.log.LogManager;

public class CryptographManager {

	protected ILogger logger = LogManager.getDefaultLogger();

	public CryptographManager() {
	}

	public String encrypt(String text, String type) throws AppException {
		String result = "";
		if (Global.CRYPTOGRAPH_RC4.equals(type)) {
			this.encryptRC4(text);
		} else if (Global.CRYPTOGRAPH_AES.equals(type)) {
			this.encryptAES(text);
		} else {
			throw new AppException(ErrorCode.ERR_INVALID_CRYPTOGRAPH_TYPE);
		}
		return result;
	}

	public String decrypt(String text, String type) throws AppException {
		String result = "";
		if (Global.CRYPTOGRAPH_RC4.equals(type)) {
			this.decryptRC4(text);
		} else if (Global.CRYPTOGRAPH_AES.equals(type)) {
			this.decryptAES(text);
		} else {
			throw new AppException(ErrorCode.ERR_INVALID_CRYPTOGRAPH_TYPE);
		}
		return result;
	}

	private String encryptRC4(String text) {
		String result = "";
		try {
			result = new String(SAK_Formater.encodebase64(RC4.encrypt(text.getBytes()).getBytes()));
		} catch (Exception e) {
			this.logger.printStackTrace(e);
		}
		return result;
	}

	private String encryptAES(String text) {
		String result = "";
		try {
			result = new String(SAK_Formater.CipherData(text));
		} catch (Exception e) {
			this.logger.printStackTrace(e);
		}
		return result;
	}

	private String decryptRC4(String text) {
		String result = "";
		try {
			result = RC4.decrypt(SAK_Formater.decodebase64(text));
		} catch (Exception e) {
			this.logger.printStackTrace(e);
		}
		return result;
	}

	private String decryptAES(String text) {
		String result = "";
		try {
			result = SAK_Formater.DecipherData(text);
		} catch (Exception e) {
			this.logger.printStackTrace(e);
		}
		return result;
	}

}
