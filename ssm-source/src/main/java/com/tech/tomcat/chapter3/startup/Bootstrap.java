package com.tech.tomcat.chapter3.startup;


import com.tech.tomcat.chapter3.connector.http.HttpConnector;

public final class Bootstrap {
  public static void main(String[] args) {
    HttpConnector connector = new HttpConnector();
    connector.start();
  }
}