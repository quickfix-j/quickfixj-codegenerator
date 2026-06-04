# QuickFIX/J Code Generator Maven Plugin

A Maven plugin that generates Java source files for FIX (Financial Information eXchange) messages from QuickFIX XML dictionaries.

## Overview

The QuickFIX/J Code Generator Maven Plugin allows you to automatically create Java sources for FIX Messages based on a QuickFIX XML dictionary. This plugin simplifies the development of FIX protocol implementations by generating the necessary message classes, field definitions, and other FIX-related Java code.

## Requirements

- Maven 3.9.12 or higher
- Java 8 or higher

## Installation

Add the plugin to your project's `pom.xml`:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.quickfixj</groupId>
            <artifactId>quickfixj-codegenerator</artifactId>
            <version>3.0.2</version>
            <executions>
                <execution>
                    <goals>
                        <goal>generate</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <!-- Configuration options here -->
            </configuration>
        </plugin>
    </plugins>
</build>
```

## Usage

The plugin will automatically generate Java sources during the Maven build lifecycle. Configure the plugin to specify:

- The location of your QuickFIX XML dictionary file
- The output directory for generated sources
- Package names for generated classes
- Other generation options as needed

### Running the Generator

Execute the plugin as part of your Maven build:

```bash
mvn clean install
```

Or run the plugin goal directly:

```bash
mvn quickfixj-codegenerator:generate
```

## Configuration

Detailed configuration options can be specified in the `<configuration>` section of the plugin declaration. Refer to the plugin documentation for available parameters.

## Version Information

> **NB:** The first released version is 3.0.2 so that it does not conflict with the released 3.0.0 version (which still was part of the full QuickFIX/J release). From now on the plugin will be separate from QFJ.

## License

This project is licensed under [The QuickFIX Software License, Version 1.0](http://www.quickfixj.org/documentation/license.html).

## Links

- **Website:** [http://www.quickfixj.org](http://www.quickfixj.org)
- **GitHub Repository:** [https://github.com/quickfix-j/quickfixj-codegenerator](https://github.com/quickfix-j/quickfixj-codegenerator)
- **Issue Tracker:** [https://github.com/quickfix-j/quickfixj-codegenerator/issues](https://github.com/quickfix-j/quickfixj-codegenerator/issues)

## Contributing

Contributions are welcome! Please feel free to submit issues or pull requests on GitHub.
