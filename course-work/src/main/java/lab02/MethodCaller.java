package lab02;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodCaller {

    public void callAnnotatedMethods(Object target) {
        Method[] methods = target.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (!method.isAnnotationPresent(CallTimes.class)) continue;

            int modifiers = method.getModifiers();

            if (Modifier.isProtected(modifiers) || Modifier.isPrivate(modifiers)) {
                CallTimes annotation = method.getAnnotation(CallTimes.class);
                int callCount = annotation.value();

                method.setAccessible(true);

                for (int i = 0; i < callCount; i++) {
                    try {
                        Object[] args = generateMethodArguments(method);
                        method.invoke(target, args);
                    } catch (Exception e) {
                        System.err.println("Ошибка: " + e.getMessage());
                    }
                }
            }
        }
    }

    private Object[] generateMethodArguments(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            args[i] = getDefaultValue(parameterTypes[i]);
        }
        return args;
    }

    private Object getDefaultValue(Class<?> type) {
        if (type == boolean.class || type == Boolean.class)   return true;
        if (type == byte.class    || type == Byte.class)      return (byte) 1;
        if (type == short.class   || type == Short.class)     return (short) 2;
        if (type == int.class     || type == Integer.class)   return 3;
        if (type == long.class    || type == Long.class)      return 4L;
        if (type == float.class   || type == Float.class)     return 5.0f;
        if (type == double.class  || type == Double.class)    return 6.0;
        if (type == char.class    || type == Character.class) return '7';
        if (type == String.class)                             return "8_str";
        return null;
    }
}