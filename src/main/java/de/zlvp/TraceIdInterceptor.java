package de.zlvp;

import java.util.UUID;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class TraceIdInterceptor implements MethodInterceptor {

    private static Logger log = LoggerFactory.getLogger(TraceIdInterceptor.class);

    private static final String TRACE_ID = "TRACE_ID";

    @Override
    public Object invoke(MethodInvocation invocation) {
        try {
            MDC.put(TRACE_ID, UUID.randomUUID().toString());
            Object result = invocation.proceed();
            return result;
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            MDC.remove(TRACE_ID);
        }
    }

}
