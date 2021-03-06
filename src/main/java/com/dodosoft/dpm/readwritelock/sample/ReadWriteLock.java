// 本ソースコードは、結城浩著 増補改訂版Java言語で学ぶデザインパターン入門マルチスレッド編(http://www.hyuki.com/dp/dp2.html)
// ReadWriteLock/Sample/ReadWriteLock.java
// を元にし、学習目的で一部変更を加えています。
package com.dodosoft.dpm.readwritelock.sample;

public final class ReadWriteLock {

  private int readingReaders = 0; // (A) 実際に読んでいる最中のスレッドの数
  private int waitingWriters = 0; // (B) 書くのを待っているスレッドの数
  private boolean writing = false; // (C) 実際に書いている最中のスレッドの数
  private boolean preferWriter = true; // 書くのを優先するならtrue

  public synchronized void readLock() throws InterruptedException {
    while (writing || (preferWriter && waitingWriters > 0)) {
      wait();
    }
    readingReaders++;                       // (A) 実際に読んでいるスレッドの数を1増やす
  }

  public synchronized void readUnlock() {
    readingReaders--;                       // (A) 実際に読んでいるスレッドの数を1減らす
    preferWriter = true;
    notifyAll();
  }

  public synchronized void writeLock() throws InterruptedException {
    waitingWriters++;                       // (B) 書くのを待っているスレッドの数を1増やす
    try {
      while (readingReaders > 0 || writing) {
        wait();
      }
    } finally {
      waitingWriters--;                   // (B) 書くのを待っているスレッドの数を1減らす
    }
    writing = true;                       // (C) 実際に書いているスレッドの数を1増やす
  }

  public synchronized void writeUnlock() {
    writing = false;                       // (C) 実際に書いているスレッドの数を1減らす
    preferWriter = false;
    notifyAll();
  }
}
