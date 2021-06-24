package gb.cloud.server.service.impl.command;

import gb.cloud.server.service.CommandService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

import gb.cloud.server.service.impl.authorize.PGUse;

public class AddUserCommand implements CommandService {

    @Override
    public String processCommand(String command) throws IOException, SQLException, ClassNotFoundException {

        System.out.println(command);
        final int requirementCountCommandParts = 3;

        String[] actualCommandParts = command.split("=", 3);
        if (actualCommandParts.length != requirementCountCommandParts) {
            throw new IllegalArgumentException("Command add is not correct");
        }
        System.out.println(Arrays.toString(actualCommandParts));

        PGUse.connectToPG();
        int currentUserNum = PGUse.showUsers();
        currentUserNum++;

        String S = PGUse.addUser(currentUserNum, actualCommandParts[1], actualCommandParts[2]);

        String userDirName = "Cloud\\" + "___private___" + actualCommandParts[1];
        File userDir = new File(userDirName);
        if (S.equals("Success")) {
            if (!userDir.exists()) userDir.mkdir();
            return userDirName;
        }
        if (!userDir.exists()) userDir.mkdir();
        return (S);
    }

    @Override
    public String getCommand() {
        return "add";
    }
}
