package com.webengage.gtm.sample;

import android.app.Application;

import com.webengage.sdk.android.LocationTrackingStrategy;
import com.webengage.sdk.android.WebEngageActivityLifeCycleCallbacks;
import com.webengage.sdk.android.WebEngageConfig;
import com.webengage.sdk.android.actions.database.ReportingStrategy;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WebEngageConfig config = new WebEngageConfig.Builder()
                .setWebEngageKey("YOUR-WEBENGAGE-LICENSE-CODE")
                .setDebugMode(true)
                .setAutoGCMRegistrationFlag(false)
                .setEventReportingStrategy(ReportingStrategy.BUFFER)
                .setPushSmallIcon(R.mipmap.ic_launcher_round)
                .setPushLargeIcon(R.mipmap.ic_launcher)
                .setEveryActivityIsScreen(true)
                .setLocationTrackingStrategy(LocationTrackingStrategy.ACCURACY_CITY)
                .build();
        registerActivityLifecycleCallbacks(new WebEngageActivityLifeCycleCallbacks(this, config));
    }
}
