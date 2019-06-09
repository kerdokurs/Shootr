package me.kerdo.shootr.utils;

public class InvPos {
	public static int[] xPos;
	public static int[] yPos;
	
	public static void init() {
		xPos = new int[32];
		yPos = new int[xPos.length];
		loadX();
		loadY();
		/*
		for(int i = 0; i < invHeight; i++) {
			xPos[i * 8] = 66;
			xPos[(i * 8) + 1] = 138;
			xPos[(i * 8) + 2] = 210;
			xPos[(i * 8) + 3] = 282;
			xPos[(i * 8) + 4] = 354;
			xPos[(i * 8) + 5] = 426;
			xPos[(i * 8) + 6] = 498;
			xPos[(i * 8) + 7] = 570;
		}
		*/
	}
	
	public static void loadX() {
		xPos[0] = 66;
		xPos[1] = 138;
		xPos[2] = 210;
		xPos[3] = 282;
		xPos[4] = 354;
		xPos[5] = 426;
		xPos[6] = 498;
		xPos[7] = 570;
		xPos[8] = 66;
		xPos[9] = 138;
		xPos[10] = 210;
		xPos[11] = 282;
		xPos[12] = 354;
		xPos[13] = 426;
		xPos[14] = 498;
		xPos[15] = 570;
		xPos[16] = 66;
		xPos[17] = 138;
		xPos[18] = 210;
		xPos[19] = 282;
		xPos[20] = 354;
		xPos[21] = 426;
		xPos[22] = 498;
		xPos[23] = 570;
		xPos[24] = 66;
		xPos[25] = 138;
		xPos[26] = 210;
		xPos[27] = 282;
		xPos[28] = 354;
		xPos[29] = 426;
		xPos[30] = 498;
		xPos[31] = 570;
	}
	
	public static void loadY() {
		yPos[0] = 56;
		yPos[1] = 56;
		yPos[2] = 56;
		yPos[3] = 56;
		yPos[4] = 56;
		yPos[5] = 56;
		yPos[6] = 56;
		yPos[7] = 56;
		yPos[8] = 128;
		yPos[9] = 128;
		yPos[10] = 128;
		yPos[11] = 128;
		yPos[12] = 128;
		yPos[13] = 128;
		yPos[14] = 128;
		yPos[15] = 128;
		yPos[16] = 200;
		yPos[17] = 200;
		yPos[18] = 200;
		yPos[19] = 200;
		yPos[20] = 200;
		yPos[21] = 200;
		yPos[22] = 200;
		yPos[23] = 200;
		yPos[24] = 272;
		yPos[25] = 272;
		yPos[26] = 272;
		yPos[27] = 272;
		yPos[28] = 272;
		yPos[29] = 272;
		yPos[30] = 272;
		yPos[31] = 272;
	}
}
/*
66
138
210
282
354
426
498
570*/