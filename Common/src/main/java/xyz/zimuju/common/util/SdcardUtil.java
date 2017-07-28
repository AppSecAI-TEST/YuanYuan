package xyz.zimuju.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SdcardUtil {
    public static String USERICONNAME = System.currentTimeMillis() + ".jpg";// 图片名


    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡路径
     * <p>先用shell，shell失败再普通方法获取，一般是/storage/emulated/0/</p>
     *
     * @return SD卡路径
     */
    public static String getSDCardPath() {
        if (!isSDCardEnable()) return "sdcard unable!";
        String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();
        BufferedReader bufferedReader = null;
        try {
            Process p = run.exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream())));
            String lineStr;
            while ((lineStr = bufferedReader.readLine()) != null) {
                if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray.length >= 5) {
                        return strArray[1].replace("/.android_secure", "") + File.separator;
                    }
                }
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    return " 命令执行失败";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(bufferedReader);
        }
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    /**
     * 获取SD卡data路径
     *
     * @return SD卡data路径
     */
    public static String getDataPath() {
        if (!isSDCardEnable()) return "sdcard unable!";
        return Environment.getExternalStorageDirectory().getPath() + File.separator + "data" + File.separator;
    }

    /**
     * 获取SD卡剩余空间
     *
     * @return SD卡剩余空间
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getFreeSpace() {
        if (!isSDCardEnable()) return "sdcard unable!";
        StatFs stat = new StatFs(getSDCardPath());
        long blockSize, availableBlocks;
        availableBlocks = stat.getAvailableBlocksLong();
        blockSize = stat.getBlockSizeLong();
        return ConvertUtil.byte2FitSize(availableBlocks * blockSize);
    }

    /**
     * 获取SD卡信息
     *
     * @return SDCardInfo
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getSDCardInfo() {
        SDCardInfo sd = new SDCardInfo();
        if (!isSDCardEnable()) return "sdcard unable!";
        sd.isExist = true;
        StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
        sd.totalBlocks = sf.getBlockCountLong();
        sd.blockByteSize = sf.getBlockSizeLong();
        sd.availableBlocks = sf.getAvailableBlocksLong();
        sd.availableBytes = sf.getAvailableBytes();
        sd.freeBlocks = sf.getFreeBlocksLong();
        sd.freeBytes = sf.getFreeBytes();
        sd.totalBytes = sf.getTotalBytes();
        return sd.toString();
    }

    /**
     * 判断sdcard是否存在
     *
     * @return
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        //getVolumeState出现异常了就返回null，所以state在异常时候是null值
        return Environment.MEDIA_MOUNTED.equals(state);
    }


//	public static String userIconPath(Context context) {
//		boolean sdcard = hasSdcard();
//		String rootPath = getRootPath(sdcard, context) + File.separator + Constants.WAKEY_CACHE_IMG;
//		File file = getFileDir(rootPath);
//		LogUtils.i(TAG, " userIconPath : dir = " + file.getPath());
//		return file.getPath() + File.separator + USERICONNAME;
//	}

    /**
     * @param bitmap
     */
    public static boolean saveBitmapToSdcard(Bitmap bitmap, String path) {
        boolean sdcard = hasSdcard();
        if (sdcard) {
            File f = new File(path);
            try {
                f.createNewFile();
                FileOutputStream fOut = null;
                fOut = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);

                fOut.flush();
                fOut.close();

                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }

//	private static String saveApkToSdcard(String path) throws IOException {
//		boolean sdcard = hasSdcard();
//		String fileName = path.substring(path.lastIndexOf("/"));
//		if (sdcard) {
//			File file = new File(getSdcardRootPath(), fileName);
//			FileOutputStream fos = null;
//			InputStream ins = null;
//			
//			URL url = new URL(path);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			ins = conn.getInputStream();
//			fos = new FileOutputStream(file);
//			int length;
//			byte[] buffer = new byte[1024];
//			while ((length = ins.read(buffer)) != -1) {
//				fos.write(buffer, 0, length);
//			}
//			
//			if (fos != null) {
//				fos.close();
//			}
//			if (ins != null) {
//				ins.close();
//			}
//			return file.getAbsolutePath();	
//			
//		}
//		return null;
//	}

    public static boolean saveBitmapToSdcard(Context context, Bitmap bitmap, String path, String fileName, boolean isBroadcast) {
        boolean sdcard = hasSdcard();
        if (sdcard) {
            // File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), path);
            File appDir = new File(path);
            if (!appDir.exists()) {
                appDir.mkdir();
            }

            File file = new File(appDir, fileName);
            try {
                FileOutputStream fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.flush();
                fOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (isBroadcast) {
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
                }
            }

            if (!bitmap.isRecycled()) {
                System.gc(); // 通知系统回收
            }
            return true;
        }
        return false;
    }

    public static boolean saveBitmapToSdcardPNG(Context context, Bitmap bitmap, String path, String fileName) {
        boolean sdcard = hasSdcard();
        if (sdcard) {
            File appDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), path);
            if (!appDir.exists()) {
                appDir.mkdir();
            }

            File file = new File(appDir, fileName);
            try {
                FileOutputStream fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
            }

            if (!bitmap.isRecycled()) {
                System.gc(); // 通知系统回收
            }
            return true;
        }
        return false;
    }

    public static Bitmap getBitmapFromSdcard(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            return bitmap;
        } catch (OutOfMemoryError e) {
            System.gc();
        } catch (Throwable e) {

        }
        return null;
    }

    /**
     * 获取sdcard目录
     *
     * @return root path
     */
    private static String getSdcardRootPath() {
        File dir = Environment.getExternalStorageDirectory();
        return dir.getAbsolutePath();
    }

    /**
     * 根据文件夹名字创建文件夹
     * @param hasSdcard
     * @param dirName
     */
//	private static boolean mkdir(boolean hasSdcard, String parentPath,String folderName) {
//		boolean b = false;
//		if (hasSdcard) {
//			File dir = new File(parentPath + File.separator + folderName);
//			b = dir.mkdir();
//		}
//		return b;
//	}

//	/**
//	 * 在sdcard或者内存中创建工作区间
//	 * @param context
//	 */
//	public static void mkTclDir(Context context) {
//		boolean hasSdcard = hasSdcard();
//		String rootPath = getRootPath(hasSdcard, context);
//		getFileDir(rootPath + File.separator + Constants.WAKEY_CACHE_IMG);
//	}

    /**
     * 获取内存目录
     *
     * @return root path
     */
    private static String getCacheRootPath(Context context) {
        File cacheDir = context.getCacheDir();
        return cacheDir.getPath();
    }

    /**
     * 获取sdcard中闹钟的保存目录
     * @return root path
     */
//	public static String getClockRootPath() {
////		String clockdir = getSdcardRootPath() + File.separator + CLOCK_ROOT_PATH;
////		File file = new File(clockdir);
////		if ( !file.exists() ) {
////			file.mkdirs();
////		}
////		LogUtils.i(TAG, " getClockRootPath : clockdir = " + clockdir );
////		return clockdir;
//		String clockdir = getSdcardRootPath() + File.separator;
//		LogUtils.i(TAG, " getClockRootPath : clockdir = " + clockdir );
//		return clockdir;
//	}

//	public static String getFilePath( String path ) {
//		return getFileDir(path).getPath();
//	}
//
//	public static File getFileDir(String path) {
//		File file = new File(path);
//		if (!file.exists()) {
//			file.mkdirs();
//		}
//		LogUtils.i(TAG, " getFileDir : dir = " + file.getPath());
//		return file;
//	}

    /**
     * 得到保存的根目录
     *
     * @param hasSdcard
     * @return
     */
    public static String getRootPath(Context context) {
        boolean hasSdcard = hasSdcard();
        String rootPath = null;
        if (hasSdcard) {
            rootPath = getSdcardRootPath();
        } else {
            rootPath = getCacheRootPath(context);
        }
        return rootPath;
    }

//	public static String getCachePath() {
//		String path = "";
//		if (SdcardUtil.hasSdcard()) {
//			String dirPath = SdcardUtil.getClockRootPath() + Constants.WAKEY_CACHE;
//			path = SdcardUtil.getFilePath(dirPath);
//		}
//		return path;
//	}

    /**
     * 1.4.5版本以后整理闹钟sd卡文件夹，删除之前混乱的sd卡文件夹
     */
//	public static void deleteBeforeFile() {
//		LogUtils.i(TAG, " deleteBeforeFile " );
//		String dirpath = getSdcardRootPath();
//		File wakeycache2 = new File(dirpath, Constants.WAKEY_CACHE2);
//		deleteFile(wakeycache2);
//		File alarmHead = new File(dirpath, Constants.ALARM_HEAD);
//		deleteFile(alarmHead);
//		File headImg = new File(dirpath, Constants.HEAD_IMG);
//		deleteFile(headImg);
//	}
//
//	public static void deleteFile(File file) {
//		if ( file.exists() ) {
//			if ( file.isFile() ) {
//				file.delete();
//				return;
//			}
//			if ( file.isDirectory() ) {
//				File filelist[] = file.listFiles();
//				if ( filelist == null || filelist.length == 0 ) {
//					file.delete();
//					return;
//				}
//				for ( File f : filelist ) {
//					deleteFile(f);
//				}
//				file.delete();
//			}
//		}
//	}
    public static long getAvailaleSize() {
        File file = Environment.getExternalStorageDirectory(); //取得sdcard文件路径
        String path = file.getPath();
        if (StringUtils.isEmpty(path)) {
            return 0;
        }
        StatFs stat = new StatFs(path);
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
        //(availableBlocks * blockSize)/1024      KIB 单位
        //(availableBlocks * blockSize)/1024 /1024  MIB单位
    }

    public static long getAllSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getBlockCount();
        return availableBlocks * blockSize;
    }

    public static class SDCardInfo {
        boolean isExist;
        long totalBlocks;
        long freeBlocks;
        long availableBlocks;
        long blockByteSize;
        long totalBytes;
        long freeBytes;
        long availableBytes;

        @Override
        public String toString() {
            return "isExist=" + isExist +
                    "\ntotalBlocks=" + totalBlocks +
                    "\nfreeBlocks=" + freeBlocks +
                    "\navailableBlocks=" + availableBlocks +
                    "\nblockByteSize=" + blockByteSize +
                    "\ntotalBytes=" + totalBytes +
                    "\nfreeBytes=" + freeBytes +
                    "\navailableBytes=" + availableBytes;
        }
    }
}
