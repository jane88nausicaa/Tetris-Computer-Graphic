package tetris;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.omg.CORBA.PRIVATE_MEMBER;

class Background extends Canvas{
	int centerX, centerY;
	int mainAreaUnitSizeMode = 0;   // 0: based on width, 1: based on height
    int left, right, bottom ,top, xMiddle, yMiddle ;
    	int unitSize;
    	int mainAreaUnitSize;
    	int mainAreaHeight = 20, mainAreaWidth = 10;
    	float pixelSize, rWidth = (float)mainAreaHeight, rHeight = (float)mainAreaHeight;
    	Graphics g = getGraphics();
    	
    boolean showPause = false;    
    boolean  mouseInsidePol = false;
    int fallingTetrisType = -1;
    int fallingTetrisState = -1;
    int nextTetrisType = -1;
    int nextTetrisState = -1;
    int curX, curY;
    int[][] map = new int[mainAreaHeight][mainAreaWidth]; 
    Color[][] cmap = new Color[mainAreaHeight][mainAreaWidth];
    
    public static int M = 5;
    public static int N = 2;
    public static float S = 0.2f;
    TetrisGui tetrisGui = new TetrisGui(this);
    boolean guiStart = false;
    boolean[] availableShapes = {true, true, true, true, true, true, true, false, false, false};
    
    
    int lines = 0;
    int score = 0;
    int level = 1;
    float fs = 1;
    int totalLinesEachLevel = 0;
    int timeFactor = 300;
 
	Timer timer;
	int flag = 0;
    int shape[][][] = new int[][][]{
    		// each shape have 4 types
    		// 0: Z
    	  	{ { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
    	  	    { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },
            { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 } },
    	  	// 1: s
    	  	{ { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            	{ 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
            { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
    	  	// 2: z
    	  	{ { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },
    	  	// 3: j
    	  	{ { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
    	  	// 4: o
    	  	{ { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
    	  	// 5: l
    	  	{ { 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
      	// 6: t
      	{ { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 } }, 
         // 7: 1 square
        { { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
         // 8: 2 square
        { { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } }, 
         // 9: 3 square
        { { 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } } 
     };
     
     Background(){ 
    	 	addMouseMotionListener(new MouseAdapter(){
    	 		public void mouseMoved(MouseEvent evt){	
    	 			if(guiStart)
    	 				return;
    				int x = evt.getX(); int y = evt.getY();
    				Graphics g = getGraphics();
    				int font = Math.round(5/(6*pixelSize));
    				Font f = new Font("TimesRoman", Font.BOLD, font);
    				
    				if(x > left && x < left + mainAreaUnitSize*mainAreaWidth 
    						&& y > top && y < top + mainAreaUnitSize*mainAreaHeight){
    					timer.stop();
    					if(!showPause){
    						showPause = true;
    						drawPause(g, f);	
    					}
    					else {
    						if(insidePolygon(x, y) && !mouseInsidePol) {
    							repaint();
    							drawPause(g, f);	
    							fallingTetrisType = nextTetrisType;
    					 		fallingTetrisState = nextTetrisState;
        						drawUnit(g, curX, curY, fallingTetrisType, fallingTetrisState);
        						
        						scoreWhenInPol();
        						mouseInsidePol = true;
        					}
    	 					else if(!insidePolygon(x, y) && mouseInsidePol){
    	 						mouseInsidePol = false;
    	 					}
    					}
    				}
    				else{
    					timer.start();
    					if(showPause){
    						showPause = false;
    						repaint();
    					}
    				}
    			}
    		});
    		
    		addMouseWheelListener(new MouseAdapter(){
    			 public void mouseWheelMoved(MouseWheelEvent e){
    				 int notches = e.getWheelRotation();
    			     if(notches > 0 && !showPause && !guiStart)
    			    	 	turnCounterClockwise();
    			     if(notches < 0 && !showPause && !guiStart)
    			    	 	turnClockwise();
    			 }
    		});
    		
    		addMouseListener(new MouseAdapter(){  
    			 public void mousePressed(MouseEvent evt) {
    				 if(guiStart)
    					 return;
    				 int x = evt.getX(); int y = evt.getY();
    				 if(x > left + unitSize*12 && x < left + unitSize*16 && 
    						 y > top + unitSize*18 && y < top + unitSize*20){
    					 System.exit(0);
    				} 
    				 if(x > left + unitSize*12 && x < left + unitSize*20 &&
    						 y > top + unitSize*15 && y < top + unitSize*17) {
    					 
    					 tetrisGui.initialize();
    					 guiStart = true;
    					 showPause = true;
    					 timer.stop();
    					 repaint();
    				 }
    				 if(SwingUtilities.isRightMouseButton(evt) && !showPause && !guiStart) {
    					 right();
    				 }
    				 if(SwingUtilities.isLeftMouseButton(evt) && !showPause && !guiStart) {
    					 left();
    				 }
    			}
    		});  
    		
    		timer = new Timer(timeFactor, new ActionListener(){
    		    @Override
    		    public void actionPerformed(ActionEvent e) {
    		        if (isCan(curX, curY + 1, fallingTetrisState)) {
    		            curY++;
    		            removeLine();
    		        } 
    		        else {
    		        		if(flag == 1) {
    		        			saveBlockToStore(curX, curY);
    		        			removeLine();
    		        			gameOver();
    		        			createBlock();
    		        			flag = 0;
    		        		}
    		        		flag = 1;
    		        	}
    		        repaint();
    		    }
    		});
    
    		getRandomFallingTetris();
    		getRandomNextTetris();
    		newMap();
    		timer.start();
     }
     
     public void reStart() {
    	 	map = new int[mainAreaHeight][mainAreaWidth];
    	 	cmap = new Color[mainAreaHeight][mainAreaWidth];
    	 	lines = 0;
    	 	level = 1;
    	 	score = 0;
    	 	fs = 1;
    	 	totalLinesEachLevel = 0;
    	 	getRandomFallingTetris();
    		getRandomNextTetris();
    		newMap();
    		timer.start();
    		repaint();
     }
     
     public void gameOver() {
    	 	for(int i = 0; i < mainAreaWidth; i++) {
    	 		if(map[0][i] == 1) {
    	 			System.exit(0);
    	 		}
    	 	}
     }
     
     public void createBlock(){
    	 	getRandomFallingTetris();
		getRandomNextTetris();
     }
     
     public void turnClockwise(){
    	 	int tempturnState = fallingTetrisState;
		fallingTetrisState = (fallingTetrisState + 3) % 4;
		if (!isCan(curX, curY, fallingTetrisState)) 
			fallingTetrisState = tempturnState;
		else 
			repaint();
     }
     
     public void turnCounterClockwise(){
		int tempturnState = fallingTetrisState;
		fallingTetrisState = (fallingTetrisState + 1) % 4;
		if (!isCan(curX, curY, fallingTetrisState))
			fallingTetrisState = tempturnState;
		else
			repaint();
     }
     
     public boolean isCan(int x, int y, int fallingTetrisState){
    	 	for (int a = 0; a < 4; a++) {
    	 		for (int b = 0; b < 4; b++){
	            	try{
	            		if(shape[fallingTetrisType][fallingTetrisState][a * 4 + b] == 0)
	    					continue;
	            		if (( y + a < 0 || y + a >= mainAreaHeight || x + b >= mainAreaWidth 
	            				|| x + b < 0|| (map[y + a ][x + b] == 1)))
	    					return false;
	            	} catch(Exception e){
	            		System.out.println(x);
	            		System.out.println(y);
	            		System.out.println(a);
	            		System.out.println(b);
	            	}
    	 		}
    	 	}
        return true;
     }
     
     public void removeLine(){
		int c = 0;
		for (int b = 0; b < mainAreaHeight; b++){
			for (int a = 0; a < mainAreaWidth; a++){
				if (map[b][a] == 1) {
					c = c + 1;
					if (c == mainAreaWidth) {
						System.out.println(b);
						for (int d = b; d >= 1; d--){
							for (int e = 0; e < mainAreaWidth; e++){
								map[d][e] = map[d-1][e];
								cmap[d][e] = cmap[d-1][e];
							}
						}
						displayMenu();
					}
				}
			}
			c = 0;
		}
     }
     
     public void displayMenu(){
    	 	lines++;
    	 	totalLinesEachLevel++;
    	 	score = score + level * M;
    	 	
    	 	if(totalLinesEachLevel >= N) {
    	 		level++;
    	 		fs = fs * (1 + level * S);
    	 		timer.setDelay((int)(timeFactor/fs));
    	 		totalLinesEachLevel = 0;
		}	
     }
     
     public void scoreWhenInPol() {
    	 	score = score - level * M;
     }
      
     public boolean insidePolygon(float x, float y) {  
    	 	int polLength = 4;
    	 	float xPol = left + curX * mainAreaUnitSize;
    	 	float yPol = top + curY * mainAreaUnitSize;
    	 	boolean b = false;
    	 	
    	 	for(int k = 0; k < 16; ++k) {
    	 		// check each polygon (square) has 4 edges
    	 		if(shape[fallingTetrisType][fallingTetrisState][k] == 1) {
    	 			float[][] polyEdges = { 
    	 					{xPol, yPol}, 					// left-top point
    	 					{xPol+mainAreaUnitSize, yPol}, 			// right-top point
    	 					{xPol+mainAreaUnitSize, yPol+mainAreaUnitSize},	// right-bottom point
    	 					{xPol, yPol+mainAreaUnitSize} 			// left-bottom point
    	 			};
    	 			int j = polLength - 1;
            	    for (int i = 0; i < polLength; ++i) { 
            	    		if (polyEdges[j][1] <= y && y < polyEdges[i][1] 
    	    					&& area2(polyEdges[j][0], polyEdges[j][1], 
    	    							polyEdges[i][0], polyEdges[i][1], x, y) > 0 
            	    			|| polyEdges[i][1] <= y && y < polyEdges[j][1] 
            	    			&& area2(polyEdges[i][0], polyEdges[i][1],
            	    					polyEdges[j][0], polyEdges[j][1], x, y) > 0) 
            	    			b = !b;
            	    			j = i;
            	    	}
            	    if(b)
            	    		return b;
    	 		}
    	 		if((k+1) % 4 != 0) 
    	 			xPol += mainAreaUnitSize;
    	 		else {
    	 			yPol += mainAreaUnitSize;
    	 			xPol = left + curX * mainAreaUnitSize;
    	 		}		
    	 	}
    	 	return false;
     }
     
     public float area2(float x1, float y1, float x2, float y2, float x3, float y3){ 
    	 	return (x1 - x3) * (y2 - y3) - (y1 - y3) * (x2 - x3);
     }
     
     public void saveBlockToStore(int x, int y){
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
	            	if(y + a < 0 || y + a >= mainAreaHeight || x + b < 0 || x + b >= mainAreaWidth)
						continue;
	            if (map[y + a][x + b] == 0) {
	            		map[y + a][x + b] = shape[fallingTetrisType][fallingTetrisState][4 * a + b];
	                cmap[y + a][x + b] = findColor(fallingTetrisType);
	            }
            }
        }
     }
     
     public void getRandomFallingTetris(){
    	 	if(fallingTetrisType == -1){
	    		Random r = new Random();
			fallingTetrisType = r.nextInt(7);
			fallingTetrisState = 0;
			curX = 3; curY = 0;
	    	} 
    		else{
	    		fallingTetrisType = nextTetrisType;
	    		fallingTetrisState = nextTetrisState;
	    		curX = 3; curY = 0;
    		}
     }
     
     public void drawFallingTetris(Graphics g){
    		drawUnit(g, curX, curY, fallingTetrisType, fallingTetrisState);
     }
     
     public void getRandomNextTetris(){
    	 	Random r = new Random();
    	 	int type = r.nextInt(10);
    	 	while(fallingTetrisType == type || !availableShapes[type]) {
    	 		type = r.nextInt(10);
    	 	}
    	 	nextTetrisType = type;
    	 	nextTetrisState = 0;
     }
     
     public void drawNextTetris(Graphics g){
		drawUnitForNext(g, 13, 1, nextTetrisType, nextTetrisState);
     }
     
     public void getNextBlock() {
    	 	fallingTetrisType = nextTetrisType;
 		fallingTetrisState = nextTetrisState;
 		drawFallingTetris(g);
     }
     
     public void newMap(){}
     
     void initgr(){
    		Dimension d = getSize();
 		int maxX = d.width - 1, maxY = d.height - 1;
 		pixelSize = Math.max(rWidth/maxX, rHeight/maxY);
 	    centerX = maxX/2; centerY = maxY/2;
     }
     
     int iX(float x){return Math.round(centerX + x/pixelSize);}
     int iY(float y){return Math.round(centerY - y/pixelSize);}
     
     public void drawQuit(Graphics g, Font f){
    		g.drawRect(left + unitSize*(10+2), top + unitSize*(20-2), 4*unitSize, 2*unitSize);
        String text = "QUIT";
        g.setFont(f);
        FontMetrics fontMetrics = g.getFontMetrics();		
		int x = left + unitSize*(10+2) + (4*unitSize - fontMetrics.stringWidth(text)) / 2;
		int y = top + unitSize*(20-2) + (2*unitSize - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
		g.drawString(text, x, y);
     }
     
     void drawPause(Graphics g, Font f){
		g.setColor(Color.BLUE);
		g.drawRect(left + mainAreaUnitSize*(mainAreaWidth/2-2), top + mainAreaUnitSize*(mainAreaHeight/2-1), 
				4*mainAreaUnitSize, 2*mainAreaUnitSize);
		String text = "PAUSE";
        g.setFont(f);
		FontMetrics fontMetrics = g.getFontMetrics();
		int x = left + mainAreaUnitSize*(mainAreaWidth/2-2) + (4*mainAreaUnitSize - fontMetrics.stringWidth(text)) / 2;
		int y = top + mainAreaUnitSize*(mainAreaHeight/2-1) + (2*mainAreaUnitSize - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
		g.drawString(text, x, y);
     }
     
     void drawSetParameters(Graphics g, Font f){
    	 	g.drawRect(left + unitSize*(10+2), top + unitSize*(20-5), 8*unitSize, 2*unitSize);
        String text = "SET PARAMETERS";
        g.setFont(f);
        FontMetrics fontMetrics = g.getFontMetrics();		
 		int x = left + unitSize*(10+4) + (4*unitSize - fontMetrics.stringWidth(text)) / 2;
 		int y = top + unitSize*(20-5) + (2*unitSize - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
 		g.drawString(text, x, y);
      }
     
     public void menu(Graphics g, Font f){
        g.setColor(Color.BLACK);
        g.setFont(f);
        g.drawString("Level:    " + level, left + unitSize*(10+2), top + unitSize*(20-12));
        g.drawString("Lines:    " + lines, left + unitSize*(10+2), top + unitSize*(20-10));
        g.drawString("Score:    " + score, left + unitSize*(10+2), top + unitSize*(20-8));
     }
     
     public void drawSingleSquare(Graphics g, int LeftInd, int TopInd, Color color){
 	 	int leftStart = left + mainAreaUnitSize*LeftInd;	
		int topStart = top + mainAreaUnitSize*TopInd;
		g.setColor(color);
		g.fillRect(leftStart, topStart, mainAreaUnitSize, mainAreaUnitSize);
		g.setColor(Color.black);
     g.drawRect(leftStart, topStart, mainAreaUnitSize, mainAreaUnitSize);
  }
     
     public Color findColor(int shapeType){
    	 	Color c = null;
	    	switch(shapeType){
			case 0:
				c = Color.CYAN;
			case 1:
				c = Color.YELLOW;
				break;
			case 2:
				c = Color.GREEN;
				break;
			case 3:
				c = Color.RED;
				break;
			case 4:
				c = Color.BLUE;
				break;
			case 5:
				c = Color.ORANGE;
				break;
			case 6:
				c = Color.MAGENTA;
				break;
			case 7:
				c = Color.BLACK;
				break;
			case 8:
				c = Color.PINK;
				break;
			case 9:
				c = Color.lightGray;
				break;
			default:
				break;
			};
		return c;
     }
     
     public void drawUnit(Graphics g, int shapeLeftInd, int shapeTopInd, 
    		int shapeType, int shapeState){
    	 	Color c = findColor(shapeType);
		g.setColor(c);
		// calculate starting point to fill color
		int leftStart = left + mainAreaUnitSize*shapeLeftInd;	
		int topStart = top + mainAreaUnitSize*shapeTopInd;
		
		for(int i = 0; i < 16; ++i){
			if(shape[shapeType][shapeState][i] == 1) {
				g.fillRect(leftStart, topStart, mainAreaUnitSize, mainAreaUnitSize);
				g.setColor(Color.BLACK);
		        g.drawRect(leftStart, topStart, mainAreaUnitSize, mainAreaUnitSize);
			}
			if(i != 3 && i != 7 && i != 11)
				leftStart += mainAreaUnitSize;
			else {
				leftStart -= 3*mainAreaUnitSize;
				topStart += mainAreaUnitSize;
			}
			g.setColor(c);
		}
     }
     
     public void drawUnitForNext(Graphics g, int shapeLeftInd, int shapeTopInd, 
     		int shapeType, int shapeState){
     	 	Color c = findColor(shapeType);
 		g.setColor(c);
 		// calculate starting point to fill color
 		int leftStart = left + unitSize*shapeLeftInd;	
 		int topStart = top + unitSize*shapeTopInd;
 		
 		for(int i = 0; i < 16; ++i){
 			if(shape[shapeType][shapeState][i] == 1) {
 				g.fillRect(leftStart, topStart, unitSize, unitSize);
 				g.setColor(Color.BLACK);
 		        g.drawRect(leftStart, topStart, unitSize, unitSize);
 			}
 			if(i != 3 && i != 7 && i != 11)
 				leftStart += unitSize;
 			else {
 				leftStart -= 3*unitSize;
 				topStart += unitSize;
 			}
 			g.setColor(c);
 		}
      }
     
     public void left(){
		if (isCan(curX - 1, curY, fallingTetrisState))
			curX--;
		repaint();
     }
	
     public void right(){
		if (isCan(curX + 1, curY, fallingTetrisState))
			curX++;
		repaint();
     }
	
     public void drawMap(Graphics g) {
		for(int i = 0; i < mainAreaHeight; i++) {
			for(int j = 0; j < mainAreaWidth; j++) {
				if(map[i][j] == 1) 
				    drawSingleSquare(g, j, i, cmap[i][j]); 
			}
		}
     }
    
     public void paint(Graphics g){
        initgr();
        
        left = iX(-rWidth/2); right = iX(rWidth/2);
        bottom = iY(-rHeight/2); top = iY(rHeight/2);
        	xMiddle = iX (0); yMiddle = iY (0);
        	unitSize = (right - left)/20;
        	
        	if(mainAreaUnitSizeMode == 0) {
        		mainAreaUnitSize = (right - left) / 2 / mainAreaWidth;
        	}else {
        		mainAreaUnitSize = (bottom - top) / mainAreaHeight;
        	}
        	
        	
        	int font = Math.round(5/(6*pixelSize));
        	Font f = new Font("TimesRoman", Font.BOLD, font);
        	
        	g.setColor(Color.BLACK);
        // draw main area
        g.drawRect(left, top, mainAreaUnitSize*mainAreaWidth, mainAreaUnitSize*mainAreaHeight);
        
        // draw next area
        g.drawRect(left + unitSize*12, top, unitSize*5, 4*unitSize);
        // draw quit
        drawQuit(g, f);
        menu(g, f);
        drawSetParameters(g, f);
       
        // shapeLeftInd, shapeTopInd, shapeType, shapeState
        drawFallingTetris(g);
        drawNextTetris(g);
        drawMap(g);
     }
}

