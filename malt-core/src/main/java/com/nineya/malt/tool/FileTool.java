package com.nineya.malt.tool;

import com.nineya.malt.exception.MaltFileException;
import com.nineya.malt.internal.MIME;

import java.io.*;
import java.util.*;

/**
 * @author linsongwang
 * @date 2020/5/31 10:14
 * 文件操作工具
 */
public class FileTool {
    /**
     * 读取文件
     * @param path 文件路径
     * @return 文件内容
     */
    public static String readFile(String path){
        try {
            InputStream inputStream = new FileInputStream(path);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();
            return new String(bytes, MIME.UTF_8.getValue());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 写入文件
     * @param path 写入的路径
     * @param content 文件内容
     */
    public static void writeFile(String path, String content){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path),MIME.UTF_8.getValue()));;
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断文件或目录是否存在
     * @param path 文件路径
     * @return true：存在，false：不存在
     */
    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 判断路径是不是文件夹
     * @param path 路径
     * @return true 是文件夹，false不是
     */
    public static boolean isDirectory(String path){
        return new File(path).isDirectory();
    }

    /**
     * 判断路径是不是文件
     * @param path 路径
     * @return true 是文件，false不是
     */
    public static boolean isFile(String path){
        return new File(path).isFile();
    }

    /**
     * 取得文件后缀
     * @param path 文件路径
     * @return 返回后缀名
     */
    public static String nameSuffix(String path){
        int index = path.lastIndexOf(".");
        if (index==-1){
            return null;
        }
        return path.substring(index + 1);
    }

    /**
     * 传入路径，返回是否是绝对路径，是绝对路径返回true，反之
     * @param path 路径
     * @return
     */
    public static boolean isAbsolutePath(String path) {
        if (path.startsWith("/") || path.indexOf(":") > 0) {
            return true;
        }
        return false;
    }

    /**
     * 生成一个同级目录的文件
     * @param path 路径
     * @return 同级路径
     */
    public static String createPeerFile(String path){
        int index = path.lastIndexOf(".");
        if (index==-1){
            return null;
        }
        String suffix = path.substring(index + 1);
        path = path.substring(0, index);
        String url = path + "-malt."+suffix;
        // 该路径已经存在文件
        if (fileExists(url)){
            int num = 1;
            while (true){
                url = path + "-malt("+ (num++) +")."+suffix;
                if (!fileExists(url)){
                    break;
                }
            }
        }
        return url;
    }

    /**
     * 生成一个同级目录的目录
     * @param path 路径
     * @return 同级路径
     */
    public static String createPeerDirectory(String path){
        String url = path + "-malt";
        // 该路径已经存在文件
        if (fileExists(url)){
            int num = 1;
            while (true){
                url = path + "-malt("+ (num++) +")";
                if (!fileExists(url)){
                    break;
                }
            }
        }
        return url;
    }

    /**
     * 递归获取目录和子目录下的所有文件
     * @param path 文件路径
     * @return 返回文件列表
     */
    public static List<String> recursiveFiles(String path){
        Queue<File> directorys = new LinkedList<>();
        directorys.offer(new File(path));
        List<String> files = new ArrayList<>();
        while (!directorys.isEmpty()){
            File directory = directorys.poll();
            File[] fs = directory.listFiles();
            if (fs != null && fs.length != 0){
                for (File f : fs){
                    if (f.isDirectory()){   //文件夹
                        directorys.offer(f);
                    } else {
                        files.add(f.getAbsolutePath());
                    }
                }
            }
        }
        return files;
    }

    // 取得jar所在path
    public static String jarWholePath(){
        String jarWholePath = FileTool.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        try {
            jarWholePath = java.net.URLDecoder.decode(jarWholePath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.toString());
        }
        return new File(jarWholePath).getParentFile().getAbsolutePath();
    }

    public static void judgeMIME(String path) throws MaltFileException {
        if (!fileExists(path)){
            throw new MaltFileException("“"+path+"”文件不存在！");
        }
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path));
            int p = (inputStream.read()<<8) + inputStream.read();
            String code = null;

            switch (p) {
                case 0xefbb:
                    code = "UTF-8";
                    break;
                case 0xfffe:
                    code = "Unicode";
                    break;
                case 0xfeff:
                    code = "UTF-16BE";
                    break;
                default:
                    code = "GBK";
            }
            System.out.println("文件编码格式："+code);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
