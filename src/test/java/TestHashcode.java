public class TestHashcode {

    public static void main(String[] args) {
        long test;
        Object object = new Object();
        test = object.hashCode();

        System.out.println(object.hashCode());
    }
}
