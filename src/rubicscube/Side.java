package rubicscube;

import java.awt.Color;

public class Side {
	private int[][] pieces = new int[3][3];
	
	public Side(int value) {
		for (int y = 0; y < pieces.length; y++) {
			for (int x = 0; x < pieces[y].length; x++) {
				pieces[y][x] = value;
			}
		}
	}
	
	public Side() {
		int counter = 1;
		for (int y = 0; y < pieces.length; y++) {
			for (int x = 0; x < pieces[y].length; x++) {
				/*if (counter > 6) {
					counter = 1;
				}*/
				pieces[y][x] = counter;
				counter++;
			}
		}
	}
	
	public int[] getTopRow() {
		return new int[] {pieces[0][0], pieces[0][1], pieces[0][2]};
	}
	
	public int[] getRightRow() {
		return new int[] {pieces[0][2], pieces[1][2], pieces[2][2]};
	}
	
	public int[] getBottomRow() {
		return new int[] {pieces[2][0], pieces[2][1], pieces[2][2]};
	}
	
	public int[] getLeftRow() {
		return new int[] {pieces[0][0], pieces[1][0], pieces[2][0]};
	}
	
	public void replaceTopRow(int[] row) {
		pieces[0][0] = row[0];
		pieces[0][1] = row[1];
		pieces[0][2] = row[2];
	}
	
	public void replaceRightRow(int[] row) {
		pieces[0][2] = row[0];
		pieces[1][2] = row[1];
		pieces[2][2] = row[2];
	}
	
	public void replaceBottomRow(int[] row) {
		pieces[2][0] = row[0];
		pieces[2][1] = row[1];
		pieces[2][2] = row[2];
	}
	
	public void replaceLeftRow(int[] row) {
		pieces[0][0] = row[0];
		pieces[1][0] = row[1];
		pieces[2][0] = row[2];
	}
	
	public void rotate(boolean clockwise) {
		if (clockwise) {
			int[] corners = {pieces[0][0], pieces[0][2], pieces[2][2], pieces[2][0]};
			int[] middles = {pieces[0][1], pieces[1][2], pieces[2][1], pieces[1][0]};
			pieces[0][0] = corners[3];
			pieces[0][2] = corners[0]; 
			pieces[2][2] = corners[1];
			pieces[2][0] = corners[2];
			
			pieces[0][1] = middles[3];
			pieces[1][2] = middles[0];
			pieces[2][1] = middles[1];
			pieces[1][0] = middles[2];
		} else {
			int[] corners = {pieces[0][0], pieces[2][0], pieces[2][2], pieces[0][2]};
			int[] middles = {pieces[0][1], pieces[1][0], pieces[2][1], pieces[1][2]};
			pieces[0][0] = corners[3];
			pieces[2][0] = corners[0];
			pieces[2][2] = corners[1];
			pieces[0][2] = corners[2];
			
			pieces[0][1] = middles[3];
			pieces[1][0] = middles[0];
			pieces[2][1] = middles[1];
			pieces[1][2] = middles[2];
		}
	}
	
	public void flipUpsideDown() {
		int[] apu = arrayCopy(getTopRow());
		replaceTopRow(arrayCopy(getBottomRow()));
		replaceBottomRow(apu);
	}
	
	public void flipSideways() {
		int[] apu = arrayCopy(getLeftRow());
		replaceLeftRow(getRightRow());
		replaceRightRow(apu);
	}
	
	
	
	@Override
	public String toString() {
		String print = "";
		for (int y = 0; y < pieces.length; y++) {
			for (int x = 0; x < pieces[y].length; x++) {
				print += pieces[y][x] + " ";
			}
			print += "\n";
		}
		
		return print;
	}
	
	public String toString(Boolean tabs) {
		String print = "";
		for (int y = 0; y < pieces.length; y++) {
			print += tabs ? "\t" : "";
			for (int x = 0; x < pieces[y].length; x++) {
				print += pieces[y][x] + " ";
			}
			print += "\n";
		}
		
		return print;
	}
	
	public String toStringTopRow() {
		return pieces[0][0] + " " + pieces[0][1] + " " + pieces[0][2];
	}
	
	public String toStringMiddleRow() {
		return pieces[1][0] + " " + pieces[1][1] + " " + pieces[1][2];
	}
	
	public String toStringBottomRow() {
		return pieces[2][0] + " " + pieces[2][1] + " " + pieces[2][2];
	}

	public Color getColorAfAPiece(int indX, int indY) {
		int value = pieces[indY][indX];
		switch (value) {
			case 1: 
				return Color.RED;
			case 2: 
				return Color.BLUE;
			case 3: 
				return new Color(255, 140, 0);
			case 4: 
				return Color.GREEN;
			case 5: 
				return Color.WHITE;
			case 6: 
				return new Color(255, 255, 0);
		}
		return Color.RED;
	}
	
	public int[] arrayCopy(int[] ar) {
		int[] uus = new int[ar.length];
		for (int i = 0; i < ar.length; i++) {
			uus[i] = ar[i];
		}
		return uus;
	}
	
	public Side copy() {
		Side copy = new Side();
		for (int y = 0; y < this.pieces.length; y++) {
			for (int x = 0; x < this.pieces[y].length; x++) {
				copy.pieces[y][x] = this.pieces[y][x];
			}
		}
		return copy;
	}

	public boolean isWin() {
		int firstPiece = pieces[0][0];
		
		for (int y = 0; y < pieces.length; y++) {
			for (int x = 0; x < pieces[y].length; x++) {
				if (pieces[y][x] != firstPiece) {
					return false;
				}
			}
		}
		
		return true;
	}
}
