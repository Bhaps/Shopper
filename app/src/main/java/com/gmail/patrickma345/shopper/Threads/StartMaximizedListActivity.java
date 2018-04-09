package com.gmail.patrickma345.shopper.Threads;

import android.content.Context;
import android.content.Intent;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Callable thread to get the result from the MaximizeItemsCallable using Future.get() and
 * send the result to MaximizeListActivity without causing the main thread to be blocked.
 *
 * Created by patrickma345 on 20/01/18.
 */

public class StartMaximizedListActivity implements Runnable {

    private Future future;
    private Context context;
    private Intent intent;

    public StartMaximizedListActivity(Future future, Intent intent, Context context) {
        this.future = future;
        this.intent = intent;
        this.context = context;
    }

    @Override
    public void run() {

        String maximizedListsSummary = null;


        try {
            maximizedListsSummary = (String) future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.flush();
        intent.putExtra(Intent.EXTRA_TEXT, maximizedListsSummary);
        context.startActivity(intent);

    }

}
