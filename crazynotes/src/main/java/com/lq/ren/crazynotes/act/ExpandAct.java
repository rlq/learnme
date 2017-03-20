package com.lq.ren.crazynotes.act;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lq.ren.crazynotes.R;

import java.io.File;
import java.io.IOException;

/**
 * Author lqren on 17/3/13.
 * ExpandableListView
 * Menu 相关设置 参考 这个文章还是挺全面
 * http://www.cnblogs.com/smyhvae/p/4133292.html
 */

public class ExpandAct extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private static final int CAMERA_REQUEST_CODE = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_expand);
        initView();
    }

    private void initView() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(getResources().getString(R.string.expand_center));
//        //setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowHomeEnabled(true);
//        }
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        /**之前用了一个带有两个参数的onCreate 导致这个没起作用*/
        findViewById(R.id.popMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popMenuShow(view);
            }
        });
        //为按钮绑定上下文菜单（注意不是绑定监听器） 长按
        registerForContextMenu(findViewById(R.id.popMenu));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expand_menu, menu);
        //通过代码的方式来添加Menu
        //添加菜单项（组ID，菜单项ID，排序，标题）
//        menu.add(0, START_ITEM, 100, "Start");
//        menu.add(0, OVER_ITEM, 200, "Over");
//         //添加子菜单
//        SubMenu sub1 = menu.addSubMenu("setting");
//        sub1.add(1, SET_ITEM1, 300, "声音设置");
//        sub1.add(1, SET_ITEM2, 400, "背景设置");
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu == null) {
            menu.findItem(R.id.open_prefer).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    //响应式点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_expand:
                finish();
                break;
            case R.id.open_prefer:
                startActivity(new Intent(ExpandAct.this, PreferAct.class));
                finish();
                break;
            case R.id.background:
            case R.id.sounds:
                Toast.makeText(this, "no work", Toast.LENGTH_SHORT).show();
                break;

        }
        //使用add时，点击使用switch(item.getItemId()){case START_ITEM: }
        //增加菜单项的快捷选项
        //item.setAlphabeticShortcut();
        //item.setNumericShortcut()
        return super.onOptionsItemSelected(item);
    }


    /** popMenu
     （1）通过PopupMenu的构造函数实例化一个PopupMenu对象，需要传递一个当前上下文对象以及绑定的View。
     （2）调用PopupMenu.setOnMenuItemClickListener()设置一个PopupMenu选项的选中事件。
     （3）使用MenuInflater.inflate()方法加载一个XML文件到PopupMenu.getMenu()中。
     （4）在需要的时候调用PopupMenu.show()方法显示。
     */
    private void popMenuShow(View v) {
        //创建弹出式菜单对象（最低版本11）
        PopupMenu popup = new PopupMenu(this, v);//第二个参数是绑定的那个view
        //获取菜单填充器
        MenuInflater inflater = popup.getMenuInflater();
        //填充菜单
        inflater.inflate(R.menu.pop_menu, popup.getMenu());
        //绑定菜单项的点击事件
        popup.setOnMenuItemClickListener(this);
        popup.show(); //这一行代码不要忘记了
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.copy:
                Toast.makeText(this, "copy", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    //Context  上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.start:
                Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
                break;
            case R.id.over:
                Toast.makeText(this, "over", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
