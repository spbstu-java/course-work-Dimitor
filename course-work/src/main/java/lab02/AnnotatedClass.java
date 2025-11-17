package lab02;

public class AnnotatedClass {
    public void publicMethod() {
        System.out.println("PublicMethod");
    }

    @CallTimes(1)
    public void publicMethod2(int param) {
        System.out.println("PublicMethod2: param=" + param);
    }

    @CallTimes(2)
    public void publicMethod3(String param1, double param2) {
        System.out.println("PublicMethod3: param1=" + param1 + ", param2=" + param2);
    }

    //////////////////////////////////////////////////

    @CallTimes(3)
    protected void protectedMethod() {
        System.out.println("ProtectedMethod");
    }

    @CallTimes(2)
    protected void protectedMethod2(float param) {
        System.out.println("ProtectedMethod2: param=" + param);
    }

    protected void protectedMethod3() {
        System.out.println("ProtectedMethod3 (без аннотаций)");
    }

    //////////////////////////////////////////////////

    @CallTimes(2)
    private void privateMethod(boolean param) {
        System.out.println("PrivateMethod: param=" + param);
    }

    @CallTimes(3)
    private void privateMethod2(long param1, char param2) {
        System.out.println("PrivateMethod2: param1=" + param1 + ", param2=" + param2);
    }

    @CallTimes(1)
    private void privateMethod3(byte param) {
        System.out.println("PrivateMethod3: param=" + param);
    }
}