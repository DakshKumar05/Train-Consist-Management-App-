package main;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * UC11: Validate Train ID & Cargo Codes (Regex)
 *
 * Drawback of UC10:
 * In UC10, the system calculates total seating capacity, but all previous
 * use cases assume input data is already valid. In real railway systems,
 * user input can be incorrectly formatted, inconsistent, or prone to error.
 *
 * Goal:
 * Validate Train ID and Cargo Code formats using Regular Expressions.
 *
 * Key Concepts:
 * - Regular Expressions (Regex) for format validation
 * - Pattern class for compiled regex
 * - Matcher class for input checking
 * - matches() for exact pattern matching
 * - Format Enforcement and Data Integrity Validation
 */
public class UseCase11TrainConsistMgmt {

    // Regex pattern for Train ID: TRN- followed by exactly 4 digits
    // Valid: TRN-1234   Invalid: TRAIN12, TRN12A, 1234-TRN
    private static final String TRAIN_ID_REGEX = "TRN-\\d{4}";

    // Regex pattern for Cargo Code: PET- followed by exactly 2 uppercase letters
    // Valid: PET-AB   Invalid: PET-ab, PET123, AB-PET
    private static final String CARGO_CODE_REGEX = "PET-[A-Z]{2}";

    // Compiled Pattern objects for reuse
    private static final Pattern trainIdPattern = Pattern.compile(TRAIN_ID_REGEX);
    private static final Pattern cargoCodePattern = Pattern.compile(CARGO_CODE_REGEX);

    /**
     * Validates whether the given Train ID matches the format TRN-1234.
     * @param trainId the Train ID to validate
     * @return true if valid, false otherwise
     */
    public boolean validateTrainId(String trainId) {
        if (trainId == null) return false;
        Matcher matcher = trainIdPattern.matcher(trainId);
        return matcher.matches();
    }

    /**
     * Validates whether the given Cargo Code matches the format PET-AB.
     * @param cargoCode the Cargo Code to validate
     * @return true if valid, false otherwise
     */
    public boolean validateCargoCode(String cargoCode) {
        if (cargoCode == null) return false;
        Matcher matcher = cargoCodePattern.matcher(cargoCode);
        return matcher.matches();
    }

    public static void main(String[] args) {

        UseCase11TrainConsistMgmt validator = new UseCase11TrainConsistMgmt();

        // Train ID Validation
        String trainId1 = "TRN-1234";
        String trainId2 = "TRAIN12";
        String trainId3 = "TRN12A";
        String trainId4 = "1234-TRN";

        System.out.println("=== Train ID Validation ===");
        System.out.println(trainId1 + " -> " + (validator.validateTrainId(trainId1) ? "Valid" : "Invalid"));
        System.out.println(trainId2 + " -> " + (validator.validateTrainId(trainId2) ? "Valid" : "Invalid"));
        System.out.println(trainId3 + " -> " + (validator.validateTrainId(trainId3) ? "Valid" : "Invalid"));
        System.out.println(trainId4 + " -> " + (validator.validateTrainId(trainId4) ? "Valid" : "Invalid"));

        // Cargo Code Validation
        String cargoCode1 = "PET-AB";
        String cargoCode2 = "PET-ab";
        String cargoCode3 = "PET123";
        String cargoCode4 = "AB-PET";

        System.out.println("\n=== Cargo Code Validation ===");
        System.out.println(cargoCode1 + " -> " + (validator.validateCargoCode(cargoCode1) ? "Valid" : "Invalid"));
        System.out.println(cargoCode2 + " -> " + (validator.validateCargoCode(cargoCode2) ? "Valid" : "Invalid"));
        System.out.println(cargoCode3 + " -> " + (validator.validateCargoCode(cargoCode3) ? "Valid" : "Invalid"));
        System.out.println(cargoCode4 + " -> " + (validator.validateCargoCode(cargoCode4) ? "Valid" : "Invalid"));
    }
}
