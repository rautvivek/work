package com.db.awmd.challenge.domain;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {
	private final ReentrantLock lock = new ReentrantLock();
    private int count = 0;

     //Locking using Lock and ReentrantLock
     public int getCount() {
        try {
			if(lock.tryLock(2,TimeUnit.SECONDS)) {
			try {
				  Thread.sleep(1500);
			    System.out.println(Thread.currentThread().getName() + " gets Count: " + count);
			    return count++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			} finally {
			    lock.unlock();
			}
			}else {
				System.out.println("in else:"+Thread.currentThread().getName());
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
        return 0;
     }

     //Implicit locking using synchronized keyword
     /*public synchronized int getCountTwo() {
            return count++;
     }*/


//javarevisited.blogspot.com/2013/03/reentrantlock-example-in-java-synchronized-difference-vs-lock.html#ixzz52RFn7VEk
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestReentrantLock counter=new TestReentrantLock();
		Thread t1 = new Thread("Thread 1") {

            @Override
            public void run() {	
              //  while (counter.getCount()<6) {
                    try {
                    	counter.getCount();
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();                    }
                }
           // }
        };
      
        Thread t2 = new Thread("Thread 2") {

            @Override
            public void run() {
             //   while (counter.getCount() < 6) {
                    try {
                    	counter.getCount();
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
           // }
        };
        Thread t3 = new Thread("Thread 3") {

            @Override
            public void run() {
               // while (counter.getCount() < 6) {
                    try {
                    	counter.getCount();
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            //}
        };
      
        t1.start();
        t2.start();
        t3.start();
     




	}

}
