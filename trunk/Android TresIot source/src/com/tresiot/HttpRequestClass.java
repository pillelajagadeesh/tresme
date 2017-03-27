package com.tresiot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

import android.content.Context;
import android.util.Log;

public class HttpRequestClass {

	private static final String DEFAULT_API_ENDPOINT = "http://139.59.3.152";
	protected AsyncHttpClient mAsynHttp;
	public Context mContext;

	public HttpRequestClass(Context conx) {
		mAsynHttp = new AsyncHttpClient();
		mAsynHttp.setTimeout(60000);
		this.mContext = conx;
	}

	public void postEvent(JSONObject object,
			final TresIOTCallback interfaceCallBack) {
		try {

			String url = DEFAULT_API_ENDPOINT + "/api/eventdata";
			url = url.replaceAll(" ", "%20");
			StringEntity entity = null;
			try {
				entity = new StringEntity(object.toString());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mAsynHttp.post(mContext, url, entity, "application/json",
					new HttpResponseHandler(mContext) {
						@Override
						public void onSuccess(int statusCode, String response) {
							super.onSuccess(statusCode, response);

							try {
								interfaceCallBack.onSuccess();

							} catch (Exception e) {
								// TODO: handle exception
							}
						}

						@Override
						public void onFailure(Throwable e, String message) {
							// TODO Auto-generated method stub
							super.onFailure(e, message);
							interfaceCallBack.onError(message);
						}

					});
		} catch (Exception e) {
		}
	}
	
	
	
	public void postSensor(JSONObject object) {
		try {
		
			String url = DEFAULT_API_ENDPOINT + "/api/sensordata";
			url = url.replaceAll(" ", "%20");
			StringEntity entity = null;
			try {
				entity = new StringEntity(object.toString());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mAsynHttp.post(mContext, url, entity, "application/json",
					new HttpResponseHandler(mContext) {
						@Override
						public void onSuccess(int statusCode,
								String response) {
							super.onSuccess(statusCode, response);
						}
						
						
						@Override
						public void onFailure(Throwable e, String message) {
							// TODO Auto-generated method stub
							super.onFailure(e, message);
						}

					});
		}catch(Exception e){}
			}


	private static String convertStreamToString(InputStream is) {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static HttpClient getNewHttpClient() {
		try {

			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			// A set of supported protocol schemes. Schemes are identified by
			// lowercase names.
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);

			HttpClient client = new DefaultHttpClient(ccm, params);

			HttpConnectionParams
					.setConnectionTimeout(client.getParams(), 20000);
			HttpConnectionParams.setSoTimeout(client.getParams(), 20000);
			return client;
		} catch (Exception e) {
			return getNewHttpClient();
		}
	}

}
