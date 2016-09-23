package com.lq.ren.many.calendar.generic921;

import android.util.Log;

/**
 * Author lqren on 16/9/21.
 */
public class Box<T> {
    private static final String TAG = "GENERIC";
    private T t;

    public void add(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

    public <U extends Number> void inspect(U u) {
        Log.i(TAG, "T: " + t.getClass().getName());
        Log.i(TAG, "U: " + u.getClass().getName());
    }

    public static void main() {
        Box<Integer> intBox = new Box<>();
        Box<String> strBox = new Box<>();

        intBox.add(1);
        strBox.add("haha");

        Log.i(TAG, "int 类型 "+ intBox.get());
        Log.i(TAG, "String 类型 "+ strBox.get());

        //strBox.inspect("abc"); // 不能编译通过,U必须是Number或其子类
        intBox.inspect(new Integer(1));
    }


    /**interface 与 class的泛型应用类似 */
    public interface List<E> {
        void add(E e);
        Iterator<E> iterator();
    }

    public interface Iterator<E> {
        E next();
        boolean hasNext();
    }


    public class NumberTest<T extends Integer> {
        private T num;
        public NumberTest(T num) {
            this.num = num;
        }

        public boolean isOdd() {
            return num.intValue() % 2 == 1;
        }
    }

    /**既然设定了泛型参数的界限, 是否可以调用BoundingType(上指Number)的相对应的方法
     * 添加多个限制范围,如 <T extends A & B & C>
     * 如果用一个类NumberTest作为限定，它必须是限定列表中的第一个
     */
    //class D <T extends  List & Iterator & NumberTest > {
        //此时就会编译出错,需把NumberTest放到第一个
    //}

    public <T extends Comparable<T>> int countGreater(T[] array, T elem) {
        int count = 0;
        for (T t : array) {
            if (t.compareTo(elem) > 0) {
                ++count;
            }

            //if (t > elem) {//编译出错,因为只有基本数据类型才能使用>
                ++count;
            //}
        }
        return count;
    }

    interface Comparable<T> {
        int compareTo(T t);
    }

    /**也可以选择添加界限Comparator<T,T>,只不过此界限需要两个参数而已 */

//    public <T extends ComparableTwo<T1, T2>> int count(T[] array, T elem) {
//        return 1;
//    }

    interface ComparableTwo<T1, T2> {
        int compareTo(T1 t1, T2 t2);
    }
}
