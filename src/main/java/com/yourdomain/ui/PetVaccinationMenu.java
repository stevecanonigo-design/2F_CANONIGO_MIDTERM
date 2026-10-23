package com.yourdomain.ui;

import com.yourdomain.dao.PetVaccinationDao;
import com.yourdomain.model.PetVaccinationEntity;

import java.util.List;
import java.util.Scanner;

public class PetVaccinationMenu {
    private static final String LINE = "=".repeat(52);
    private final Scanner scanner = new Scanner(System.in);
    private final PetVaccinationDao dao = new PetVaccinationDao();

    public void start() {
        boolean running = true;
        while (running) {
            showMenu();
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();   // nextLine() leaves no leftover newline
            switch (choice) {
                case "1" -> addRecord();
                case "2" -> viewRecords();
                case "3" -> {
                    System.out.println("\nExiting system... Thank you!");
                    running = false;
                }
                default -> System.out.println("\nERROR: Invalid choice. Please enter 1, 2, or 3.\n");
            }
        }
        scanner.close();
    }

    private void showMenu() {
        System.out.println(LINE);
        System.out.println("          PET CLINIC VACCINATION LOG");
        System.out.println(LINE);
        System.out.println("[1] Add Pet Record");
        System.out.println("[2] View Vaccination Records");
        System.out.println("[3] Exit System");
        System.out.println(LINE);
    }

    private void addRecord() {
        System.out.println("\n--- ADD PET VACCINATION RECORD ---");

        String name;
        while (true) {
            System.out.print("Enter Pet Name: ");
            name = scanner.nextLine().trim();
            if (!name.isEmpty()) break;
            System.out.println("ERROR: Pet name cannot be empty.");
        }

        String species;
        while (true) {
            System.out.print("Enter Species (DOG / CAT / BIRD): ");
            species = scanner.nextLine().trim().toUpperCase();
            if (species.equals("DOG") || species.equals("CAT") || species.equals("BIRD")) break;
            System.out.println("ERROR: Species must be DOG, CAT, or BIRD.");
        }

        String vaccine;
        while (true) {
            System.out.print("Enter Vaccine Type (RABIES / DHPP / FVRCP): ");
            vaccine = scanner.nextLine().trim().toUpperCase();
            if (vaccine.equals("RABIES") || vaccine.equals("DHPP") || vaccine.equals("FVRCP")) break;
            System.out.println("ERROR: Vaccine must be RABIES, DHPP, or FVRCP.");
        }

        double fee;
        while (true) {
            System.out.print("Enter Service Fee: ");
            String input = scanner.nextLine().trim();
            try {
                fee = Double.parseDouble(input);
                if (fee >= 0) break;
                System.out.println("ERROR: Fee cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Please enter a valid number (e.g., 500.00).");
            }
        }

        PetVaccinationEntity pet = new PetVaccinationEntity(0, name, species, vaccine, fee);
        if (dao.insert(pet)) {
            System.out.println("\nSUCCESS: Pet vaccination record saved!\n");
        } else {
            System.out.println("\nERROR: Failed to save record.\n");
        }
    }

    private void viewRecords() {
        List<PetVaccinationEntity> list = dao.findAll();
        String border = "+----+--------------------+------------+--------------+----------------+";
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                     PET VACCINATION RECORDS");
        System.out.println("=".repeat(70));
        System.out.println(border);
        System.out.printf("| %-2s | %-18s | %-10s | %-12s | %-14s |%n",
                "ID", "PET NAME", "SPECIES", "VACCINE", "FEE");
        System.out.println(border);
        for (PetVaccinationEntity p : list) {
            System.out.printf("| %-2d | %-18s | %-10s | %-12s | %-14.2f |%n",
                    p.id(), p.petName(), p.species(), p.vaccine(), p.fee());
        }
        System.out.println(border);
        System.out.println("Total Records Displayed: " + list.size() + "\n");
    }
}
