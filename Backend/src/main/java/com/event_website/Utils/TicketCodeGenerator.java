package com.event_website.Utils;

import java.util.UUID;

/**
 * Utility class for generating unique ticket codes.
 *
 * <p>Ticket codes follow the format: TKT-XXXXXXXX Where X is an uppercase alphanumeric character
 * from a UUID.
 *
 * <p>This class is stateless and thread-safe.
 *
 * @since 09-09-2025
 * @version 1.0
 */
public class TicketCodeGenerator {

  /**
   * Generates a unique ticket code using UUID.
   *
   * @return a ticket code in the format "TKT-XXXXXXXX"
   */
  public static String generateTicketCode() {
    String prefix = "TKT";
    String randomPart = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();

    return prefix + "-" + randomPart;
  }
}
