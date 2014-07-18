// 本ソースコードは、結城浩著 増補改訂版Java言語で学ぶデザインパターン入門マルチスレッド編(http://www.hyuki.com/dp/dp2.html)
// SingleThreadedExecution/Sample2/UserThread.java
// を元にし、学習目的で一部変更を加えています。
package com.dodosoft.dpm.singlethreadedexecution.inheritance_anomaly;

public class UserThread extends Thread {

    private final Gate gate;
    private final String myname;
    private final String myaddress;

    public UserThread(Gate gate, String myname, String myaddress) {
        this.gate = gate;
        this.myname = myname;
        this.myaddress = myaddress;
    }

    public void run() {
        System.out.println(myname + " BEGIN");
        while (true) {
            if (gate instanceof NiceGate) {
                ((NiceGate)gate).setName(this.myname);
            }
            gate.pass(myname, myaddress);
        }
    }
}
