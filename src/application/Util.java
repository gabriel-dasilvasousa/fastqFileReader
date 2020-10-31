package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Util {

	public static int fastqReader(String path, List<String> fastqFileLines, List<String> fastqSequencingLines,
			List<String> fastqQualityLines) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			int totalBases = 0;
			String line = br.readLine();
			while (line != null) {
				if (line.startsWith("@") || line.startsWith("+")) {
					line = br.readLine();
				}

				fastqFileLines.add(line);
				line = br.readLine();
			}

			for (int i = 0; i < fastqFileLines.size(); i++) {
				if (i % 2 == 0) {
					fastqSequencingLines.add(fastqFileLines.get(i));
					totalBases += fastqFileLines.get(i).length();
				}
				if (i % 2 != 0) {
					fastqQualityLines.add(fastqFileLines.get(i));
				}
			}

			return totalBases;
		} catch (IOException e) {
			System.out.println("I/O Error" + e.getMessage());
			return 0;
		}
	}

	public static int countGCNumber(List<String> fastqSequencingLines) {
		int GCNumber = 0;
		for (String sequencing : fastqSequencingLines) {
			char[] bases = sequencing.toCharArray();
			for (char base : bases) {
				if (base == "C".charAt(0) || base == "G".charAt(0)) {
					GCNumber++;
				}
			}
		}

		return GCNumber;
	}

	public static void calculateQuality(List<String> fastqQualityLines, List<Double> fastqQualityMedia) {
		int countLoop = 0;
		for (String lineOfQuality : fastqQualityLines) {
			char[] qualitysInAsc = lineOfQuality.toCharArray();
			int[] qualitysInDec = new int[lineOfQuality.length()];
			for (int i = 0; i < lineOfQuality.length(); i++) {
				qualitysInDec[i] = qualitysInAsc[i];
			}

			int countQuality = 0;

			for (int x : qualitysInDec) {
				countQuality += x;
			}

			double qualityMedia = (double) countQuality / qualitysInDec.length;
			countLoop++;
			fastqQualityMedia.add(qualityMedia);
			System.out.println("Quality Media of Sequencing " + countLoop + ": " + qualityMedia);
		}
	}

	public static void readerRelatory(String path, String text, List<Double> fastqQualityMedia) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
			bw.write(text);
			for (int i = 0; i < fastqQualityMedia.size(); i++) {
				bw.write("Quality Media of Sequencing " + (int) (i + 1) + ": " + fastqQualityMedia.get(i) + "\n");
			}
			System.out.println("Successfull, relatory file created");
		} catch (IOException e) {
			e.getMessage();
		}
	}
}
