package com.baozi.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.Random;

/**
 * Created by baozi on 28/03/2018.
 */
public class ZKMaster {

    private ZkClient client;

    private static final String CONNECTION_STRING = "10.116.18.109:2181";

    public static final String NODE_PATH = "/master";

    private volatile boolean isMaster = false;

    public ZKMaster() {
        client = new ZkClient(CONNECTION_STRING, 5, Integer.MAX_VALUE);
    }

    public void takeMaster() {
        try {
            client.createEphemeral(NODE_PATH);
            isMaster = true;
        } catch (RuntimeException e) {
            System.out.println("强master节点失败");
        }
    }

    public void subscribeDataChanges() {
        client.subscribeDataChanges(NODE_PATH, dataListener);
    }


    public IZkDataListener dataListener = new IZkDataListener() {
        @Override
        public void handleDataChange(String dataPath, Object data) throws Exception {
        }

        @Override
        public void handleDataDeleted(String dataPath) throws Exception {
            takeMaster();
        }
    };


    public static void main(String[] args) throws Exception {
        ZKMaster zkClient = new ZKMaster();
        zkClient.subscribeDataChanges();
        zkClient.takeMaster();

        Random random = new Random();
        int i = random.nextInt(10);

        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //业务逻辑
            if (zkClient.isMaster) {
                System.out.println("实例" + i + " 为master节点: 执行业务代码");
            }
        }
    }

}
