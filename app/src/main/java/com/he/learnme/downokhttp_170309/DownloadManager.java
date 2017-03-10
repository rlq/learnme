package com.he.learnme.downokhttp_170309;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author lqren on 17/3/9.
 */

public class DownloadManager {

    private static final AtomicReference<DownloadManager> INSTANCE = new AtomicReference<>();

    private Map<String, Call> downCalls;//download request
    private OkHttpClient client;
    private Context context;

    public static DownloadManager getInstance() {
        for (; ;) {
            DownloadManager cur = INSTANCE.get();
            if (cur != null) {
                return cur;
            }
            cur = new DownloadManager();
            if (INSTANCE.compareAndSet(null, cur)) {
                return cur;
            }
        }
    }

    private DownloadManager() {
        downCalls = new HashMap<>();
        client = new OkHttpClient().newBuilder().build();
    }

    private DownloadInfo createDownInfo(String url) {
        DownloadInfo info = new DownloadInfo(url);
        long contentLength = getContentLength(url);
        info.setTotal(contentLength);

        String fileName = url.substring(url.lastIndexOf("/"));
        info.setFileName(fileName);
        return info;
    }

    private DownloadInfo getRealFileName(DownloadInfo info) {
        String fileName = info.getFileName();
        long downloadLength = 0;
        long contentLength = info.getTotal();
        File file = new File(context.getFilesDir(), fileName);
        if (file.exists()) {
            downloadLength = file.length();//已经下载过了
        }
        //重新下载
        int i = 1;
        while (downloadLength >= contentLength) {
            int dotIndex = fileName.lastIndexOf(".");
            String fileNameOther;
            if (dotIndex == -1) {
                fileNameOther = fileName + "(" + i + ")";
            } else {
                fileNameOther = fileName.substring(0, dotIndex)
                         + "(" + i + ")" + fileName.substring(dotIndex);
            }
            File newFile = new File(context.getFilesDir(), fileNameOther);
            file = newFile;
            downloadLength = newFile.length();
            i++;
        }
        //设置改变过的文件名大小
        info.setProgress(downloadLength);
        info.setFileName(file.getName());
        return info;

    }

    public void download(String url, DownloadObserver downloadObserver) {
        Observable.just(url)
                .filter(s -> !downCalls.containsKey(s))//call的map已经存在，就不再下载
                .flatMap(s -> Observable.just(createDownInfo(s)))
                .map(this::getRealFileName)//检测本地文件，生成新的文件名
                .flatMap(downloadInfo -> Observable.create(new DownloadSubscribe(downloadInfo)))//下载
                .observeOn(AndroidSchedulers.mainThread())//主线程回调
                .subscribeOn(Schedulers.io())//在子线程中执行
                .subscribe(downloadObserver);//添加观察者
    }

    public void cancle(String url) {
        Call call = downCalls.get(url);
        if (call != null) {
            call.cancel();
        }
        downCalls.remove(url);
    }

    //下载长度
    private long getContentLength(String downloadurl) {
        Request request = new Request.Builder()
                .url(downloadurl)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                //response.close();
                return contentLength == 0 ? DownloadInfo.TOTAL_ERROR : contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DownloadInfo.TOTAL_ERROR;
    }


    private class DownloadSubscribe implements ObservableOnSubscribe<DownloadInfo> {

        private DownloadInfo downloadInfo;

        public DownloadSubscribe(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void subscribe(ObservableEmitter<DownloadInfo> e) throws Exception {
            String url = downloadInfo.getUrl();
            long downloadLength = downloadInfo.getProgress();
            long contentLength = downloadInfo.getTotal();
            e.onNext(downloadInfo);

            Request request = new Request.Builder()
                    .addHeader("RANGE", "bytes=" + downloadLength + "-" + contentLength)
                    .url(url)
                    .build();
            Call call = client.newCall(request);
            downCalls.put(url, call);
            Response response = call.execute();

            File file = new File(context.getFilesDir(), downloadInfo.getFileName());
            InputStream is = null;
            FileOutputStream fileOutPutStream = null;
            try {
                is = response.body().byteStream();
                fileOutPutStream = new FileOutputStream(file, true);
                byte[] buffer = new byte[2048];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fileOutPutStream.write(buffer, 0, len);
                    downloadLength += len;
                    downloadInfo.setProgress(downloadLength);
                    e.onNext(downloadInfo);
                }
                fileOutPutStream.flush();
                downCalls.remove(url);
            } finally {
                IOUtil.closeAll(is, fileOutPutStream);
            }

        }
    }


    public interface ObservableOnSubscribe<T> {
        void subscribe(ObservableEmitter<T> e) throws Exception;
    }
}
