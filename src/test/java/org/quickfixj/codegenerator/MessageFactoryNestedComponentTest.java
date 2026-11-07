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

        // 1. 코드 생성 작업 설정
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

        // 2. FIX Dictionary -> Java 소스 생성
        new MessageCodeGenerator().generate(task);

        // 3. 생성된 MessageFactory.java 읽기
        File factoryFile = new File(
            outputDir, "quickfix/test/MessageFactory.java"
        );

        String factory = new String(
            Files.readAllBytes(factoryFile.toPath()),
            StandardCharsets.UTF_8
        );

        // 4. 기존에 생성되던 바깥쪽 그룹 확인
        assertTrue(
            factory.contains(
                "return new quickfix.test."
                + "TradeCaptureReport.NoUnderlyings();"
            ),
            "Outer group should be generated"
        );

        // 5. 이슈 #822: 안쪽 그룹 생성 분기 확인
        assertTrue(
            factory.contains(
                "case quickfix.field.NoUnderlyingSecurityAltID.FIELD:"
            ),
            "Group 457 factory case is missing"
        );

        // 6. 올바른 중첩 Java 클래스 생성 확인
        assertTrue(
            factory.contains(
                "return new quickfix.test.TradeCaptureReport."
                + "NoUnderlyings.NoUnderlyingSecurityAltID();"
            ),
            "Nested group factory class is missing"
        );
    }
}