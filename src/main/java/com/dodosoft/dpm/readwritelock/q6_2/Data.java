// 本ソースコードは、結城浩著 増補改訂版Java言語で学ぶデザインパターン入門マルチスレッド編(http://www.hyuki.com/dp/dp2.html)
// ReadWriteLock/Q6-2/Data.java
// を元にし、学習目的で一部変更を加えています。
package com.dodosoft.dpm.readwritelock.q6_2;
public class Data {
    private final char[] buffer;
    public Data(int size) {
        this.buffer = new char[size];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = '*';
        }
    }
    public char[] read() throws InterruptedException {
        return doRead();
    }
    public void write(char c) throws InterruptedException {
        doWrite(c);
    }
    private char[] doRead() {
        char[] newbuf = new char[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            newbuf[i] = buffer[i];
        }
        slowly();
        return newbuf;
    }
    private void doWrite(char c) {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = c;
            slowly();
        }
    }
    private void slowly() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }
    }
}
