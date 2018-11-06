package math;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class MathTest {

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

        double [][] expected = math.Math.multiple(a, b);
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

        double [][] expected = math.Math.multiple(a, b);
        assertArrayEquals(actual, expected);
    }
}