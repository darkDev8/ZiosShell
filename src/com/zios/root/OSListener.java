package com.zios.root;

import java.io.File;

public interface OSListener {
    public String getUsername();
    public String getExecutablePath();
    public String getOSName();
    public File[] getPartitions();
    public boolean executeCommandShell(String command);
}
