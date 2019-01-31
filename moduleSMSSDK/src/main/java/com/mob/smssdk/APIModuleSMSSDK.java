package com.mob.smssdk;

import com.mob.smssdk.util.SMSSDKLog;
import com.mob.tools.utils.Hashon;
import com.uzmap.pkg.uzcore.UZWebView;
import com.uzmap.pkg.uzcore.uzmodule.UZModule;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class APIModuleSMSSDK extends UZModule {
	private static final String KEY_CODE = "code";
	private static final String KEY_MSG = "msg";
	private static final int BRIDGE_ERR = 700;
	private static final String ERROR_INTERNAL = "APICloud bridge internal error: ";
	private Hashon hashon;

	public APIModuleSMSSDK(UZWebView webView) {
		super(webView);
		hashon = new Hashon();
	}

	/*
	 * 不提供全局注册EventHandler的方式，全局的需要将event类型也返回给JS，以区分是哪个接口的数据
	 */
//	public void jsmethod_registerEventHandler(final UZModuleContext moduleContext){
//		SMSSDKLog.d("jsmethod_registerEventHandler()");
//
//		EventHandler callback = new EventHandler() {
//			@Override
//			public void afterEvent(final int event, final int result, final Object data) {
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						if (result == SMSSDK.RESULT_COMPLETE) {
//							if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//								boolean smart = (Boolean)data;
//								// callback onSuccess
//								JSONObject res = new JSONObject();
//								try {
//									res.put("smart", smart);
//									throwSuccess(moduleContext, res);
//								} catch (JSONException e) {
//									SMSSDKLog.e("jsmethod_getCode() internal error. msg= " + e.getMessage(), e);
//									throwInternalError(moduleContext, "Generate JSONObject error");
//								}
//							}
//						} else {
//							if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//								// callback onError
//								if (data instanceof Throwable) {
//									Throwable throwable = (Throwable) data;
//									String msg = throwable.getMessage();
//									try {
//										JSONObject res = new JSONObject(msg);
//										throwSdkError(moduleContext, res);
//									} catch (JSONException e) {
//										SMSSDKLog.e("jsmethod_getCode() internal error. msg= " + e.getMessage(), e);
//										throwInternalError(moduleContext,"Generate JSONObject error");
//									}
//								} else {
//									String msg = "Sdk returned 'RESULT_ERROR', but the data is NOT an instance of Throwable";
//									SMSSDKLog.e("jsmethod_getCode() internal error: " + msg);
//									throwInternalError(moduleContext, msg);
//								}
//							}
//						}
//					}
//				});
//			}
//		};
//		SMSSDK.registerEventHandler(callback);
//	}

	public void jsmethod_getTextCode(final UZModuleContext moduleContext){
		SMSSDKLog.d("jsmethod_getTextCode()");
		// 注册监听器
		EventHandler callback = new EventHandler() {
			@Override
			public void afterEvent(final int event, final int result, final Object data) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (result == SMSSDK.RESULT_COMPLETE) {
							if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
								boolean smart = (Boolean)data;
								// callback onSuccess
								JSONObject res = new JSONObject();
								try {
									res.put("smart", smart);
									throwSuccess(moduleContext, res);
								} catch (JSONException e) {
									SMSSDKLog.e("jsmethod_getTextCode() internal error. msg= " + e.getMessage(), e);
									throwInternalError(moduleContext, "Generate JSONObject error");
								}
							}
						} else {
							if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
								// callback onError
								if (data instanceof Throwable) {
									Throwable throwable = (Throwable) data;
									String msg = throwable.getMessage();
									throwSdkError(moduleContext, msg);
								} else {
									String msg = "Sdk returned 'RESULT_ERROR', but the data is NOT an instance of Throwable";
									SMSSDKLog.e("jsmethod_getTextCode() internal error: " + msg);
									throwInternalError(moduleContext, msg);
								}
							}
						}
					}
				});
			}
		};
		SMSSDK.registerEventHandler(callback);

		// 调用接口
		String tempCode = moduleContext.optString("tempCode");
		String zone = moduleContext.optString("zone");
		String phoneNumber = moduleContext.optString("phoneNumber");

		SMSSDKLog.d("tempCode: " + tempCode);
		SMSSDKLog.d("zone: " + zone);
		SMSSDKLog.d("phoneNumber: " + phoneNumber);
		SMSSDK.getVerificationCode(tempCode, zone, phoneNumber);
	}

	public void jsmethod_getVoiceCode(final UZModuleContext moduleContext){
		SMSSDKLog.d("jsmethod_getVoiceCode()");
		// 注册监听器
		EventHandler callback = new EventHandler() {
			@Override
			public void afterEvent(final int event, final int result, final Object data) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (result == SMSSDK.RESULT_COMPLETE) {
							if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
								// callback onSuccess
								// 此接口data=null
								JSONObject res = new JSONObject();
								throwSuccess(moduleContext, res);
							}
						} else {
							if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
								// callback onError
								if (data instanceof Throwable) {
									Throwable throwable = (Throwable) data;
									String msg = throwable.getMessage();
									throwSdkError(moduleContext, msg);
								} else {
									String msg = "Sdk returned 'RESULT_ERROR', but the data is NOT an instance of Throwable";
									SMSSDKLog.e("jsmethod_getVoiceCode() internal error: " + msg);
									throwInternalError(moduleContext, msg);
								}
							}
						}
					}
				});
			}
		};
		SMSSDK.registerEventHandler(callback);

		// 调用接口
		String zone = moduleContext.optString("zone");
		String phoneNumber = moduleContext.optString("phoneNumber");

		SMSSDKLog.d("zone: " + zone);
		SMSSDKLog.d("phoneNumber: " + phoneNumber);
		SMSSDK.getVoiceVerifyCode(zone, phoneNumber);
	}

	public void jsmethod_commitCode(final UZModuleContext moduleContext){
		SMSSDKLog.d("jsmethod_commitCode()");
		// 注册监听器
		EventHandler callback = new EventHandler() {
			@Override
			public void afterEvent(final int event, final int result, final Object data) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (result == SMSSDK.RESULT_COMPLETE) {
							if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
								// callback onSuccess
								// data示例：{country=86, phone=13362206853}
//								HashMap<String, Object> map = (HashMap<String, Object>)data;
//								JSONObject res = new JSONObject();
//								try {
//									if (map != null && !map.isEmpty()) {
//										res.put("country", map.get("country"));
//										res.put("phone", map.get("phone"));
//									}
//									throwSuccess(moduleContext, res);
//								} catch (JSONException e) {
//									SMSSDKLog.e("jsmethod_commitCode() internal error. msg= " + e.getMessage(), e);
//									throwInternalError(moduleContext, "Generate JSONObject error");
//								}
								throwSuccess(moduleContext, null);
							}
						} else {
							if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
								// callback onError
								if (data instanceof Throwable) {
									Throwable throwable = (Throwable) data;
									String msg = throwable.getMessage();
									throwSdkError(moduleContext, msg);
								} else {
									String msg = "Sdk returned 'RESULT_ERROR', but the data is NOT an instance of Throwable";
									SMSSDKLog.e("jsmethod_commitCode() internal error: " + msg);
									throwInternalError(moduleContext, msg);
								}
							}
						}
					}
				});
			}
		};
		SMSSDK.registerEventHandler(callback);

		// 调用接口
		String zone = moduleContext.optString("zone");
		String phoneNumber = moduleContext.optString("phoneNumber");
		String code = moduleContext.optString("code");

		SMSSDKLog.d("zone: " + zone);
		SMSSDKLog.d("phoneNumber: " + phoneNumber);
		SMSSDKLog.d("code: " + code);
		SMSSDK.submitVerificationCode(zone, phoneNumber, code);
	}

	public void jsmethod_getSupportedCountries(final UZModuleContext moduleContext){
		SMSSDKLog.d("jsmethod_getSupportedCountries()");
		// 注册监听器
		EventHandler callback = new EventHandler() {
			@Override
			public void afterEvent(final int event, final int result, final Object data) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (result == SMSSDK.RESULT_COMPLETE) {
							if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
								// callback onSuccess
								// data示例：[{zone=590, rule=^\d+}, {zone=680, rule=^\d+}]
								ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>)data;
								HashMap<String, Object> map = new HashMap<String, Object>();
								map.put("countries", list);
								JSONObject res = new JSONObject(map);
								throwSuccess(moduleContext, res);
							}
						} else {
							if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
								// callback onError
								if (data instanceof Throwable) {
									Throwable throwable = (Throwable) data;
									String msg = throwable.getMessage();
									throwSdkError(moduleContext, msg);
								} else {
									String msg = "Sdk returned 'RESULT_ERROR', but the data is NOT an instance of Throwable";
									SMSSDKLog.e("jsmethod_getSupportedCountries() internal error: " + msg);
									throwInternalError(moduleContext, msg);
								}
							}
						}
					}
				});
			}
		};
		SMSSDK.registerEventHandler(callback);

		// 调用接口
		SMSSDK.getSupportedCountries();
	}

	public void jsmethod_getFriends(final UZModuleContext moduleContext){
		SMSSDKLog.d("jsmethod_getFriends()");
		// 注册监听器
		EventHandler callback = new EventHandler() {
			@Override
			public void afterEvent(final int event, final int result, final Object data) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (result == SMSSDK.RESULT_COMPLETE) {
							if (event == SMSSDK.EVENT_GET_FRIENDS_IN_APP) {
								// callback onSuccess
								/* data示例：[{uid=1155310877, phone=17301652905, nickname=SmsSDK_User_1155310877,
								 *     avatar=http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg, isnew=true}]
								 */
								ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>)data;
								HashMap<String, Object> map = new HashMap<String, Object>();
								map.put("friends", list);
								JSONObject res = new JSONObject(map);
								throwSuccess(moduleContext, res);
							}
						} else {
							if (event == SMSSDK.EVENT_GET_FRIENDS_IN_APP) {
								// callback onError
								if (data instanceof Throwable) {
									Throwable throwable = (Throwable) data;
									String msg = throwable.getMessage();
									throwSdkError(moduleContext, msg);
								} else {
									String msg = "Sdk returned 'RESULT_ERROR', but the data is NOT an instance of Throwable";
									SMSSDKLog.e("jsmethod_getFriends() internal error: " + msg);
									throwInternalError(moduleContext, msg);
								}
							}
						}
					}
				});
			}
		};
		SMSSDK.registerEventHandler(callback);

		// 调用接口
		SMSSDK.getFriendsInApp();
	}

	private void throwSuccess(UZModuleContext moduleContext, JSONObject res) {
		moduleContext.success(res, true);
	}

	private void throwSdkError(UZModuleContext moduleContext, String error) {
		try {
			JSONObject resp = new JSONObject(error);
			int code = resp.optInt("status");
			String msg = resp.optString("error");
			resp.remove("status");
			resp.remove("error");
			resp.put(KEY_CODE, code);
			resp.put(KEY_MSG, msg);
			moduleContext.error(null, resp, true);
		} catch (JSONException e) {
			SMSSDKLog.e("jsmethod_getSupportedCountries() internal error. msg= " + e.getMessage(), e);
			throwInternalError(moduleContext,"Generate JSONObject error");
		}
	}

	private void throwInternalError(UZModuleContext moduleContext, String errMsg) {
		JSONObject errJSON = new JSONObject();
		try {
			errJSON.put(KEY_CODE, BRIDGE_ERR);
			errJSON.put(KEY_MSG, ERROR_INTERNAL + errMsg);
		} catch (JSONException e) {
			SMSSDKLog.e("throwInternalError() encountered exception. msg= " + e.getMessage(), e);
		}
		moduleContext.error(null, errJSON, true);
	}
}
