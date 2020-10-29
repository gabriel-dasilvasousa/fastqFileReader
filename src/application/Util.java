package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Util {
	
	public static int fastqReader(String path, List<String> fastqFileLines, List<String> fastqSequencingLines, List<String> fastqQualityLines) {
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			int totalBases=0;
			String line = br.readLine();
			while(line != null) {
				if(line.startsWith("@ERR") || line.startsWith("+ERR")) {
					line = br.readLine();
				}
				
				fastqFileLines.add(line);
				line = br.readLine();
			}
			
			for(int i=0; i<fastqFileLines.size(); i++) {
				if(i%2==0) {
					fastqSequencingLines.add(fastqFileLines.get(i));
					totalBases += fastqFileLines.get(i).length();
				}
				fastqQualityLines.add(fastqFileLines.get(i));
			}
			
			return totalBases;
		}
		catch(IOException e) {
			System.out.println("I/O Error" + e.getMessage());
			return 0;
		}
	}
	
	public static int countGCNumber(List<String> fastqSequencingLines) {
		int GCNumber = 0;
		for(String sequencing : fastqSequencingLines) {
			char[] bases = sequencing.toCharArray();
			for(char base : bases) {
				if(base == "C".charAt(0) || base == "G".charAt(0)) {
					GCNumber++;
				}
			}
		}
		
		return GCNumber;
	}
	
	public static void calculateQuality(List<String> fastqQualityLines) {
		int countLoop=0;
		for(String lineOfQuality : fastqQualityLines) {
			char[] qualitysInAsc = lineOfQuality.toCharArray();
			int[] qualitysInDec = new int[lineOfQuality.length()];
			for(int i=0; i<lineOfQuality.length();i++) {
				qualitysInDec[i] = qualitysInAsc[i];
			}
			
			int countQuality=0;
			
			for(int x : qualitysInDec) {
				countQuality+=x;
			}
			
			double qualityMedia = (double) countQuality/qualitysInDec.length;
			countLoop++;
			System.out.println("Quality Media of Sequencing " + countLoop + ": " + String.format("%.2f", qualityMedia));
		}
	}
}
