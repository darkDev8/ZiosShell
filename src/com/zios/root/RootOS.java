package com.zios.root;

import java.io.File;

public class RootOS implements OSListener{

    @Override
    public String getUsername() {
        return System.getProperty("user.name");
    }

    @Override
    public String getExecutablePath() {
        return System.getProperty("user.dir");
    }

    @Override
    public String getOSName() {
        return null;
    }

    @Override
    public File[] getPartitions() {
        return new File[0];
    }

    @Override
    public boolean executeCommandShell(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", command);
            processBuilder.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
