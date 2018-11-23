package common;

import javax.swing.*;
import java.util.Enumeration;

public class Compatibility {
	
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
		Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if(value != null && value instanceof javax.swing.plaf.FontUIResource) UIManager.put(key, f);
		}
	}

}
