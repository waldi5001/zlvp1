package de.zlvp;

import javax.swing.SwingUtilities;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

import de.javasoft.swing.DetailsDialog;
import de.zlvp.controller.AsyncCallback;

public class AsyncAndErrorInterceptor implements MethodInterceptor {
    private static Logger log = LoggerFactory.getLogger(AsyncAndErrorInterceptor.class);

    private TaskExecutor taskExecutor;

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(MethodInvocation invocation) {
        AsyncCallback<Object> originalCallback = (AsyncCallback<Object>) invocation
                .getArguments()[invocation.getArguments().length - 1];

        AsyncCallback<Object> edtCallback = new EDTCallback(originalCallback);

        ProxyCallback proxy = new ProxyCallback();
        invocation.getArguments()[invocation.getArguments().length - 1] = proxy;

        taskExecutor.execute(() -> {
            try {
                invocation.proceed();
                edtCallback.get(proxy.getControllerResult());
            } catch (Throwable e) {
                handleThrowable(e);
            }

        });

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

        @Override
        public String toString() {
            return "AsyncCallback@" + Integer.toHexString(hashCode());
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

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

}
