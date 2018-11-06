package compute;

public class Math {

    // умножение матриц
    public static double[][] multiple (double a[][], double b[][]) {
        // количество строк и столбцов в новой матрице
        int newRows = a.length;
        int newColumns = b[0].length;
        // итоговая матрица
        double[][] matrix = new double[newRows][newColumns];
        for (int i = 0; i < b[0].length; i++) {
            for (int j = 0; j < a.length; j++) {
                double sum = 0;
                for (int k = 0; k < a[0].length; k++) {
                    sum += a[j][k] * b[k][i];
                }
                matrix[j][i] = sum;
            }
        }
        return matrix;
    }
}
