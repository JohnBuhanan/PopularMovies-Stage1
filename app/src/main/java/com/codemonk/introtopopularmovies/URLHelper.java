package com.codemonk.introtopopularmovies;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jtbuhana on 10/9/2016.
 */
public final class URLHelper {

    private static final String LOG_TAG = URLHelper.class.getSimpleName();

    private URLHelper() {

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

    public static String Get(URL url) {
        if (url == null)
            return null;

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        // Will contain the raw JSON response as a string.
        String jsonResponse = null;

        try {
            // Create the request to OpenWeatherMap, and open the connection
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.connect();

            // Read the input stream into a String
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }

            jsonResponse = stringBuffer.toString();
        } catch (IOException e) {
            Log.e("URLHelp", "Error ", e);
            return null;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return jsonResponse;
    }
}
