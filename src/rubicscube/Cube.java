package rubicscube;

import java.awt.Color;

public class Cube {
	private Side current;
	private Side right;
	private Side back;
	private Side left;
	private Side top;
	private Side bottom;
	
	
	public Cube() {
		current = new Side(1);
		right = new Side(2);
		back = new Side(3);
		left = new Side(4);
		top = new Side(5);
		bottom = new Side(6);
	}
	/*public Cube() {
		current = new Side();
		right = new Side();
		back = new Side();
		left = new Side();
		top = new Side();
		bottom = new Side();
	}*/
	
	/**
	 * 
	 * @param index 0 - 3 is horizontal, 0 is current, goes to the right from current, 4 top, 5 bottom
	 * @param clockwise 
	 */
	public void rotateSide(int index, boolean clockwise) {
		if (index == 0) { //current
			rotateCurrentSide(clockwise);
		} else if (index == 1) { //right
			turnCubeFromRight();
			rotateCurrentSide(clockwise);
			turnCubeFromLeft();
		} else if (index == 2) { //back
			turnCubeFromRight();
			turnCubeFromRight();
			rotateCurrentSide(clockwise);
			turnCubeFromLeft();
			turnCubeFromLeft();
		} else if (index == 3) { //left
			turnCubeFromLeft();
			rotateCurrentSide(clockwise);
			turnCubeFromRight();
		} else if (index == 4) { //top
			turnCubeFromUp();
			rotateCurrentSide(clockwise);
			turnCubeFromDown();
		} else if (index == 5) { //bottom
			turnCubeFromDown();
			rotateCurrentSide(clockwise);
			turnCubeFromUp();
		}
	}
	
	private void rotateCurrentSide(boolean clockwise) {
		current.rotate(clockwise);
		
		if (clockwise) {
			int[] apu = arrayCopy(top.getBottomRow());
			top.replaceBottomRow(flipRow(left.getRightRow()));
			left.replaceRightRow(bottom.getTopRow());
			bottom.replaceTopRow(flipRow(right.getLeftRow()));
			right.replaceLeftRow(apu);
		} else {
			int[] apu = arrayCopy(top.getBottomRow());
			top.replaceBottomRow(right.getLeftRow());
			right.replaceLeftRow(flipRow(bottom.getTopRow()));
			bottom.replaceTopRow(left.getRightRow());
			left.replaceRightRow(flipRow(apu));
		}
	}
	
	//so it shows right side
	public void turnCubeFromRight() {
		Side apu = current;
		current = right;
		right = back;
		back = left;
		left = apu;
		
		top.rotate(true);
		bottom.rotate(false);
	}
	
	public void turnCubeFromLeft() {
		Side apu = current;
		current = left;
		left = back;
		back = right;
		right = apu;
		
		top.rotate(false);
		bottom.rotate(true);
	}
	
	public void turnCubeFromUp() {
		Side apu = current;
		current = top;
		top = back;
		top.flipUpsideDown();
		top.flipSideways();
		back = bottom;
		back.flipUpsideDown();
		back.flipSideways();
		bottom = apu;
		
		left.rotate(true);
		right.rotate(false);
	}
	
	public void turnCubeFromDown() {
		Side apu = current.copy();
		current = bottom.copy();
		bottom = back.copy();
		bottom.flipUpsideDown();
		bottom.flipSideways();
		back = top.copy();
		back.flipUpsideDown();
		back.flipSideways();
		top = apu;
		
		left.rotate(false);
		right.rotate(true);
	}
	
	public void rotateCube(boolean clockwise) {
		if (clockwise) {
			turnCubeFromLeft();
			turnCubeFromDown();
			turnCubeFromRight();
		} else {
			turnCubeFromRight();
			turnCubeFromDown();
			turnCubeFromLeft();
		}
	}
	
	public Color getColorOfAPiece(int sideIndex, int indX, int indY) {
		return getSideFromIndex(sideIndex).getColorAfAPiece(indX, indY);
	}
	
	public Side getSideFromIndex(int sideIndex) {
		if (sideIndex == 0) { //current
			return current;
		} else if (sideIndex == 1) { //right
			return right;
		} else if (sideIndex == 2) { //back
			return back;
		} else if (sideIndex == 3) { //left
			return left;
		} else if (sideIndex == 4) { //top
			return top;
		} else if (sideIndex == 5) { //bottom
			return bottom;
		}
		return current;
	}
	
	@Override
	public String toString() {
		String toPrint = top.toString(true) + "\n";
		toPrint += left.toStringTopRow() + "\t" + current.toStringTopRow() + "\t" + right.toStringTopRow() + "\t" + back.toStringTopRow() + "\n";
		toPrint += left.toStringMiddleRow() + "\t" + current.toStringMiddleRow() + "\t" + right.toStringMiddleRow() + "\t" + back.toStringMiddleRow() + "\n";
		toPrint += left.toStringBottomRow() + "\t" + current.toStringBottomRow() + "\t" + right.toStringBottomRow() + "\t" + back.toStringBottomRow() + "\n\n";
		toPrint += bottom.toString(true);
		return toPrint;
	}

	private int[] flipRow(int[] row) {
		int apu = row[0];
		row[0] = row[2];
		row[2] = apu;
		return row;
	}
	
	public int[] arrayCopy(int[] ar) {
		int[] uus = new int[ar.length];
		for (int i = 0; i < ar.length; i++) {
			uus[i] = ar[i];
		}
		return uus;
	}

	public boolean isWin() {
		return current.isWin() && top.isWin() && right.isWin() && bottom.isWin() && left.isWin() && back.isWin();
	}
}
