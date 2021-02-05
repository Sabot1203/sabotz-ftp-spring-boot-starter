package com.sabotz.utils;

import com.sabotz.ftppool.FTPClientPool;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * FTP工具类
 * @author sabot
 * @date 2021/2/4
 */
public class FTPUtils {


    private static FTPClientPool ftpClientPool;


    public FTPUtils(FTPClientPool ftpClientPool){

        FTPUtils.ftpClientPool = ftpClientPool;
    }


    /**
     * 上传文件
     */
    public  String upload(InputStream inputStream, String originName, String saveDir) {

        StringBuilder url = new StringBuilder();
        FTPClient ftp = null;
        try {
            ftp = ftpClientPool.getClient();
            createDir(ftp, saveDir);
            url.append(saveDir + "/");

            originName= System.currentTimeMillis()+originName.substring(originName.lastIndexOf('.'));
            url.append(originName);
            ftp.storeFile(originName, inputStream);

        } catch (IOException e) {
            throw new RuntimeException("文件上传失败");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ftpClientPool.returnClient(ftp);
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return url.toString();
    }

    /**
     * 下载到客户端
     */
    public boolean  download(String filename, HttpServletResponse response) {

        FTPClient ftpClient = null;
        InputStream in=null;
        try {
            ftpClient = ftpClientPool.getClient();

            String[] dirs = filename.split("/");
            int n = dirs.length;
            for (int i = 0; i < n - 1; ++i) {
                if (dirs[i] == null || dirs[i].equals("")) {
                    continue;
                }
                if (!ftpClient.changeWorkingDirectory(dirs[i])) {
                    return false;
                }
            }
            FTPFile[] ftpFiles = ftpClient.listFiles(dirs[n-1]);
            if (ftpFiles == null || ftpFiles.length != 1) {
                return false;
            }

            // ftp文件获取文件
            in = ftpClient.retrieveFileStream(dirs[n-1]);
            ServletOutputStream out = null;
            response.setHeader("Content-disposition", "attachment;filename=" + dirs[n-1]);
            response.setContentType("application/octet-stream;charset=UTF-8");
            out = response.getOutputStream();
            // 读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
            response.flushBuffer();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ftpClientPool.returnClient(ftpClient);
        }
        return true;
    }



    /**
     * 下载到服务器本地文件
     */
    public boolean  download(String filename, String localPath) {



        FTPClient ftpClient = null;
        InputStream in=null;
        try {
            ftpClient = ftpClientPool.getClient();

            String[] dirs = filename.split("/");
            int n = dirs.length;
            for (int i = 0; i < n - 1; ++i) {
                if (dirs[i] == null || dirs[i].equals("")) {
                    continue;
                }
                if (!ftpClient.changeWorkingDirectory(dirs[i])) {
                    return false;
                }
            }
            FTPFile[] ftpFiles = ftpClient.listFiles(dirs[n-1]);
            if (ftpFiles == null || ftpFiles.length != 1) {
                return false;
            }

            // ftp文件获取文件
            in = ftpClient.retrieveFileStream(dirs[n-1]);
            byte[] bytes = new byte[1024];
            FileOutputStream downloadFile = new FileOutputStream(localPath +"\\"+dirs[n-1]);
            int index;
            while ((index = in.read(bytes)) != -1) {
                downloadFile.write(bytes, 0, index);
                downloadFile.flush();
            }
            downloadFile.close();
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ftpClientPool.returnClient(ftpClient);
        }
        return true;
    }


    /**
     * 创建文件夹
     */
    private void createDir(FTPClient client, String path) throws IOException {
        String[] dirs = path.split("/");
        for (String dir : dirs) {
            if (dir == null || dir.equals("")) {
                continue;
            }
            if (!client.changeWorkingDirectory(dir)) {
                client.makeDirectory(dir);
            }
            client.changeWorkingDirectory(dir);
        }
    }

    /**
     * 获取客户端
     */
    public FTPClient fetFTPClient(){
        try {
            return ftpClientPool.getClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将客户端返回池中
     */
    public void returnFTPClient(FTPClient ftpClient){
        ftpClientPool.returnClient(ftpClient);
    }

}
