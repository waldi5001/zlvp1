package de.zlvp;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.SwingUtilities;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.task.TaskExecutor;

import de.javasoft.swing.DetailsDialog;
import de.zlvp.controller.AsyncCallback;
import de.zlvp.gui.FensterKlasse;

public class AsyncAndErrorInterceptor implements MethodInterceptor {
    private static Logger log = LoggerFactory.getLogger(AsyncAndErrorInterceptor.class);

    private TaskExecutor taskExecutor;

    private FensterKlasse fensterKlasse;

    private AtomicInteger invocationCounter = new AtomicInteger(0);

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(MethodInvocation invocation) {
        AsyncCallback<Object> originalCallback = (AsyncCallback<Object>) invocation.getArguments()[invocation.getArguments().length - 1];

        ProxyCallback proxy = new ProxyCallback();
        invocation.getArguments()[invocation.getArguments().length - 1] = proxy;

        Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();

        CompletableFuture.supplyAsync(() -> {
            try {
                MDC.setContextMap(copyOfContextMap);

                if (invocationCounter.incrementAndGet() == 1) {
                    CursorToolkit.startWaitCursor(fensterKlasse.getRootPane());
                }

                invocation.proceed();

                return proxy.getControllerResult();
            } catch (Throwable e) {
                handleThrowable(e);
            } finally {
                if (invocationCounter.decrementAndGet() == 0) {
                    CursorToolkit.stopWaitCursor(fensterKlasse.getRootPane());
                }
                MDC.clear();
            }
            return null;
        }, taskExecutor).thenAcceptAsync(originalCallback::get, SwingUtilities::invokeLater);
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

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void setFensterKlasse(FensterKlasse fensterKlasse) {
        this.fensterKlasse = fensterKlasse;
    }

}
