package com.fqc.zk.heartbeats;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by fqc on 2016/4/30.
 */
public class AppServer {
    private static final String CONNECTION = "mini2:2181";
    private static final int CONNECTION_TIMEOUT = 30000;
    private static final String sGroup = "/sGroup";
    private static final String subNode = "/subNode7";
    private ZooKeeper zooKeeper;

    public static void main(String[] args) {
        AppServer appServer = new AppServer();
        ZooKeeper zk = appServer.getConnection();
        String path = appServer.createZnode(zk);
        appServer.getNodeData(path);
        appServer.handler();

    }

    private void getNodeData(String path) {
        try {
            byte[] data = this.zooKeeper.getData(path, false, null);
            System.out.println(new String(data));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * nothing to do ,just sleep
     */
    private void handler() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String createZnode(ZooKeeper zk) {
        String createPath = null;
        try {
            try {
                if (!isExistZkNode(sGroup + subNode)) {
                    createPath = zk.create(sGroup + subNode, new String("appServer7").getBytes("utf-8"), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                } else {
                    System.out.println("已经存在节点，直接返回");
                    createPath=sGroup+subNode;
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println(createPath);

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return createPath;
    }


    private ZooKeeper getConnection() {
        try {

            this.zooKeeper = new ZooKeeper("mini2:2181,mini3:2181,mini4:2181", CONNECTION_TIMEOUT, new Watcher() {
                public void process(WatchedEvent watchedEvent) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return zooKeeper;
    }

    private boolean isExistZkNode(String path) {
        Stat isExist = null;
        try {

            isExist = zooKeeper.exists(path, false);

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isExist==null?false:true;
    }

}
