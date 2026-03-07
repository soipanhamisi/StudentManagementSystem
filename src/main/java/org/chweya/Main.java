package org.chweya;

public class Main {

    public static void main(String[] args) {
        ConsoleMenu menu = new ConsoleMenu();
        DbMigration migration = new DbMigration();

        migration.runMigration();
        menu.displayMenu();
    }
}
