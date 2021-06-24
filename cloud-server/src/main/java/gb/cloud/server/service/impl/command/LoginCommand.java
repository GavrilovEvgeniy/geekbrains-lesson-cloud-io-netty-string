package gb.cloud.server.service.impl.command;

import gb.cloud.server.service.CommandService;
import gb.cloud.server.service.impl.authorize.PGUse;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import gb.cloud.server.service.impl.authorize.PGUse;


public class LoginCommand implements CommandService {

    @Override
    public String processCommand(String command) throws SQLException, ClassNotFoundException {

        String userLogged = "";

        System.out.println(command);
        final int requirementCountCommandParts = 3;

        String[] actualCommandParts = command.split("=", 3);
        if (actualCommandParts.length != requirementCountCommandParts) {
            throw new IllegalArgumentException("Command login is not correct");
        }
        System.out.println(Arrays.toString(actualCommandParts));

        PGUse.connectToPG();
        PGUse.showUsers();

        for (String user : PGUse.userList) {
            String[] userParams = user.split(" ", 2);
            if ((userParams[0].equals(actualCommandParts[1])) & (userParams[1].equals(actualCommandParts[2]))) {
                System.out.println("Autentication done for " + userParams[0]);
                userLogged = "Logged " + userParams[0];
                break;
            }
        }

        return userLogged;
    }

    @Override
    public String getCommand() {
        return "login";
    }
}
