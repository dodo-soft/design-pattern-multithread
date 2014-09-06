// 本ソースコードは、結城浩著 増補改訂版Java言語で学ぶデザインパターン入門マルチスレッド編(http://www.hyuki.com/dp/dp2.html)
// ReadWriteLock/A6-4b/Database.java
// を元にし、学習目的で一部変更を加えています。
package com.dodosoft.dpm.readwritelock.a6_4b;
import java.util.Map;
import java.util.HashMap;

public class Database<K,V> {
    private final Map<K,V> map = new HashMap<K,V>();

    // すべてクリアする
    public synchronized void clear() {
        verySlowly();
        map.clear();
    }

    // keyにvalueを割り当てる
    public synchronized void assign(K key, V value) {
        verySlowly();
        map.put(key, value);
    }

    // keyに割り当てた値を取得する
    public synchronized V retrieve(K key) {
        slowly();
        return map.get(key);
    }

    // 処理に時間がかかることをシミュレートする
    private void slowly() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }
    }

    // 処理にとても時間がかかることをシミュレートする
    private void verySlowly() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
    }
}
