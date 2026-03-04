package org.hamisi;

import io.github.cdimascio.dotenv.Dotenv;
import org.flywaydb.core.Flyway;

public class DbMigration {
    public static void main(String[] args) {
//      database-setup program
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String pass = dotenv.get("DB_PASS");
        String user = dotenv.get("DB_USER");
        String url = "jdbc:mysql://localhost:3306/collegeDB";


        Flyway flyway = Flyway.configure()
                .dataSource(url, user, pass)
                .baselineOnMigrate(true)
                .defaultSchema("collegeDB")
                .load();

        flyway.migrate();

        System.out.println("Database migrations completed successfully!");

    }
}
