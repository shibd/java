package com.baozi.leetcode.foobar;

/**
 * 1115.交替打印FooBar
 *
 * @author baozi
 */
public class FooBar {

    private int n;
    private int state = 0; // foo=0 bar=1

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized (FooBar.class) {
                while (state == 1) {
                    FooBar.class.wait();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                state = 1;
                FooBar.class.notifyAll();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized (FooBar.class) {
                while (state == 0) {
                    FooBar.class.wait();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printBar.run();
                state = 0;
                FooBar.class.notifyAll();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        FooBar fooBar = new FooBar(10);
        new Thread(() -> {
            try {
                fooBar.foo(() -> System.out.print("foo"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                fooBar.bar(() -> System.out.print("bar"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}