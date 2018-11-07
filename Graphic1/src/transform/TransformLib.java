package transform;

import compute.Math;

public class TransformLib {

    // Отжать у матрицы z-координату
    public static double[][] calculateOrto (double[][] stock) {
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < stock.length; i++) {
            dots[i] = Math.multiple(stock[i], OrtographProjection.getProjectionXY());
        }
        return dots;
    }

    // Расятнуть x,y,z
    public static double[][] calculateScale(double[][] stock, double xS, double yS, double zS) {
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < stock.length; i++) {
            dots[i] = Math.multiple(stock[i], DefaultTransform.getScaleMatrix(xS, yS, zS));
        }
        return dots;
    }
    // повернуть x
    public static double[][] calculateRotateX(double[][] stock, double angle) {
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < stock.length; i++) {
            dots[i] = Math.multiple(stock[i], DefaultTransform.getRotateMatrixX(angle));
        }
        return dots;
    }
    // повернуть y
    public static double[][] calculateRotateY(double[][] stock, double angle) {
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < stock.length; i++) {
            dots[i] = Math.multiple(stock[i], DefaultTransform.getRotateMatrixY(angle));
        }
        return dots;
    }
    // повернуть z
    public static double[][] calculateRotateZ(double[][] stock, double angle) {
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < stock.length; i++) {
            dots[i] = Math.multiple(stock[i], DefaultTransform.getRotateMatrixZ(angle));
        }
        return dots;
    }
    // переместить
    public static double[][] calculateTransfer(double[][] stock, double xTr, double yTr, double zTr) {
        double[][] dots = new double[stock.length][];
        for (int i = 0; i < stock.length; i++) {
            dots[i] = Math.multiple(stock[i], DefaultTransform.getTransferMatrix(xTr, yTr, zTr));
        }
        return dots;
    }
}
