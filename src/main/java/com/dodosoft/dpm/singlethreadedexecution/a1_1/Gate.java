// 本ソースコードは、結城浩著 増補改訂版Java言語で学ぶデザインパターン入門マルチスレッド編(http://www.hyuki.com/dp/dp2.html)
// SingleThreadedExecution/A1-1/Gate.java
// を元にし、学習目的で一部変更を加えています。
package com.dodosoft.dpm.singlethreadedexecution.a1_1;
public class Gate {
    private int counter = 0;
    private String name = "Nobody";
    private String address = "Nowhere";
    public void pass(String name, String address) {
        this.counter++;
        this.name = name;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        this.address = address;
        check();
    }
    public String toString() {
        return "No." + counter + ": " + name + ", " + address;
    }
    private void check() {
        if (name.charAt(0) != address.charAt(0)) {
            System.out.println("***** BROKEN ***** " + toString());
        }
    }
}
