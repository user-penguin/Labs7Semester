package transform;

public class DefaultTransform {

    private static final int SIZE = 4;

    // получить матрицу вращения вокруг оси абсцисс на угол phi
    public static double[][] getRotateMatrixX (double phi) {
        double[][] matrix = {
                {1,              0,             0, 0},
                {0,  Math.cos(phi), Math.sin(phi), 0},
                {0, -Math.sin(phi), Math.cos(phi), 0},
                {0,              0,             0, 0}
        };
        return matrix;
    }
}
