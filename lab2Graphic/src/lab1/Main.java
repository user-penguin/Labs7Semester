package lab1;

import common.Compatibility;
import common.Pair;
import common.Point2D;
import common.Point3D;

import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.*;

public class Main {
	
	// Enum-ы возможных режимов проецирования и осей масштабирования/перемещения
	public enum Plane { YZ, XZ, XY, CAB }
	public enum Axis {A, X, Y, Z, C}
	
	// Список точек пространства и соединяющих из ребер
	protected ArrayList<Point3D> points;
	protected ArrayList<Pair<Integer, Integer>> edges;
	
	// Параметры произвольной оси вращения - точка A и направляющий вектор
	protected double[] RA = new double[] {4, 0, 0};
	protected double[] vector = new double[] {1 / sqrt(3), 1 / sqrt(3), 1 / sqrt(3)};
	
	// Окно с отрисовкой
	protected Frame frame; 
	
	// Точка входа в приложение
	public static void main(String[] args) throws Exception {
		new Main(new Scanner(new FileReader("points.txt")), new Frame());
	}
	
	protected Main(Scanner in, Frame f) throws Exception {
		
		frame = f;
		frame.setParent(this);
		
		// Ввод координат точек
		int n = in.nextInt();
		points = new ArrayList<>(n);
		for(int i = 0; i < n; i++) points.add(new Point3D(in.nextInt(), in.nextInt(), in.nextInt(), 1));
		
		// Ввод ребер в виде индексов соединяемых точек
		int m = in.nextInt();
		edges = new ArrayList<>(m);
		for(int i = 0; i < m; i++) edges.add(Pair.of(in.nextInt(), in.nextInt()));
		
		// Отображение окна приложения
		frame.setVisible(true);
	}
	
	// Отрисовка области окна приложения
	public void paintScene(Graphics2D g) {
		// Сглаживание
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Отрисовка осей координат
		g.setColor(Color.decode("#CCCCCC"));
		Plane p = frame.currentPlane;
		
		drawLine(g, projection(new Point3D(0, 0, 100, 1), p), projection(new Point3D(0, 0, -100, 1), p));
		drawLine(g, projection(new Point3D(0, 100, 0, 1), p), projection(new Point3D(0, -100, 0, 1), p));
		drawLine(g, projection(new Point3D(100, 0, 0, 1), p), projection(new Point3D(-100, 0, 0, 1), p));
		
		// Отрисовка прямой произвольного вращения
		if(frame.currentAxis == Axis.C) {
			g.setColor(Color.MAGENTA);
			Point3D A = new Point3D(RA[0] + vector[0] * 100, RA[1] + vector[1] * 100, RA[2] + vector[2] * 100, 1);
			Point3D B = new Point3D(RA[0] - vector[0] * 100, RA[1] - vector[1] * 100, RA[2] - vector[2] * 100, 1);
			drawLine(g, projection(A, p), projection(B, p));
		}
		
		paintFigure(g);
	}
	
	// Отрисовка фигуры
	protected void paintFigure(Graphics2D g) {
		// Определение проекций точек
		ArrayList<Point2D> projections = new ArrayList<>(points.size());
		for(Point3D pnt : points) projections.add(projection(pnt, frame.currentPlane));

		// Отрисовка ребер
		g.setColor(Color.decode("#84a0ca"));
		for(Pair<Integer, Integer> edge : edges) drawLine(g, projections.get(edge.left), projections.get(edge.right));

		// Отрисовка точек
		g.setColor(Color.RED);
		for(Point2D point : projections) drawPoint(g, point);
	}
	
	// Отрисовка точки
	protected void drawPoint(Graphics2D g, Point2D point) {
		Point2D p = convert(point);
		g.drawOval((int)round(p.x) - 2, (int)round(p.y) - 2, 4, 4);
		g.fillOval((int)round(p.x) - 2, (int)round(p.y) - 2, 4, 4);
	}
	
	// Отрисовка линии
	protected void drawLine(Graphics2D g, Point2D from, Point2D to) {
		Point2D a = convert(from), b = convert(to);
		g.drawLine((int)round(a.x), (int)round(a.y), (int)round(b.x), (int)round(b.y));
	}
	
	// Проекция точки на заданную плоскость
	protected Point2D projection(Point3D point, Plane plane) {
		switch(plane) {
			case YZ:
				// Формирование матрицы преобразование
				double[][] matrixYZ = new double[][]{
						{0, 0, 0, 0},
						{0, 1, 0, 0},
						{0, 0, 1, 0},
						{0, 0, 0, 1}
				};
				// Умножение матриц и вычисление 2D-координат точки
				double[] yz = multiplyMatrixes(point.toMatrix(), matrixYZ)[0];
				return new Point2D(yz[1] / yz[3], yz[2] / yz[3]);
			case XZ:
				double[][] matrixXZ = new double[][]{
						{1, 0, 0, 0},
						{0, 0, 0, 0},
						{0, 0, 1, 0},
						{0, 0, 0, 1}
				};
				double[] xz = multiplyMatrixes(point.toMatrix(), matrixXZ)[0];
				return new Point2D(xz[0] / xz[3], xz[2] / xz[3]);
			case XY:
				double[][] matrixXY = new double[][]{
						{1, 0, 0, 0},
						{0, 1, 0, 0},
						{0, 0, 0, 0},
						{0, 0, 0, 1}
				};
				double[] xy = multiplyMatrixes(point.toMatrix(), matrixXY)[0];
				return new Point2D(xy[0] / xy[3], xy[1] / xy[3]);
			case CAB:
				double a = 1D / 2 * cos(PI / 4);
				double[][] matrixCAB = new double[][]{
						{1, 0, 0, 0},
						{0, 1, 0, 0},
						{a, a, 0, 0},
						{0, 0, 0, 1}
				};
				double[] cab = multiplyMatrixes(point.toMatrix(), matrixCAB)[0];
				return new Point2D(cab[0] / cab[3], cab[1] / cab[3]);
		}
		return null;
	}
	
	// Масштабирование всех точек с заданным коэффициентом
	public void resize(double coeff) {
		Axis s = frame.currentScale;
		// Формирование матрицы преобразования
		double[][] matrix = new double[][] {
				{s == Axis.A || s == Axis.X ? coeff : 1, 0, 0, 0},
				{0, s == Axis.A || s == Axis.Y ? coeff : 1, 0, 0},
				{0, 0, s == Axis.A || s == Axis.Z ? coeff : 1, 0},
				{0, 0, 0, 1}
		};
		// Применение матрицы ко всем точкам
		applyMatrixToAllPoints(matrix);
	}
	
	// Вращение всех точек вокруг оси на заданный угол (в радианах)
	public void rotate(Axis axis, double f) {
		double[][] matrix = null;
		// Формирование матрицы преобразования
		switch(axis) {
			case X: 
				matrix = new double[][] {
						{1, 0, 0, 0},
						{0, cos(f), sin(f), 0},
						{0, -sin(f), cos(f), 0},
						{0, 0, 0, 1}
				};
				break;
			case Y:
				matrix = new double[][] {
						{cos(f), 0, -sin(f), 0},
						{0, 1, 0, 0},
						{sin(f), 0, cos(f), 0},
						{0, 0, 0, 1}
				};
				break;
			case Z:
				matrix = new double[][] {
						{cos(f), sin(f), 0, 0},
						{-sin(f), cos(f), 0, 0},
						{0, 0, 1, 0},
						{0, 0, 0, 1}
				};
				break;
			case C:
				// Матрица вращения вокруг произвольной прямой, заданной LA и vector
				// Смещение точки RA в центр координат
				move(-RA[0], -RA[1], -RA[2]);
				// Компоненты направляющего вектора
				double l = vector[0], m = vector[1], n = vector[2];
				// Формирование матрицы преобразования
				matrix = new double[][] {
						{l * l + cos(f) * (1 - l * l), l * (1 - cos(f)) * m + n * sin(f), l * (1 - cos(f)) * n - m * sin(f), 0},
						{l * (1 - cos(f)) * m - n * sin(f),	m * m + cos(f) * (1 - m * m), m * (1 - cos(f)) * n + l * sin(f), 0},
						{l * (1 - cos(f)) * n + m * sin(f), m * (1 - cos(f)) * n - l * sin(f), n * n + cos(f) * (1 - n * n), 0},
						{0, 0, 0, 1}
				};
				// Применение матрицы ко всем точкам
				applyMatrixToAllPoints(matrix);
				// Возврат к начальной системе координат
				move(RA[0], RA[1], RA[2]);
				return;
		}
		// Применение матрицы ко всем точкам
		applyMatrixToAllPoints(matrix);
	}
	
	// Перемещение всех точек в заданном направлении на заданное расстояние
	public void move(Axis axis, double v) {
		double a = axis == Axis.X ? v : 0;
		double b = axis == Axis.Y ? v : 0;
		double c = axis == Axis.Z ? v : 0;
		move(a, b, c);
	}
	
	// Перемещение всех точек на заданные значения по осям
	public void move(double a, double b, double c) {
		// Формирование матрицы преобразования
		double[][] matrix = new double[][] {
				{1, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 0, 1, 0},
				{a, b, c, 1}
		};
		// Применение матрицы ко всем точкам
		applyMatrixToAllPoints(matrix);
	}
	
	// Применение матрицы ко всем точкам
	protected void applyMatrixToAllPoints(double[][] matrix) {
		ArrayList<Point3D> changed = new ArrayList<>(points.size());
		for(Point3D p : points) changed.add(new Point3D(multiplyMatrixes(p.toMatrix(), matrix)[0]));

		points.clear();
		points.addAll(changed);
	}
	
	// Преобразование двухмерных координат в координаты на канве
	protected Point2D convert(Point2D p) {
		double x = p.x * frame.SCALE + frame.S_WIDTH / 2;
		double y = - p.y * frame.SCALE + frame.S_HEIGHT / 2;
		return new Point2D(x, y);
	}
	
	// Перемножение двух матриц
	protected double[][] multiplyMatrixes(double[][] a, double[][] b) {
		double[][] result = new double[a.length][b[0].length];
		for(int i = 0; i < result.length; i++)
			for(int j = 0; j < result[i].length; j++)
				for(int k = 0; k < a[i].length; k++) result[i][j] += a[i][k] * b[k][j];
		return result;
	}
	
}
