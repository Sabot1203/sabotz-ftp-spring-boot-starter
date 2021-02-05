package com.sabotz.ftppool;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * FTP配置类
 * @author sabot
 * @date 2021/2/4
 */
@ConfigurationProperties(prefix = "sabotz-ftp.server")
public class FTPProperties {

    private String host;

    private int port = 21;

    private String username;

    private String password;

    private boolean passiveMode = true;

    private String encoding = "GBK";

    private int clientTimeout = 600;

    private int threadNum = 1;


    /**
     * 文件传送类型
     * 0=ASCII_FILE_TYPE（ASCII格式）
     * 1=EBCDIC_FILE_TYPE
     * 2=LOCAL_FILE_TYPE（二进制文件）
     */
    private int transferFileType = 2;

    private boolean renameUploaded = true;

    private int retryTimes = 1200;

    private int bufferSize = 1024;

    private String workingDirectory = "/var/ftp/ftptest";


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPassiveMode() {
        return passiveMode;
    }

    public void setPassiveMode(boolean passiveMode) {
        this.passiveMode = passiveMode;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getClientTimeout() {
        return clientTimeout;
    }

    public void setClientTimeout(int clientTimeout) {
        this.clientTimeout = clientTimeout;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public int getTransferFileType() {
        return transferFileType;
    }

    public void setTransferFileType(int transferFileType) {
        this.transferFileType = transferFileType;
    }

    public boolean isRenameUploaded() {
        return renameUploaded;
    }

    public void setRenameUploaded(boolean renameUploaded) {
        this.renameUploaded = renameUploaded;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }
}
