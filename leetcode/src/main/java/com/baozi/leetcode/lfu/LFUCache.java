package com.baozi.leetcode.lfu;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 460.LFU缓存
 *
 * @author baozi
 */
public class LFUCache {

    int minfreq, capacity;
    Map<Integer, Node> keyTable;
    Map<Integer, LinkedList<Node>> freqTable;

    public LFUCache(int capacity) {
        this.minfreq = 0;
        this.capacity = capacity;
        keyTable = new HashMap<Integer, Node>();
        freqTable = new HashMap<Integer, LinkedList<Node>>();
    }

    public int get(int key) {
        if (capacity == 0) {
            return -1;
        }
        if (!keyTable.containsKey(key)) {
            return -1;
        }
        Node node = keyTable.get(key);
        int val = node.val, freq = node.freq, newFreq = freq + 1;
        // 从链表中移除当前节点(因为频率+1了)
        freqTable.get(freq).remove(node);
        // 如果当前链表为空，我们需要在哈希表中删除，且更新minFreq
        if (freqTable.get(freq).size() == 0) {
            freqTable.remove(freq);
            // 当前链表为空,并且最小使用频率等于当前节点频率,那么要增加最小使用频率为新的使用频率
            if (minfreq == freq) {
                minfreq = newFreq;
            }
        }
        // 插入到 freq + 1 中
        LinkedList<Node> list = freqTable.getOrDefault(newFreq, new LinkedList<Node>());
        list.offerFirst(new Node(key, val, newFreq));
        freqTable.put(newFreq, list);
        keyTable.put(key, freqTable.get(newFreq).peekFirst());
        return val;
    }

    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }
        if (!keyTable.containsKey(key)) {
            // 缓存已满，需要进行删除操作
            if (keyTable.size() == capacity) {
                // 通过 minFreq 拿到 freq_table[minFreq] 链表的末尾节点
                Node node = freqTable.get(minfreq).peekLast();
                keyTable.remove(node.key);
                freqTable.get(minfreq).pollLast();
                if (freqTable.get(minfreq).size() == 0) {
                    freqTable.remove(minfreq);
                }
            }
            LinkedList<Node> list = freqTable.getOrDefault(1, new LinkedList<Node>());
            list.offerFirst(new Node(key, value, 1));
            freqTable.put(1, list);
            keyTable.put(key, freqTable.get(1).peekFirst());
            minfreq = 1;
        } else {
            // 与 get 操作基本一致，除了需要更新缓存的值
            Node node = keyTable.get(key);
            int freq = node.freq;
            freqTable.get(freq).remove(node);
            if (freqTable.get(freq).size() == 0) {
                freqTable.remove(freq);
                if (minfreq == freq) {
                    minfreq += 1;
                }
            }
            LinkedList<Node> list = freqTable.getOrDefault(freq + 1, new LinkedList<Node>());
            list.offerFirst(new Node(key, value, freq + 1));
            freqTable.put(freq + 1, list);
            keyTable.put(key, freqTable.get(freq + 1).peekFirst());
        }
    }
}

class Node {
    int key, val, freq;

    Node(int key, int val, int freq) {
        this.key = key;
        this.val = val;
        this.freq = freq;
    }
}
