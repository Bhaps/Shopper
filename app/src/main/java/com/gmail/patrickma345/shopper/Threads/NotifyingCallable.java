package com.gmail.patrickma345.shopper.Threads;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * A thread will extend this class to ensure it has the functionality to signal when the thread
 * has finished.
 *
 * Credit: https://stackoverflow.com/questions/702415/how-to-know-if-other-threads-have-finished
 * https://dev4devs.com/2016/06/21/java-how-to-create-threads-that-return-values/
 *
 * Created by patrickma345 on 19/01/18.
 */
public abstract class NotifyingCallable implements Callable<String> {

    private final Set<ThreadCompleteListener> listeners = new CopyOnWriteArraySet<ThreadCompleteListener>();

    public final void addListener(final ThreadCompleteListener listener) {
        listeners.add(listener);
    }

    public final void removeListener(final ThreadCompleteListener listener) {
        listeners.remove(listener);
    }

    private final void notifyListeners() {
        for (ThreadCompleteListener listener : listeners) {
            listener.notifyOfThreadComplete(this);
        }
    }

    /**
     * @return At the end, return a String representing all the items that are part of the maximized
     * list.
     */
    @Override
    public final String call() throws Exception {
        String items;
        try {
            items = doRun();
        } finally {
            notifyListeners();
        }

        return items;
    }

    /**
     * Child class will implement doRun instead of run.
     */
    public abstract String doRun() throws Exception;
}