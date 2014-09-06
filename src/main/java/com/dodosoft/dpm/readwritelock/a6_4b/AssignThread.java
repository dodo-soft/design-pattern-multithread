// 本ソースコードは、結城浩著 増補改訂版Java言語で学ぶデザインパターン入門マルチスレッド編(http://www.hyuki.com/dp/dp2.html)
// ReadWriteLock/A6-4b/AssignThread.java
// を元にし、学習目的で一部変更を加えています。
package com.dodosoft.dpm.readwritelock.a6_4b;
import java.util.*;

public class AssignThread extends Thread {
    private static Random random = new Random(314159);
    private final Database<String,String> database;
    private final String key;
    private final String value;

    public AssignThread(Database<String,String> database, String key, String value) {
        this.database = database;
        this.key = key;
        this.value = value;
    }

    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + ":assign(" + key + ", " + value + ")");
            database.assign(key, value);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
            }
        }
    }
}
