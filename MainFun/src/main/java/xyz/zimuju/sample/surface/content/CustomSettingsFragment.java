package xyz.zimuju.sample.surface.content;


import android.os.Bundle;

import xyz.zimuju.common.webview.WebSettings;
import xyz.zimuju.sample.surface.sample.CustomSettings;

public class CustomSettingsFragment extends AgentWebFragment {

    public static AgentWebFragment getInstance(Bundle bundle) {

        CustomSettingsFragment mCustomSettingsFragment = new CustomSettingsFragment();
        if (bundle != null)
            mCustomSettingsFragment.setArguments(bundle);

        return mCustomSettingsFragment;

    }


    @Override
    public WebSettings getSettings() {

        return (WebSettings) new CustomSettings();
    }
}
