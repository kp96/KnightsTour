import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
public class KnightsTour extends Applet implements MouseListener,ActionListener,Runnable{
	/*

  	<applet code = "KnightsTour" width = 1366 height = 768> </applet>
	 
	*/
	
	
	private static final long serialVersionUID = 1L;
	AudioClip gameover,mouseclick,error,background;
	URL codeb;
	Label l1,l2,l3,l4,l5;
	static int xfinal,yfinal,flag,gridnumber,score,buttonflag,hintflag,gameoverflag;
	boolean[] squares;
	boolean setReverse;
	Button b1,b2,b3,b4,b5;
	Thread simulator;
	BufferedImage photo;
	public static final int[] solution = {34, 17, 20,  3, 36,  7, 22,  5, 19,  2, 35, 40, 21,  4, 37,  8, 16, 33, 18, 51, 44, 39,  6, 23,  1,
		50, 43, 46, 41, 56,  9, 38, 32, 15, 54, 61, 52, 45, 24, 57, 49, 62, 47, 42, 55 ,60, 27, 10, 14 ,31, 64, 53, 12, 29, 58, 
		25, 63, 48 ,13, 30, 59, 26, 11, 28 };
public void init()//sets all the variables adds new objects , gridnumber starts at 24 middle :), layout is set
{
	
/*Audio initializations here*/
	gameover   = getAudioClip(getCodeBase(),"gameover.wav");
	error      = getAudioClip(getCodeBase(),"error.wav");
	mouseclick = getAudioClip(getCodeBase(),"stephorse.wav");
	background = getAudioClip(getCodeBase(),"background.wav");
	
	
	
/*Labels initializations here*/
	l1 = new Label();
	l2 = new Label();
	l3 = new Label();
	l4 = new Label();
	l5 = new Label();
	
/*Buttons initializations here*/
	
	b1 = new Button("Simulate");
	b2 = new Button("Play");
	b3 = new Button("Restart");
	b4 = new Button("Reverse");
	b5 = new Button("Hints");
	
/* Labels,Buttons bound here ,mouse action*/
	
	b1.setBounds(310, 620 ,80,40);
	b2.setBounds(310, 520, 80, 40);
	b3.setBounds(310, 570, 80, 40);
	b4.setBounds(220, 570, 80, 40);
	b5.setBounds(400, 570 ,80, 40);
	l1.setBounds(120,470,700,40);
	l2.setBounds(650,540,100,20);
	l3.setBounds(700,50,500,60);
	l5.setBounds(740,140,300,20);
	l4.setBounds(150,0,600,40);
	
//Thread and mouse listener here
	addMouseListener(this);
	simulator = new Thread(this);
	
/* Labels text here ,fonts are not working but kept anyway*/
	
	l1.setForeground(Color.RED);
	l1.setFont(new Font("TimesNewRoman",Font.BOLD,30));
	l1.setText("Welcome to Knight's Tour - The Killer Puzzle ");
	l2.setFont(new Font("BlackadderITC",Font.BOLD,20));
	l4.setFont(new Font("BlackadderITC",Font.BOLD,20));
	l4.setText("Game Rules: Cover all 64 squares , repetition not allowed !");
	l3.setFont(new Font("Serif",Font.BOLD,30));
	l3.setForeground(Color.blue);
	l3.setText("Click play to start");
	l5.setText("Simulation can be done only once at start");
	l2.setText("Score : " + score);
	
/* Layout added here */
	setLayout(null);
	add(l1);
	add(l2);
	add(b1);
	add(b2);
	add(b3);
	add(b4);
	add(b5);
	add(l3);
	add(l4);
	setVisible(true);
	
/* All other variables here */
	setReverse = true;
	buttonflag = 0;
	score = 0;
	hintflag = 1;
	add(l5);
	xfinal = 0;
	yfinal = 0;
	flag = 0;
	gameoverflag = 0;
	squares = new boolean[64];
	gridnumber = 24;

//knight image here
	try 
    {
       URL u = new URL(getCodeBase(),"knight.png");
       photo = ImageIO.read(u);
    }   
    catch (IOException e) 
    {
      //t1.setText("Cant load image");
    }
	
	
	
//All buttons action listeners here	
	
	
	b1.addActionListener(new ActionListener() {
		//starts the simulation thread
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				b2.disable();
				b5.disable();
				b4.disable();
				b1.disable();//can run only once
				simulator.start();
				//t3.setText("Started Thread");
			}
	});
	b2.addActionListener(new ActionListener() {
		//To play
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			buttonflag = 1;
			b1.disable();
			background.loop();
		}
	});
	b3.addActionListener(new ActionListener() {
		//sets all the bool to false and gridnumber  to default invokes repaint
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			// for restart
		
			{
			for(int i = 0; i < 64; i++)
				squares[i] = false;
			gridnumber = 24;
			squares[24] = true;
			flag = 0;
			score = 0;
			l2.setText("Score : " + score);
			repaint();
			b2.enable();
			b1.disable();
			simulator.stop();
			buttonflag = 0;
			gameoverflag = 0;
			l3.setText("Click play to begin");
			}
			
		}
	});
	b4.addActionListener(new ActionListener() {
		//reverse the game mode. works only after restart, logic based on buttonflag
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(buttonflag == 0)
			{
				if(setReverse)
					setReverse = false;
				else
					setReverse = true;
				repaint();
			}
			else
			{
				l3.setText("Restart the game");
			}
		}
	});
	
	b5.addActionListener(new ActionListener() {
		//switches hints,based on hintflag
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(hintflag == 0)
				hintflag = 1;
			else
				hintflag = 0;
			repaint();
		}
	});
}
@Override
public void run() {
	//inside run disables all buttons,iterates through the solution array finds the gridnumber repaints
	
	b1.disable();
	b2.disable();
	b3.disable();
	//t1.setText("Here inside run");
	// TODO Auto-generated method stub
	gridnumber = 0;
	for(int i = 0; i < 64; i++)
		squares[i] = false;
	squares[24] = true;
	int solutionIndex = 0;
	for(int i = 1; i < 64; i++)
	{
	 while(i != (solution[solutionIndex]-1))
		 
	 {
		 gridnumber++;
		 solutionIndex++;
		 
	 }
	 //if(gridnumber !=0)
		 //gridnumber--;
	 //t2.setText(gridnumber + "");
	 getCoordinatesAndPaint(gridnumber);
	 try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
	 gridnumber = 0;
	 solutionIndex = 0;
	}
	stop();
	b5.enable();
	b4.enable();
	b3.enable();
	l3.setText("Restart the app");
	b1.disable();
	b2.enable();
}
public void paint(Graphics g)
{
	//prints chess board based on sign logic
	//inital co-ords here
	//xfinal yfinal are used to paint the horse's current location
	//grid starts are 24
	int sign = -1;
	int xcord = 200;
	int ycord = 50;
	for(int i = 1; i <= 8; i++)
	{
		for(int j = 1; j <= 8; j++)
		{
			sign *= -1;
			if(sign > 0)
			{
				g.setColor(Color.BLACK);
			}
			else
			{
				g.setColor(Color.WHITE);
			}
			g.fillRect(xcord, ycord, 50, 50);
			
			if((i == 4 && j == 1)&& (flag ==0 ))//draw initial knight
			{
				squares[24] = true;
				g.drawImage(photo,xcord + 10, ycord + 10, 30, 30,null);
				//t1.setText("I am here");
			}
			
			
			xcord += 50;
		}
		xcord = 200;//reset x
		ycord += 50;//add 50 to y
		sign *= -1;//change sign
	}
	if(flag == 1)//draw next positions based on flag , always 1 after initial step
	{
		g.drawImage(photo,xfinal + 10, yfinal + 10, 30, 30,null);
	}
	for(int i = 0; i < 64; i++ )
	{
		if(squares[i] == setReverse && hintflag == 1)//setReverse and hintflags based on that prints the red oval
		{
			int xCordCalc = i % 8;
			int yCordCalc = i / 8;
			int xFilled = 200;
			int yFilled = 50;
			while(xCordCalc-- != 0)
			{
				xFilled += 50;
			}
			while(yCordCalc-- != 0)
			{
				yFilled += 50;
			}
			g.setColor(Color.RED);
			g.fillOval(xFilled + 35, yFilled + 35, 5,5);
		}
		
	}
	
	//t1.setText("co-ordinates are " + xcord + " " + ycord);
}

@Override
public void mouseClicked(MouseEvent clickpos) {
	// TODO Auto-generated method stub
	//most important method
	mouseclick.play();//plays sound
	if(buttonflag == 1 && gameoverflag == 0){//checks valid conditions
	int x = clickpos.getX();
	int y = clickpos.getY();
	//t1.setText("" + x +" " + y);
	if(x >= 200 && x <= 600 && y >= 50 && y <= 450)//checks bounds of board
	{
		flag = 1;
		getSquareNumber(x , y);//normalization function with x and y 
	}
	}
	else if(buttonflag == 1)
		l3.setText("Click play to start");
	else if(gameoverflag == 1)
		l3.setText("Click restart to play again");
}
public void getCoordinatesAndPaint(int gridNumber)//for thread
{
	int xCordCalc = gridNumber % 8;
	int yCordCalc = gridNumber / 8;
	int xFilled = 200;
	int yFilled = 50;
	while(xCordCalc-- != 0)
	{
		xFilled += 50;
	}
	while(yCordCalc-- != 0)
	{
		yFilled += 50;
	}
	xfinal = xFilled;
	yfinal = yFilled;
	squares[gridNumber] = true;
	flag = 1;
	repaint();
}
public void getSquareNumber(int x, int y)
{//gets the gridnumber
	int rowindex = 0;
	int columnindex = 0;
	int xcordstart = 200;
	int ycordstart = 50;
	while(x >= xcordstart)
	{
		columnindex++;
		xcordstart += 50;
	}
	while(y >= ycordstart)
	{
		rowindex++;
		ycordstart += 50;
	}
	xfinal = xcordstart-50;
	yfinal = ycordstart-50;
	rowindex -= 1;
	columnindex-=1;
	int gridnumber = (rowindex)*8 + columnindex ;
	if(isValid(rowindex , columnindex))
	{
		if(squares[gridnumber] == true)
		{//if already arrived game over
			l3.setText("Game Over. Restart to play again");
			background.stop();
			gameover.play();
			b2.disable();
			gameoverflag = 1;
		}
		else{
			squares[gridnumber] = true;
			++score;
			l2.setText("Score : " + score);
			l3.setText("Next move");
			repaint();
		}
	}
}
public boolean isValid(int currow, int curcol)
{//horse move valid checker
	int prevrow = gridnumber / 8;
	int prevcol = gridnumber % 8;
	
	if	  (( currow + 2 == prevrow) && (curcol + 1 == prevcol))
	{
		//t2.setText(gridnumber + " ");
		gridnumber = currow*8 + curcol; 
		return true;
	}
	else if(( currow + 2 == prevrow) && (curcol - 1 == prevcol))
	{
		//t2.setText(gridnumber + " ");
		gridnumber = currow*8 + curcol; 
		return true;
	}
	else if(( currow + 1 == prevrow) && (curcol + 2 == prevcol))
	{
		//t2.setText(gridnumber + " ");
		gridnumber = currow*8 + curcol; 
		return true;
	}
	else if(( currow + 1 == prevrow) && (curcol - 2 == prevcol))
	{
		//t2.setText(gridnumber + " ");
		gridnumber = currow*8 + curcol; 
		return true;
	}
	else if(( currow - 1 == prevrow) && (curcol + 2 == prevcol))
	{
		//t2.setText(gridnumber + " ");
		gridnumber = currow*8 + curcol; 
		return true;
	}
	else if(( currow - 1 == prevrow) && (curcol - 2 == prevcol))
	{
		gridnumber = currow*8 + curcol; 
		//t2.setText(gridnumber + " ");
		return true;
	}
	else if(( currow - 2 == prevrow) && (curcol + 1 == prevcol))
	{
		gridnumber = currow*8 + curcol; 
		//t2.setText(gridnumber + " ");
		return true;
	}
	else if(( currow - 2 == prevrow) && (curcol - 1 == prevcol))
	{	
		gridnumber = currow*8 + curcol;
		//t2.setText(gridnumber + " ");
		return true;
	}
	//t2.setText("I am here with currrow " + currow +"curr column:" + curcol + " prevrow:" + prevrow + " prevcol:" + prevcol);
	error.play();
	l3.setText("Illegal Move Try Again");
	return false;
}
//unused but overloaded methods
@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}
}
