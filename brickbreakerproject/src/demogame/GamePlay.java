package demogame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;



public class GamePlay extends JPanel implements ActionListener, KeyListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean play=false;
    private int totalbricks=21;
    private int score=0;
    private Timer timer;
    private int delay=8;
    private int ballposx=120;
    private int ballposy=350;
    private int ballXdir=-1;
    private int ballYdir=-2;
    private int playerX=350;
    private MapGenerator map;
    public GamePlay() {
    	addKeyListener(this);
    	setFocusable(true);
    	setFocusTraversalKeysEnabled(true);
    	
		
		timer=new Timer(delay,this);
    	timer.start();
    	map=new MapGenerator(3,7);
    }
    public void paint(Graphics g) {
    	//black canvas
    	g.setColor(Color.black);
    	g.fillRect(1,1,692,592);
    	//border
    	g.setColor(Color.green);
    	g.fillRect(0,0,692,3);
    	g.fillRect(0,3,3,592);
    	g.fillRect(691,3,3,592);
    	//paddle
    	g.setColor(Color.cyan);
    	g.fillRect(playerX, 550, 100, 6);
    	//bricks
    	map.draw((Graphics2D)g);
    	
    	//ball
    	g.setColor(Color.red);
    	g.fillOval(ballposx, ballposy, 20, 20);
    	
    	
    	//score
    	g.setColor(Color.green);
    	g.setFont(new Font("serif",Font.BOLD,20));
    	g.drawString("score:"+score, 550, 30);
    	
    	//game over
    	if(ballposy>=570) {
    		play=false;
    		ballXdir=0;
    		ballYdir=0;
    		g.setColor(Color.green);
    		g.setFont(new Font("serif",Font.BOLD,40));
    		g.drawString("GameOver!!, Score:"+score , 200, 300);
    		g.setFont(new Font("serif",Font.BOLD,25));
    		g.drawString("Press Enter to restart" , 230, 350);
    	
    	
    	}
    	if(totalbricks<=0) {
    		play=false;
    		ballXdir=0;
    		ballYdir=0;
    		g.setColor(Color.green);
    		g.setFont(new Font("serif",Font.BOLD,40));
    		g.drawString("You Won!!, Score:"+score , 200, 300);
    		g.setFont(new Font("serif",Font.BOLD,25));
    		g.drawString("Press Enter to restart" , 230, 350);
    	
    	}
    }
    private void moveLeft() {
    	play=true;
    	playerX=-20;
    	
    }
    private void moveRight() {
    	play=true;
    	playerX=20;
    	
    }
    
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			if(playerX<=0)
				playerX=0;
			else
			moveLeft();
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(playerX>=600)
				playerX=600;
				else
			moveRight();
		}
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(!play) {
				score=0;
				totalbricks=21;
				ballposx=120;
				ballposy=350;
				ballXdir=-1;
				ballYdir=-2;
				playerX=320;
				map=new MapGenerator(3,7);
			}
		}
		repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {
	
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(play) {
			if(ballposx<=0) {
				ballXdir=-ballXdir;
			}
			if(ballposx>=0) {
				ballXdir=-ballXdir;
			}
			if(ballposy<=0) {
				ballYdir=-ballYdir;
			}
			Rectangle ballRect=new Rectangle(ballposx,ballposy,20,20);
			Rectangle paddleRect=new Rectangle(playerX,550,100,8);
			
			if(ballRect.intersects(paddleRect)) {
				ballYdir=-ballYdir;
			}
			A: for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int width=map.brickwidth;
						int height=map.brickheight;
						int brickXpos=80+j*width;
						int brickYpos=5+i*height;
						
						Rectangle brickRext=new Rectangle(brickXpos,brickYpos,width,height);
						if(ballRect.intersects(brickRext)) {
							map.setBrick(0, i, j);
							totalbricks--;
							score+=5;
							if(ballposx+90<=brickXpos||ballposx+1>=brickXpos+width) {
								ballXdir=-ballXdir;
							
							}
							else {
								ballYdir=-ballYdir;
							}
							break A;
							
						}
						
					}
				}
			}
			ballposx+=ballXdir;
			ballposy+=ballYdir;
		}
		repaint();
	}
}
