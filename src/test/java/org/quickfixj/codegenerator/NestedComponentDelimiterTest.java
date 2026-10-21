package org.quickfixj.codegenerator;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test for issue #1084: Verify that MessageCodeGenerator correctly sets the 
 * group delimiter when the first element of a group is a component whose 
 * first element is another component (nested > 1).
 */
public class NestedComponentDelimiterTest {

    @TempDir
    File tempFolder;

    private File dictFile = new File("./src/test/resources/org/quickfixj/codegenerator/NestedComponentsTest.xml");
    private File schemaDirectory = new File("./src/main/resources/org/quickfixj/codegenerator");
    private String fieldPackage = "quickfix.field";
    private String messagePackage = "quickfix.test";
    private MessageCodeGenerator generator;

    @BeforeEach
    public void setup() throws IOException {
        generator = new MessageCodeGenerator();
    }

    @Test
    public void testNestedComponentGroupDelimiter() throws Exception {
        // Generate code from the test specification
        MessageCodeGenerator.Task task = new MessageCodeGenerator.Task();
        task.setName("NestedComponentsTest");
        task.setSpecification(dictFile);
        task.setTransformDirectory(schemaDirectory);
        task.setMessagePackage(messagePackage);
        task.setOutputBaseDirectory(tempFolder);
        task.setFieldPackage(fieldPackage);
        task.setOverwrite(true);
        task.setOrderedFields(true);
        task.setDecimalGenerated(false);
        
        generator.generate(task);

        // Verify that the NestedTwice component was generated
        String componentPath = tempFolder.getAbsolutePath() + "/quickfix/test/component/NestedTwice.java";
        File componentFile = new File(componentPath);
        assertTrue(componentFile.exists(), "NestedTwice component file should exist");

        // Read the generated file and check for the correct constructor
        String fileContent = readFileContent(componentFile);
        
        // Verify the NoEntries group class is present
        assertTrue(fileContent.contains("public static class NoEntries extends Group"),
                   "NoEntries group class should be present");
        
        // Verify the ORDER array is present
        assertTrue(fileContent.contains("private static final int[] ORDER ="),
                   "ORDER array should be present");
        
        // Verify the constructor has the correct delimiter (58 is the field number for Text)
        // The constructor should be: super(20001, 58, ORDER);
        assertTrue(fileContent.contains("super(20001, 58, ORDER);"),
                   "Constructor should contain correct delimiter");
        
        // Also verify it doesn't have an empty delimiter (the bug case)
        assertFalse(fileContent.contains("super(20001, , ORDER);"),
                    "Constructor should not have empty delimiter");
    }

    private String readFileContent(File file) throws FileNotFoundException {
        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        }
        return content.toString();
    }
}
