package org.quickfixj.codegenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class MessageFactoryNestedComponentTest {

    @TempDir
    File outputDir;

    @Test
    public void generatesFactoryForNestedComponentGroup() throws Exception {

        MessageCodeGenerator.Task task = new MessageCodeGenerator.Task();

        task.setName("NestedComponentTest");
        task.setSpecification(new File(
            "src/test/resources/org/quickfixj/codegenerator/"
            + "MessageFactoryNestedComponent.xml"
        ));

        task.setTransformDirectory(new File(
            "src/main/resources/org/quickfixj/codegenerator"
        ));

        task.setMessagePackage("quickfix.test");
        task.setFieldPackage("quickfix.field");
        task.setOutputBaseDirectory(outputDir);
        task.setOverwrite(true);
        task.setOrderedFields(true);
        task.setDecimalGenerated(false);

        new MessageCodeGenerator().generate(task);

        File factoryFile = new File(
            outputDir, "quickfix/test/MessageFactory.java"
        );

        String factory = new String(
            Files.readAllBytes(factoryFile.toPath()),
            StandardCharsets.UTF_8
        );

        assertTrue(
            factory.contains(
                "return new quickfix.test."
                + "TradeCaptureReport.NoUnderlyings();"
            ),
            "Outer group should be generated"
        );

        assertTrue(
            factory.contains(
                "case quickfix.field.NoUnderlyingSecurityAltID.FIELD:"
            ),
            "Group 457 factory case is missing"
        );

        assertTrue(
            factory.contains(
                "return new quickfix.test.TradeCaptureReport."
                + "NoUnderlyings.NoUnderlyingSecurityAltID();"
            ),
            "Nested group factory class is missing"
        );
    }
}