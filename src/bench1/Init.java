package bench1;

import java.sql.Statement;

public class Init {

	static void init(Statement stmt) {

		// in Schleife alle Einzel-Strings aus strings.xxxxxx ausfuehren
		try {
			for (String str : Statements.createDb) {
				System.out.println(str);
				stmt.executeUpdate(str);
			}
		} catch (Exception e) {
			System.out.println("Error...");
			System.out.println(e);
		}
	}

}
