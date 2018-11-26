import lab1.Frame;
import lab1.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class RobertsFrame extends Frame {
	
	public RobertsFrame() {
		super();
		this.setTitle("Совместная разработка Kit & Dimasta");
		changeDefaultProjection(Main.Plane.CAB);
	}
	
	private void changeDefaultProjection(Main.Plane plane) {
		this.currentPlane = plane;
		for(Component c : this.getContentPane().getComponents())
			if(c instanceof JRadioButton) {
				JRadioButton radio = (JRadioButton)c;
				if(radio.getActionCommand().equals("plane" + plane.toString())) radio.setSelected(true);
			}
	}
	
	@Override
	protected boolean onKeyEvent(KeyEvent e) {
		boolean sp = super.onKeyEvent(e);
		if(sp) return true;
		
		if(e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_Q) {
			Roberts roberts = (Roberts)this.parent;
			if(roberts.currentHighlightedFace != -1) return false;
			roberts.printDebugInfo();
			
			final Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					roberts.currentHighlightedFace++;
					if(roberts.currentHighlightedFace == roberts.figureMatrix[0].length) {
						roberts.currentHighlightedFace = -1;
						timer.cancel();
					}
					repaint();
				}
			}, 0, 500);
			return true;
		}
		return false;
	}
	
}
