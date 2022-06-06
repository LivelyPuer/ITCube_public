package com.livlypuer.popava;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public abstract class MainThreadCallback implements Callback {
    private static final String TAG = MainThreadCallback.class.getSimpleName();

    abstract public void onFail(final Exception error);

    abstract public void onSuccess(final String responseBody);

    public void onFailure(final Request request, final IOException e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                e.printStackTrace();
                if (e.getMessage().contains("Canceled") || e.getMessage().contains("Socket closed")) {
                    Log.d("MY", "FAIL");
                } else {
                    onFail(e);
                }
            }
        });
    }

    public void onResponse(final Response response) throws IOException {
        if (!response.isSuccessful() || response.body() == null) {
            onFailure(response.request(), new IOException("Failed"));
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    onSuccess(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                    onFailure(response.request(), new IOException("Failed"));
                }
            }
        });
    }

    private void runOnUiThread(Runnable task) {
        new Handler(Looper.getMainLooper()).post(task);
    }
}