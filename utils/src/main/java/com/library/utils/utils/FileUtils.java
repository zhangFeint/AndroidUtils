package com.library.utils.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件辅助类
 */
public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();
    private static FileUtils fileUtils;

    // 消息类型
    public final int MSG_TEXT = 1;// 文本
    public final int MSG_IMAGE = 2;// 图片
    public final int MSG_AUDIO = 3;// 语音
    public final int AVATAR_USER = 4;// avatar
    public final int APK = 5;// apk升级目录
    public final int TEMP = 6;// 临时文件目录
    public final int HTML = 7;// 压缩文件夹
    public final int REQUEST = 10;
    public final int EMOTION_NUMBER_PAGE = 28;// 每页28个表情
    public final String ROOT_PATH = Environment.getExternalStorageDirectory() + "/com.agriculture/";// 根目录
    public final String IMAGE_FLODER = "/image/";// 图片文件夹
    public final String AUDIO_FOLDER = "/audio/";// 语音文件夹
    public final String AVATAR_FOLDER = ROOT_PATH + "avatar/";// 联系人头像文件夹
    public final String HTML_FOLDER = ROOT_PATH + "html/";// 网页文件夹
    public final String LOG_FOLDER = ROOT_PATH + "log/";// 日志文件夹
    public final String APK_PATH = ROOT_PATH + "apk/";// 系统安装包路径
    public final String TEMP_PATH = ROOT_PATH + "temp/";// 临时目录


    //文件路径


    /**
     * 单例模式
     */
    public static FileUtils getInstance() {
        if (fileUtils == null) {
            fileUtils = new FileUtils();
        }
        return fileUtils;
    }


    //********************************************************************文件保存路径处理***************************************************************************************

    /**
     * 设置保存路径公共目录的文件,type 文件保存的路径选择1-6
     *
     * @param activity
     * @param type
     * @return 1-3 内存存储路径 4-7外部存储路径
     */
    public static String getFilePath(Activity activity, int type) {
        String filePath = null;
        switch (type) {
            case 1:
                filePath = activity.getCacheDir().getAbsolutePath(); // /data/data/packagename/cache
                break;
            case 2:
                filePath = activity.getFilesDir().getAbsolutePath();  // /data/data/packagename/files
                break;
            case 3:
                filePath = Environment.getExternalStorageDirectory().getAbsolutePath();//图片位置 //  /storage/emulated/0
                break;
            case 4:
                filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath(); // /storage/emulated/0/DCIM
                break;
            case 5:
                filePath = activity.getExternalCacheDir().getAbsolutePath(); // 可以作为外部缓存的路径,卸载app时，会自动删除文件  /storage/emulated/0/Android/data/packagename/cache
                break;
            case 6:
                // 手机图片的公共目录
                filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();//手机图片的公共目录
                break;
            case 7:
                filePath = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath(); // /storage/emulated/0/Android/data/packagename/files/Pictures
                break;
            default:
                break;
        }
        Log.i(TAG, filePath + "");
        return filePath;
    }

    /**
     * @param filePath   保存文件的位置
     * @param subcatalog 子目录
     * @param fileName   文件名
     * @return
     */
    public File getSaveFile(String filePath, String subcatalog, String fileName) {
        File pFile = new File(filePath, subcatalog);//文件保存位置
        ifFileExist(pFile);
        return new File(pFile + File.separator + fileName);
    }

    /**
     * @param messageType 类型 int
     * @param toId        标识
     * @return
     */
    public String getFolder(int messageType, String toId) {
        String strFolder = null;
        switch (messageType) {
            case MSG_IMAGE:
                strFolder = ROOT_PATH + toId + IMAGE_FLODER;
                break;
            case MSG_AUDIO:
                strFolder = ROOT_PATH + toId + AUDIO_FOLDER;
                break;
            case AVATAR_USER:
                strFolder = AVATAR_FOLDER;
                break;
            case APK:
                strFolder = APK_PATH;
                break;
            case TEMP:
                strFolder = TEMP_PATH;
                break;
            case HTML:
                strFolder = HTML_FOLDER;
                break;
            default:
                break;
        }
        File f = new File(strFolder);
        try {
            if (!f.exists()) {
//                boolean is = f.mkdirs();
                System.out.println("fdb");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strFolder;
    }

    /**
     * Name文件名+时间==图片地址
     *
     * @param prefix 前缀
     * @param suffix 后缀  ".jpg"
     * @return
     */
    public String getFileName(String prefix, String suffix) {
        String result = String.valueOf(System.currentTimeMillis());
        if (!StringUtil.getInstance().isEmpty(prefix)) {
            result = prefix + result;
        }
        if (!StringUtil.getInstance().isEmpty(suffix)) {
            result = result + suffix;
        }
        return result;
    }
    /**
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    private static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    //********************************************************************文件处理***************************************************************************************

    /**
     * 判断源文件是否存在，不存在就创建文件
     */
    public void ifFileExist(String filePath) {
        File srcFile = new File(filePath);
        ifFileExist (srcFile);
    }
    public void ifFileExist(File File) {
        if (!File.exists()) {
            File.mkdirs();
        }
    }




    /**
     * 删除文件
     *
     * @param filePath
     * @return
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
            return true;// 成功
        }
        return false;
    }

    /**
     * 复制单个文件
     *
     * @param srcFileName 待复制的文件名,目标文件名
     * @param overlay     如果目标文件存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
        File srcFile = new File(srcFileName);
        // 判断源文件是否存在
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        // 判断目标文件是否存在
        File destFile = new File(destFileName);
        if (destFile.exists()) { // 如果目标文件存在并允许覆盖
            if (overlay) {
                new File(destFileName).delete();// 删除已经存在的目标文件，无论目标文件是目录还是单个文件
            }
        } else {
            // 如果目标文件所在目录不存在，则创建目录
            if (!destFile.getParentFile().exists()) {
                // 目标文件所在目录不存在
                if (!destFile.getParentFile().mkdirs()) {
                    // 复制文件失败：创建目标文件所在目录失败
                    return false;
                }
            }
        }

        // 复制文件
        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 复制整个目录的内容
     *
     * @param srcDirName  待复制目录的目录名
     * @param destDirName 目标目录名
     * @param overlay     如果目标目录存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */

    public boolean copyDirectory(String srcDirName, String destDirName, boolean overlay) {
        // 判断源目录是否存在
        File srcDir = new File(srcDirName);
        if (!srcDir.exists()) {
            return false;
        } else if (!srcDir.isDirectory()) {
            return false;
        }

        // 如果目标目录名不是以文件分隔符结尾，则加上文件分隔符
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        File destDir = new File(destDirName);
        // 如果目标文件夹存在
        if (destDir.exists()) {
            // 如果允许覆盖则删除已存在的目标目录
            if (overlay) {
                new File(destDirName).delete();
            } else {
                return false;
            }
        } else {
            // 创建目的目录
            System.out.println("目的目录不存在，准备创建。。。");
            if (!destDir.mkdirs()) {
                System.out.println("复制目录失败：创建目的目录失败！");
                return false;
            }
        }

        boolean flag = true;
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 复制文件
            if (files[i].isFile()) {
                flag = copyFile(files[i].getAbsolutePath(), destDirName
                        + files[i].getName(), overlay);
                if (!flag)
                    break;
            } else if (files[i].isDirectory()) {
                flag = copyDirectory(files[i].getAbsolutePath(), destDirName
                        + files[i].getName(), overlay);
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 下载文件
     *
     * @param file
     * @param is
     * @param total
     * @param listener
     */
    public  void downloadFile(final File file, InputStream is, long total, final OnDownloadListener listener) {
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            long sum = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                sum += len;
                int progress = (int) (sum * 1.0f / total * 100);
                listener.onDownloading(progress);// 下载中
            }
            fos.flush();
            listener.onDownloadSuccess(file); // 下载完成
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }
    }


    public interface OnDownloadListener {
        //················下载成功·················
        void onDownloadSuccess(File file);

        //················下载进度·················
        void onDownloading(int progress);

        //················下载失败·················
        void onDownloadFailed();
    }


    /**
     * 压缩图片 质量压缩法
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100 && options > 10) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 保存图片
     */
    public static boolean saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String imgToBase64(String imgPath) {
        ByteArrayOutputStream out = null;
        try {
            Bitmap bitmap = null;
            if (imgPath != null && imgPath.length() > 0) {
                bitmap = BitmapFactory.decodeFile(imgPath);
            }
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static String imgToBase64(Bitmap bitmap) {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static String getRealPathFromURI(Context mContext, Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = mContext.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    //****************************************专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使***************************************************

    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}
