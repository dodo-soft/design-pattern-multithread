/**
 * Copyright (C) 2014 uphy.jp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dodosoft.dpm.importer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * @author Yuhi Ishikura
 */
public class SampleProgramImporter {

    static final Charset SOURCE_ENCODING = Charset.forName("EUC-JP");
    static final Path SOURCE = Paths.get("/Users/ishikura/Documents/ソフトウェア工学勉強会/src");

    public static void main(String[] args) throws IOException {
        Path destination = Paths.get("src/main/java/com/dodosoft/dpm");

        Files.list(SOURCE).forEach(chapterDir -> {
            if (Files.isDirectory(chapterDir) == false) {
                return;
            }
            final String chapterPackageName = toPackageName(chapterDir.getFileName().toString());
            final Path chapterDestinationDir = destination.resolve(chapterPackageName);
            if (Files.exists(chapterDestinationDir)) {
                return;
            }
            try {
                Files.list(chapterDir).forEach(sampleDir -> {
                    if (Files.isDirectory(sampleDir) == false) {
                        return;
                    }

                    final String samplePackageName = toPackageName(sampleDir.getFileName().toString());
                    final Path sampleDestinationDir = chapterDestinationDir.resolve(samplePackageName);
                    if (Files.exists(sampleDestinationDir) == false) {
                        try {
                            Files.createDirectories(sampleDestinationDir);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    try {
                        copyPackage("com.dodosoft.dpm." + chapterPackageName, sampleDir, sampleDestinationDir);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(sampleDestinationDir);
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    static void copyPackage(String parentPackageName, Path source, Path destination) throws IOException {
        if (Files.exists(destination) == false) {
            try {
                Files.createDirectories(destination);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        final String packageName = parentPackageName + "." + toPackageName(source.getFileName().toString());
        Files.list(source).forEach(c -> {
            if (c.getFileName().toString().startsWith(".")) {
                return;
            }

            if (Files.isDirectory(c)) {
                try {
                    copyPackage(packageName, c, destination.resolve(c.getFileName()));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
                return;
            }

            final Path to = destination.resolve(c.getFileName());
            try (BufferedReader reader = Files.newBufferedReader(c, SOURCE_ENCODING);
                final PrintWriter writer = new PrintWriter(Files.newBufferedWriter(to, StandardCharsets.UTF_8))) {

                String line;
                boolean first = true;
                while ((line = reader.readLine()) != null) {
                    if (first) {
                        first = false;
                        writer.println("// 本ソースコードは、結城浩著 増補改訂版Java言語で学ぶデザインパターン入門マルチスレッド編(http://www.hyuki.com/dp/dp2.html)");
                        writer.println("// " + SOURCE.relativize(c));
                        writer.println("// を元にし、学習目的で一部変更を加えています。");
                        writer.printf("package %s;%n", packageName);
                        if (line.startsWith("package")) {
                            continue;
                        }
                    }
                    if (line.startsWith("import")) {
                        String importName = line.substring(6, line.length() - 1).trim();
                        if (importName.startsWith("java") == false) {
                            writer.printf("import %s;%n", packageName + "." + importName);
                            continue;
                        }
                    }
                    writer.println(line);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private static String toPackageName(String s) {
        return s.toLowerCase().replaceAll("\\-", "_");
    }

}
