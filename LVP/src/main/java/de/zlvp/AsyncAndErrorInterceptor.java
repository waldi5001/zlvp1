package de.zlvp;

import static javax.swing.SwingUtilities.invokeLater;

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
    @SuppressWarnings("unchecked")
    public Object invoke(MethodInvocation invocation) {
        try {
            AsyncCallback<Object> originalCallback = (AsyncCallback<Object>) invocation
                    .getArguments()[invocation.getArguments().length - 1];

            AsyncCallback<Object> edtCallback = result -> invokeLater(() -> {
                originalCallback.get(result);
            });

            invocation.getArguments()[invocation.getArguments().length - 1] = edtCallback;

            SwingWorker<Object, Void> sw = new SwingWorker<Object, Void>() {
                @Override
                protected Object doInBackground() {
                    try {
                        return invocation.proceed();
                    } catch (Throwable e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            };
            sw.execute();
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
