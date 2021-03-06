package com.leanplum.activities;

import android.accounts.AccountAuthenticatorActivity;
import android.content.res.Resources;
import com.leanplum.Leanplum;
import com.leanplum.LeanplumActivityHelper;
import com.newrelic.agent.android.api.v2.TraceFieldInterface;
import com.newrelic.agent.android.background.ApplicationStateMonitor;
import com.newrelic.agent.android.instrumentation.Instrumented;

@Instrumented
public class LeanplumAccountAuthenticatorActivity extends AccountAuthenticatorActivity implements TraceFieldInterface {
    private LeanplumActivityHelper f8726a;

    protected void onStart() {
        super.onStart();
        ApplicationStateMonitor.getInstance().activityStarted();
    }

    private LeanplumActivityHelper m12713a() {
        if (this.f8726a == null) {
            this.f8726a = new LeanplumActivityHelper(this);
        }
        return this.f8726a;
    }

    protected void onPause() {
        super.onPause();
        m12713a().onPause();
    }

    protected void onStop() {
        ApplicationStateMonitor.getInstance().activityStopped();
        super.onStop();
        m12713a().onStop();
    }

    protected void onResume() {
        super.onResume();
        m12713a().onResume();
    }

    public Resources getResources() {
        if (Leanplum.isTestModeEnabled()) {
            return super.getResources();
        }
        return m12713a().getLeanplumResources(super.getResources());
    }

    public void setContentView(int i) {
        if (Leanplum.isTestModeEnabled()) {
            super.setContentView(i);
        }
        m12713a().setContentView(i);
    }
}
