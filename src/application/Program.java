package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
	
	public static void main(String[] args) {
		
		List<String> fastqFileLines = new ArrayList<>();
		List<String> fastqSequencingLines = new ArrayList<>();
		List<String> fastqQualityLines = new ArrayList<>();
		List<Double> fastqQualityMedia = new ArrayList<>();
		
		int totalBases, GCNumber;
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the path of your .fastq file: ");
		String path = sc.nextLine();
		
		System.out.println("Wait a minute, file analysing...");
		
		totalBases = Util.fastqReader(path, fastqFileLines, fastqSequencingLines, fastqQualityLines);
		GCNumber = Util.countGCNumber(fastqSequencingLines);
		Util.calculateQuality(fastqQualityLines, fastqQualityMedia);
		
		System.out.println("GCNumber: " + GCNumber);
		System.out.println("total bases: " + totalBases);
		double contentGc = (double) GCNumber/totalBases;
		System.out.println("GCcontent: " + Math.ceil(contentGc*100) + "%");
		
		System.out.println("Enter a complete path (include the file name) for your result relatory:");
		String pathResult = sc.nextLine();
		
		StringBuilder sb = new StringBuilder();
		sb.append("GCNumber: " + GCNumber + "\n");
		sb.append("total bases: " + totalBases + "\n");
		sb.append("GCcontent: " + Math.ceil(contentGc*100) + "%" + "\n");
		
		Util.readerRelatory(pathResult, sb.toString(), fastqQualityMedia);
		
		sc.close();
	}
}
