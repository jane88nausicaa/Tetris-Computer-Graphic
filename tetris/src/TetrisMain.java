/*
 * This program originally:
 * 		M = 5;
    		N = 2;
    		S = 0.2f;
    		Main area width = 10
    		Main area height = 20
    		It can provide 7 types of shapes for user to play
    		We provide each shape a 4 * 4 grids to be drawn and changed direction
    
    More Interesting:
    		User can change the parameters through click 'SET PARAMETER'
    		Then they can change the parameters manually 
    		They can also choose to include 3 more shapes to play with
 */
package tetris;

import java.awt.*;
import java.awt.event.*;

public class TetrisMain extends Frame{
	public static void main(String[] args) { 
		new TetrisMain(); 
	}	
	
	TetrisMain(){
		super("PLAYING TETRIS!!");
		addWindowListener(new WindowAdapter(){
					public void windowClosing(WindowEvent e) { System.exit(0); }});
		setSize(600, 500);
		add("Center", new Background());
		show();
	}
}
