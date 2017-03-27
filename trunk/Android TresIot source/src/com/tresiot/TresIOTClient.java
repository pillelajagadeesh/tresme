package com.tresiot;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

class TresIOTClient {
	private static final String TAG = TresIOTClient.class.getSimpleName();
	private static String defaultApiKey;
	private static String apiEndpoint="http://139.59.3.152/";
	private static String encryptionKey;
	static Context context;
	private boolean enableRetryUploading = false;

	
	TresIOTClient(Context context, String apiKey) throws IOException {

		// setDebugMode(true);
		this.context= context;
		setApiKey(apiKey == null ? TresIOTClient.defaultApiKey : apiKey);
		
	}

	static void setDefaultApiKey(String defaultApiKey) {
		TresIOTClient.defaultApiKey = defaultApiKey;
	}

	static String getDefaultApiKey() {
		return TresIOTClient.defaultApiKey;
	}

	static void setApiEndpoint(String apiEndpoint) {
		TresIOTClient.apiEndpoint = apiEndpoint;
	}

	public static void setEncryptionKey(String encryptionKey) {
		TresIOTClient.encryptionKey = encryptionKey;
	}

	public void disableAutoRetryUploading() {
		enableRetryUploading = false;
	}

	@Deprecated
	TresIOTClient(String apiKey) {
		
		setApiKey(apiKey);
	}

	private void setApiKey(String apiKey) {
		String projectId;
		try {
			projectId = createProjectIdFromApiKey(apiKey);
		} catch (Exception e) {
			projectId = "_td default";
		}
		
		
	}

	private String createProjectIdFromApiKey(String apiKey)
			throws NoSuchAlgorithmException {
		StringBuilder hexString = new StringBuilder();
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] hash = md5.digest(apiKey.getBytes());

		for (byte aHash : hash) {
			if ((0xff & aHash) < 0x10) {
				hexString.append("0").append(
						Integer.toHexString((0xFF & aHash)));
			} else {
				hexString.append(Integer.toHexString(0xFF & aHash));
			}
		}
		return "_td " + hexString.toString();
	}

	public void enableAutoRetryUploading() {
		// TODO Auto-generated method stub
		enableRetryUploading = true;
	}
}
