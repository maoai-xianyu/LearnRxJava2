package com.mao.cn.learnRxJava2.utils.tools;

import android.os.Environment;
import android.util.Log;

import com.mao.cn.learnRxJava2.utils.tools.utils.FileUtils;
import com.mao.cn.learnRxJava2.utils.tools.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class FileU extends FileUtils {
    public FileU() {
    }

    /**
     * 删除文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] to = file.listFiles();
                if (to == null) {
                    return;
                }

                for (int i = 0; i < to.length; ++i) {
                    deleteFile(to[i]);
                }

                file.delete();
            } else {
                File var3 = new File(file.getAbsolutePath() + System.nanoTime());
                file.renameTo(var3);
                var3.delete();
            }

        }
    }

    /**
     * @param size
     * @return
     */
    public static String convertFileSize(long size) {
        long kb = 1024L;
        long mb = kb * 1024L;
        long gb = mb * 1024L;
        if (size >= gb) {
            return String.format("%.1f GB", new Object[]{Float.valueOf((float) size / (float) gb)});
        } else {
            float f;
            if (size >= mb) {
                f = (float) size / (float) mb;
                return String.format(f > 100.0F ? "%.0f MB" : "%.1f MB", new Object[]{Float.valueOf(f)});
            } else if (size >= kb) {
                f = (float) size / (float) kb;
                return String.format(f > 100.0F ? "%.0f KB" : "%.1f KB", new Object[]{Float.valueOf(f)});
            } else {
                return String.format("%d B", new Object[]{Long.valueOf(size)});
            }
        }
    }

    /**
     * 创建文件
     *
     * @param file
     */
    public static void createFile(File file) {
        if (!file.exists()) {
            try {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                file.createNewFile();
            } catch (IOException e) {
            }
        }

    }

    /**
     * 文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean isExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 目录下的文件 存在
     *
     * @param fileDir
     * @param fileName
     * @return
     */
    public static boolean isExist(String fileDir, String fileName) {
        File file = new File(fileDir, fileName);
        return file.exists();
    }

    /**
     * 创建文件夹下路径下文件
     *
     * @param fileDir
     * @param fileName
     * @return
     */
    public static boolean ifNotExistCreateFile(String fileDir, String fileName) {
        File file = new File(fileDir, fileName);
        if (!file.exists()) {
            try {
                if (file.getParent() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                return file.createNewFile();
            } catch (IOException var4) {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 路径
     *
     * @param filePath
     * @return
     */
    public static boolean ifNotExistCreateFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                if (file.getParent() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                return file.createNewFile();
            } catch (IOException var3) {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 创建文件
     *
     * @param file
     * @return
     */
    public static boolean ifNotExistCreateFile(File file) {
        if (!file.exists()) {
            try {
                if (file.getParent() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                return file.createNewFile();
            } catch (IOException var2) {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 穿件目录
     *
     * @param fileDir
     * @return
     */
    public static boolean ifNotExistCreateDir(String fileDir) {
        File file = new File(fileDir);
        return !file.exists() ? file.mkdirs() : true;
    }

    /**
     * 获取SD可写目录
     *
     * @return File
     */
    public static File getSdWritePath() {
        if (hasSDCard()) {
            return Environment.getExternalStorageDirectory();
        } else {
            File w = new File("/mnt/");
            if (w.exists()) {
                File[] f = w.listFiles();
                for (int i = 0, j = f.length; i < j; ++i) {
                    if (f[i].isDirectory() && f[i].canWrite()) {
                        return f[i];
                    }
                }
            }
        }
        return null;
    }

    /**
     * 是否有sd
     *
     * @return
     */
    public static boolean hasSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 删除文件和目录
     */
    public static boolean clearFiles(String workspaceRootPath) {
        try {
            File file = new File(workspaceRootPath);
            if (file.exists()) {
                deleteFile(file);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 缓冲文件路径
     *
     * @param filename
     * @return
     */
    public static String autoCreateDir(String filename) {
        String one = filename.substring(0, 2);
        String two = filename.substring(2, 3);

        String filePath = File.separator + one + File.separator + two + File.separator;
        File dataFile = new File(PathU.getInstance().getResourcePath() + filePath);
        if (!dataFile.exists()) {
            boolean mkdirs = dataFile.mkdirs();
        }
        return filePath + filename;
    }


    /**
     * 路径
     *
     * @param filename
     * @return
     */
    public static boolean hasSdfilesFile(String filename) {
        File file;
        try {
            file = new File(PathU.getInstance().getFilesPath() + filename);
        } catch (Exception e) {
            return false;
        }
        return file.exists();
    }


    /**
     * 文件写流
     */
    public static boolean writeStreamToFile(String filePath, InputStream in) {
        File file = null;
        FileOutputStream out = null;
        try {
            if (!ifNotExistCreateFile(filePath)) {
                return false;
            }
            file = new File(filePath);
            out = new FileOutputStream(file);
            IOUtils.copyLarge(in, out);
        } catch (Exception e) {
            return false;
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
        return true;
    }

    /**
     * @param fileName
     * @param data
     * @return
     */
    public static boolean writeDataToFile(String fileName, String data) {
        File file;
        try {
            file = new File(fileName);
            writeStringToFile(file, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean writeDataToFile(String filePath, InputStream is) {
        writeStreamToFile(filePath, is);
        return false;
    }

    /**
     * 读文件
     *
     * @param fileName
     * @return
     */
    public static String readDataFromFile(String fileName) {
        String content = "";
        try {
            content = FileU.readFileToString(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 文件重命名
     *
     * @param oldPath 原文件
     * @param newPath 重命名后的文件
     * @return
     */
    public static boolean renameFile(String oldPath, String newPath) {
        File file = new File(oldPath);
        return file.renameTo(new File(newPath));
    }

    /**
     * 获取指定文件大小
     *
     * @param fileStr
     * @return
     * @throws Exception
     */
    public static long getFileSize(String fileStr) {
        File file = new File(fileStr);
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
                file.createNewFile();
                Log.e("获取文件大小", "文件不存在!");
            }
        } catch (Exception e) {
            return 0;
        }

        return size;
    }

    /**
     * 更新文本
     */
    public static void updateFile(File fileName, String data) {
        if (!fileName.exists()) {
            try {
                fileName.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        OutputStream os = null;
        try {
            os = new FileOutputStream(fileName);
            os.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param file
     * @return 是否存在录音文件
     */
    public static boolean isExistAudioFile(File file) {
        return file.exists();
    }

    /**
     * 压缩单个文件
     */
    public static void zipFile(String filePath, String zipPath) {
        try {
            File file = new File(filePath);
            File zipFile = new File(zipPath);
            InputStream input = new FileInputStream(file);
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            zipOut.setLevel(1);
            zipOut.putNextEntry(new ZipEntry(file.getName()));
            int temp = 0;
            while ((temp = input.read()) != -1) {
                zipOut.write(temp);
            }
            input.close();
            zipOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
