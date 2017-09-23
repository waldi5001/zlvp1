package de.zlvp;

import javax.swing.SwingUtilities;
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

            AsyncCallback<Object> edtCallback = new EDTCallback(originalCallback);

            ProxyCallback proxy = new ProxyCallback();
            invocation.getArguments()[invocation.getArguments().length - 1] = proxy;

            SwingWorker<Object, Void> sw = new SwingWorker<Object, Void>() {
                @Override
                protected Object doInBackground() {
                    try {
                        Object proceed = invocation.proceed();
                        edtCallback.get(proxy.getControllerResult());
                        return proceed;
                    } catch (Throwable e) {
                        handleThrowable(e);
                    }
                    return null;
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

    private static class ProxyCallback implements AsyncCallback<Object> {
        private Object controllerResult = null;

        @Override
        public void get(Object controllerResult) {
            this.controllerResult = controllerResult;
        }

        public Object getControllerResult() {
            return controllerResult;
        }

    }

    private static class EDTCallback implements AsyncCallback<Object> {
        private final AsyncCallback<Object> originalCallback;

        public EDTCallback(AsyncCallback<Object> originalCallback) {
            this.originalCallback = originalCallback;
        }

        @Override
        public void get(Object result) {
            SwingUtilities.invokeLater(() -> originalCallback.get(result));
        }

    }

}
