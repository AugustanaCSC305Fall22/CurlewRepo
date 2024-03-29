package curlew.gameboardeditor.datamodel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * 
 * @author Team Curlew
 * This class is responsible for exporting TerrainMaps to obj file format
 *
 */

public class ObjExporter {
	
	TerrainMap map;
	
	/**
	 * Makes an ObjExporter for a given TerrainMap.
	 * 
	 */

	public ObjExporter(TerrainMap map) {
		super();
		this.map = map;
	}

	/**
	 * Returns the OBJ file of the TerrainMap
	 * @param objFile The file to write in.
	 * @throws IOException
	 */
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
	
	// Writes the vertices of Square grids.
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
	
	// Writes the faces of Square grids.
	private void exportSquareFaces(PrintWriter fileOut) {
		int count = 0;
		for (int r = 0; r < map.getRows() ; r++) {
			for ( int c = 0 ; c < map.getColumns() ; c++) {
				int N = count++;
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
	
	//Writes the vertices of Hexagon grids.
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
	
	//Writes the face of Hexagon grids.
	private void exportHexFaces(PrintWriter fileOut) {
		int count = 0;
		for (int r = 0; r <  map.getRows() ; r++) {
			for ( int c = 0 ; c < map.getColumns() ; c++) {
				int N = count++;
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

