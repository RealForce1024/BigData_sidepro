package com.fqc.jvm;

/**
 * Hello world!
 */
public class App {


    public static void main(String[] args) {
        long start_time = System.currentTimeMillis();

        for (int i = 0; i <100000000 ; i++) {
            alloc();
        }

        long end_time = System.currentTimeMillis();
        System.out.println(end_time - start_time);
        
    }


    public static void alloc() {
        Byte[] bytes = new Byte[2];
        bytes[0] = 1;
    }
}
