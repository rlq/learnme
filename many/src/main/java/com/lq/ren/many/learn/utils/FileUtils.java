package com.lq.ren.many.learn.utils;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {
    private final static String TAG = "FileUtils";

    /**
     * 获取除扩展名以外的部分
     * 
     * @param fileName
     * @return
     */
    public static String getFileNameWithoutSuffix(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }
        int lastIndex = fileName.lastIndexOf(".");
        String fileNameWithoutSuffix = "";
        if (lastIndex != -1) {
            fileNameWithoutSuffix = fileName.substring(0, lastIndex);
        }
        return fileNameWithoutSuffix;
    }

    /**
     * 获取文件扩展名
     * 
     * @param path
     * @return
     */
    public static String getFileExtension(String path) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        int index = path.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        String extension = path.substring(index + 1, path.length());
        if (TextUtils.isEmpty(extension)) {
            return "";
        }
        return extension;
    }

    /**
     * 获取文件目录
     */
    public static String getFilePath(String fullPathName) {
        int lastIndex = fullPathName.lastIndexOf(File.separator);
        String path = "";
        if (lastIndex != -1) {
            path = fullPathName.substring(0, lastIndex);
        }
        return path;
    }

    /**
     * 获取不带路径和后缀的文件名
     * 
     * @param path
     * @return
     */
    public static String getFileNameByPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        int separatorIndex = path.lastIndexOf(File.separator);
        if (separatorIndex > 0 && separatorIndex != path.length() - 1) {
            String fullName = path.substring(separatorIndex + 1, path.length());
            String name = getFileNameWithoutSuffix(fullName);
            return name;
        }
        return path;
    }

    /**
     * 获取不带路径的文件名
     * 
     * @param path
     * @return
     */
    public static String getFullFileNameByPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }

        int separatorIndex = path.lastIndexOf(File.separator);

        if (separatorIndex > 0 && separatorIndex != path.length() - 1) {
            String fullName = path.substring(separatorIndex + 1, path.length());
            return fullName;
        }

        return path;
    }

    // 文件改名
    public static boolean fileMove(String from, String to, boolean overwrite) {
        return fileMove(from, to, overwrite, false);
    }

    public static boolean fileMove(String from, String to, boolean overwrite, boolean copy) {
        File fromFile = new File(from);

        if (!fromFile.exists()) {
            return false;
        }

        File toFile = new File(to);
        if (toFile.exists()) {
            if (overwrite) {
                toFile.delete();
            } else {
                return false;
            }
        }

        boolean ret = false;

        if (!copy) {
            ret = fromFile.renameTo(toFile);
        }

        if (!ret) {
            ret = fileCopy(fromFile, toFile);
            if (ret) {
                deleteFile(from);
            }
        }

        return ret;
    }

    // 过滤掉不可当文件名的字符
    static public String delInvalidFileNameStr(String title) {
        if (title != null && title.length() > 0) {
            String illegal = "[`\\\\~!@#\\$%\\^&\\*+=\\|\\{\\}:;\\,/\\.<>\\?·\\s\"]";
            Pattern pattern = Pattern.compile(illegal);
            Matcher matcher = pattern.matcher(title);
            return matcher.replaceAll("_").trim();
        }

        return title;
    }

    /**
     * 创建空文件
     * 
     * @param path
     *            待创建的文件路径
     * @param size
     *            空文件大小
     * @return 创建是否成功
     * @throws IOException
     */
    public static boolean createEmptyFile(String path, long size) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(path, "rw");
            try {
                raf.setLength(size);
            } finally {
                raf.close();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 判断文件是否存在
     * 
     * @param path
     *            文件路径
     * @return 是否存在
     */
    public static boolean isExist(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File file = new File(path);
        return file.exists();
    }

    // 拷贝文件
    public static boolean fileCopy(String from_file, String to_file) {
        return fileCopy(new File(from_file), new File(to_file));
    }

    public static boolean fileCopy(File from_file, File to_file) {
        if (!from_file.exists()) {
            return false;
        }

        if (to_file.exists()) {
            to_file.delete();
        }
        boolean success = true;
        FileInputStream from = null;
        FileOutputStream to = null;
        byte[] buffer;
        try {
            buffer = new byte[1024];
        } catch (OutOfMemoryError oom) {
            Log.e(TAG, getStackTraceString(oom));
            return false;
        }
        try {
            from = new FileInputStream(from_file);
            to = new FileOutputStream(to_file); // Create output stream
            int bytes_read;

            while ((bytes_read = from.read(buffer)) != -1) {
                // Read until EOF
                to.write(buffer, 0, bytes_read);
            }
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        } finally {
            buffer = null;
            if (from != null) {
                try {
                    from.close();
                } catch (IOException e) {
                }
                from = null;
            }
            if (to != null) {
                try {
                    to.close();
                } catch (IOException e) {
                }
                to = null;
            }
        }

        if (!success) {
            to_file.delete();
        }

        return success;
    }

    /**
     * 删除文件或者目录
     * 
     * @param path
     *            指定路径的文件或目录
     * @return 返回操作结果
     */
    public static boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }
        File file = new File(path);
        if (!file.exists())
            return true;

        if (file.isDirectory()) {
            String[] subPaths = file.list();
            for (String p : subPaths) {
                if (!deleteFile(path + File.separator + p)) {
                    return false;
                }
            }
        }

        return file.delete();
    }

    public static boolean deleteFilesExcept(String path, String fileName) {
        File[] files = getFiles(path);
        if (files != null) {
            for (File f : files) {
                if (!f.getAbsolutePath().endsWith(fileName)) {
                    f.delete();
                }
            }
        }
        return true;
    }

    /**
     * 创建目录，包括必要的父目录的创建，如果未创建
     * 
     * @param path
     *            待创建的目录路径
     * @return 返回操作结果
     */
    public static boolean mkdir(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return true;
        }

        return file.mkdirs();
    }

    @SuppressWarnings("deprecation")
    public static long getRomTotalSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = null;
        try {
            stat = new StatFs(path.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return blockSize * totalBlocks;
    }

    /**
     * 获得机身可用内存
     * 
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long getRomAvailableSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = null;
        try {
            stat = new StatFs(path.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        long availableSize = availableBlocks * blockSize;
        return availableSize - 5 * 1024 * 1024;// 预留5M的空间
    }

    public static boolean isExternalStorageWriterable() {
        String state = null;

        try {
            state = Environment.getExternalStorageState();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public static long getTotalExternalMemorySize() {
        String state = null;
        try {
            state = Environment.getExternalStorageState();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = null;
            try {
                stat = new StatFs(path.getPath());
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }

            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return blockSize * totalBlocks;
        }

        return 0;
    }

    /**
     * 检查当前sdcard剩余空间大小
     */
    @SuppressWarnings("deprecation")
    public static long getAvailableExternalMemorySize() {
        String state = null;

        try {
            state = Environment.getExternalStorageState();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = null;
            try {
                stat = new StatFs(path.getPath());
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }

            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            long availableSize = availableBlocks * blockSize;
            return availableSize - 5 * 1024 * 1024;// 预留5M的空间
        }

        return 0;
    }

    @SuppressWarnings("deprecation")
    public static long getAvailableStorage() {

        String storageDirectory = null;
        storageDirectory = Environment.getExternalStorageDirectory().toString();

        try {
            StatFs stat = new StatFs(storageDirectory);
            long avaliableSize = ((long) stat.getAvailableBlocks() * (long) stat.getBlockSize());
            return avaliableSize;
        } catch (RuntimeException ex) {
            return 0;
        }
    }

    /**
     * 检查当前sdcard剩余空间大小是否够用，32M以上返回true
     */
    public static boolean isExternalSpaceAvailable() {
        return getAvailableExternalMemorySize() > 32 * 1024 * 1024;
    }

    /**
     * 获取当前目录的文件夹列表
     */
    public static ArrayList<File> getDirs(String path) {
        ArrayList<File> fileList = new ArrayList<File>();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        fileList.add(f);
                    }
                }
            }
        }
        return fileList;
    }

    public static File[] getFiles(String path) {
        return getFiles(path, null);
    }

    /**
     * 获取当前目录的文件列表
     */
    public static File[] getFiles(String path, final String[] filters) {
        File file = new File(path);
        if (!file.isDirectory()) {
            return null;
        }

        FilenameFilter filter = null;
        if (filters != null && filters.length > 0) {
            filter = new FilenameFilter() {
                @Override
                public boolean accept(File directory, String filename) {
                    if (!TextUtils.isEmpty(filename)) {
                        for (String type : filters) {
                            if (filename.toLowerCase().endsWith(type)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            };
        }

        File[] fileList = file.listFiles(filter);
        return fileList;
    }

    /**
     * 获取当前目录的文件列表，用正则匹配
     */
    public static File[] getFilesByRegex(String path, final String regex, final String exceptRegex) {
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(regex)) {
            return null;
        }
        File[] fileList = null;
        File file = new File(path);
        if (file.isDirectory()) {
            fileList = file.listFiles(new FilenameFilter() {

                @Override
                public boolean accept(File directory, String filename) {
                    if (filename != null && !"".equals(filename)) {
                        try {
                            if (filename.matches(regex)) {
                                if (exceptRegex == null || exceptRegex.length() == 0) {
                                    return true;
                                } else {
                                    return !filename.matches(exceptRegex);
                                }
                            }
                        } catch (Exception e) {
                            Log.e(TAG, getStackTraceString(e));
                            return false;
                        }
                    }
                    return false;
                }
            });
        }

        return fileList;
    }

    public static long getFileSize(final String file) {
        if (TextUtils.isEmpty(file)) {
            return 0;
        }
        return new File(file).length();
    }

    // 传统的＊和？匹配
    public static File[] getFilesClassic(final String dir, final String pattern) {
        if (TextUtils.isEmpty(dir) || TextUtils.isEmpty(pattern)) {
            return null;
        }
        StringBuilder builder = new StringBuilder("^");
        int state = 0;
        for (int i = 0; i < pattern.length(); i++) {
            char word = pattern.charAt(i);
            if (state == 0) {
                if (word == '?') {
                    builder.append('.');
                } else if (word == '*') {
                    builder.append(".*");
                } else {
                    builder.append("\\Q");
                    builder.append(word);
                    state = 1;
                }
            } else {
                if (word == '?' || word == '*') {
                    builder.append("\\E");
                    state = 0;
                    if (word == '?') {
                        builder.append('.');
                    } else {
                        builder.append(".*");
                    }
                } else {
                    builder.append(word);
                }
            }
        }
        if (state == 1) {
            builder.append("\\E");
        }
        builder.append('$');
        ArrayList<File> list = null;
        try {
            Pattern p = Pattern.compile(builder.toString());
            list = filePattern(new File(dir), p);
        } catch (Exception e) {
            return null;
        }
        if (list == null || list.size() == 0) {
            return null;
        }
        File[] rtn = new File[list.size()];
        list.toArray(rtn);
        return rtn;
    }

    private static ArrayList<File> filePattern(File file, Pattern p) {
        if (file == null) {
            return null;
        } else if (file.isFile()) {
            Matcher fMatcher = p.matcher(file.getName());
            if (fMatcher.matches()) {
                ArrayList<File> list = new ArrayList<File>();
                list.add(file);
                return list;
            }
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                ArrayList<File> list = new ArrayList<File>();
                for (File f : files) {
                    if (p.matcher(f.getName()).matches()) {
                        list.add(f);
                    }
                }
                return list;
            }
        }
        return null;
    }

    // 李建衡：读文件。如果无法读出数据，就返回null
    public static String fileRead(String file) {
        if (TextUtils.isEmpty(file)) {
            return null;
        }

        byte[] buffer = fileRead(new File(file));

        if (buffer == null) {
            return null;
        }

        return new String(buffer);
    }

    public static String fileRead(String file, String charsetName) {
        if (TextUtils.isEmpty(file) || TextUtils.isEmpty(charsetName)) {
            return null;
        }

        byte[] buffer = fileRead(new File(file));

        if (buffer == null) {
            return null;
        }

        try {
            return new String(buffer, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] fileReadBytes(String file) {
        if (TextUtils.isEmpty(file)) {
            return null;
        }

        return fileRead(new File(file));
    }

    public static byte[] fileRead(File file) {
        byte[] buffer = null;

        if (!file.exists()) {
            return buffer;
        }

        FileInputStream fis;

        try {
            fis = new FileInputStream(file);
            try {
                int len = fis.available();
                buffer = new byte[len];
                fis.read(buffer);
            } finally {
                fis.close();
            }
        } catch (Throwable e) { // new byte有可能是OOM异常，要用Throwable跟IOException一起捕获
            e.printStackTrace();
            return null;
        }

        return buffer;
    }

    public static boolean fileWrite(String file, String data) {
        if (TextUtils.isEmpty(file) || data == null) {
            return false;
        }

        try {
            return fileWrite(new File(file), data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean fileWrite(File file, byte[] data) {
        FileOutputStream fos;

        try {
            file.createNewFile();
            fos = new FileOutputStream(file);

            try {
                fos.write(data);
            } finally {
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean createFile(File file) {
        try {
            if (file != null) {
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        return sw.toString();
    }
}
