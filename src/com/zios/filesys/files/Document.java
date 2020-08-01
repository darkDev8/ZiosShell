package com.zios.filesys.files;

import com.zios.root.Strings;
import com.zios.root.ToolBox;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Document extends FileSYS {

    private long characters;
    private long lines;
    private long words;
    private long emptyLines;

    public Document(String path) {
        super(path);

        this.lines = getFileInfo(path, 3);
        this.characters = getFileInfo(path, 1);
        this.words = getFileInfo(path, 2);
        this.emptyLines = getFileInfo(path, 6);
    }

    public String read(String path) {
        return read(path, false, -1);
    }

    public String read(String path, boolean reverse) {
        return read(path, reverse, -1);
    }

    public String read(String path, boolean reverse, int length) {
        try {
            StringBuilder builder = new StringBuilder();

            if (exist()) {
                FileReader fr = new FileReader(path);
                if (length < 0) {
                    int n;
                    while ((n = fr.read()) != -1) {
                        builder.append((char) n);
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        int j = fr.read();
                        builder.append((char) j);
                    }
                }

                if (reverse) {
                    return builder.reverse().toString();
                }

                return builder.toString();
            }

            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int write(String path, String text, boolean append) {

        try {
            if (!exist()) {
                if (!create(path)) {
                    return -1;
                }
            }

            if (append) {
                Files.write(Paths.get(path), text.getBytes(), StandardOpenOption.APPEND);
            } else {
                Files.write(Paths.get(path), text.getBytes(), StandardOpenOption.WRITE);
            }

            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return -2;
        }
    }

    public long getFileInfo(String path, int mode) {
        long lines = 0, words = 0, characters = 0, whiteSpaces = 0,
                sentence = 0, emptyLines = 0;

        if (exist()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)));) {
                String line = null;

                while ((line = reader.readLine()) != null) {
                    if (!line.equals("")) {
                        lines++;

                        characters += line.length();

                        String[] wordList = new Strings().split(line, " ");
                        words += wordList.length;
                        whiteSpaces += words - 1;

                        String[] sentenceList = line.split("[!?.:]+");
                        sentence += sentenceList.length;
                    } else {
                        emptyLines++;
                    }
                }

                switch (mode) {
                    case 1:
                        return characters;

                    case 2:
                        return words;

                    case 3:
                        return lines;

                    case 4:
                        return whiteSpaces;

                    case 5:
                        return sentence;

                    case 6:
                        return emptyLines;

                    default:
                        return -1;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return -1;
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return -1;
    }

    public boolean create(String path) {
        try {
            return new File(path).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public long getCharacters() {
        return characters;
    }

    public long getLines() {
        return lines;
    }

    public long getWords() {
        return words;
    }

    public long getEmptyLines() {
        return emptyLines;
    }
}
