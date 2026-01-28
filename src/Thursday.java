import java.util.Arrays;

public class Thursday {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5};
        int[] b = {9, 8, 7};
        mystery(a, b);
        System.out.println(a.length + " " + a[0]);
    }

    public static void mystery(int[] x, int[] y) {
        int[] temp = x;
        x = y;
        y = temp;
        x[0] = y[y.length - 1];
        y[0] = x[x.length - 1];
    }
}

