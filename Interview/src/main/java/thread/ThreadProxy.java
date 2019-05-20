package thread;

/**
 * @Auther: baozi
 * @Date: 2019/5/20 10:17
 * @Description:
 * TODO
 * 1. 要么静态实现
 * 2. 要么作为单例
 */
public class ThreadProxy {

    // 线程运行状态
    public boolean started = false;

    // 线程终止标志位
    private volatile boolean terminated = false;

    // 采集线程
    private Thread rptThread;

    // 启动采集功能
    public synchronized void start(){
        // 不允许同时启动多个采集线程
        if (started) {
            return;
        }
        started = true;
        terminated = false;
        rptThread = new Thread(()->{
            while (!terminated){
                // 省略采集、回传实现
                report();
                // 每隔两秒钟采集、回传一次数据
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e){
                    // 重新设置线程中断状态
                    Thread.currentThread().interrupt();
                }
            }
            // 执行到此处说明线程马上终止
            started = false;
        });
        rptThread.start();
    }

    // 终止采集功能
    public synchronized void stop(){
        // 设置中断标志位
        terminated = true;
        // 中断线程 rptThread
        rptThread.interrupt();
    }

    /**
     * 采集上报
     */
    private void report() {
        System.out.println("todo...");
    }
}
