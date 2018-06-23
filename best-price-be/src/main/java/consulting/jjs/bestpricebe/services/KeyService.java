package consulting.jjs.bestpricebe.services;

import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class KeyService {

  @Getter
  SecretKeySpec secretKey;

  @PostConstruct
  private void generateKey() {
    // TODO: change secret password
    String keyString = "simplekey";
    secretKey = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
  }

}
