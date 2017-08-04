package de.zlvp;

import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.javasoft.swing.DetailsDialog;
import de.zlvp.controller.AsyncCallback;

public class AsyncAndErrorInterceptor implements MethodInterceptor {
    private static Logger log = LoggerFactory.getLogger(AsyncAndErrorInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) {
        try {
            if (invocation.getArguments().length > 0) {
                Object arg = invocation.getArguments()[invocation.getArguments().length - 1];
                if (arg instanceof AsyncCallback<?>) {
                    SwingWorker<Object, Void> sw = new SwingWorker<Object, Void>() {
                        @Override
                        protected Object doInBackground() {
                            try {
                                return invocation.proceed();
                            } catch (Throwable e) {
                                throw new RuntimeException(e.getMessage(), e);
                            }
                        }

                        @SuppressWarnings("unchecked")
                        @Override
                        protected void done() {
                            try {
                                ((AsyncCallback<Object>) arg).get(get());
                            } catch (InterruptedException | ExecutionException e) {
                                handleThrowable(e);
                            }
                        }
                    };
                    sw.execute();
                } else {
                    return invocation.proceed();
                }
            } else {
                return invocation.proceed();
            }
        } catch (Throwable e) {
            handleThrowable(e);
        }
        return null;
    }

    private void handleThrowable(Throwable e) {
        log.error(e.getMessage(), e);
        String message = e.getMessage();
        if (message == null || message.length() == 0) {
            message = "Fatal: " + e.getClass();
        }
        DetailsDialog.showDialog(null, "Fehler", message, e);
    }

}
