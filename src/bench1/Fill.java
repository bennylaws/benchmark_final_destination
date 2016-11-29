package bench1;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Fill {

	static void fill(Connection conni, PreparedStatement prepStmt, int n,
			FileWriter fw, int[] randomArr) throws SQLException, IOException {

		conni.setAutoCommit(false);

		// Schleife um die BRANCHES-Relation zu fuellen
		prepStmt = conni.prepareStatement(Statements.branches);

		prepStmt.setString(2, "nameMit20Buchstaben!");
		prepStmt.setInt(3, 0);
		prepStmt.setString(4,
				"abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrst");

		for (int i = 0; i < n; i++) {
			prepStmt.setInt(1, i + 1);
			prepStmt.addBatch();
		}
		
		prepStmt.executeBatch();
		prepStmt.clearBatch();

		// Schleife um die TELLERS-Relation zu fuellen
		prepStmt = conni.prepareStatement(Statements.tellers);

		prepStmt.setString(2, "nameMit20Buchstaben!");
		prepStmt.setInt(3, 0);
		prepStmt.setString(5,
				"abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnop");

		for (int i = 0; i < n * 10; i++) {
			prepStmt.setInt(1, i + 1);
			prepStmt.setInt(4, (int) (1 + (Math.random() * n)));
			prepStmt.addBatch();
		}
		
		prepStmt.executeBatch();
		prepStmt.clearBatch();

		// Schleife um die ACCOUNTS-Relation zu fuellen
		for (int i = 0; i < n * 100000; i++) {
			fw.write((i + 1)
					+ "\tnameMit20Buchstaben!\t0\t"
					+ randomArr[i]
					+ "\tabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnop");
			@SuppressWarnings("unused")
			String foo = System.getProperty("line.separator");
			fw.write(System.getProperty("line.separator"));
		}

		prepStmt = conni.prepareStatement(Statements.loadfile);
		prepStmt.execute();
		conni.commit();
	}
}
