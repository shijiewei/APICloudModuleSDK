package com.mob.smssdk.appdelegate;

import android.app.Activity;
import android.content.Context;

import com.mob.MobSDK;
import com.mob.smssdk.appdelegate.util.SMSSDKLog;
import com.uzmap.pkg.EntranceActivity;
import com.uzmap.pkg.uzcore.uzmodule.AppInfo;
import com.uzmap.pkg.uzcore.uzmodule.ApplicationDelegate;

import cn.smssdk.SMSSDK;

public class SMSSDKAppDelegate extends ApplicationDelegate {
	@Override
	public void onApplicationCreate(Context context, AppInfo info) {
		super.onApplicationCreate(context, info);
		SMSSDKLog.d("onApplicationCreate()");
		//例如很多第三方SDK要求在Application的onCreate中初始化的代码可以放到该函数中
		MobSDK.init(context);
	}

	@Override
	public void onActivityFinish(Activity activity, AppInfo info) {
		super.onActivityFinish(activity, info);
		SMSSDKLog.d("onActivityFinish(). activity= " + activity.getClass().getName());
		// 离开APICloud页面时，注销所有EventHandler，防止泄漏
		if (activity instanceof EntranceActivity) {
			SMSSDK.unregisterAllEventHandler();
		}
	}
}
