package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commons.Date;
import seedu.address.model.commons.Name;
import seedu.address.model.developer.Salary;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedDeveloper.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

public class JsonAdaptedDeveloperTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_PROJECT = "#friend";

    private static final String INVALID_DATE_JOINED = "2019";
    private static final String INVALID_USERNAME = "Rachel Ha";
    private static final String INVALID_PASSWORD = "password";
    private static final String INVALID_ROLE = "hiring";
    private static final String INVALID_SALARY = "300";

    private static final String VALID_DATE_JOINED = BENSON.getDateJoined().toString();
    private static final String VALID_USERNAME = BENSON.getUsername().toString();
    private static final String VALID_PASSWORD = BENSON.getPassword().toString();
    private static final String VALID_ROLE = BENSON.getRole().toString();
    private static final String VALID_SALARY = BENSON.getSalary().toString();

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedProject> VALID_PROJECTS = BENSON.getProjects().stream()
            .map(JsonAdaptedProject::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedDeveloper person = new JsonAdaptedDeveloper(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedDeveloper person =
                new JsonAdaptedDeveloper(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_JOINED,
                        VALID_USERNAME, VALID_PASSWORD, VALID_ROLE, VALID_SALARY, VALID_PROJECTS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedDeveloper person = new JsonAdaptedDeveloper(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_JOINED,
                VALID_USERNAME, VALID_PASSWORD, VALID_ROLE, VALID_SALARY, VALID_PROJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedDeveloper person =
                new JsonAdaptedDeveloper(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_JOINED,
                        VALID_USERNAME, VALID_PASSWORD, VALID_ROLE, VALID_SALARY, VALID_PROJECTS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedDeveloper person = new JsonAdaptedDeveloper(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_JOINED,
                VALID_USERNAME, VALID_PASSWORD, VALID_ROLE, VALID_SALARY, VALID_PROJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedDeveloper person =
                new JsonAdaptedDeveloper(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_DATE_JOINED,
                        VALID_USERNAME, VALID_PASSWORD, VALID_ROLE, VALID_SALARY, VALID_PROJECTS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedDeveloper person = new JsonAdaptedDeveloper(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_DATE_JOINED,
                VALID_USERNAME, VALID_PASSWORD, VALID_ROLE, VALID_SALARY, VALID_PROJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedDeveloper person =
                new JsonAdaptedDeveloper(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_DATE_JOINED,
                        VALID_USERNAME, VALID_PASSWORD, VALID_ROLE, VALID_SALARY, VALID_PROJECTS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedDeveloper person = new JsonAdaptedDeveloper(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_DATE_JOINED,
                VALID_USERNAME, VALID_PASSWORD, VALID_ROLE, VALID_SALARY, VALID_PROJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedProject> invalidTags = new ArrayList<>(VALID_PROJECTS);
        invalidTags.add(new JsonAdaptedProject(INVALID_PROJECT));
        JsonAdaptedDeveloper person =
                new JsonAdaptedDeveloper(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_JOINED,
                        VALID_USERNAME, VALID_PASSWORD, VALID_ROLE, VALID_SALARY, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidDateJoined_throwsIllegalValueException() {
        JsonAdaptedDeveloper person = new JsonAdaptedDeveloper(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, INVALID_DATE_JOINED,
                VALID_USERNAME, VALID_PASSWORD, VALID_ROLE, VALID_SALARY, VALID_PROJECTS);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDateJoined_throwsIllegalValueException() {
        JsonAdaptedDeveloper person = new JsonAdaptedDeveloper(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null,
                VALID_USERNAME, VALID_PASSWORD, VALID_ROLE, VALID_SALARY, VALID_PROJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidUsername_throwsIllegalValueException() {
        JsonAdaptedDeveloper person = new JsonAdaptedDeveloper(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_JOINED,
                INVALID_USERNAME, VALID_PASSWORD, VALID_ROLE, VALID_SALARY, VALID_PROJECTS);
        String expectedMessage = Username.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullUsername_throwsIllegalValueException() {
        JsonAdaptedDeveloper person = new JsonAdaptedDeveloper(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_JOINED,
                null, VALID_PASSWORD, VALID_ROLE, VALID_SALARY, VALID_PROJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Username.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPassword_throwsIllegalValueException() {
        JsonAdaptedDeveloper person = new JsonAdaptedDeveloper(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_JOINED,
                VALID_USERNAME, INVALID_PASSWORD, VALID_ROLE, VALID_SALARY, VALID_PROJECTS);
        String expectedMessage = Password.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPassword_throwsIllegalValueException() {
        JsonAdaptedDeveloper person = new JsonAdaptedDeveloper(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_JOINED,
                VALID_USERNAME, null, VALID_ROLE, VALID_SALARY, VALID_PROJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Password.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedDeveloper person = new JsonAdaptedDeveloper(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_JOINED,
                VALID_USERNAME, VALID_PASSWORD, INVALID_ROLE, VALID_SALARY, VALID_PROJECTS);
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedDeveloper person = new JsonAdaptedDeveloper(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_JOINED,
                VALID_USERNAME, VALID_PASSWORD, null, VALID_SALARY, VALID_PROJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSalary_throwsIllegalValueException() {
        JsonAdaptedDeveloper person = new JsonAdaptedDeveloper(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_JOINED,
                VALID_USERNAME, VALID_PASSWORD, VALID_ROLE, INVALID_SALARY, VALID_PROJECTS);
        String expectedMessage = Salary.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSalary_throwsIllegalValueException() {
        JsonAdaptedDeveloper person = new JsonAdaptedDeveloper(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_DATE_JOINED,
                VALID_USERNAME, VALID_PASSWORD, VALID_ROLE, null, VALID_PROJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Salary.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}
