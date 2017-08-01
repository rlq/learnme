package com.lq.ren.many.calendar.package1212;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

/*
import org.apache.http.protocol.ResponseConnControl;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
*/
import com.he.learnme.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
/**
 * Author lqren on 16/12/14.
 */

public class UpdateManager {

    private ProgressBar mProgressBar;
    private Dialog mDownloadDialog;

    private String mSavePath;
    private int mProgress;

    private boolean mIsCancel = false;

    private static final int DOWNLOADING = 1;
    private static final int DOWNLOAD_FINISH = 2;

    private static final String PATH = "https://github.com/rlq/image/apk";

    private String mVersion_code;
    private String mVersion_name = "learnme.apk";
    private String mVersion_desc;
    private String mVersion_path = "https://github.com/rlq/image/tree/master/apk/learnme.apk";

    private Context mContext;

    public UpdateManager(Context context) {
        mContext = context;
    }

    private Handler mGetVersionHandler = new Handler(){
        public void handleMessage(Message msg) {
            JSONObject jsonObject = (JSONObject) msg.obj;
            System.out.println(jsonObject.toString());
            try {
                mVersion_code = jsonObject.getString("version_code");
                mVersion_name = jsonObject.getString("version_name");
                mVersion_desc = jsonObject.getString("version_desc");
                mVersion_path = jsonObject.getString("version_path");

                if (isUpdate()){
                    Toast.makeText(mContext, "需要更新", Toast.LENGTH_SHORT).show();
                    // 显示更新对话框
                    showNoticeDialog();
                } else{
                    Toast.makeText(mContext, "不需要更新", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        };
    };


    /*
     * 检查是否需要更新
     */
    public void checkUpdate() {
        /*RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest request = new JsonObjectRequest(PATH, null, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Message msg = Message.obtain();
                msg.obj = jsonObject;
                mGetVersionHandler.sendMessage(msg);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                System.out.println(arg0.toString());
            }
        });
        requestQueue.add(request);*/
    }

    /*
     * 与本地apk相比 判断是否需要更新
     */
    protected boolean isUpdate() {
        int serverVersion = Integer.parseInt(mVersion_code);
        int localVersion = 1;

        try {
            localVersion = mContext.getPackageManager().getPackageInfo("com.he.learnme", 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        if (serverVersion > localVersion)
            return true;
        else
            return false;
    }

    /*
     * 更新对话框
     */
    public void showNoticeDialog() {
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("提示ʾ");
        String message = "软件有更新，要下载安装吗？ \n" + mVersion_desc;
        builder.setMessage(message);

        builder.setPositiveButton("更新", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
            }
        });

        builder.setNegativeButton("下次再说", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 隐藏
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    /*
     * 下载对话框
     */
    protected void showDownloadDialog() {
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("下载中。。。");
        View view = LayoutInflater.from(mContext).inflate(R.layout.down_install_progress1214, null);
        mProgressBar = (ProgressBar) view.findViewById(R.id.id_progress);
        builder.setView(view);

        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mIsCancel = true;
            }
        });

        mDownloadDialog = builder.create();
        mDownloadDialog.show();

        // 下载安装包
        downloadAPK();
    }

    /*
     * 开启新线程下载apk包
     */
    private void downloadAPK() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        String sdPath = Environment.getExternalStorageDirectory() + "/";
                        mSavePath = sdPath + "Download/"; //下载apk保存路径
                        Log.d("HEHE","down path: " + mSavePath);

                        File apkFile = new File(mSavePath + mVersion_name);

//                        if (!apkFile.exists()) {
//                            Log.d("HEHE","!dir.exists()");
//                            apkFile.mkdirs();
//                        }
//                        Log.d("HEHE","dir.exists()");

                        // http请求 下载文件
                        HttpURLConnection conn = (HttpURLConnection) new URL(mVersion_path).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        int length = conn.getContentLength();
                        Log.d("HEHE","conn.connect() :" + length );

                        Log.d("HEHE","apkFile :" + apkFile.getName() );
                        FileOutputStream fos = new FileOutputStream(apkFile);
                        Log.d("HEHE","apkFile fos :" + fos);

                        int count = 0;//下载到的字节数
                        byte[] buffer = new byte[1024];
                        while (!mIsCancel){
                            Log.d("HEHE","mIsCancel" );
                            int numread = is.read(buffer);//本次从缓存中所读取的字节数
                            count += numread;
                            // 计算进度条的位置
                            mProgress = (int) (((float)count/length) * 100);
                            // 更新进度条，子线程不可以更新UI，使用handler
                            mUpdateProgressHandler.sendEmptyMessage(DOWNLOADING);//只发送一个status，不需要发送message
                            Log.d("HEHE","mProgress : " + mProgress);

                            // 下载完成
                            if (numread < 0){
                                mUpdateProgressHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                                Log.d("HEHE","numread < 0");
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                }catch(Exception e){
//                    e.printStackTrace();
                    Log.e("HEHE", "File :", e);
                }
            }
        }).start();
    }

    private Handler mUpdateProgressHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DOWNLOADING:
                    // 下载中
                    Log.d("HEHE","handleMessage :" + mProgress);
                    mProgressBar.setProgress(mProgress);
                    break;
                case DOWNLOAD_FINISH:
                    //下载完成，安装apk
                    mDownloadDialog.dismiss();
                    // 安装apk
                    installAPK();
            }
        };
    };

    /*
     * 包管理器 安装apk
     */
    protected void installAPK() {
        File apkFile = new File(mSavePath + mVersion_name);
        if (!apkFile.exists())
            return;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file://" + apkFile.toString());
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

}

