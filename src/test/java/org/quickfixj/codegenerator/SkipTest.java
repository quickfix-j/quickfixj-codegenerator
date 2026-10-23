package org.quickfixj.codegenerator;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SkipTest {

    private File outputDirectory = new File("./target/test-output-skip/");
    private File dictFile = new File("./src/test/resources/FIX41.xml");
    private GenerateMojo mojo;

    @BeforeEach
    public void setup() throws IOException {
        if (outputDirectory.exists()) {
            FileUtils.deleteDirectory(outputDirectory);
        }
        mojo = new GenerateMojo();
        mojo.setDictFile(dictFile);
        mojo.setOutputDirectory(outputDirectory);
        mojo.setSchemaDirectory(new File("./src/main/resources/org/quickfixj/codegenerator"));
        mojo.setPackaging("quickfix.fix41");
    }

    @Test
    public void testNothingGeneratedWhenSkipTrue() throws MojoExecutionException {
        mojo.setSkip(true);
        mojo.execute();

        assertFalse(new File(outputDirectory, "quickfix/field/AllocShares.java").exists());
    }

    @Test
    public void testGeneratedWhenSkipFalse() throws MojoExecutionException {
        mojo.setSkip(false);
        mojo.execute();

        assertTrue(new File(outputDirectory, "quickfix/field/AllocShares.java").exists());
    }
}
