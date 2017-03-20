package com.lq.ren.crazynotes.act;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.Toast;

import com.lq.ren.crazynotes.R;

import java.util.List;

/**
 * Author lqren on 17/3/13.
 * 保存一些选项参数
 * 1. 嵌入类，PreferenceActivity.Header 用来描述用户将要点击的标头列表项
 * 2. 重要方法，loadHeadersFromResource, onBuildHeaders
 * 3. 结合PreferFragment加载选项设置布局
 */

public class PreferAct extends PreferenceActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (hasHeaders()) {
            Button button = new Button(this);
            button.setText("set opration");
            setListFooter(button);
        }
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        super.loadHeadersFromResource(R.layout.layout_preference, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return true;
    }

    public static class Prefer1Fragment extends PreferenceFragment {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
        }
    }

    public static class Prefer2Fragment extends PreferenceFragment {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.displayprefs);
            String website = getArguments().getString("website");
            Toast.makeText(getActivity(), "the website is " + website, Toast.LENGTH_SHORT).show();
        }
    }
}
