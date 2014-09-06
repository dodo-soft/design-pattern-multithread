// 本ソースコードは、結城浩著 増補改訂版Java言語で学ぶデザインパターン入門マルチスレッド編(http://www.hyuki.com/dp/dp2.html)
// ReadWriteLock/Q6-6/ReadWriteLock.java
// を元にし、学習目的で一部変更を加えています。
package com.dodosoft.dpm.readwritelock.q6_6;
public final class ReadWriteLock {
    private int readingReaders = 0; // (a) 実際に読んでいるスレッドの数
    private int writingWriters = 0; // (b) 実際に書いているスレッドの数

    public synchronized void readLock() throws InterruptedException {
        while (writingWriters > 0) {
            wait();
        }
        readingReaders++;                       // (a) 実際に読んでいるスレッドの数を1増やす
    }

    public synchronized void readUnlock() {
        readingReaders--;                       // (a) 実際に読んでいるスレッドの数を1減らす
        notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException {
        while (readingReaders > 0 || writingWriters > 0) {
            wait();
        }
        writingWriters++;                       // (b) 実際に書いているスレッドの数を1増やす
    }

    public synchronized void writeUnlock() {
        writingWriters--;                       // (b) 実際に書いているスレッドの数を1減らす
        notifyAll();
    }
}
