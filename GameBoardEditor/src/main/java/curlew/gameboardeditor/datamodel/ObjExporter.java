package curlew.gameboardeditor.datamodel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class ObjExporter {
	
	Tile2DGeometry coords;
	
	
	TerrainMap map;
	
	private static int TOTAL_VERTICES = 8;
	
	private static int TOTAL_FACES = 6;
	
	private static int numOfTiles;
	

	public ObjExporter(TerrainMap map) {
		super();
		this.map = map;
	}

	
	public void export(File objFile) throws IOException {
		FileWriter fWriter = new FileWriter(objFile);
		PrintWriter fileOut = new PrintWriter(fWriter);
		
		if (map.getTileShape() == Tile2DGeometry.TileShape.SQUARE) {
			exportSquareVertices(fileOut);
			exportSquareFaces(fileOut);
		} else {
			exportHexVertices(fileOut);
			exportHexFaces(fileOut);	
		}
		fileOut.close();
	}
	private void exportSquareVertices(PrintWriter fileOut) {
		
		for (int r = 0; r < map.getRows() ; r++) {
			for ( int c = 0 ; c < map.getColumns() ; c++) {
				double z = map.getHeight(r, c);
				fileOut.println("v " + c      + " "  + r     + " "  + z);
				fileOut.println("v " + c      + " "  + r     + " "  + 0);
				fileOut.println("v " + c      + " "  + (r+1) + " "  + 0);
				fileOut.println("v " + c      + " "  + (r+1) + " "  + z);
				fileOut.println("v " + (c+1)  + " "  + r     + " "  + z);
				fileOut.println("v " + (c+1)  + " "  + r     + " "  + 0);
				fileOut.println("v " + (c+1)  + " "  + (r+1) + " "  + 0);
				fileOut.println("v " + (c+1)  + " "  + (r+1) + " "  + z);
				fileOut.println(" ");


				}
				
			}
		}
	
	
	private void exportSquareFaces(PrintWriter fileOut) {
		int count = 0;
		for (int r = 0; r < map.getRows() ; r++) {
			for ( int c = 0 ; c < map.getColumns() ; c++) {
				double z = map.getHeight(r, c);
				int N = count++;
				System.out.println("N = " + N);
				fileOut.println("f " + (8*N+4) + " " + (8*N+3) + " " + (8*N+2) + " " + (8*N+1));
				fileOut.println("f " + (8*N+2) + " " + (8*N+6) + " " + (8*N+5) + " " + (8*N+1));
				fileOut.println("f " + (8*N+3) + " " + (8*N+7) + " " + (8*N+6) + " " + (8*N+2));
				fileOut.println("f " + (8*N+8) + " " + (8*N+7) + " " + (8*N+3) + " " + (8*N+4));
				fileOut.println("f " + (8*N+5) + " " + (8*N+8) + " " + (8*N+4) + " " + (8*N+1));
				fileOut.println("f " + (8*N+6) + " " + (8*N+7) + " " + (8*N+8) + " " + (8*N+5));
				fileOut.println(" ");

			}
		}
		
	}
	
	private void exportHexVertices(PrintWriter fileOut) {
		for (int r = 0; r < map.getRows() ; r++) {
			for ( int c = 0 ; c < map.getColumns() ; c++) {
				double z = map.getHeight(r, c);
				Tile2DGeometry tile = map.getShapeAt(r, c);
				double[] xCoords= tile.getPolygonXCoords(1);
				double[] yCoords = tile.getPolygonYCoords(1);
				

				fileOut.println("v " + xCoords[0]  + " " + yCoords[0]   + " " + 0);				
				fileOut.println("v " + xCoords[1]  + " " + yCoords[1]   + " " + 0);
				fileOut.println("v " + xCoords[2]  + " " + yCoords[2]   + " " + 0);
				fileOut.println("v " + xCoords[3]  + " " +  yCoords[3]  + " " + 0);
				fileOut.println("v " + xCoords[4]  + " " + yCoords[4]   + " " + 0);
				fileOut.println("v " + xCoords[5]  + " " + yCoords[5]   + " " + 0);
				fileOut.println("v " + xCoords[0]  + " " + yCoords[0]   + " " + z);
				fileOut.println("v " + xCoords[1]  + " " + yCoords[1]   + " " + z);
				fileOut.println("v " + xCoords[2]  + " " + yCoords[2]   + " " + z);
				fileOut.println("v " + xCoords[3]  + " " + yCoords[3]   + " " + z);
				fileOut.println("v " + xCoords[4]  + " " + yCoords[4]   + " " + z);
				fileOut.println("v " + xCoords[5]  + " " + yCoords[5]   + " " + z);
				fileOut.println(" ");



				
			}
	}
		
		
	}
	
	
	private void exportHexFaces(PrintWriter fileOut) {
		int count = 0;
		for (int r = 0; r <  map.getRows() ; r++) {
			for ( int c = 0 ; c < map.getColumns() ; c++) {
				int N = count++;
				System.out.println("N = " + N);
				fileOut.println("f " + (12*N+6) + " " + (12*N+5) + " " + (12*N+4) + " " + (12*N+3) + " " + (12*N+2) + " " + (12*N+1));
				fileOut.println("f " + (12*N+1) + " " + (12*N+7) + " " + (12*N+8) + " " + (12*N+2));
				fileOut.println("f " + (12*N+2) + " " + (12*N+8) + " " + (12*N+9) + " " + (12*N+3));
				fileOut.println("f " + (12*N+3) + " " + (12*N+9) + " " + (12*N+10) + " " + (12*N+4));
				fileOut.println("f " + (12*N+4) + " " + (12*N+10) + " " + (12*N+11) + " " + (12*N+5));
				fileOut.println("f " + (12*N+5) + " " + (12*N+11) + " " + (12*N+12) + " " + (12*N+6));
				fileOut.println("f " + (12*N+6) + " " + (12*N+12) + " " + (12*N+7) + " " + (12*N+1));
				fileOut.println("f " + (12*N+7) + " " + (12*N+8) + " " + (12*N+9) + " " + (12*N+10) + " " + (12*N+11) + " " + (12*N+12));

									
				fileOut.println(" ");



				
			}
		}
	}
	
	
}

