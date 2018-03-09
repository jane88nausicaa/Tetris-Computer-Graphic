package tetris;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class TetrisGui {

	private JFrame frame;
	private JTextField textM;
	private JTextField textN;
	private JTextField textS;
	private JTextField textWidth;
	private JTextField textHeight;
	private String m = "5";
	private String n = "2";
	private String s = "0.2";
	private String w = "10";
	private String h = "20";
	private boolean add1Square = false;
	private boolean add2Squares = false;
	private boolean add3Squares = false;
	
	Background bg;
	
	public void changeMainAreaSize(int w, int h) {
		if((w == bg.mainAreaWidth && h == bg.mainAreaHeight) || w <= 4 || h <= 4)
			return;
		
		bg.mainAreaWidth = w;
		bg.mainAreaHeight = h;
		
		float ratio = h / w;
		if(ratio > 2) {
			bg.mainAreaUnitSizeMode = 1;
		}else {
			bg.mainAreaUnitSizeMode = 0;
		}
		
		bg.reStart();
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public TetrisGui(Background bg) {
		this.bg = bg;
		//initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				Background.M = Integer.parseInt(m);
				Background.N = Integer.parseInt(n);
				Background.S = Float.parseFloat(s);
				
				bg.guiStart = false;
				
				if(!bg.showPause) {
					bg.timer.start();
					bg.repaint();
				}
				changeMainAreaSize(Integer.parseInt(w), Integer.parseInt(h));
			}
		});
		frame.setBounds(100, 100, 600, 640);
	
		frame.getContentPane().setLayout(new MigLayout("", "[][grow][][grow][grow]", "[][][][][][][][][][][][][][][][][][30.00][27.00,grow 50][][]"));
		
		JLabel lblEnterParameters = new JLabel("Enter Parameters: ");
		frame.getContentPane().add(lblEnterParameters, "cell 0 1");
		
		JLabel lblDefaultM = new JLabel("Default: M = 5, N = 20, S = 0.2");
		frame.getContentPane().add(lblDefaultM, "cell 3 1");
		
		JLabel lblM = new JLabel("M: ");
		frame.getContentPane().add(lblM, "cell 1 2,alignx trailing");
		
		
		textM = new JTextField();
		frame.getContentPane().add(textM, "cell 3 2,alignx left");
		textM.setColumns(10);
		
		JButton btnSubmit1 = new JButton("Submit");
		btnSubmit1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m = textM.getText();
				n = textN.getText();
				s = textS.getText();
			}
		});
		
		JLabel lblN = new JLabel("N: ");
		frame.getContentPane().add(lblN, "cell 1 3,alignx trailing");
		
		textN = new JTextField();
		frame.getContentPane().add(textN, "cell 3 3,alignx left");
		textN.setColumns(10);
		
		JLabel lblS = new JLabel("S: ");
		frame.getContentPane().add(lblS, "cell 1 4,alignx trailing");
		
		textS = new JTextField();
		frame.getContentPane().add(textS, "cell 3 4,alignx left");
		textS.setColumns(10);
		frame.getContentPane().add(btnSubmit1, "cell 3 5,alignx right");
		
		JLabel lblSquare = new JLabel("Enter Square Number: ");
		frame.getContentPane().add(lblSquare, "cell 0 6");
		
		JButton btnSubmit2 = new JButton("Submit");
		btnSubmit2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				w = textWidth.getText();
				h = textHeight.getText();				
			}
		});
		
		JLabel lblDefaultWidth = new JLabel("Default: Width = 10, Height = 20");
		frame.getContentPane().add(lblDefaultWidth, "cell 3 6");
		
		JLabel lblWidth = new JLabel("Width: ");
		frame.getContentPane().add(lblWidth, "cell 1 7");
		
		textWidth = new JTextField();
		frame.getContentPane().add(textWidth, "cell 3 7,alignx left");
		textWidth.setColumns(10);
		
		JLabel lblHeight = new JLabel("Height: ");
		frame.getContentPane().add(lblHeight, "cell 1 8");
		
		textHeight = new JTextField();
		frame.getContentPane().add(textHeight, "cell 3 8,alignx left");
		textHeight.setColumns(10);
		frame.getContentPane().add(btnSubmit2, "cell 3 9,alignx right");
		
		JLabel lblDoNotForget = new JLabel("Do Not Forget to Click Submit Button!");
		lblDoNotForget.setForeground(Color.RED);
		frame.getContentPane().add(lblDoNotForget, "cell 3 10");
		
		JLabel lblWantToPlay = new JLabel("Want to Play More Shapes ?");
		frame.getContentPane().add(lblWantToPlay, "cell 0 11");
		
		JLabel lbl1Square = new JLabel("");
		frame.getContentPane().add(lbl1Square, "cell 0 12");
		Image img1 = new ImageIcon(this.getClass().getResource("/1Square.png")).getImage();
		lbl1Square.setIcon(new ImageIcon(img1));
		JCheckBox chckbx1Square = new JCheckBox("Select");
		chckbx1Square.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				add1Square = e.getStateChange() == 1;
			}
		});
		frame.getContentPane().add(chckbx1Square, "cell 1 12");
		
		JLabel lbl2Squares = new JLabel("");
		frame.getContentPane().add(lbl2Squares, "cell 0 13,aligny center");
		Image img2 = new ImageIcon(this.getClass().getResource("/2Squares.png")).getImage();
		lbl2Squares.setIcon(new ImageIcon(img2));
		JCheckBox chckbx2Squares = new JCheckBox("Select");
		chckbx2Squares.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				add2Squares = e.getStateChange() == 1;
			}
		});
		frame.getContentPane().add(chckbx2Squares, "cell 1 13");
		
		JLabel lbl3Squares = new JLabel("");
		frame.getContentPane().add(lbl3Squares, "cell 0 14");
		Image img3 = new ImageIcon(this.getClass().getResource("/3Squares.png")).getImage();
		lbl3Squares.setIcon(new ImageIcon(img3));
		JCheckBox chckbx3Squares = new JCheckBox("Select");
		chckbx3Squares.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				add2Squares = e.getStateChange() == 1;
			}
		});
		frame.getContentPane().add(chckbx3Squares, "cell 1 14");
		
		JButton btnSubmit3 = new JButton("Submit");
		btnSubmit3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(add1Square) {
					bg.availableShapes[7] = true;
				}
				if(add2Squares) {
					bg.availableShapes[8] = true;
				}
				if(add3Squares) {
					bg.availableShapes[9] = true;
				}
			}
		});
		frame.getContentPane().add(btnSubmit3, "cell 3 13,alignx right");
		
		frame.setVisible(true);
	}
}
