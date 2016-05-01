package com.fqc.zk.heartbeats;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fqc on 2016/4/30.
 */
public class AppClient {
    private static final int CONNECTION_TIMEOUT = 3000;
    private static final String sGroup = "/sGroup";
    private ZooKeeper zooKeeper;
    private Stat stat = new Stat();
    private volatile ArrayList<String> serverList;

    public static void main(String[] args) {
        AppClient appClient = new AppClient();
        ZooKeeper zk = appClient.getConnection();
        appClient.updateServerList(zk);
        appClient.handler();
    }

    private void updateServerList(ZooKeeper zk) {
        //getChildrenNode getData replace newList
        ArrayList<String> newServerList = new ArrayList<String>();

        try {
            List<String> subNodes = zk.getChildren(sGroup, true);
            for (String node : subNodes) {


                byte[] data = zk.getData(sGroup+"/"+node, false, stat);
                newServerList.add(new String(data));

            }
            serverList = newServerList;
            System.out.println(serverList);

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void handler() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ZooKeeper getConnection() {

        try {
            zooKeeper = new ZooKeeper("mini2:2181,mini3:2181,mini4:2181", CONNECTION_TIMEOUT, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    //type and path
                    if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged&&(sGroup).equals(watchedEvent.getPath())) {
                        updateServerList(zooKeeper);
                    }
                }
            });
            updateServerList(zooKeeper);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return zooKeeper;
    }
}
