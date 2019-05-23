package transform;

public class OrtographProjection {

    // матрица ортографического проектирования на плоскость YZ
    public static double[][] getProjectionYZ () {
        double[][] matrix = {
                {0, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        return matrix;
    }

    // матрица ортографического проектирования на плоскость XY
    public static double[][] getProjectionXY () {
        double[][] matrix = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 1}
        };
        return matrix;
    }

    // матрица ортографического проектирования на плоскость XZ
    public static double[][] getProjectionXZ () {
        double[][] matrix = {
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        return matrix;
    }
}
