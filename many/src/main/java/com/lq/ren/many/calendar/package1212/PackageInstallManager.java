package com.lq.ren.many.calendar.package1212;

/**
 * Author lqren on 16/12/12.
 */
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInstaller;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class PackageInstallManager {
    private static final String TAG = "PackageInstallManager";
    public static final String ACTION_INSTALL_COMPLETE = "com.mobvoi.ticwear.packagemanager.INSTALL_PACKAGE";
    private static PackageInstallManager sInstance;
    private PackageInstaller mPackageInstaller;
    private Context mContext;


    private PackageInstallManager(Context context) {
        this.mContext = context;
        mPackageInstaller = mContext.getPackageManager().getPackageInstaller();
    }

    public static PackageInstallManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PackageInstallManager(context);
        }
        return sInstance;
    }

    public boolean installPackage(File apkFile, String packageName) {
        Log.d(TAG, "installPackage : %s" + packageName);
        PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(
                PackageInstaller.SessionParams.MODE_FULL_INSTALL);
        params.setReferrerUri(Uri.fromFile(apkFile));
        params.setAppPackageName(packageName);

        int sessionId = 0;
        try {
            sessionId = mPackageInstaller.createSession(params);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return startInstall(mContext, apkFile, packageName, sessionId);
    }

    private boolean startInstall(Context context, File apkFile, String packageName,
                                 int sessionId) {
        OutputStream out = null;
        InputStream in = null;
        try {
            // setup session
            PackageInstaller.Session session = mPackageInstaller.openSession(sessionId);
            out = session.openWrite(packageName, 0, -1);
            in = new FileInputStream(apkFile);
            byte[] buffer = new byte[65536];
            int c;
            while ((c = in.read(buffer)) != -1) {
                out.write(buffer, 0, c);
            }
            session.fsync(out);
            session.commit(createInstallIntentSender(context, sessionId, packageName));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public List<PackageInstaller.SessionInfo> getAllUnActiveSession(Context context) {
        PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
        List<PackageInstaller.SessionInfo> allSessions = packageInstaller.getAllSessions();
        if (allSessions == null || allSessions.size() == 0) {
            return null;
        } else {
            for (int i = 0; i < allSessions.size(); i++) {
                if (allSessions.get(i).isActive()) {
                    allSessions.remove(i);
                    --i;
                }
            }
        }
        return allSessions;
    }

    /**
     * install the apks download success but not installed
     */
    public void installAPKIfNecessary() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<PackageInstaller.SessionInfo> allUnActiveSession = getAllUnActiveSession(mContext);
                DownloadManager downloadManager
                        = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Query query = new DownloadManager.Query();
                Cursor cursor = downloadManager.query(query);
                if (cursor != null) {
                    try {
                        while (cursor.moveToNext()) {
                            int downloadStatus = cursor.getInt(
                                    cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));

                            if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL) {
                                String downloadPackageName = cursor.getString(
                                        cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));

                                PackageInstaller.SessionInfo sessionInfo =
                                        getUnInstallInfoByPkg(allUnActiveSession, downloadPackageName);
                                if (sessionInfo == null || !sessionInfo.isActive()) {
                                    String downloadUri = cursor.getString(
                                            cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                                    installPackage(new File(Uri.parse(downloadUri).getPath()), downloadPackageName);
                                    Log.d(TAG, "installUnActivePackage : %s" + downloadPackageName);
                                }
                            }
                        }
                    } finally {
                        cursor.close();
                    }
                }
            }
        });
    }

    private PackageInstaller.SessionInfo getUnInstallInfoByPkg(
            List<PackageInstaller.SessionInfo> allSeesion, String packageName) {
        if (TextUtils.isEmpty(packageName) || allSeesion == null || allSeesion.size() == 0) {
            return null;
        }
        for (int i = 0; i < allSeesion.size(); i++) {
            if (packageName.equals(allSeesion.get(i).getInstallerPackageName())) {
                return allSeesion.get(i);
            }
        }
        return null;
    }

    private static IntentSender createInstallIntentSender(Context context, int sessionId, String pkgName) {
        Intent intent = new Intent(ACTION_INSTALL_COMPLETE);
        intent.putExtra(PackageInstaller.EXTRA_PACKAGE_NAME, pkgName);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, sessionId, intent, 0);
        return pendingIntent.getIntentSender();
    }
}