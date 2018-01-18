package com.example.patrick.shopper.Threads;

import java.util.concurrent.Callable;

/**
 * Interface to ensure that the class which starts the treads will be implement the method to
 * some action when the thread has finished.
 *
 * Credit: https://stackoverflow.com/questions/702415/how-to-know-if-other-threads-have-finished
 *
 * Created by patrick on 19/01/18.
 */

public interface ThreadCompleteListener {
    void notifyOfThreadComplete(final Callable call);
}
