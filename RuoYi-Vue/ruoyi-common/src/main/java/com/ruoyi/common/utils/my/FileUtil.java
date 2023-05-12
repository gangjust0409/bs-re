package com.ruoyi.common.utils.my;

import java.io.*;

/**
 * 操作文件工具类
 */
public class FileUtil {

    private static Log log = Log.getLogger(FileUtil.class);

    /**
     * 用于文件复制
     * @param sourceFile file 封装的图片的位置
     * @param targetFile file 封装的保存的位置
     */
    public static void transformTo(File sourceFile, File targetFile) {
        //创建输出文件流  日志
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            //读取图片的流
            fileInputStream = new FileInputStream(sourceFile);
            //定义写入的流
            fileOutputStream = new FileOutputStream(targetFile);
            //定义一个字节数组  每次以 1 kb读取
            byte[] fileBytes = new byte[1024];
            //获取每次读取的长度
            int len;
            while ((len = fileInputStream.read(fileBytes)) != -1) {
                fileOutputStream.write(fileBytes, 0, len);
            }
            log.info("文件复制成功！");
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }

    }

    /**
     * 文件复制
     * @param inputStream
     * @param targetFile
     */
    public static void transformTo(InputStream inputStream, File targetFile) {
        //创建输出文件流  日志
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            //读取图片的流
            fileInputStream = (FileInputStream) inputStream;
            //定义写入的流
            fileOutputStream = new FileOutputStream(targetFile);
            //定义一个字节数组  每次以 1 kb读取
            byte[] fileBytes = new byte[1024];
            //获取每次读取的长度
            int len;
            while ((len = fileInputStream.read(fileBytes)) != -1) {
                fileOutputStream.write(fileBytes, 0, len);
            }
            log.info("文件复制成功！");
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取文件的路径
     * @return
     */
    public static String getPrefix(String originName) {
        if(originName.lastIndexOf(".") != -1) {
            //String[] originNames = originName.split("\\.");
            String prefix = originName.substring(originName.lastIndexOf(".")+1);
            return prefix;
        }
        return null;
    }

}
