/***************************************************************************
                           FreeMindSplash, taken from GanttSplash.java  -  description
                             -------------------
    begin                : dec 2002
    copyright            : (C) 2002 by Thomas Alexandre
    email                : alexthomas(at)ganttproject.org
 ***************************************************************************/

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/

package freemind.main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;



/**
 * Class to put a splash before lunch the soft
 */

class FreeMindSplash extends JFrame {

    private final FreeMindMain frame;


    public FreeMindSplash(final FreeMindMain frame){
    	super("FreeMind");
        this.frame = frame;
    	
    	ImageIcon icon = new ImageIcon(frame.getResource(
			"images/FreeMindWindowIcon.png"));
    	setIconImage(icon.getImage());	//set the ganttproject icon
    	setDefaultLookAndFeelDecorated(false);
    	setUndecorated(true);
    	getRootPane().setWindowDecorationStyle(JRootPane.NONE); //set no border
        
    	ImageIcon splashImage = new ImageIcon(frame.getResource("images/splash.JPG"));
        JLabel l = new JLabel(splashImage) {
        	public void paint (Graphics g) {
        		super.paint(g);
        		Graphics2D g2 = (Graphics2D) g;
        		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        		Font font = new Font("Arial", Font.BOLD + Font.ITALIC, 22);
        		g2.setFont(font);
        		g2.setColor(Color.white);
        		g2.drawString(frame.getFreemindVersion(), 0 /*(int) (getSize().getWidth()/3)*/, (int)(getSize().getHeight()/3));
        		g2.setColor(Color.black);
        		g2.drawString(frame.getFreemindVersion(), 1 /*(int) (getSize().getWidth()/3)*/, (int)(getSize().getHeight()/3)+1);
        	}
        };
        
        
        getContentPane().add(l, BorderLayout.CENTER);
        pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = l.getPreferredSize();

        // Put image at the middle of the screen
        setLocation(screenSize.width/2 - (labelSize.width/2),
                    screenSize.height/2 - (labelSize.height/2));
		
    }
    

    public void close() {
          setVisible(false);
    	  dispose();
    }

}

