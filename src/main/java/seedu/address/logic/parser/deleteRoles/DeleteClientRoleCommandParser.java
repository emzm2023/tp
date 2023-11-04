package seedu.address.logic.parser.deleteRoles;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.deleteRoles.DeleteClientRoleCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteClientRoleCommandParser implements Parser<DeleteClientRoleCommand> {
    @Override
    public DeleteClientRoleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (!args.isEmpty()) {
            String role = args.substring(1);
            return new DeleteClientRoleCommand(role);
        } else {
            throw new ParseException("Role cannot be empty!");
        }
    }
}
