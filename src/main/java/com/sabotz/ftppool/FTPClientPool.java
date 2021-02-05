package com.sabotz.ftppool;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * FTPCLIENT连接池
 * @author sabot
 * @date 2021/2/4
 */
public class FTPClientPool {

    private static GenericObjectPool<FTPClient> ftpClientPool;

    public FTPClientPool(PoolProperties poolProperties,FTPProperties ftpProperties){

        // 初始化对象池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

        poolConfig.setBlockWhenExhausted(poolProperties.isBlockWhenExhausted());
        poolConfig.setMaxWaitMillis(poolProperties.getMaxWait());
        poolConfig.setMinIdle(poolProperties.getMinIdle());
        poolConfig.setMaxIdle(poolProperties.getMaxIdle());
        poolConfig.setMaxTotal(poolProperties.getMaxTotal());
        poolConfig.setTestOnBorrow(poolProperties.isTestOnBorrow());
        poolConfig.setTestOnReturn(poolProperties.isTestOnReturn());
        poolConfig.setTestOnCreate(poolProperties.isTestOnCreate());
        poolConfig.setTestWhileIdle(poolProperties.isTestWhileIdle());
        poolConfig.setLifo(poolProperties.isLifo());

        //ftp客户端配置
        FTPProperties ftpConfig = new FTPProperties();

        ftpConfig.setHost(ftpProperties.getHost());
        ftpConfig.setPort(ftpProperties.getPort());
        ftpConfig.setUsername(ftpProperties.getUsername());
        ftpConfig.setPassword(ftpProperties.getPassword());
        ftpConfig.setClientTimeout(ftpProperties.getClientTimeout());
        ftpConfig.setEncoding(ftpProperties.getEncoding());
        ftpConfig.setWorkingDirectory(ftpProperties.getWorkingDirectory());
        ftpConfig.setPassiveMode(ftpProperties.isPassiveMode());
        ftpConfig.setRenameUploaded(ftpProperties.isRenameUploaded());
        ftpConfig.setRetryTimes(ftpProperties.getRetryTimes());
        ftpConfig.setTransferFileType(ftpProperties.getTransferFileType());
        ftpConfig.setBufferSize(ftpProperties.getBufferSize());

        ftpClientPool = new GenericObjectPool<FTPClient>(new FTPClientFactory(ftpConfig), poolConfig);
    }


    public FTPClient getClient() throws Exception {

        return ftpClientPool.borrowObject();
    }

    public void returnClient(FTPClient ftpClient) {

        ftpClientPool.returnObject(ftpClient);

    }
}
