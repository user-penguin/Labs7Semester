package lab1;

import common.Compatibility;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JFrame implements ActionListener, MouseWheelListener {
	
	// Размеры области окна для отрисовки и коэффициент масштабирования изображения
	public final int S_WIDTH = 500;
	public final int S_HEIGHT = 330;
	public final int SCALE = 6;
	
	// Стартовые значения настраиваемых опций
	public Main.Plane currentPlane = Main.Plane.YZ;
	public Main.Axis currentAxis = Main.Axis.X;
	public Main.Axis currentScale = Main.Axis.A;
	public Main.Axis currentMoveAxis = Main.Axis.X;
	
	// Объект главного класса для передачи отрисовки
	protected Main parent = null;
	
	public Frame() {
		super("Компьютерная графика - Лабораторная работа #1");
		this.setResizable(false);
		this.setLayout(null);
		
		Compatibility.setUIFont(new FontUIResource("Verdana", Font.PLAIN, 11));
		this.getContentPane().setPreferredSize(new Dimension(710, 350));
		
		JPanel scene = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				if(parent != null) parent.paintScene((Graphics2D)g);
			}
		};
		scene.setBackground(Color.WHITE);
		scene.setBounds(10, 10, S_WIDTH, S_HEIGHT);
		scene.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.add(scene);
		
		JLabel planel = new JLabel("Проекция на плоскость:");
		planel.setBounds(540, 10, 200, 20);
		this.add(planel);
		
		ButtonGroup plane = new ButtonGroup();
		addRadioButton("YZ", "planeYZ", plane, 540, 30, 40, 20, true);
		addRadioButton("XZ", "planeXZ", plane, 590, 30, 45, 20, false);
		addRadioButton("XY", "planeXY", plane, 640, 30, 40, 20, false);
		addRadioButton("Кабинетная", "planeCAB", plane, 540, 50, 100, 20, false);
		
		int of = 20;
		
		JLabel axisl = new JLabel("Ось вращения:");
		axisl.setBounds(540, 70 + of, 200, 20);
		this.add(axisl);
		
		ButtonGroup axis = new ButtonGroup();
		addRadioButton("X", "axisX", axis, 540, 90 + of, 36, 20, true);
		addRadioButton("Y", "axisY", axis, 575, 90 + of, 36, 20, false);
		addRadioButton("Z", "axisZ", axis, 610, 90 + of, 36, 20, false);
		addRadioButton("C", "axisC", axis, 645, 90 + of, 36, 20, false);

		JLabel scalel = new JLabel("Ось масштабирования:");
		scalel.setBounds(540, 130 + of, 200, 20);
		this.add(scalel);

		ButtonGroup scale = new ButtonGroup();
		addRadioButton("*", "scaleA", scale, 540, 150 + of, 36, 20, true);
		addRadioButton("X", "scaleX", scale, 575, 150 + of, 36, 20, false);
		addRadioButton("Y", "scaleY", scale, 610, 150 + of, 36, 20, false);
		addRadioButton("Z", "scaleZ", scale, 645, 150 + of, 36, 20, false);

		JLabel movel = new JLabel("Ось перемещения:");
		movel.setBounds(540, 190 + of, 200, 20);
		this.add(movel);

		ButtonGroup move = new ButtonGroup();
		addRadioButton("X", "moveX", move, 540, 210 + of, 36, 20, true);
		addRadioButton("Y", "moveY", move, 590, 210 + of, 36, 20, false);
		addRadioButton("Z", "moveZ", move, 640, 210 + of, 36, 20, false);
		
		JLabel help = new JLabel("<html><center>Масштаб: <i>колесо мыши</i><br/>" +
				"Вращение: ← →<br/>" +
				"Перемещение: ↑ ↓</center></html>");
		help.setForeground(Color.decode("#5c9862"));
		help.setBounds(540, 255, 200, 100);
		this.add(help);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.addMouseWheelListener(this);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this::onKeyEvent);
	}
	
	public void setParent(Main m) {
		parent = m;
	}
	
	protected void addRadioButton(String text, String action, ButtonGroup group, int x, int y, int w, int h, boolean f) {
		JRadioButton bt = new JRadioButton(text);
		bt.setActionCommand(action);
		bt.addActionListener(this);
		bt.setBounds(x, y, w, h);
		if(f) bt.setSelected(true);
		group.add(bt);
		this.add(bt);
	}
	
	protected Object get(Object[] values, String prefix, String c) {
		String target = c.substring(prefix.length());
		for(Object o : values) if(o.toString().equals(target)) return o;
		throw new RuntimeException("Unsupported enum value");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String c = e.getActionCommand();
		if(c.startsWith("plane")) currentPlane = (Main.Plane)get(Main.Plane.values(), "plane", c);
		if(c.startsWith("axis")) currentAxis = (Main.Axis)get(Main.Axis.values(), "axis", c);
		if(c.startsWith("scale")) currentScale = (Main.Axis)get(Main.Axis.values(), "scale", c);
		if(c.startsWith("move")) currentMoveAxis = (Main.Axis)get(Main.Axis.values(), "move", c);
		repaint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		parent.resize(e.getWheelRotation() < 0 ? 1.1 : 0.9);
		repaint();
	}
	
	protected boolean onKeyEvent(KeyEvent e) {
		if(e.getID() == KeyEvent.KEY_PRESSED)
			if(e.getKeyCode() == 37 || e.getKeyCode() == 39) {
				parent.rotate(currentAxis, Math.PI / 180 * 3 * (e.getKeyCode() == 39 ? -1 : 1));
				repaint();
				return true;
			} else if(e.getKeyCode() == 38 || e.getKeyCode() == 40) {
				parent.move(currentMoveAxis, e.getKeyCode() == 38 ? 1 : -1);
				repaint();
				return true;
			}
		return false;
	}
	
}
