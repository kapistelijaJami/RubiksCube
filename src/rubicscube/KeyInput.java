package rubicscube;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	private RubicsCube peli;
	private Cube cube;
	private boolean arrowUpHold = false;
	private boolean arrowRightHold = false;
	private boolean arrowDownHold = false;
	private boolean arrowLeftHold = false;
	
	public KeyInput(RubicsCube peli) {
		this.peli = peli;
		cube = peli.getCube();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (anyIsHolding()) {
			if (arrowUpHold) {
				if (key == KeyEvent.VK_A || key == KeyEvent.VK_Q) {
					cube.rotateSide(4, true);
					peli.isMoved = true;
				} else if (key == KeyEvent.VK_D || key == KeyEvent.VK_E) {
					cube.rotateSide(4, false);
					peli.isMoved = true;
				}
			} else if (arrowRightHold) {
				if (key == KeyEvent.VK_W) {
					cube.rotateSide(1, true);
					peli.isMoved = true;
				} else if (key == KeyEvent.VK_S) {
					cube.rotateSide(1, false);
					peli.isMoved = true;
				}
			} else if (arrowDownHold) {
				if (key == KeyEvent.VK_A || key == KeyEvent.VK_Q) {
					cube.rotateSide(5, false);
					peli.isMoved = true;
				} else if (key == KeyEvent.VK_D || key == KeyEvent.VK_E) {
					cube.rotateSide(5, true);
					peli.isMoved = true;
				}
			} else if (arrowLeftHold) {
				if (key == KeyEvent.VK_W) {
					cube.rotateSide(3, false);
					peli.isMoved = true;
				} else if (key == KeyEvent.VK_S) {
					cube.rotateSide(3, true);
					peli.isMoved = true;
				}
			}
		} else {
			if (key == KeyEvent.VK_A) {
				cube.turnCubeFromRight();
			} else if (key == KeyEvent.VK_D) {
				cube.turnCubeFromLeft();
			} else if (key == KeyEvent.VK_W) {
				cube.turnCubeFromDown();
			} else if (key == KeyEvent.VK_S) {
				cube.turnCubeFromUp();
			} else if (key == KeyEvent.VK_Q) {
				cube.rotateCube(false);
			} else if (key == KeyEvent.VK_E) {
				cube.rotateCube(true);
			} else if (key == KeyEvent.VK_DELETE) {
				cube.rotateSide(0, false);
				peli.isMoved = true;
			} else if (key == KeyEvent.VK_PAGE_DOWN) {
				cube.rotateSide(0, true);
				peli.isMoved = true;
			}
		}
		
		if (key == KeyEvent.VK_UP) {
			resetAllHolding();
			arrowUpHold = true;
		} else if (key == KeyEvent.VK_RIGHT) {
			resetAllHolding();
			arrowRightHold = true;
		} else if (key == KeyEvent.VK_DOWN) {
			resetAllHolding();
			arrowDownHold = true;
		} else if (key == KeyEvent.VK_LEFT) {
			resetAllHolding();
			arrowLeftHold = true;
		}
	}
	
	private void resetAllHolding() {
		arrowUpHold = false;
		arrowRightHold = false;
		arrowDownHold = false;
		arrowLeftHold = false;
	}
	
	private boolean anyIsHolding() {
		return arrowUpHold || arrowRightHold || arrowDownHold || arrowLeftHold;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_UP) {
			resetAllHolding();
		} else if (key == KeyEvent.VK_RIGHT) {
			resetAllHolding();
		} else if (key == KeyEvent.VK_DOWN) {
			resetAllHolding();
		} else if (key == KeyEvent.VK_LEFT) {
			resetAllHolding();
		}
	}
}
