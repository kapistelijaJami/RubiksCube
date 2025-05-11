package rubicscube;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 * CONTROLS:
 * Q, W, E, A, S, D rotates the cube without moving any sides.
 * Holding arrow keys determines which side to move from the front perspective,
 * and then you can move it with W, A, S, D (two of which do something per horizontal or vertical arrow direction).
 * 
 * @author Jami-PC
 */
public class RubicsCube extends Canvas implements Runnable {
	
	public boolean running = false;
	public Thread thread;
	public static JFrame frame;
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 700;
	public static final double updatesPerSecond = 60.0;

	private Cube cube;
	private KeyInput input;
	
	public boolean isMoved = false;
	public boolean winnerWinner = false;
	
	public Cube getCube() {
		return cube;
	}
	
	public void init() {
		cube = new Cube();
		Scrambler.scramble(cube);
		//textBased();
		input = new KeyInput(this);
        this.addKeyListener(input);
	}
	
	public void textBased() {
		System.out.println(cube);
		
		System.out.println("Commands:");
		System.out.println("\tTo move side, first char: side (t, r, d, l, f, b), second char: direction (c, a) -> rc;");
		System.out.println("\tTo rotate the whole cube (left, up, right, down)");
		System.out.println("\tTo scramble the cube (scramble, shuffle, sekoita)");
		System.out.println("\tTo stop (stop)");
		System.out.println("");
		
		
		Scanner lukija = new Scanner(System.in);
		
		while (true) {
			System.out.print("Syöte: ");
			String input = lukija.nextLine();
			System.out.println("");
			if (input.length() == 2 && !input.equals("up")) {
				int side = getIndexFromInput(input.charAt(0));
				boolean dir = input.charAt(1) == 'c';

				cube.rotateSide(side, dir);
			} else {
				if (input.equals("left")) {
					cube.turnCubeFromLeft();
				} else if (input.equals("right")) {
					cube.turnCubeFromRight();
				} else if (input.equals("up")) {
					cube.turnCubeFromUp();
				} else if (input.equals("down")) {
					cube.turnCubeFromDown();
				} else if (input.equals("stop")) {
					return;
				} else if (input.equals("scramble") || input.equals("shuffle") || input.equals("sekoita")) {
					Scrambler.scramble(cube);
				}
			}
			
			System.out.println(cube);
		}
	}
	
	public static int getIndexFromInput(char input) {
		if (input == 'f') {
			return 0;
		} else if (input == 'r') {
			return 1;
		} else if (input == 'b') {
			return 2;
		} else if (input == 'l') {
			return 3;
		} else if (input == 't') {
			return 4;
		} else if (input == 'd') {
			return 5;
		}
		return 0;
	}
	
	public static boolean getDirFromInput(char input) {
		return input == 'c';
	}
	
	public void createWindow() {
		Window window = new Window(WIDTH, HEIGHT, "RageGame", this);
		frame = window.getFrame();
    }
	
	public synchronized void start() {
		if (running) {
			return;
		}
		
		running = true;
		
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		//frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		try {
			running = false;
			frame.dispose();
			System.exit(0);
			thread.join();
		} catch (Exception e) {
		}
	}
	
	@Override
	public void run() {
		init();
		this.requestFocus();
		
		long timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double nanoSecondsPerUpdate = 1000000000 / updatesPerSecond;	//päivitysten väliin tarvittavat nanosekunnit
		double delta = 0;												//nanosekuntien määrä suhteessa päivitykseen tarvittaviin nanosekunteihin, kun 1, niin tehdään päivitys
		
		int updates = 0;		//päivitysten määrä sekunnissa
		int frames = 0;			//renderöintien määrä sekunnissa
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nanoSecondsPerUpdate;		//deltaan lisätään loopiin kuluneet nanosekunnit per päivitykseen tarvittavat nanosekunnit
			lastTime = now;
			
			while (delta >= 1) {	//deltan arvo on 1 kun on mennyt 1/updatesPerSecond sekuntia (eli 1/60)
				update();
				updates++;
				render();
				frames++;
				delta--;			//deltan arvoa miinustetaan yhdellä (eli putoaa hyvin lähelle lukua 0)
			}
			
			if (System.currentTimeMillis() - timer > 1000) {		//jos on mennyt sekunti viime kerrasta (tänne pääsee siis sekunnin välein)
				timer += 1000;		//timer jahtaa currentTimeMillis funktiota
				frame.setTitle("Rubics cube " + "Updates: " + updates + ", Frames: " + frames);
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}
	
	public void update() {
		if (isMoved && checkIfWin()) {
			winnerWinner = true;
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT); //valkonen tausta
		
		g.setColor(Color.DARK_GRAY);
		/*Point leftMiddle = new Point(200, 200);
		Point top = new Point(300, 170);
		Point middle = new Point(300, 230);
		Point rightMiddle = new Point(400, 200);
		Point bottomLeft = new Point(200, 350);
		Point bottom = new Point(300, 380);
		Point bottomRight = new Point(400, 350);*/
		
		Point leftMiddle = new Point(200, 200);
		Point top = new Point(270, 130);
		Point middle = new Point(400, 200);
		Point rightMiddle = new Point(470, 130);
		Point bottomLeft = new Point(200, 400);
		Point bottom = new Point(400, 400);
		Point bottomRight = new Point(470, 330);
		
		
		Parallellogram currentFace = new Parallellogram(new Point[] {new Point(leftMiddle), new Point(middle), new Point(bottom), new Point(bottomLeft)});
		Parallellogram topFace = new Parallellogram(new Point[] {new Point(leftMiddle), new Point(top), new Point(rightMiddle), new Point(middle)});
		Parallellogram rightFace = new Parallellogram(new Point[] {new Point(middle), new Point(rightMiddle), new Point(bottomRight), new Point(bottom)});
		
		currentFace.multiplyAll(2);
		currentFace.substractAll(200);
		topFace.multiplyAll(2);
		topFace.substractAll(200);
		rightFace.multiplyAll(2);
		rightFace.substractAll(200);
		
		currentFace.draw(g, true);
		topFace.draw(g, true);
		rightFace.draw(g, true);
		
		//g.setColor(Color.RED);
		Parallellogram[][] currentPieces = currentFace.getPieces();
		for (int y = 0; y < currentPieces.length; y++) {
			for (int x = 0; x < currentPieces[y].length; x++) {
				currentPieces[y][x].draw(g, cube.getColorOfAPiece(0, x, y));
			}
		}
		
		
		Parallellogram[][] topPieces = topFace.getPieces();
		//g.setColor(Color.YELLOW);
		for (int y = 0; y < topPieces.length; y++) {
			for (int x = 0; x < topPieces[y].length; x++) {
				topPieces[y][x].draw(g, cube.getColorOfAPiece(4, y, 2-x)); //0,0 0,1 0,2 1,1 1,2 1,3 -> 0,3-x 0,3-x
			}
		}
		
		
		
		Parallellogram[][] rightPieces = rightFace.getPieces();
		g.setColor(Color.BLUE);
		for (int y = 0; y < rightPieces.length; y++) {
			for (int x = 0; x < rightPieces[y].length; x++) {
				rightPieces[y][x].draw(g, cube.getColorOfAPiece(1, x, y));
			}
		}
		
		g.setColor(Color.BLACK);
		if (winnerWinner) {
			g.setFont(new Font("TimesRoman", Font.BOLD, 50)); 
			g.drawString("WINNER WINNER CHICKEN DINNER!", WIDTH / 20, HEIGHT / 2);
			winnerWinner = false;
		}
		
		g.dispose();
        bs.show();
	}

	private boolean checkIfWin() {
		return cube.isWin();
	}
}
