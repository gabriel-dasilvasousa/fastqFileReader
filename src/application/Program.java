package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
	
	public static void main(String[] args) {
		
		List<String> fastqFileLines = new ArrayList<>();
		List<String> fastqSequencingLines = new ArrayList<>();
		List<String> fastqQualityLines = new ArrayList<>();
		
		int totalBases, GCNumber;
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the path of your .fastq file: ");
		String path = sc.nextLine();
		
		totalBases = Util.fastqReader(path, fastqFileLines, fastqSequencingLines, fastqQualityLines);
		GCNumber = Util.countGCNumber(fastqSequencingLines);
		Util.calculateQuality(fastqQualityLines);
		
		System.out.println("GCNumber " + GCNumber);
		System.out.println("total bases " + totalBases);
		double contentGc = (double) GCNumber/totalBases;
		System.out.println("GCcontent " + Math.ceil(contentGc*100) + "%");
		sc.close();
	}
}
