package seedu.address.logic.parser.imports;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_FILE;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import seedu.address.logic.commands.imports.ImportClientCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientRoles;
import seedu.address.model.client.Document;
import seedu.address.model.commons.Name;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;

public class ImportClientCommandParser implements Parser<ImportClientCommand> {

    @Override
    public ImportClientCommand parse(String fileName) throws ParseException {
        try {
            FileWriter myWriter = new FileWriter("filename.txt");
            myWriter.write("Files in Java might be tricky, but it is fun enough!");
            myWriter.close();
            fileName = fileName.trim();
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = "";
            String splitBy = ",";

            // Check if the CSV file contains valid column names
            boolean isValid = checkColumnNames(br.readLine());
            if (!isValid) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportClientCommand.MESSAGE_USAGE));
            }

            ArrayList<Client> toAddList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] clientData = line.split(splitBy);

                // Parse client data from CSV columns
                Name name = ParserUtil.parseName(clientData[0]);
                Phone phone = ParserUtil.parsePhone(clientData[1]);
                Email email = ParserUtil.parseEmail(clientData[2]);
                Address address = ParserUtil.parseAddress(clientData[3]);
                ClientRoles role = ParserUtil.parseClientRole(clientData[4]);
                Name organisation = ParserUtil.parseName(clientData[5]);
                Document document = ParserUtil.parseDocument(clientData[6]);
                ArrayList<String> projects = new ArrayList<>();
                for (int i = 7; i < clientData.length; i++) {
                    projects.add(clientData[i]);
                }
                Set<String> projectList = ParserUtil.parseProjectsToSet(projects);

                // Create a Client object
                Client client = new Client(name, phone, email, address, role, projectList, organisation, document);

                // Add the client to the list
                toAddList.add(client);
            }

            return new ImportClientCommand(toAddList);
        } catch (FileNotFoundException ex) {
            throw new ParseException(MESSAGE_INVALID_FILE);
        } catch (IOException e) {
            throw new ParseException("Error reading line from file " + fileName);
        }
    }

    // Define a method to check if the CSV file contains valid column names
    //Name name, Phone phone, Email email, Address address, Role role, Set<String> projects,
    //                  Name organisation, Document document
    private boolean checkColumnNames(String line) {
        String[] columnNames = line.split(",");
        return columnNames.length == 8 && columnNames[0].contains("Name") &&
                columnNames[1].contains("Contact Number") && columnNames[2].contains("Email") &&
                columnNames[3].contains("Address") && columnNames[4].contains("Role") &&
                columnNames[5].contains("Organisation") && columnNames[6].contains("Document") &&
                columnNames[7].contains("Projects");
    }
}
