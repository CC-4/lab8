import java.util.Enumeration;
import java.util.Hashtable;

public class Cycles {

    public static void main(String[] args) {
        System.out.println("Test 1:\n");
        test1();
        System.out.println("\nTest 2:\n");
        test2();
        System.out.println("\nTest 3:\n");
        test3();
        System.out.println("\nTest 4:\n");
        test4();
    }

    public static void test1() {
        // Cuando una clase hereda de ella misma
        Graph g = new Graph();
        g.addClass("object", "no_parent");
        g.addClass("main", "object");
        g.addClass("A", "A");
        Hashtable<String, Boolean> v = new Hashtable<String,Boolean>();
        for(Enumeration<String> e = g.getClasses(); e.hasMoreElements();) {
            String c = e.nextElement();
            v.clear();
            System.out.println(c + " -- has cycles --> " + g.hasCycles(c, v));
        }
    }

    public static void test2() {
        // Cuando una clase tiene parents que heredan de ellos mismos
        Graph g = new Graph();
        g.addClass("object", "no_parent");
        g.addClass("main", "object");
        g.addClass("A", "B");
        g.addClass("B", "B");
        Hashtable<String, Boolean> v = new Hashtable<String,Boolean>();
        for(Enumeration<String> e = g.getClasses(); e.hasMoreElements();) {
            String c = e.nextElement();
            v.clear();
            System.out.println(c + " -- has cycles --> " + g.hasCycles(c, v));
        }
    }

    public static void test3() {
        // Cuando todo esta bien :D !
        Graph g = new Graph();
        g.addClass("object", "no_parent");
        g.addClass("main", "object");
        g.addClass("A", "B");
        g.addClass("B", "C");
        g.addClass("C", "object");
        Hashtable<String, Boolean> v = new Hashtable<String,Boolean>();
        for(Enumeration<String> e = g.getClasses(); e.hasMoreElements();) {
            String c = e.nextElement();
            v.clear();
            System.out.println(c + " -- has cycles --> " + g.hasCycles(c, v));
        }
    }

    public static void test4() {
        Graph g = new Graph();
        g.addClass("object", "no_parent");
        g.addClass("A", "B");
        g.addClass("B", "C");
        g.addClass("C", "A");
        Hashtable<String, Boolean> v = new Hashtable<String,Boolean>();
        for(Enumeration<String> e = g.getClasses(); e.hasMoreElements();) {
            String c = e.nextElement();
            v.clear();
            System.out.println(c + " -- has cycles --> " + g.hasCycles(c, v));
        }
    }

}
