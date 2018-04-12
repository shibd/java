package com.baozi.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.EnsurePath;

import java.util.Random;

/**
 * Curator framework's leader election test.
 * Output:
 *  LeaderSelector-2 take leadership!
 *  LeaderSelector-2 relinquish leadership!
 *  LeaderSelector-1 take leadership!
 *  LeaderSelector-1 relinquish leadership!
 *  LeaderSelector-0 take leadership!
 *  LeaderSelector-0 relinquish leadership!
 *      ...
 */
public class CuratorLeaderTest {

    /** Zookeeper info */
    private static final String ZK_ADDRESS = "10.116.18.109:2181";
    private static final String ZK_PATH = "/zktest";
    private boolean isMaster = false;

    public static void main(String[] args) throws InterruptedException {
        LeaderSelectorListener listener = new LeaderSelectorListener() {
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                int i = new Random().nextInt(10);
                while (true) {
                    System.out.println("实例<" + i + ">为master节点: 执行业务逻辑........");
                    // takeLeadership() method should only return when leadership is being relinquished.
                    Thread.sleep(10000L);
                }
            }

            @Override
            public void stateChanged(CuratorFramework client, ConnectionState state) {
            }
        };

        new Thread(() -> {
            registerListener(listener);
        }).start();

        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void registerListener(LeaderSelectorListener listener) {
        // 1.Connect to zk
        // sessionTimeOutMs session保持时间,时间过了就要重新发心跳检测.
        CuratorFramework client = CuratorFrameworkFactory.newClient(
                ZK_ADDRESS,
                5,
                Integer.MAX_VALUE,
                new RetryNTimes(5, 100)
        );

        client.start();

        // 2.Ensure path
        try {
            new EnsurePath(ZK_PATH).ensure(client.getZookeeperClient());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3.Register listener
        LeaderSelector selector = new LeaderSelector(client, ZK_PATH, listener);
        selector.autoRequeue();
        selector.start();
    }
}
