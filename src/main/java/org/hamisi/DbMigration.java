package org.hamisi;

import io.github.cdimascio.dotenv.Dotenv;
import org.flywaydb.core.Flyway;

/**
 * This class Initializes Flyway database migration tool.
 * Loads DB credentials from .env file, configures MySQL connection,
 * and executes pending migrations defined in resourced.db.migration automatically.
 * @see Flyway
 * @see Dotenv
 */

public class DbMigration {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String pass = dotenv.get("DB_PASS");
        String user = dotenv.get("DB_USER");
        String url = "jdbc:mysql://localhost:3306/collegeDB";


        Flyway flyway = Flyway.configure()
                .dataSource(url, user, pass)
                .baselineOnMigrate(true)
                .defaultSchema("collegeDB")
                .load();

        flyway.repair();
        flyway.migrate();

        System.out.println("Database migrations completed successfully!");
    }
}
