package com.tresiot;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class TresIOTData {
	private static final String TAG = TresIOTData.class.getSimpleName();
	private static final String VERSION = "1.0";
	private static final String LABEL_ADD_EVENT = "addEvent";
	private static final String LABEL_UPLOAD_EVENTS = "uploadEvents";
	private static final Pattern DATABASE_NAME_PATTERN = Pattern
			.compile("^[0-9a-z_]{3,255}$");
	private static final Pattern TABLE_NAME_PATTERN = Pattern
			.compile("^[0-9a-z_]{3,255}$");
	private static final String SHARED_PREF_NAME = "tresiot_sdk_info";
	private static final String SHARED_PREF_KEY_UUID = "uuid";
	private static final String SHARED_PREF_KEY_FIRST_RUN = "first_run";
	private static final String EVENT_KEY_UUID = "tresiot_uuid";
	private static final String EVENT_KEY_SESSION_ID = "tresiot_session_id";
	private static final String EVENT_KEY_SESSION_EVENT = "tresiot_session_event";
	private static final String EVENT_KEY_BOARD = "tresiot_board";
	private static final String EVENT_KEY_BRAND = "tresiot_brand";
	private static final String EVENT_KEY_DEVICE = "tresiot_device";
	private static final String EVENT_KEY_DISPLAY = "tresiot_display";
	private static final String EVENT_KEY_MODEL = "tresiot_model";
	private static final String EVENT_KEY_OS_VER = "tresiot_os_ver";
	private static final String EVENT_KEY_OS_TYPE = "tresiot_os_type";
	private static final String EVENT_KEY_APP_VER = "tresiot_app_ver";
	private static final String EVENT_KEY_APP_VER_NUM = "tresiot_app_ver_num";
	private static final String EVENT_KEY_LOCALE_COUNTRY = "tresiot_locale_country";
	private static final String EVENT_KEY_LOCALE_LANG = "tresiot_locale_lang";
	private static final String EVENT_KEY_SERVERSIDE_UPLOAD_TIMESTAMP = "#SSUT";
	private static final String EVENT_DEFAULT_KEY_RECORD_UUID = "record_uuid";
	private static final String OS_TYPE = "Android";

	public static final String EVENT_NAME = "name";
	public static final String EVENT_APPLICATION_KEY = "applicationId";
	public static final String EVENT_DESCRIPTION = "description";

	public static final String EVENT_CLIENT_ID = "clientid";
	public static final String EVENT_CLIENT_OS = "clientos";
	public static final String EVENT_DEVICE_MAKE = "devicemake";
	public static final String EVENT_APP_VERSION = "appversion";
	public static final String EVENT_LOCATION = "location";
	public static final String EVENT_APPLICATION_ID = "applicationId";
	public static final String EVENT_APPLICATION_EVENT_ID = "applicationeventId";
	public static final String EVENT_APPLICATION_VIEW_ID = "applicationviewId";
	public static final String EVENT_SESSION_DURATION = "sessionduration";
	
	public static final String SENSOR_CLIENT_ID = "clientid";
	public static final String SENSOR_APPLICATION_ID = "applicationId";
	public static final String SENSOR_CREATEDTIME = "createdTime";
	public static final String SENSOR_ID = "sensorId";
	public static final String SENSOR_VALUE = "value";
	
	

	

	private static TresIOTData sharedInstance;
	private final static WeakHashMap<Context, Session> sessions = new WeakHashMap<Context, Session>();

	private final Context context;
	private final TresIOTClient client;
	private final String uuid;
	private volatile String defaultDatabase;
	private volatile TresIOTCallback addEventCallBack;
	private volatile TresIOTCallback uploadEventsCallBack;
	private volatile boolean autoAppendUniqId;
	private volatile boolean autoAppendModelInformation;
	private volatile boolean autoAppendAppInformation;
	private volatile boolean autoAppendLocaleInformation;
	private static volatile long sessionTimeoutMilli = Session.DEFAULT_SESSION_PENDING_MILLIS;
	private final String appVersion;
	private final int appVersionNumber;
	private volatile boolean serverSideUploadTimestamp;
	private volatile String serverSideUploadTimestampColumn;
	private Session session = new Session();
	private volatile String autoAppendRecordUUIDColumn;
	HttpRequestClass httpRequestClass;

	public static TresIOTData initializeSharedInstance(Context context,
			String apiKey) {
		sharedInstance = new TresIOTData(context, apiKey);

		return sharedInstance;
	}

	public static TresIOTData initializeSharedInstance(Context context) {
		return initializeSharedInstance(context, null);
	}

	public static TresIOTData sharedInstance() {
		if (sharedInstance == null) {
			Log.w(TAG, "sharedInstance is initialized properly");
			return new NullTreasureData();
		}
		return sharedInstance;
	}

	private SharedPreferences getSharedPreference(Context context) {
		return context.getSharedPreferences(SHARED_PREF_NAME,
				Context.MODE_PRIVATE);
	}

	public String getUUID(Context context) {
		SharedPreferences sharedPreferences = getSharedPreference(context);
		synchronized (this) {
			String uuid = sharedPreferences.getString(SHARED_PREF_KEY_UUID,
					null);
			if (uuid == null) {
				uuid = UUID.randomUUID().toString();
				sharedPreferences.edit().putString(SHARED_PREF_KEY_UUID, uuid)
						.commit();
			}
			return uuid;
		}
	}

	public boolean isFirstRun(Context context) {
		SharedPreferences sharedPreferences = getSharedPreference(context);
		synchronized (this) {
			return sharedPreferences
					.getBoolean(SHARED_PREF_KEY_FIRST_RUN, true);
		}
	}

	public void clearFirstRun(Context context) {
		SharedPreferences sharedPreferences = getSharedPreference(context);
		synchronized (this) {
			sharedPreferences.edit()
					.putBoolean(SHARED_PREF_KEY_FIRST_RUN, false).commit();
		}
	}

	public TresIOTData(Context context, String apiKey) {
		Context applicationContext = context.getApplicationContext();
		this.context = applicationContext;
		uuid = getUUID(applicationContext);
		httpRequestClass = new HttpRequestClass(context);
		TresIOTClient client = null;
		if (apiKey == null && TresIOTClient.getDefaultApiKey() == null) {
			Log.e(TAG, "initializeApiKey() hasn't called yet");
		} else {
			try {
				client = new TresIOTClient(applicationContext, apiKey);
			} catch (IOException e) {
				Log.e(TAG, "Failed to construct TresIOTData object", e);
			}
		}

		String appVersion = "";
		int appVersionNumber = 0;
		try {
			PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			appVersion = pkgInfo.versionName;
			appVersionNumber = pkgInfo.versionCode;
		} catch (Exception e) {
			Log.e(TAG, "Failed to get package information", e);
		}
		this.appVersion = appVersion;
		this.appVersionNumber = appVersionNumber;

		this.client = client;
	}

	public TresIOTData(Context context) {
		this(context, null);
	}

	public static void enableLogging() {
	//	TresIOTLogging.enableLogging();
	}

	public static void disableLogging() {
		//TresIOTLogging.disableLogging();
	}

	public static void initializeApiEndpoint(String apiEndpoint) {
		TresIOTClient.setApiEndpoint(apiEndpoint);
	}

	public static void initializeDefaultApiKey(String defaultApiKey) {
		TresIOTClient.setDefaultApiKey(defaultApiKey);
	}

	public static void initializeEncryptionKey(String encryptionKey) {
		TresIOTClient.setEncryptionKey(encryptionKey);
	}

	public static void enableEventCompression() {
		//TresIOTHttpHandler.enableEventCompression();
	}

	public static void disableEventCompression() {
		//TresIOTHttpHandler.disableEventCompression();
	}

	public void setDefaultDatabase(String defaultDatabase) {
		this.defaultDatabase = defaultDatabase;
	}

	public synchronized void setAddEventCallBack(TresIOTCallback callBack) {
		this.addEventCallBack = callBack;
	}

	public TresIOTCallback getAddEventCallBack() {
		return this.addEventCallBack;
	}

	public synchronized void setUploadEventsCallBack(TresIOTCallback callBack) {
		this.uploadEventsCallBack = callBack;
	}

	public TresIOTCallback getUploadEventsCallBack() {
		return this.uploadEventsCallBack;
	}

	

		
	public void addEventWithCallback(Map<String, Object> origRecord,
			TresIOTCallback callback) {
		

		if (callback == null) {
			callback = addEventCallBack;
		}

		Map<String, Object> record = new HashMap<String, Object>();
		if (origRecord != null) {
			record.putAll(origRecord);
		}

		// client.queueEvent(null, null, record, null,
		// createKeenCallback(LABEL_ADD_EVENT, callback));
		JSONObject jObj = new JSONObject(origRecord);

		httpRequestClass.postEvent(jObj, callback);
	}
	
	
	
	public void addSensors(Map<String, Object> origRecord) {
		
		Map<String, Object> record = new HashMap<String, Object>();
		if (origRecord != null) {
			record.putAll(origRecord);
		}

		// client.queueEvent(null, null, record, null,
		// createKeenCallback(LABEL_ADD_EVENT, callback));
		JSONObject jObj = new JSONObject(origRecord);

		httpRequestClass.postSensor(jObj);
	}

		public void appendSessionId(Map<String, Object> record) {
		String instanceSessionId = session.getId();
		String globalSessionId = null;
		Session globalSession = getSession(context);
		if (globalSession != null) {
			globalSessionId = globalSession.getId();
		}

		if (globalSession != null && instanceSessionId != null) {
			Log.w(TAG,
					"instance method #startSession(String) and static method TresIOTData.startSession(android.content.Context) are both enabled, but the instance method will be ignored.");
		}

		if (instanceSessionId != null) {
			record.put(EVENT_KEY_SESSION_ID, instanceSessionId);
		}

		if (globalSessionId != null) {
			record.put(EVENT_KEY_SESSION_ID, globalSessionId);
		}
	}

	public void appendUniqId(Map<String, Object> record) {
		record.put(EVENT_KEY_UUID, uuid);
	}

	public void appendModelInformation(Map<String, Object> record) {
		record.put(EVENT_KEY_BOARD, Build.BOARD);
		record.put(EVENT_KEY_BRAND, Build.BRAND);
		record.put(EVENT_KEY_DEVICE, Build.DEVICE);
		record.put(EVENT_KEY_DISPLAY, Build.DISPLAY);
		record.put(EVENT_KEY_DEVICE, Build.DEVICE);
		record.put(EVENT_KEY_MODEL, Build.MODEL);
		record.put(EVENT_KEY_OS_VER, Build.VERSION.SDK_INT);
		record.put(EVENT_KEY_OS_TYPE, OS_TYPE);
	}

	public void appendAppInformation(Map<String, Object> record) {
		record.put(EVENT_KEY_APP_VER, appVersion);
		record.put(EVENT_KEY_APP_VER_NUM, appVersionNumber);
	}

	public void appendLocaleInformation(Map<String, Object> record) {
		Locale locale = context.getResources().getConfiguration().locale;
		record.put(EVENT_KEY_LOCALE_COUNTRY, locale.getCountry());
		record.put(EVENT_KEY_LOCALE_LANG, locale.getLanguage());
	}

	public void appendRecordUUID(Map<String, Object> record) {
		record.put(autoAppendRecordUUIDColumn, UUID.randomUUID().toString());
	}

	public void disableAutoAppendUniqId() {
		this.autoAppendUniqId = false;
	}

	public void enableAutoAppendUniqId() {
		this.autoAppendUniqId = true;
	}

	public void disableAutoAppendModelInformation() {
		this.autoAppendModelInformation = false;
	}

	public void enableAutoAppendModelInformation() {
		this.autoAppendModelInformation = true;
	}

	public void disableAutoAppendAppInformation() {
		this.autoAppendAppInformation = false;
	}

	public void enableAutoAppendAppInformation() {
		this.autoAppendAppInformation = true;
	}

	public void disableAutoAppendLocaleInformation() {
		this.autoAppendLocaleInformation = false;
	}

	public void enableAutoAppendLocaleInformation() {
		this.autoAppendLocaleInformation = true;
	}

	public void disableAutoRetryUploading() {
		client.disableAutoRetryUploading();
	}

	public void enableAutoRetryUploading() {
		client.enableAutoRetryUploading();
	}

	public static void setSessionTimeoutMilli(long timeoutMilli) {
		sessionTimeoutMilli = timeoutMilli;
	}

	private static Session getSession(Context context) {
		if (context == null) {
			Log.w(TAG, "context is null. It's an unit test, right?");
			return null;
		}
		Context applicationContext = context.getApplicationContext();
		return sessions.get(applicationContext);
	}

	public void startSession(String table) {
		startSession(defaultDatabase, table);
	}

	public void startSession(String database, String table) {
		session.start();
		HashMap<String, Object> record = new HashMap<String, Object>(1);
		record.put(EVENT_KEY_SESSION_EVENT, "start");
//		addEvent(database, table, record);
	}

	public static void startSession(Context context) {
		Session session = getSession(context);
		if (session == null) {
			session = new Session(sessionTimeoutMilli);
			sessions.put(context.getApplicationContext(), session);
		}
		session.start();
	}

	public void endSession(String table) {
		endSession(defaultDatabase, table);
	}

	public void endSession(String database, String table) {
		HashMap<String, Object> record = new HashMap<String, Object>(1);
		record.put(EVENT_KEY_SESSION_EVENT, "end");
	//	addEvent(database, table, record);
		session.finish();
	}

	public static void endSession(Context context) {
		Session session = getSession(context);
		if (session != null) {
			session.finish();
		}
	}

	public static String getSessionId(Context context) {
		Session session = getSession(context);
		if (session == null) {
			return null;
		}
		return session.getId();
	}

	public void enableServerSideUploadTimestamp() {
		serverSideUploadTimestamp = true;
		serverSideUploadTimestampColumn = null;
	}

	public void enableServerSideUploadTimestamp(String columnName) {
		if (columnName == null) {
			Log.w(TAG, "columnName shouldn't be null");
			return;
		}

		serverSideUploadTimestamp = true;
		serverSideUploadTimestampColumn = columnName;
	}

	public void disableServerSideUploadTimestamp() {
		serverSideUploadTimestamp = false;
		serverSideUploadTimestampColumn = null;
	}

	public void enableAutoAppendRecordUUID() {
		autoAppendRecordUUIDColumn = EVENT_DEFAULT_KEY_RECORD_UUID;
	}

	public void enableAutoAppendRecordUUID(String columnName) {
		if (columnName == null) {
			Log.w(TAG, "columnName shouldn't be null");
			return;
		}
		autoAppendRecordUUIDColumn = columnName;
	}

	public void disableAutoAppendRecordUUID() {
		autoAppendRecordUUIDColumn = null;
	}

	// Only for testing
	@Deprecated
	TresIOTData(Context context, TresIOTClient mockClient, String uuid) {
		this.context = context;
		this.client = mockClient;
		this.uuid = uuid;
		this.appVersion = "3.1.4";
		this.appVersionNumber = 42;
	}

	static class NullTreasureData extends TresIOTData {
		public NullTreasureData() {
			super(null, null, null);
		}

		@Override
		public synchronized void setAddEventCallBack(TresIOTCallback callBack) {
		}

		@Override
		public TresIOTCallback getAddEventCallBack() {
			return null;
		}

		@Override
		public synchronized void setUploadEventsCallBack(
				TresIOTCallback callBack) {
		}

		@Override
		public TresIOTCallback getUploadEventsCallBack() {
			return null;
		}

		public void addEvent(String database, String table,
				Map<String, Object> record) {
		}

		
		public void addEventWithCallback(String database, String table,
				Map<String, Object> record, TresIOTCallback callback) {
		}

		
		public void uploadEvents() {
		}

		
		public void uploadEventsWithCallback(TresIOTCallback callback) {
		}

		@Override
		public void disableAutoAppendUniqId() {
		}

		@Override
		public void enableAutoAppendUniqId() {
		}

		@Override
		public void disableAutoAppendModelInformation() {
		}

		@Override
		public void enableAutoAppendModelInformation() {
		}

		@Override
		public void enableAutoRetryUploading() {
		}

		@Override
		public void disableAutoRetryUploading() {
		}

		@Override
		public void appendUniqId(Map<String, Object> record) {
		}

		@Override
		public void appendModelInformation(Map<String, Object> record) {
		}

		@Override
		public boolean isFirstRun(Context context) {
			return false;
		}

		@Override
		public void clearFirstRun(Context context) {
		}

		@Override
		public String getUUID(Context context) {
			return null;
		}

		@Override
		public void setDefaultDatabase(String defaultDatabase) {
		}

		
		public void addEvent(String table, Map<String, Object> record) {
		}

		
		public void addEventWithCallback(String table,
				Map<String, Object> record, TresIOTCallback callback) {
		}

		@Override
		public void appendSessionId(Map<String, Object> record) {
		}

		@Override
		public void startSession(String table) {
		}

		@Override
		public void startSession(String database, String table) {
		}

		@Override
		public void endSession(String table) {
		}

		@Override
		public void endSession(String database, String table) {
		}

		@Override
		public void enableServerSideUploadTimestamp() {
		}

		@Override
		public void disableServerSideUploadTimestamp() {
		}
	}
	
}
