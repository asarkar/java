package name.abhijitsarkar.java;

import java.util.function.BinaryOperator;
import java.util.function.Function;

// http://www.javamagazine.mozaicreader.com/MayJune2016#&pageSet=56&page=0
public class Goat {
    public static String getIt(String a, String b) {
        return "Hello " + a + " " + b;
    }

    public static String getIt(Goat a, String b) {
        return "Goodbye " + a + " " + b;
    }

    public String getIt() {
        return "Goated! ";
    }

    public String getIt(String b) {
        return "Really " + b + "!";
    }

    public static <E extends CharSequence> void show(BinaryOperator<E> op, E e1, E e2) {
        System.out.println("> " + op.apply(e1, e2));
    }

    public static <E extends Goat, F> void show(Function<E, F> op, E e, F f) {
        System.out.println(">> " + op.apply(e) + f);
    }

    public String toString() {
        return "Goat";
    }

    public static void main(String[] args) {
        show(Goat::getIt, new Goat(), "baaa");
    }
}
