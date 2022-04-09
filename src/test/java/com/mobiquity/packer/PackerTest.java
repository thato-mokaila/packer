package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class PackerTest {

    private final Logger logger = LogManager.getLogger(PackerTest.class);

    @Test
    void should_fail_on_non_existent_file_path() {
        Exception expectedEx = assertThrows(APIException.class, () ->
                Packer.pack("c:\\files\\unknown.txt")
        );
        assertTrue(expectedEx.getMessage().startsWith("Provided input file resource does not exists"));
    }

    @Test
    void should_successfully_process_all_packages_for_given_file() {

        Path resource = Paths.get("src","test","resources", "example_input");
        String absolutePath = resource.toFile().getAbsolutePath();

        String result = Packer.pack(absolutePath);
        writeToFile(result);
        assertEquals(result, "4\n-\n2,7\n8,9");
    }

    /**
     * Test utility method to write output to file. Path is
     * static and points to test resources.
     * @param results String results to write to file
     */
    private void writeToFile(String results) {

        Path resource = Paths.get("src","test","resources", "example_output");
        String outputPath = resource.toFile().getAbsolutePath();

        logger.info(outputPath);

        try {
            PrintWriter out = new PrintWriter(new FileWriter(outputPath, false), true);
            out.write(results);
            out.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}