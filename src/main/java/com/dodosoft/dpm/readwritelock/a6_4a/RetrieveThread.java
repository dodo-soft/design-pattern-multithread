// 本ソースコードは、結城浩著 増補改訂版Java言語で学ぶデザインパターン入門マルチスレッド編(http://www.hyuki.com/dp/dp2.html)
// ReadWriteLock/A6-4a/RetrieveThread.java
// を元にし、学習目的で一部変更を加えています。
package com.dodosoft.dpm.readwritelock.a6_4a;
import java.util.concurrent.atomic.AtomicInteger;

public class RetrieveThread extends Thread {
    private final Database<String,String> database;
    private final String key;
    private static final AtomicInteger atomicCounter = new AtomicInteger(0);

    public RetrieveThread(Database<String,String> database, String key) {
        this.database = database;
        this.key = key;
    }

    public void run() {
        while (true) {
            int counter = atomicCounter.incrementAndGet();
            String value = database.retrieve(key);
            System.out.println(counter + ":" + key + " => " + value);
        }
    }
}
