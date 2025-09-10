package com.event_website.Logging;

import java.lang.annotation.*;

/**
 * @author Omar Alomair
 *
 * This annotation will be used at each controller method to create a custom logging entry for it.
 * For example, for /auth/login the description will be "Trying to log in for user: XYZ".
 * this description will be used for central logging of the app.
 *
 * */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogRequest {
    String description() default "";
}