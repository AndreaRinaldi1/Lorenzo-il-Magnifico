package it.polimi.ingsw.GC_28.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class View{


	
	public static void main(String[] args) {
		createViewComponents();
	
	}
	
	public static void createViewComponents(){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	/*	frame.getContentPane().setLayout(new GridLayout(4,4));
		for(int i = 0; i < 17; i++){
			JButton b = new JButton(""+i);
			b.setBorder(BorderFactory.createEmptyBorder());

			b.setContentAreaFilled(false);
			frame.getContentPane().add(b);
		}*/
		BufferedImage i = null;
		try {
			i = ImageIO.read(new File("gameBoard.jpg"));
			frame.getContentPane().add(new JPanelWithBackground(i));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setSize(i.getWidth()*975/i.getHeight(), 995);
		frame.setVisible(true);
		
		
	}
	
	
	
}

class JPanelWithBackground extends JPanel{
	private BufferedImage backgroundImage;
	
	public JPanelWithBackground(BufferedImage i) throws IOException{
		backgroundImage = i;
	}
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(backgroundImage.getScaledInstance(-1, 950, Image.SCALE_SMOOTH), 0, 0, this);
	}
}
