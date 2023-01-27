package com.nozama.api.domain.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DomainUtils {

  public static String generateOrderCode() {
		var now = LocalDateTime.now();
		var code = now.format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSSSSS"));
    return code;
  }

  public static String generateDeliveryCode() {
		var now = LocalDateTime.now();
		var code = now.format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSSSSS"));
    return code;
  }
  
}
