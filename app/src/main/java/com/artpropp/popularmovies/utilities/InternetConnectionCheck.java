package com.artpropp.popularmovies.utilities;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


public class InternetConnectionCheck extends AsyncTask<Void,Void,Boolean> {

    public interface OnPostExecuteListener {
        void onPostExecute(boolean hasInternetConnection);
    }

    private OnPostExecuteListener mListener;

    public InternetConnectionCheck(OnPostExecuteListener listener) {
        mListener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Socket sock = new Socket();
            sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
            sock.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean hasInternetConnection) {
        mListener.onPostExecute(hasInternetConnection);
    }
}
