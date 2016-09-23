package com.lq.ren.many.calendar.generic921;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Author lqren on 16/9/21.
 */
public class GenericMethodTest {
    private static final String TAG = "GENERIC";

    public void main() {
        /**未使用Generic*/
        Person person = new Person();
        person.setPerson("Li");
        person.setPerson(3);
        Log.i(TAG, "Person: " + person.getPerson()); // 类型转换,可能会引起ClassCastException

        /**使用Generic,
         * tips：可以使用@SuppressWarnings("rawtypes")来抑制编译器弹出警告。
         */
        Woker<String> woker = new Woker<>();
        woker.setWoker("woker1");
//        woker.setWoker(1); // 无法编译通过
        Log.i(TAG, "Woker: " + woker.getWoker()); // 不会类型转换


        Integer[] intArray = {1,2,3,4,5};
        Double[] doubleArray = {1.1, 2.2, 3.3, 4.4};
        Character[] charArray = {'h', 'e', 'l', 'o'};
        Log.v(TAG, "int 类型的最大值:" + maximum(intArray));
        Log.v(TAG, "double 类型的最大值:" + maximum(doubleArray));
        Log.v(TAG, "char 类型的最大值:" + maximum(charArray));

        Object[] objs = new Object[100];
        Collection<Object> collection = new ArrayList<>();
        this.fromArrayToCollection(objs, collection);
    }

    public <E> void printArray(E[] inputArray) {
        for (E element : inputArray) {
            Log.e(TAG, "element : " + element);
        }
    }

    public <T extends Comparable<T>> T maximum(T[] ts) {
        T max = ts[0];
        for (int i = 1; i < ts.length; i++) {
            if (ts[i].compareTo(ts[i-1]) > 0) {
                max = ts[i];
            }
        }
        return max;
    }

    /**
     * Tips 1 定义方法所用的泛型参数需在修饰符后,如 public static <T> , 如果有多个,则<T1, T2> 或者 <K, V>
     * 2 不建议在泛型中添加其他类型, 可能会引起编译错误, 如c.add(new Object()) X
     * 3 泛型的调用 this.<Object>fromArrayToCollection(objs, collection); 方法前声明了泛型类型Object,
     * 不过因为编译器可以推断这个泛型类型,因此可以去掉,如this.fromArrayToCollection(objs, collection);
     */
    public <T> void fromArrayToCollection(T[] a, Collection<T> c) {
        for (T t : a) {
            c.add(t);
        }
    }


    public class Person {
        private Object person;

        public Object getPerson() {
            return person;
        }

        public void setPerson(Object person) {
            this.person = person;
        }
    }

    public class Woker<T> {
        private T woker;

        public T getWoker() {
            return woker;
        }

        public void setWoker(T woker) {
            this.woker = woker;
        }
    }
}
