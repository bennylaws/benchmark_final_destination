package bench1;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DBI42Bench1 {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		int n;
		double start, stop, diff;

		System.out.print("Jetzt n eingeben: ");
		n = scanner.nextInt();
		scanner.close();

		// Random Array for branchid in accounts
		int[] randomArr = new int[n * 100000];
		for (int i = 0; i < 100000; i++)
			randomArr[i] = (int) (1 + (Math.random() * n));

		Connection conni = null;
		Statement stmt = null;
		PreparedStatement prepStmt = null;

		// Verbindung herstellen
		try {
			conni = DriverManager.getConnection("jdbc:mysql://localhost",
					"dbi", "dbi_pass");
			// conni = DriverManager.getConnection(
			// "jdbc:mysql://localhost", "bench", "atb");
		}

		catch (SQLException e) {
			String err = e.getMessage();
			System.out.println("Fehler...");
			System.out.println(err);
			System.exit(-1);
		}

		System.out.println("Sehr verbunden :)");

		try {
			stmt = conni.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Init.init(stmt);

		start = System.nanoTime();

		try {
			FileWriter fw = new FileWriter("C:/User/SQL.txt");
			Fill.fill(conni, prepStmt, n, fw, randomArr);
			fw.flush();
			fw.close();
		} catch (Exception e) {
			System.out.println("Fehler...");
			System.err.println(e);

		}

		// Foreign Key checks wieder einschalten
		try {
			prepStmt = conni.prepareStatement(Statements.after1);
			prepStmt.execute();
			prepStmt = conni.prepareStatement(Statements.after2);
			prepStmt.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		stop = System.nanoTime();
		diff = stop - start;
		System.out.printf("Fertig, es hat %.2fs gedauert", diff / 1000000000);

		// Verbindung beenden, Programm verlassen
		try {
			conni.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}