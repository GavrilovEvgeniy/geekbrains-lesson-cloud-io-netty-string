package gb.cloud.server.service.impl.command;

import gb.cloud.server.service.CommandService;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class CopyFileAndDirCommand implements CommandService {

    public String processCommand(String command) throws IOException {

        System.out.println(command);
        final int requirementCountCommandParts = 3;

        String[] actualCommandParts = command.split("=", 3);
        if (actualCommandParts.length != requirementCountCommandParts) {
            throw new IllegalArgumentException("Command copy is not correct");
        }

        System.out.println(Arrays.toString(actualCommandParts));

        File sourceF = new File(actualCommandParts[1]);
        File destF = new File(actualCommandParts[2]);
        copy1(sourceF, destF);
        return "Well done";
    }

    @Override
    public String getCommand() {
        return "tocloud";
    }

    public static void copy1(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            copyDirectory(sourceLocation, targetLocation);
        } else {
            copyFile(sourceLocation, targetLocation);
        }
    }

    private static void copyDirectory(File source, File target) throws IOException {
        if (!target.exists()) {
            target.mkdir();
        }

        for (String f : Objects.requireNonNull(source.list())) {
            copy1(new File(source, f), new File(target, f));
        }
    }

    private static void copyFile(File source, File target) throws IOException {
        try (
                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(target)
        ) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }

}
