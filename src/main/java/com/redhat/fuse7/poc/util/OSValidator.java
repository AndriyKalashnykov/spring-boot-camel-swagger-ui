package com.redhat.fuse7.poc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OSValidator {

    private static final Logger logger = LoggerFactory.getLogger(OSValidator.class);

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static void main(String[] args) {

        logger.debug(OS);

        if (isWindows()) {
            logger.debug("This is Windows");
        } else if (isMac()) {
            logger.debug("This is Mac");
        } else if (isUnix()) {
            logger.debug("This is Unix or Linux");
        } else if (isSolaris()) {
            logger.debug("This is Solaris");
        } else {
            logger.debug("Your OS is not support!!");
        }
    }

    public static String getOS() {
        return OS;
    }

    public static boolean isWindows() {

        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() {

        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }

    public static boolean isSolaris() {

        return (OS.indexOf("sunos") >= 0);
    }
}