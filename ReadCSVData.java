package fileReading;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class ReadCSVData {

	private static String csvFilePath_1 = "/home/krawler/sample_Files/csv1.csv";
	private static String csvFilePath_2 = "/home/krawler/sample_Files/csv2.csv";
	Map<String,Integer> map = new HashMap<>();
	
	public static void main(String[] args) {

//		System.out.println("----------Reading CSV File Line by Line-----------");
//		readCSVLineByLine(csvFilePath_1, csvFilePath_2);
//		System.out.println("----------Reading CSV File All at Once-----------");
//		readCSVAllatOnce(csvFilePath_1, csvFilePath_2);
//		System.out.println("----------Reading CSV File by Custom Separator-----------");
//		readCSVLineByCustomSeparator(csvFilePath_1, csvFilePath_2);
		System.out.println("----------Check two CSV Files how many percentage are same-----------");
		readSimilarityOfCSVFiles(csvFilePath_1, csvFilePath_2);
		System.out.println("----------End of CSV Reading-----------");

	}

	private static void readSimilarityOfCSVFiles(String csv1, String csv2) {
		try (FileReader fileReader1 = new FileReader(csv1);
//				FileReader fileReader2 = new FileReader(csv2);
				CSVReader reader1 = new CSVReader(fileReader1)) {

			String[] nextRec;
			int rowno = 0;
			int rowinCrimentor = 0;
			int equalsCount = 0;

			while ((nextRec = reader1.readNext()) != null) {
				rowno++;

				for (int index = 0; index < nextRec.length; index++) {
					rowinCrimentor++;
					boolean found = isMatchFound(nextRec[index], index, new FileReader(csv2), rowno);
					if (found) {
						equalsCount++;
					}
				}

			}

//			if (rowinCrimentor == equalsCount) {
//				System.out.println("Both Files Matched");
//			} else {
//				System.out.println("Files didn't match");
//			}
			float percent = (float)equalsCount/(float)rowinCrimentor * 100;
			System.out.println("Files Matched by: " +percent +" %");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isMatchFound(String cell, int index, FileReader fileReader2, int rowno1) {

		String[] nextRec;
		int rowno2 = 0;
		try (CSVReader reader2 = new CSVReader(fileReader2)) {
			while ((nextRec = reader2.readNext()) != null) {
				rowno2++;

				if (rowno1 == rowno2) {
					for (int i = 0; i < nextRec.length; i++) {
						if (i == index && cell.equals(nextRec[i])) {
							return true;
						}
					}
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	private static void readCSVLineByCustomSeparator(String csv1, String csv2) {
		try {
			FileReader fileReader = new FileReader(csv1);
			CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
			CSVReader csvReader = new CSVReaderBuilder(fileReader).withCSVParser(parser).build();

			String[] nextRecord;
			while ((nextRecord = csvReader.readNext()) != null) {
				for (String cell : nextRecord) {
					String[] cellArray = cell.split(";");
					for (String c : cellArray) {
						System.out.print(c + "\t");
					}
				}
				System.out.println();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readCSVAllatOnce(String csv1, String csv2) {
		try {
			FileReader filereader = new FileReader(csv1);
			CSVReader csvReader = new CSVReader(filereader); // doesna't skip header
//			CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build(); // Skips header
			List<String[]> nextRecords = csvReader.readAll();
			if (!nextRecords.isEmpty()) {
				for (String[] recArray : nextRecords) {
					for (String record : recArray) {
						System.out.print(record + "\t");
					}
					System.out.println();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void readCSVLineByLine(String csv1, String csv2) {
		try {
			FileReader filereader = new FileReader(csv1);
			CSVReader csvReader = new CSVReader(filereader);
			String[] nextRecord;
			while ((nextRecord = csvReader.readNext()) != null) {
				for (String cell : nextRecord) {
					System.out.print(cell + "\t");
				}
				System.out.println();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
