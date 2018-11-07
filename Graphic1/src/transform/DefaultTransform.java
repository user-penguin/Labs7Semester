package transform;

public class DefaultTransform {

    public static final int SIZE = 4;

    // получить матрицу вращения вокруг оси абсцисс на угол phi
    public static double[][] getRotateMatrixX (double phi) {
        double[][] matrix = {
                {1,              0,             0, 0},
                {0,  Math.cos(phi), Math.sin(phi), 0},
                {0, -Math.sin(phi), Math.cos(phi), 0},
                {0,              0,             0, 1}
        };
        return matrix;
    }

    // получить матрицу вращения вокруг оси ординат на угол zen
    public static double[][] getRotateMatrixY (double zen) {
        double[][] matrix = {
                {Math.cos(zen), 0, -Math.sin(zen), 0},
                {            0, 1,              0, 0},
                {Math.sin(zen), 0,  Math.cos(zen), 0},
                {            0, 0,              0, 1}
        };
        return matrix;
    }

    // получить матрицу вращения вокруг оси аппликат на угол ksi
    public static double[][] getRotateMatrixZ (double ksi) {
        double[][] matrix = {
                { Math.cos(ksi), Math.sin(ksi), 0, 0},
                {-Math.sin(ksi), Math.cos(ksi), 0, 0},
                {             0,             0, 1, 0},
                {             0,             0, 0, 1}
        };
        return matrix;
    }

    // получить матрицу растяжения
    public static double[][] getScaleMatrix (double xScale, double yScale, double zScale) {
        double[][] matrix = {
                {xScale,      0,      0, 0},
                {     0, yScale,      0, 0},
                {     0,      0, zScale, 0},
                {     0,      0,      0, 1}
        };
        return matrix;
    }

    // получить матрицу отражения относительно плоскости XY
    public static double[][] getMirrorXYMatrix () {
        double[][] matrix = {
            {1, 0,  0, 0},
            {0, 1,  0, 0},
            {0, 0, -1, 0},
            {0, 0,  0, 1}
        };
        return matrix;
    }

    // получить матрицу отражения относительно плоскости YZ
    public static double[][] getMirrorYZMatrix () {
        double[][] matrix = {
                {-1, 0,  0, 0},
                { 0, 1,  0, 0},
                { 0, 0,  1, 0},
                { 0, 0,  0, 1}
        };
        return matrix;
    }

    // получить матрицу отражения относительно плоскости ZX
    public static double[][] getMirrorZXMatrix () {
        double[][] matrix = {
                { 1,  0,  0, 0},
                { 0, -1,  0, 0},
                { 0,  0,  1, 0},
                { 0,  0,  0, 1}
        };
        return matrix;
    }

    // получить матрицу переноса на вектора (xTr, yTr, zTr)
    public static double[][] getTransferMatrix (double xTr, double yTr, double zTr) {
        double[][] matrix = {
                {  1,   0,   0, 0},
                {  0,   1,   0 ,0},
                {  0,   0,   1, 0},
                {xTr, yTr, zTr, 1}
        };
        return matrix;
    }
}
