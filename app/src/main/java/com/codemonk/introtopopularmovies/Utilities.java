package com.codemonk.introtopopularmovies;

import java.io.IOException;

/**
 * Created by jtbuhana on 10/19/2016.
 */

public final class Utilities {
    private static final String LOG_TAG = Utilities.class.getSimpleName();

    private Utilities() {

    }

    public static boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();

            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}
