package de.zlvp;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class TrimInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) {
        Object[] arguments = invocation.getArguments();
        for (int i = 0; i < arguments.length; i++) {
            Object object = arguments[i];
            if (object instanceof String) {
                String trimmed = ((String) object).trim();
                arguments[i] = trimmed.isEmpty() ? null : trimmed;
            }
        }
        try {
            return invocation.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
