package curlew.gameboardeditor.datamodel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class ObjExporter {
	
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
		exportVertices(fileOut);
		exportFaces(fileOut);
		fileOut.close();
	}
	private void exportVertices(PrintWriter fileOut) {
		
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
	
	
	private void exportFaces(PrintWriter fileOut) {
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
}

