package rubicscube;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public class Parallellogram {
	Point[] points = new Point[4];
	int xOffset;
	int yOffset;
	
	public Parallellogram(Point[] points) {
		this.points = points;
	}

	public Point[] getPoints() {
		return points;
	}

	public void setPoints(Point[] points) {
		this.points = points;
	}
	
	public void multiplyAll(int multi) {
		for (int i = 0; i < points.length; i++) {
			points[i].x *= multi;
			points[i].y *= multi;
		}
	}
	
	public void substractAll(int amount) {
		for (int i = 0; i < points.length; i++) {
			points[i].x -= amount;
			points[i].y -= amount;
		}
	}
	
	public Point getSmallOffset(Point p1, Point p2) {
		//System.out.println(p1.x + " " + p1.y + ", " + p2.x + " " + p2.y);
		int offsetX = p2.x - p1.x;
		int offsetY = p2.y - p1.y;
		int amount = 5;
		
		return new Point((int)(offsetX / 300.0 * amount), (int)(offsetY / 300.0  * amount));
	}
	
	public Point test(Point p1, Point p2) {
		int offsetX = p2.x - p1.x;
		int offsetY = p2.y - p1.y;
		
		return new Point(offsetX / 3, offsetY / 3);
	}
	
	public Parallellogram[][] getPieces() {
		Parallellogram[][] pieces = new Parallellogram[3][3];
		//System.out.print("nyt: " + this + " -> ");
		
		for (int pieceIndY = 0; pieceIndY < 3; pieceIndY++) {
			for (int pieceIndX = 0; pieceIndX < 3; pieceIndX++) {
				/*Point offsetRight = getSmallOffset(points[0], points[1]); //(3, 3), (30, 13), (30, 57), (3, 47)
				Point offsetDown = getSmallOffset(points[0], points[3]);*/
				Point rightStep = test(points[0], points[1]);
				Point downStep = test(points[0], points[3]);
				
				
				Point first = new Point(points[0].x + (rightStep.x * pieceIndX) + (downStep.x * pieceIndY), points[0].y + (rightStep.y * pieceIndX) + (downStep.y * pieceIndY));
				Point second =  new Point(points[0].x + (rightStep.x * (pieceIndX+1)) + (downStep.x * pieceIndY), points[0].y + (rightStep.y * (pieceIndX+1)) + (downStep.y * pieceIndY));
				Point third =  new Point(points[0].x + (rightStep.x * (pieceIndX+1)) + (downStep.x * (pieceIndY+1)), points[0].y + (rightStep.y * (pieceIndX+1)) + (downStep.y * (pieceIndY+1)));
				Point fourth =  new Point(points[0].x + (rightStep.x * pieceIndX) + (downStep.x * (pieceIndY+1)), points[0].y + (rightStep.y * pieceIndX) + (downStep.y * (pieceIndY+1)));
				
				
				/*Point first = new Point(points[0].x + (rightStep.x * pieceIndX) + (downStep.x * pieceIndY) + offsetsX(offsetRight, offsetDown, "first"), points[0].y + (rightStep.y * pieceIndX) + (downStep.y * pieceIndY) + offsetsY(offsetRight, offsetDown, "first"));
				Point second =  new Point(points[0].x + (rightStep.x * (pieceIndX+1)) + (downStep.x * pieceIndY) + offsetsX(offsetRight, offsetDown, "second"), points[0].y + (rightStep.y * (pieceIndX+1)) + (downStep.y * pieceIndY) + offsetsY(offsetRight, offsetDown, "second"));
				Point third =  new Point(points[0].x + (rightStep.x * (pieceIndX+1)) + (downStep.x * (pieceIndY+1)) + offsetsX(offsetRight, offsetDown, "third"), points[0].y + (rightStep.y * (pieceIndX+1)) + (downStep.y * (pieceIndY+1)) + offsetsY(offsetRight, offsetDown, "third"));
				Point fourth =  new Point(points[0].x + (rightStep.x * pieceIndX) + (downStep.x * (pieceIndY+1)) + offsetsX(offsetRight, offsetDown, "fourth"), points[0].y + (rightStep.y * pieceIndX) + (downStep.y * (pieceIndY+1)) + offsetsY(offsetRight, offsetDown, "fourth"));
				*/
				
				/*int fiOffX = offsetsX(offsetRight, offsetDown, "first");
				int seOffX = offsetsX(offsetRight, offsetDown, "second");
				int thOffX = offsetsX(offsetRight, offsetDown, "third");
				int foOffX = offsetsX(offsetRight, offsetDown, "fourth");
				
				int fiOffY = offsetsY(offsetRight, offsetDown, "first");
				int seOffY = offsetsY(offsetRight, offsetDown, "second");
				int thOffY = offsetsY(offsetRight, offsetDown, "third");
				int foOffY = offsetsY(offsetRight, offsetDown, "fourth");

				if (pieceIndX == 0 && pieceIndY == 0) {
					System.out.println("Only x: " + fiOffX + " " + seOffX + " " + thOffX + " " + foOffX);
					System.out.println("Only y: " + fiOffY + " " + seOffY + " " + thOffY + " " + foOffY);
				}
				Point first = new Point(points[0].x + (rightStep.x * pieceIndX) + (downStep.x * pieceIndY) + fiOffX, points[0].y + (rightStep.y * pieceIndX) + (downStep.y * pieceIndY) + fiOffY);
				Point second =  new Point(points[0].x + (rightStep.x * (pieceIndX+1)) + (downStep.x * pieceIndY) + seOffX, points[0].y + (rightStep.y * (pieceIndX+1)) + (downStep.y * pieceIndY) + seOffY);
				Point third =  new Point(points[0].x + (rightStep.x * (pieceIndX+1)) + (downStep.x * (pieceIndY+1)) + thOffX, points[0].y + (rightStep.y * (pieceIndX+1)) + (downStep.y * (pieceIndY+1)) + thOffY);
				Point fourth =  new Point(points[0].x + (rightStep.x * pieceIndX) + (downStep.x * (pieceIndY+1)) + foOffX, points[0].y + (rightStep.y * pieceIndX) + (downStep.y * (pieceIndY+1)) + foOffY);
				*/
				
				pieces[pieceIndY][pieceIndX] = new Parallellogram(new Point[] {first, second, third, fourth});
				//System.out.println("(x " + pieceIndX + ", y " + pieceIndY + "): " + this + " -> " + pieces[pieceIndY][pieceIndX]);
			}
		}
		return pieces;
	}
	
	public int offsetsX(Point offsetRight, Point offsetDown, String point) {
		int value = offsetRight.x + offsetDown.x;
		if (point.equals("first")) {
			return value;
		} else if (point.equals("second")) {
			return value * -1;
		} else if (point.equals("third")) {
			return value * -1;
		} else if (point.equals("fourth")) {
			return value;
		}
		
		return value;
	}
	
	public int offsetsY(Point offsetRight, Point offsetDown, String point) {
		int value = offsetRight.y + offsetDown.y;
		if (point.equals("first")) {
			return value;
		} else if (point.equals("second")) {
			return value;
		} else if (point.equals("third")) {
			return value * -1;
		} else if (point.equals("fourth")) {
			return value * -1;
		}
		
		return value;
	}
	
	public Point addPoints(Point p1, Point p2) {
		return new Point(p1.x + p2.x, p1.y + p2.y);
	}

	public void draw(Graphics g, Color c) {
		g.setColor(c);
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();
		g2d.translate(averageX(), averageY());
		g2d.scale(0.9, 0.9);
		g2d.translate(-averageX(), -averageY());
		g.fillPolygon(getArrayPointsX(), getArrayPointsY(), 4);
		g2d.setTransform(old);
	}
	
	public int averageX() {
		return (int) ((points[0].x + points[1].x + points[2].x + points[3].x) / 4.0);
	}
	
	public int averageY() {
		return (int) ((points[0].y + points[1].y + points[2].y + points[3].y) / 4.0);
	}
	
	public void draw(Graphics g, boolean big) {
		/*Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();
		g2d.scale(0.95, 0.95);*/
		g.fillPolygon(getArrayPointsX(), getArrayPointsY(), 4);
		//g2d.setTransform(old);
	}
	
	public int[] getArrayPointsX() {
		return new int[] {points[0].x, points[1].x, points[2].x, points[3].x};
	}
	
	public int[] getArrayPointsY() {
		return new int[] {points[0].y, points[1].y, points[2].y, points[3].y};
	}
	
	@Override
	public String toString() {
		String ans = "";
		for (int i = 0; i < points.length; i++) {
			ans += "(" + points[i].x + ", " + points[i].y + ")" + "; ";
		}
		return ans;
	}
	
	public boolean equals(Parallellogram comp) {
		for (int i = 0; i < points.length; i++) {
			if (points[i].x != comp.points[i].x) {
				return false;
			} else if (points[i].y != comp.points[i].y) {
				return false;
			}
		}
		return true;
	}
}
