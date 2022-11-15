package vn.zalopay.noradomi;

import io.grpc.CallCredentials;
import vn.zalopay.noradomi.authorization.JwtCredential;

/**
 * Created by phucvt Date: 15/11/2022
 */
public class Runner {
  public static void main(String[] args) {
    JwtCredential credentials = new JwtCredential("default-client");
    System.out.println(credentials.generateToken());
  }
}
