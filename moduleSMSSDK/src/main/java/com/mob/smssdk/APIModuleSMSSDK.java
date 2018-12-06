package com.mob.smssdk;

import com.mob.smssdk.util.SMSSDKLog;
import com.mob.tools.utils.Hashon;
import com.uzmap.pkg.uzcore.UZWebView;
import com.uzmap.pkg.uzcore.uzmodule.UZModule;
import com.uzmap.pkg.uzcore.uzmodule.UZModuleContext;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class APIModuleSMSSDK extends UZModule {
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
//									SMSSDKLog.e("jsmethod_getVerificationCode() internal error. msg= " + e.getMessage(), e);
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
//										SMSSDKLog.e("jsmethod_getVerificationCode() internal error. msg= " + e.getMessage(), e);
//										throwInternalError(moduleContext,"Generate JSONObject error");
//									}
//								} else {
//									String msg = "Sdk returned 'RESULT_ERROR', but the data is NOT an instance of Throwable";
//									SMSSDKLog.e("jsmethod_getVerificationCode() internal error: " + msg);
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

	public void jsmethod_getVerificationCode(final UZModuleContext moduleContext){
		SMSSDKLog.d("jsmethod_getVerificationCode()");
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
									SMSSDKLog.e("jsmethod_getVerificationCode() internal error. msg= " + e.getMessage(), e);
									throwInternalError(moduleContext, "Generate JSONObject error");
								}
							}
						} else {
							if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
								// callback onError
								if (data instanceof Throwable) {
									Throwable throwable = (Throwable) data;
									String msg = throwable.getMessage();
									try {
										JSONObject res = new JSONObject(msg);
										throwSdkError(moduleContext, res);
									} catch (JSONException e) {
										SMSSDKLog.e("jsmethod_getVerificationCode() internal error. msg= " + e.getMessage(), e);
										throwInternalError(moduleContext,"Generate JSONObject error");
									}
								} else {
									String msg = "Sdk returned 'RESULT_ERROR', but the data is NOT an instance of Throwable";
									SMSSDKLog.e("jsmethod_getVerificationCode() internal error: " + msg);
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
		String country = moduleContext.optString("country");
		String phone = moduleContext.optString("phone");

		SMSSDKLog.d("tempCode: " + tempCode);
		SMSSDKLog.d("country: " + country);
		SMSSDKLog.d("phone: " + phone);
		SMSSDK.getVerificationCode(tempCode, country, phone);
	}

	public void jsmethod_getVoiceVerifyCode(final UZModuleContext moduleContext){
		SMSSDKLog.d("jsmethod_getVoiceVerifyCode()");
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
									try {
										JSONObject res = new JSONObject(msg);
										throwSdkError(moduleContext, res);
									} catch (JSONException e) {
										SMSSDKLog.e("jsmethod_getVoiceVerifyCode() internal error. msg= " + e.getMessage(), e);
										throwInternalError(moduleContext,"Generate JSONObject error");
									}
								} else {
									String msg = "Sdk returned 'RESULT_ERROR', but the data is NOT an instance of Throwable";
									SMSSDKLog.e("jsmethod_getVoiceVerifyCode() internal error: " + msg);
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
		String country = moduleContext.optString("country");
		String phone = moduleContext.optString("phone");

		SMSSDKLog.d("country: " + country);
		SMSSDKLog.d("phone: " + phone);
		SMSSDK.getVoiceVerifyCode(country, phone);
	}

	public void jsmethod_submitVerificationCode(final UZModuleContext moduleContext){
		SMSSDKLog.d("jsmethod_submitVerificationCode()");
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
								HashMap<String, Object> map = (HashMap<String, Object>)data;
								JSONObject res = new JSONObject();
								try {
									if (map != null && !map.isEmpty()) {
										res.put("country", map.get("country"));
										res.put("phone", map.get("phone"));
									}
									throwSuccess(moduleContext, res);
								} catch (JSONException e) {
									SMSSDKLog.e("jsmethod_submitVerificationCode() internal error. msg= " + e.getMessage(), e);
									throwInternalError(moduleContext, "Generate JSONObject error");
								}
							}
						} else {
							if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
								// callback onError
								if (data instanceof Throwable) {
									Throwable throwable = (Throwable) data;
									String msg = throwable.getMessage();
									try {
										JSONObject res = new JSONObject(msg);
										throwSdkError(moduleContext, res);
									} catch (JSONException e) {
										SMSSDKLog.e("jsmethod_submitVerificationCode() internal error. msg= " + e.getMessage(), e);
										throwInternalError(moduleContext,"Generate JSONObject error");
									}
								} else {
									String msg = "Sdk returned 'RESULT_ERROR', but the data is NOT an instance of Throwable";
									SMSSDKLog.e("jsmethod_submitVerificationCode() internal error: " + msg);
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
		String country = moduleContext.optString("country");
		String phone = moduleContext.optString("phone");
		String code = moduleContext.optString("code");

		SMSSDKLog.d("country: " + country);
		SMSSDKLog.d("phone: " + phone);
		SMSSDKLog.d("code: " + code);
		SMSSDK.submitVerificationCode(country, phone, code);
	}

	private void throwSuccess(UZModuleContext moduleContext, JSONObject res) {
		moduleContext.success(res, true);
	}

	private void throwSdkError(UZModuleContext moduleContext, JSONObject error) {
		moduleContext.error(null, error, true);
	}

	private void throwInternalError(UZModuleContext moduleContext, String errMsg) {
		JSONObject errJSON = new JSONObject();
		try {
			errJSON.put("error", ERROR_INTERNAL + errMsg);
		} catch (JSONException e) {
			SMSSDKLog.e("throwInternalError() encountered exception. msg= " + e.getMessage(), e);
		}
		moduleContext.error(null, errJSON, true);
	}
}
