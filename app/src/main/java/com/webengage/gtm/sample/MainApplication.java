package com.webengage.gtm.sample;

import android.app.Application;

import com.webengage.sdk.android.WebEngageActivityLifeCycleCallbacks;
import com.webengage.sdk.android.WebEngageConfig;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WebEngageConfig config = new WebEngageConfig.Builder()
                .setWebEngageKey("YOUR-WEBENGAGE-LICENSE-CODE")
                .setDebugMode(true)
                .build();
        registerActivityLifecycleCallbacks(new WebEngageActivityLifeCycleCallbacks(this, config));
    }
}
