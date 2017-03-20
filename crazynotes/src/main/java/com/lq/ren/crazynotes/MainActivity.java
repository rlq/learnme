package com.lq.ren.crazynotes;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.lq.ren.crazynotes.act.ExpandAct;
import com.lq.ren.crazynotes.act.PreferAct;

/**
 * 1.定义两个Activity的名称
 * 2.定义两个Activity对应的实现类,前者用于显示PerferenceActivity；后者用于显示ExpandableActivity
 * 3.装配ArrayAdapter适配器，将数据装配到对应的列表项视图中
 * 4.设置该窗口显示的列表所需的adapter
 * 5.重写Intent IntentForPosition(int position)方法：根据列表项返回的intent,用于启动不同的Activity
 */

public class MainActivity extends LauncherActivity {

    private Class<?>[] classNames = {PreferAct.class, ExpandAct.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] launcherNames = new String[]{
                getString(R.string.prefer),
                getString(R.string.expandable)
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1, launcherNames);
        setListAdapter(adapter);
    }

    @Override
    protected Intent intentForPosition(int position) {
        return new Intent(MainActivity.this, classNames[position]);
    }
}
