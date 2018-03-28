## 基本思路：

- 多个服务启动后，都尝试在 Zookeeper中创建一个 EPHEMERAL 类型的节点，Zookeeper本身会保证，只有一个服务会创建成功，其他服务抛出异常。
-  成功创建节点的服务，作为主节点，继续运行
- 其他服务设置一个Watcher监控节点状态，
- 如果主节点消失，其他服务会接到通知，再次尝试创建EPHEMERAL 类型的节点。



## Zookeeper Properties：
- The Zookeeper properties that will help us implement this active token concept are:
 
- When Zookeeper is requested to create a node, it will return an error if the node already exists.
 
- Requests to create nodes are serialized and are atomic.
 
- There is a node type which is called ephemeral and which lives as long as the client session lives. It is automatically deleted when the session with the client terminates.

- A client can request a watch on an existing node. If the node is deleted, then client is notified.
 
[参考文章](https://www.simplybusiness.co.uk/about-us/tech/2017/10/zookeeper-active-standby-master-process/)
