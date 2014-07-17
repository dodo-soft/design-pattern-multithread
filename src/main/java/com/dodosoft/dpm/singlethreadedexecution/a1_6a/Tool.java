// 本ソースコードは、結城浩著 増補改訂版Java言語で学ぶデザインパターン入門マルチスレッド編(http://www.hyuki.com/dp/dp2.html)
// SingleThreadedExecution/A1-6a/Tool.java
// を元にし、学習目的で一部変更を加えています。
package com.dodosoft.dpm.singlethreadedexecution.a1_6a;
public class Tool {
    private final String name;
    public Tool(String name) {
        this.name = name;
    }
    public String toString() {
        return "[ " + name + " ]";
    }
}
