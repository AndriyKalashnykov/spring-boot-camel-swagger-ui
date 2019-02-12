package com.redhat.fuse7.poc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class HostInfo {

    private static final Logger logger = LoggerFactory.getLogger(OSValidator.class);

    private OSValidator osValidator = new OSValidator();

    private String hostname = "";

    public HostInfo() throws IOException {

        if (osValidator.isWindows()) {
            this.hostname = execReadToString("hostname").trim();
        } else if (osValidator.isMac()) {
            this.hostname = execReadToString("hostname").trim();
        } else if (osValidator.isUnix()) {
            this.hostname = execReadToString("cat /etc/hostname").trim();
        } else if (osValidator.isSolaris()) {
            this.hostname = execReadToString("hostname").trim();
        } else {
            logger.warn("Your OS is not support!!");
        }
    }

    public String getHostname() {
        return hostname;
    }

    public String getOS() {
        return osValidator.getOS();
    }

    public static String execReadToString(String execCommand) throws IOException {
        Process proc = Runtime.getRuntime().exec(execCommand);
        try (InputStream stream = proc.getInputStream()) {
            try (Scanner s = new Scanner(stream).useDelimiter("\\A")) {
                return s.hasNext() ? s.next() : "";
            }
        }
    }
}
