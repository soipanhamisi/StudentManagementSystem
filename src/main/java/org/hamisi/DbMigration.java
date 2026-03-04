package org.hamisi;

import org.flywaydb.core.Flyway;

public class DbMigration {
    public static void main(String[] args) {
//      database-setup program
        String url = "jdbc:mysql://localhost:3306/collegeDB";
        String user = "root";
        String pass = "tiktok123";
        Flyway flyway = Flyway.configure()
                .dataSource(url, user, pass)
                .baselineOnMigrate(true)
                .defaultSchema("collegeDB")
                .load();

        flyway.migrate();

        System.out.println("Database migrations completed successfully!");

    }
}
