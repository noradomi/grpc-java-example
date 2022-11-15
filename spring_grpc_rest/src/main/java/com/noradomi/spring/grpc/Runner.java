package com.noradomi.spring.grpc;

import java.io.File;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Runner implements DisposableBean, CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(Runner.class, args);
  }

  @Override
  public void destroy() throws Exception {}

  @Override
  public void run(String... args) throws Exception {
    String pidFile = System.getProperty("pidFile");
    if (pidFile != null) {
      new File(pidFile).deleteOnExit();
    }
  }
}
