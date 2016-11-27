package net.battleplugins.msutton.DungeonsAhoy.GameInfo;

import net.battleplugins.msutton.DungeonsAhoy.GlobalVariable;

/**
 * Created by mts01060 on 11/15/2016.
 */

public class CheckRunning implements Runnable {


    @Override
    public void run(){
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The boolean is: " + GlobalVariable.running);
        }
    }
}
