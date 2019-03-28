package com.webengage.gtm.sample;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.webengage.sdk.android.WebEngage;

public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private SharedPreferences mSharedPreferences = null;
    private String mUserid = "";
    private String mFirstName = "";

    private EditText mUseridEditText, mFirstNameEditText, mEventEditText;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mUserid = mSharedPreferences.getString(Constants.USER_ID, "");
        mFirstName = mSharedPreferences.getString(Constants.FIRST_NAME, "");

        initViews();
    }

    private void initViews() {
        mUseridEditText = findViewById(R.id.userid_edittext);
        mEventEditText = findViewById(R.id.event_edittext);
        mFirstNameEditText = findViewById(R.id.firstname_edittext);
        mLoginButton = findViewById(R.id.login_button);

        mUseridEditText.setText(mUserid);
        if (mUserid.isEmpty()) {
            mLoginButton.setText(Constants.LOGIN);
        } else {
            mLoginButton.setText(Constants.LOGOUT);
        }

        mFirstNameEditText.setText(mFirstName);
    }

    public void login(View view) {
        if (mLoginButton.getText().toString().equals(Constants.LOGIN)) {
            login();
        } else {
            logout();
        }
    }

    private void login() {
        String userId = mUseridEditText.getText().toString().trim();

        if (userId.isEmpty()) {
            Log.e(Constants.DEBUG_TAG, "user-id cannot be empty");
            return;
        }

        mFirebaseAnalytics.setUserId(userId);

        mUserid = userId;
        mLoginButton.setText(Constants.LOGOUT);
        save(Constants.USER_ID, userId);

        // Trigger for this event should have {we_user_id: _id}
        mFirebaseAnalytics.logEvent("user_update_trigger", null);

        Log.d(Constants.DEBUG_TAG, "user logged in with id: " + userId);
    }

    private void logout() {
        mFirebaseAnalytics.setUserId(null);
        WebEngage.get().user().logout();

        mUserid = "";
        mLoginButton.setText(Constants.LOGIN);
        save(Constants.USER_ID, "");

        mFirebaseAnalytics.logEvent("user_update_trigger", null);

        Log.d(Constants.DEBUG_TAG, "user logged out");
    }

    public void set(View view) {
        String firstName = mFirstNameEditText.getText().toString().trim();
        if (firstName.isEmpty()) {
            return;
        }

        mFirebaseAnalytics.setUserProperty("first_name", firstName);

        save(Constants.FIRST_NAME, firstName);

        // Trigger for this event tag should have: {we_first_name: first_name}
        mFirebaseAnalytics.logEvent("user_update_trigger", null);
    }

    public void track(View view) {
        String event = mEventEditText.getText().toString().trim();

        // All these attributes must be added in your GTM tag to be tracked successfully
        Bundle bundle = new Bundle();
        bundle.putString("key_1", "v1");
        bundle.putString("key_2", "v2");
        bundle.putInt("key_3", 3);
        bundle.putInt("key_4", 4);
        mFirebaseAnalytics.logEvent(event, bundle);

        Log.d(Constants.DEBUG_TAG, "event tracked: " + event);
    }

    private void save(String key, String value) {
        mSharedPreferences.edit()
                .putString(key, value)
                .apply();
    }
}
