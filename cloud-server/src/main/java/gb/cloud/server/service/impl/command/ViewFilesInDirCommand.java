package gb.cloud.server.service.impl.command;


import gb.cloud.server.service.CommandService;

import java.io.File;
import java.util.Objects;

public class ViewFilesInDirCommand implements CommandService {

    public static String loggedName = "___";

    public String processCommand(String command) {
        final int requirementCountCommandParts = 3;

        String[] actualCommandParts = command.split("=", 3);
        if (actualCommandParts.length != requirementCountCommandParts) {
            throw new IllegalArgumentException("Command \"" + getCommand() + "\" is not correct");
        }

        loggedName = actualCommandParts[2];
        return process(actualCommandParts[1]);
    }

    private static String process(String dirPath) {
        File directory = new File(dirPath);

        if (!directory.exists()) {
            return "Directory is not exists";
        }

        StringBuilder builder = new StringBuilder();

        if (dirPath.startsWith("Cloud") & dirPath.length() > 5) {
            builder.append(".. | UP ").append(System.lineSeparator());
        }

        if (dirPath.startsWith("C:\\") & dirPath.length() > 4) {
            builder.append(".. | UP ").append(System.lineSeparator());
        }

        for (File childFile : Objects.requireNonNull(directory.listFiles())) {
            String typeFile = getTypeFile(childFile);
            if (childFile.getName().startsWith("___private___")) {
                String privateName = childFile.getName().substring(13);
                System.out.println("Private folder! " + privateName);
                System.out.println("Current user " + loggedName);
                if (loggedName.equals(privateName)) {
                    System.out.println("Show private folder " + childFile.getName());
                    builder.append(childFile.getName()).append(" | ").append(typeFile).append(System.lineSeparator());
                }
            } else {
//                System.out.println(childFile.getName());
                builder.append(childFile.getName()).append(" | ").append(typeFile).append(System.lineSeparator());
            }

        }

        return builder.toString();
    }

    private static String getTypeFile(File childFile) {
        return childFile.isDirectory() ? "DIR" : "FILE";
    }

    public String getCommand() {
        return "ls";
    }

}
