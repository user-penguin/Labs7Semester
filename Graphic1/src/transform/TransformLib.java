package transform;

import compute.MathMatrix;

public class TransformLib {

    // Отжать у матрицы z-координату
    public static double[][] calculateOrto (double[][] stock) {
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < stock.length; i++) {
            dots[i] = MathMatrix.multiple(stock[i], OrtographProjection.getProjectionXY());
        }
        return dots;
    }

    // Расятнуть x,y,z
    public static double[][] calculateScale(double[][] stock, double xS, double yS, double zS) {
        // переместить к началу координат
        double middleX = calculateMiddleX(stock);
        double middleY = calculateMiddleY(stock);
        double middleZ = calculateMiddleZ(stock);
        // передвинутая на начало координат матрица
        double transferMatrix[][] = calculateTransfer(stock, -middleX, -middleY, -middleZ);
        // делаем растяжение
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < stock.length; i++) {
            dots[i] = MathMatrix.multiple(transferMatrix[i], DefaultTransform.getScaleMatrix(xS, yS, zS));
        }
        dots = calculateTransfer(dots, middleX, middleY, middleZ);
        return dots;
    }

    // растянуть относительно точки x0,y0,z0
    public static double[][] calculateScaleByDot(double[][] stock, double xS, double yS, double zS,
                                                  double x0, double y0, double z0) {
        // создадим массив с точками, где начало координат смещено на x0, y0, z0
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < stock.length; i++) {
            dots[i] = new double[]{stock[i][0] - x0, stock[i][1] - y0, stock[i][2] - z0, 1};
        }

        // растягиваем фигуру
        dots = calculateScale(dots, xS, yS, zS);

        //возвращаем координаты на место
        for (int i = 0; i < stock.length; i++) {
            dots[i][0] += x0;
            dots[i][1] += y0;
            dots[i][2] += z0;
        }
        return dots;
    }

    // повернуть x
    public static double[][] calculateRotateX(double[][] stock, double angle) {
        double middleX = calculateMiddleX(stock);
        double middleY = calculateMiddleY(stock);
        double middleZ = calculateMiddleZ(stock);
        // передвинутая на начало координат матрица
        double transferMatrix[][] = calculateTransfer(stock, -middleX, -middleY, -middleZ);

        double[][] dots = new double[stock.length][];
        for (int i = 0; i < transferMatrix.length; i++) {
            dots[i] = MathMatrix.multiple(transferMatrix[i], DefaultTransform.getRotateMatrixX(Math.PI*angle/180));
        }

        dots = calculateTransfer(dots, middleX, middleY, middleZ);
        return dots;
    }

    // повернуть y
    public static double[][] calculateRotateY(double[][] stock, double angle) {
        double middleX = calculateMiddleX(stock);
        double middleY = calculateMiddleY(stock);
        double middleZ = calculateMiddleZ(stock);
        // передвинутая на начало координат матрица
        double transferMatrix[][] = calculateTransfer(stock, -middleX, -middleY, -middleZ);
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < transferMatrix.length; i++) {
            dots[i] = MathMatrix.multiple(transferMatrix[i], DefaultTransform.getRotateMatrixY(Math.PI*angle/180));
        }
        dots = calculateTransfer(dots, middleX, middleY, middleZ);
        return dots;
    }

    // повернуть z
    public static double[][] calculateRotateZ(double[][] stock, double angle) {
        double middleX = calculateMiddleX(stock);
        double middleY = calculateMiddleY(stock);
        double middleZ = calculateMiddleZ(stock);
        // передвинутая на начало координат матрица
        double transferMatrix[][] = calculateTransfer(stock, -middleX, -middleY, -middleZ);
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < transferMatrix.length; i++) {
            dots[i] = MathMatrix.multiple(transferMatrix[i], DefaultTransform.getRotateMatrixZ(Math.PI*angle/180));
        }
        dots = calculateTransfer(dots, middleX, middleY, middleZ);
        return dots;
    }

    // отобразить относительно XY
    public static double[][] calculateReflectionXY(double[][] stock) {
        double middleX = calculateMiddleX(stock);
        double middleY = calculateMiddleY(stock);
        double middleZ = calculateMiddleZ(stock);
        // передвинутая на начало координат матрица
        double transferMatrix[][] = calculateTransfer(stock, -middleX, -middleY, -middleZ);
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < transferMatrix.length; i++) {
            dots[i] = MathMatrix.multiple(transferMatrix[i], DefaultTransform.getMirrorXYMatrix());
        }
        dots = calculateTransfer(dots, middleX, middleY, middleZ);
        return dots;
    }

    // отобразить относительно YZ
    public static double[][] calculateReflectionYZ(double[][] stock) {
        double middleX = calculateMiddleX(stock);
        double middleY = calculateMiddleY(stock);
        double middleZ = calculateMiddleZ(stock);
        // передвинутая на начало координат матрица
        double transferMatrix[][] = calculateTransfer(stock, -middleX, -middleY, -middleZ);
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < transferMatrix.length; i++) {
            dots[i] = MathMatrix.multiple(transferMatrix[i], DefaultTransform.getMirrorYZMatrix());
        }
        dots = calculateTransfer(dots, middleX, middleY, middleZ);
        return dots;
    }

    // отобразить относительно ZX
    public static double[][] calculateReflectionZX(double[][] stock) {
        double middleX = calculateMiddleX(stock);
        double middleY = calculateMiddleY(stock);
        double middleZ = calculateMiddleZ(stock);
        // передвинутая на начало координат матрица
        double transferMatrix[][] = calculateTransfer(stock, -middleX, -middleY, -middleZ);
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < transferMatrix.length; i++) {
            dots[i] = MathMatrix.multiple(transferMatrix[i], DefaultTransform.getMirrorZXMatrix());
        }
        dots = calculateTransfer(dots, middleX, middleY, middleZ);
        return dots;
    }

    // переместить
    public static double[][] calculateTransfer(double[][] stock, double xTr, double yTr, double zTr) {
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < stock.length; i++) {
            dots[i] = MathMatrix.multiple(stock[i], DefaultTransform.getTransferMatrix(xTr, yTr, zTr));
        }
        return dots;
    }

    /** Инструменты для центрирования перд другими действиями */
    // подпрограмма расчёта минимальной заданной координаты
    private static double calculateMin (double dots[][], int coordinate) {
        double min = dots[0][coordinate];
        for (int i = 1; i < dots.length; i++) { if (dots[i][coordinate] < min) min = dots[i][coordinate];}
        return min;
    }

    // подпрограмма расчёта максимальной заданной координаты
    private static double calculateMax (double dots[][], int coordinate) {
        double max = dots[0][coordinate];
        for (int i = 1; i < dots.length; i++) { if (dots[i][coordinate] > max) max = dots[i][coordinate];}
        return max;
    }

    // вычисление средней точки по X
    public static double calculateMiddleX (double dots[][]) {
        double min = calculateMin(dots, 0);
        double max = calculateMax(dots, 0);
        return (min + max) / 2;
    }
    // вычисление средней точки по Y
    public static double calculateMiddleY (double dots[][]) {
        double min = calculateMin(dots, 1);
        double max = calculateMax(dots, 1);
        return (min + max) / 2;
    }

    // вычисление средней точки по Z
    public static double calculateMiddleZ (double dots[][]) {
        double min = calculateMin(dots, 2);
        double max = calculateMax(dots, 2);
        return (min + max) / 2;
    }
}
