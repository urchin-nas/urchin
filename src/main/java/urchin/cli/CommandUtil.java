package urchin.cli;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

public class CommandUtil {

    private CommandUtil() {
    }

    public static String readResponse(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()))
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
