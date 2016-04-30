package com.fqc.zk.crud;

import org.apache.hadoop.fs.Stat;
import org.apache.zookeeper.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by fqc on 2016/4/30.
 */
public class ZkCrud {
    ZooKeeper zk;
    private static final int CONNECTION_TIMEOUT = 30000;
    private static final String CONNECTION_STRING = "mini2:2181";
    Watcher wc ;
//    Watcher wc = new Watcher() {
//        public void process(WatchedEvent watchedEvent) {
//            System.out.println("path:"+watchedEvent.getPath()+",type:"+watchedEvent.getType()+",state:"+watchedEvent.getState());
//            System.out.println("watching...");
//        }
//    };

    @Before
    public void init() {

        wc = new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println("path:"+watchedEvent.getPath()+",type:"+watchedEvent.getType()+",state:"+watchedEvent.getState());
                System.out.println(watchedEvent.toString());
                System.out.println("watching...");
            }
        };

        try {
            zk = new ZooKeeper(CONNECTION_STRING, CONNECTION_TIMEOUT, wc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createZkNode() {
        //create / "createZkNode"
        try {
            String resultPath = zk.create("/zkTest24", "createZkNode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getZkNode() {
        try {
            if (zk.exists("/zkTest24", this.wc) != null) {
                byte[] data = zk.getData("/zkTest24", this.wc, null);
                System.out.println(new String(data));
            } else {
                System.out.println("no zkTest zkNode");
            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setZkNode() {
        try {
            zk.setData("/zkTest", new String("helloworld20").getBytes(), -1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ;

    }

    @Test
    public void deleteZkNode() {
        try {
            zk.delete("/zkTest", -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }


}
