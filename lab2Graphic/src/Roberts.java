
import lab1.Main;
import common.*;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;

import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import static java.lang.Math.*;

public class Roberts extends Main {
	
	// Матрица коэффициентов уравнений плоскостей, образующих фигуру
	protected double[][] figureMatrix = null;
	// Списки ребер, относящихся к граням фигуры
	protected ArrayList<HashSet<Integer>> figureFacesEdges = null;
	
	// Текущая подсвечиваемая грань
	protected int currentHighlightedFace = -1;
	
	// Точка входа в программу
	public static void main(String[] args) throws Exception {
		new Roberts();
	}
	
	public Roberts() throws Exception {
		super(new Scanner(new FileReader("points.txt")), new RobertsFrame());
		precalcPlanesMatrix();
	}
	
	// Отрисовка фигуры
	@Override
	protected void paintFigure(Graphics2D g) {
		// Определяем видимые грани
		double[] v = multiplyMatrixes(getCameraDirection(frame.currentPlane).toMatrix(), figureMatrix)[0];
		
		// Определение видимых ребер
		boolean[] visible = new boolean[edges.size()];
		for(int i = 0; i < v.length; i++)
			if(v[i] > 0) for(int edge : figureFacesEdges.get(i)) visible[edge] = true;
		
		// Определение проекций точек
		ArrayList<Point2D> projections = new ArrayList<>(points.size());
		for(Point3D pnt : points) projections.add(projection(pnt, frame.currentPlane));
		
		// Закраска граней фигуры при отладке
		if(currentHighlightedFace != -1) 
			fillFace(g, projections, currentHighlightedFace, v[currentHighlightedFace] > 0);
		
		// Отрисовка ребер
		g.setColor(Color.decode("#84a0ca"));
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));

		for(int i = 0; i < edges.size(); i++) {
			Pair<Integer, Integer> edge = edges.get(i);
			drawLine(visible[i] ? g : g2d, projections.get(edge.left), projections.get(edge.right));
		}
		g2d.dispose();
		
		// Отрисовка точек
		g.setColor(Color.RED);
		for(Point2D point : projections) drawPoint(g, point);
	}
	
	// Применение матрицы ко всем точкам
	@Override
	protected void applyMatrixToAllPoints(double[][] matrix) {
		super.applyMatrixToAllPoints(matrix);
		
		// Дополнительно модифицируем матрицу коэффициентов плоскостей [VT] = [T]^-1[V]
		if(figureMatrix != null) figureMatrix = multiplyMatrixes(inverse(matrix), figureMatrix);
	}
	
	// Закраска грани фигуры
	private void fillFace(Graphics2D g, ArrayList<Point2D> projections, int index, boolean visible) {
		ArrayList<Integer> e = new ArrayList<>(figureFacesEdges.get(index));
		boolean[] used = new boolean[e.size()];
		
		// Упорядочение точек, формирующих грань, в порядке обхода
		ArrayList<Integer> p = new ArrayList<>(e.size() - 1);
		for(int i = 0; i < e.size() - 1; i++)
			for(int j = 0; j < e.size(); j++) {
				if(used[j]) continue;
				Pair<Integer, Integer> edge = edges.get(e.get(j));
				
				if(p.size() == 0) {
					p.addAll(Arrays.asList(edge.left, edge.right));
				} else if(edge.left == (int)p.get(p.size() - 1)) {
					p.add(edge.right);
				} else if(edge.right == (int)p.get(p.size() - 1)) {
					p.add(edge.left);
				} else continue;
				
				used[j] = true;
				break;
			}
			
		// Формирование вершин полигона на экране
		int[] xPoints = new int[p.size()], yPoints = new int[p.size()];
		for(int i = 0; i < p.size(); i++) {
			Point2D converted = convert(projections.get(p.get(i)));
			xPoints[i] = (int)round(converted.x);
			yPoints[i] = (int)round(converted.y);
		}
		
		// Заливка полигона
		g.setColor(Color.decode(visible ? "#cfedc6" : "#f5cfcf"));
		g.fillPolygon(xPoints, yPoints, p.size());
	}
	
	// Вычисление матрицы коэффициентов плоскостей, задающих грани тела
	// Можно вычислить один раз, затем модифицировать [VT] = [T]^-1[V]
	protected void precalcPlanesMatrix() {
		ArrayList<double[]> faces = new ArrayList<>();
		ArrayList<HashSet<Integer>> facesEdges = new ArrayList<>();
		// Итерируемся по всем точкам многогранника и определяем грани
		for(int i = 0; i < points.size(); i++) {
			// Текущая обрабатываемая точка
			Point3D A = points.get(i);
			
			// Определяем все ребра, содержащие данную точку
			ArrayList<Integer> containing = new ArrayList<>();
			for(int j = 0; j < edges.size(); j++) {
				Pair<Integer, Integer> edge = edges.get(j);
				if(edge.left == i || edge.right == i) containing.add(j);
			}
			
			// Рассматриваем все пары ребер, образующих какую-либо грань
			for(int j = 0; j < containing.size() - 1; j++)
				for(int k = j + 1; k < containing.size(); k++) {
					Pair<Integer, Integer> e1 = edges.get(containing.get(j)), e2 = edges.get(containing.get(k));
					// Определяем две другие точки из трёх, принадлежащих этим двум ребрам
					Point3D B = points.get(e1.left == i ? e1.right : e1.left);
					Point3D C = points.get(e2.left == i ? e2.right : e2.left);
					
					// Определение уравнения плоскости через 3 точки
					double a = A.y * (B.z - C.z) + B.y * (C.z - A.z) + C.y * (A.z - B.z);
					double b = A.z * (B.x - C.x) + B.z * (C.x - A.x) + C.z * (A.x - B.x);
					double c = A.x * (B.y - C.y) + B.x * (C.y - A.y) + C.x * (A.y - B.y);
					double d = -(A.x * (B.y * C.z - C.y * B.z) + B.x * (C.y * A.z - A.y * C.z) + C.x * (A.y * B.z - B.y * A.z));
					
					// Сохранение уравнение плоскости и ребер, образовавших её
					faces.add(new double[] { a, b, c, d });
					facesEdges.add(new HashSet<>(Arrays.asList(containing.get(j), containing.get(k))));
				}
		}
		
		boolean[] dirty = new boolean[faces.size()];
		int validFaces = faces.size();
		// Исключаем повторяющиеся грани из списка
		for(int i = 0; i < faces.size(); i++)
			for(int j = 0; j < faces.size(); j++) {
				if(i == j || dirty[i] || dirty[j]) continue;
				double[] a = faces.get(i), b = faces.get(j);
				
				if(isEpsilonEquals(a, b) || isEpsilonEquals(a, multiply(b, -1))) {
					dirty[j] = true;
					facesEdges.get(i).addAll(facesEdges.get(j));
					validFaces--;
				}
			}
		
		// Определяем координаты точки внутри тела через усреднение координат
		double sx = 0, sy = 0, sz = 0;
		for(Point3D p : points) { sx += p.x; sy += p.y; sz += p.z; }
		Point3D center = new Point3D(sx / points.size(), sy / points.size(), sz / points.size(), 1);
		
		figureMatrix = new double[4][validFaces];
		figureFacesEdges = new ArrayList<>(validFaces);
		
		int faceIterator = 0;
		// Формирование матрицы тела
		for(int i = 0; i < faces.size(); i++)
			if(!dirty[i]) {
				double[] face = faces.get(i);
				int sign = sign(face[0] * center.x + face[1] * center.y + face[2] * center.z + face[3]);

				for(int j = 0; j < face.length; j++) figureMatrix[j][faceIterator] = face[j] * sign;
				figureFacesEdges.add(facesEdges.get(i));

				faceIterator++;
			}
	}
	
	// Вывод информации о результатах работы алгоритма
	public void printDebugInfo() {
		System.out.println("Текущая матрица тела: ");
		for(double[] a : figureMatrix) {
			for(double d : a) System.out.print(Utils.fd(d) + " ");
			System.out.println();
		}
		System.out.println("Принадлежащие граням ребра:");
		for(int i = 0; i < figureFacesEdges.size(); i++)
			System.out.println((i + 1) + ": " + figureFacesEdges.get(i));
		System.out.println();
	}
	
	// Вектора наблюдения
	protected Point3D getCameraDirection(Plane plane) {
		int m = Integer.MAX_VALUE;
		switch(plane) {
			case YZ:
				return new Point3D(-m, 0, 0, 1);
			case XZ:
				return new Point3D(0, -m, 0, 1);
			case XY:
				return new Point3D(0, 0, -m, 1);
			case CAB:
				double a = 1D / 2 * cos(PI / 4);
				return new Point3D(m, m, -m / a, 1);
		}
		throw new RuntimeException();
	}
	
	// Одинаковы ли два вектора с точностью
	private boolean isEpsilonEquals(double[] a, double[] b) {
		for(int i = 0; i < a.length; i++)
			if(Math.abs(a[i] - b[i]) > 1e-6) return false;
		return true;
	}
	
	// Умножение вектора на константное число
	private double[] multiply(double[] v, int a) {
		double[] nw = new double[v.length];
		for(int i = 0; i < v.length; i++) nw[i] = v[i] * a;
		return nw;
	}
	
	// Определение знака числа
	protected int sign(double a) {
		if(a > 0) return 1;
		if(a < 0) return -1;
		return 0;
	}
	
	// Получение обратной матрицы
	private double[][] inverse(double[][] matrix) {
		return new LUDecomposition(new Array2DRowRealMatrix(matrix)).getSolver().getInverse().getData();
	}
	
}
