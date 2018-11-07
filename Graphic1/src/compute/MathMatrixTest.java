package compute;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class MathMatrixTest {

    @Test
    public void multipleMatrixOnMatrix() {
        double [][] actual = {
                {14, 10},
                {32, 31},
                {11, 9}
        };

        double a[][] = {
                {1, 2, 3},
                {4, 5, 6},
                {1, 2, 2}
        };

        double b[][] = {
                {1, 5},
                {2, 1},
                {3, 1}
        };

        double [][] expected = MathMatrix.multiple(a, b);
        assertArrayEquals(actual, expected);
    }

    @Test
    public void multipleMatrixOnVector() {
        double [][] actual = {
                {14},
                {32},
                {11}
        };

        double a[][] = {
                {1, 2, 3},
                {4, 5, 6},
                {1, 2, 2}
        };

        double b[][] = {
                {1},
                {2},
                {3}
        };

        double [][] expected = MathMatrix.multiple(a, b);
        assertArrayEquals(actual, expected);
    }

    @Test
    public void multipleVectorOnMatrix() {
        double [][] actual = {
                {14, 32, 0}
        };

        double a[][] = {
                {14, 32, 12}
        };

        double b[][] = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };

        double [][] expected = MathMatrix.multiple(a, b);
        assertArrayEquals(actual, expected);
    }
}