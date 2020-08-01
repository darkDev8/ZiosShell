package com.zios.eth;

import com.zios.root.Strings;
import com.zios.root.ToolBox;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.File;
import java.io.IOException;
import java.net.*;

public class Internet {
    private String url;

    private int connectionTimeOut;
    private int readTimeOut;


    public Internet(String url) {
        this.url = url;
    }

    public Internet() {
    }

    public Internet(String url, int connectionTimeOut, int readTimeOut) {
        this.url = url;

        this.connectionTimeOut = connectionTimeOut;
        this.readTimeOut = readTimeOut;
    }


    public boolean download() {
        return download(url);
    }

    public long getSize() {
        return getSize(url);
    }

    public String getName() {
        return getName(url);
    }

    public String getReadableSize() {
        return getReadableSize(url);
    }

    public boolean validate() {
        return validate(url);
    }

    public boolean download(String url) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(getName(url)), connectionTimeOut, readTimeOut);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public long getSize(String url) {
        HttpURLConnection conn = null;
        URL ul = null;

        try {
            ul = new URL(url);

            conn = (HttpURLConnection) ul.openConnection();
            conn.setRequestMethod("HEAD");
            return conn.getContentLengthLong();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }


    public String getName(String url) {
        try {
            return FilenameUtils.getName(new URL(url).getPath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getReadableSize(String url) {
        return new Strings().getReadableSize(getSize(url));
    }

    public boolean validate(String url) {
        return new UrlValidator().isValid(url);
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public void ping(String url) {
        HttpURLConnection connection = null;
        //"http://www.google.com/"

        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("HEAD");
            int code = connection.getResponseCode();

            new ToolBox().print("\tUrl address \"".concat(url).concat("\"").
                    concat(" return code: ".concat(String.valueOf(code))), true);
        } catch (MalformedURLException e) {
            new ToolBox().print("\tUnable to reach ther server address.", true);
        } catch (IOException e) {
            new ToolBox().print("\tUnable to reach ther server address.", true);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
