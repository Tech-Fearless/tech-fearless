package com.tech.tomcat.chapter1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class LocakSocket {

    public static final Logger LOGGER = LoggerFactory.getLogger(LocakSocket.class);

    public void local(){
        try {
            Socket socket = new Socket("127.0.0.1",8080);
            OutputStream outputStream = socket.getOutputStream();
            boolean autoFlash = true;
            PrintWriter out = new PrintWriter(socket.getOutputStream(),autoFlash);
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader in = new BufferedReader(inputStreamReader);
            //send an http request to a web server
            out.println("GET /index.jsp HTTP/1.1");
            out.println("Host: localhostd:808");
            out.println("Connection:close");
            out.println();
            //read the response
            boolean loop = true;
            StringBuffer sb = new StringBuffer(8096);
            while (loop){
                if (in.ready()){
                    int i=0;
                    while (i!=-1){
                        i = in.read();
                        sb.append((char)i);
                    }
                loop = false;
                }
                Thread.currentThread().sleep(50);
            }
            //display the response
            System.out.println(sb.toString());
            socket.close();
        }catch (Exception e){
            LOGGER.error("local socket error!",e);
        }
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1",8080);
            OutputStream outputStream = socket.getOutputStream();
            boolean autoFlash = true;
            PrintWriter out = new PrintWriter(socket.getOutputStream(),autoFlash);
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader in = new BufferedReader(inputStreamReader);
            //send an http request to a web server
            out.println("GET /index.jsp HTTP/1.1");
            out.println("Host: localhostd:808");
            out.println("Connection:close");
            out.println();
            //read the response
            boolean loop = true;
            StringBuffer sb = new StringBuffer(8096);
            while (loop){
                if (in.ready()){
                    int i=0;
                    while (i!=-1){
                        i = in.read();
                        sb.append((char)i);
                    }
                    loop = false;
                }
                Thread.currentThread().sleep(50);
            }
            //display the response
            System.out.println(sb.toString());
            socket.close();
        }catch (Exception e){
            LOGGER.error("local socket error!",e);
        }
    }
}
