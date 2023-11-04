---
layout: page
title: UserGuide
pageNav: 3
---

#### Welcome to CodeContact!

##### Your personal project management tool to keep up with your schedules

> Seamlessly integrate information of your developers, clients, and projects, simplifying access to coding-related
> contacts, facilitating collaboration, and offering command-line efficiency for project managers

CodeContact is a **desktop app for handling and synchronising project information, optimized for use via a Command Line
Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, CodeContact
can get your contact management tasks done faster than traditional GUI apps.

> If you are new to CodeContact, here is how you can get started!

> If you are familiar with CodeContact, jump to the table of contents to find what you are looking for.

* ###Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

1. Navigating the user guide
2. Navigating the User Interface (GUI)
3. Command Summary
4. Available Features
5. Frequently Asked Questions (FAQ)

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `codecontact.jar`.

1. Copy the file to the folder you want to use as the _home folder_ for your CodeContact.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar codecontact.jar`
   command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)<br>
   For help navigating the GUI, click [here](#navigating)

<<<<<<< HEAD

1.

=======
> > > > > > > master

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will
   open the help window.<br>
   Some example commands you can try:

    * `list developers` : Lists all developers.

    * `n/Amy p/12345678 e/any.u.nus.edu a/NUS UTOWN d/06-09-2023 r/Developer s/4999 pr/{PROJECT1, PROJECT2…}` : Adds a
      contact named `John Doe` to the Address Book.

    * `delete developer 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Navigating the User Interface (GUI)

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be
  ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines
  as space characters surrounding line-breaks may be omitted when copied over to the application.

</div>

### Adding new information : `add`

* What it does:
    * Manager can add a new employee to the list of people into the data one by one
* Format
    * `create new employee`
    * `n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS d/DATE_JOINED r/ROLE s/SALARY pr/{PROJECT1, PROJECT2…}`
* Example
    * User types: `create new employee`
    * CLI shows: `Input employee details in the format below:`
      `n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS d/DATE_JOINED r/ROLE `
    * User types: `n/AMY p/87654321 e/amy@u.nus.edu a/NUS UTOWN d/06 Sep 2023 r/Developer s/4999`
* Acceptable parameters
    * Names can only consist of capital and small letters, spaces and hyphens.
    * Contact number has to 8 digits without spaces
    * Email has to be of the format `<TEXT>@<TEXT>`
    * Date joined has to be of format `dd mmm yyyy `(E.x 09 Sept 2022)
    * Salary has to be at least 4 digits
    * Project should be valid project name already listed in the company, multiple projects have to be comma separated
    * Password is at least 8 characters long, with a combination of uppercase letters, lowercase letters, numbers, and
      symbols
* When command succeeds
    * Continuing from the above example, CLI shows:
    ```
  The following user has been added:
  Name: AMY
    Contact Number: 87654321
    Email: amy@u.nus.edu
    Address: NUS UTOWN
    Date Joined: 06 Sep 2023
    Role: Developer
    Salary: 4999
    Assigned Projects:login
    Username: amy
    Password: Password123!
  ```
* When command fails
    * User particular's error
        * `Error! New user’s <PARTICULAR> already exists in <COMPANY_NAME>`
    * Format error
        * `Error! New user’s <PARTICULAR> does not follow the format: <FORMAT>`
        * E.g.:  `Error! New user’s name does not follow the format:
          Names can only consist of capital and small letters, spaces and hyphens.`
    * Missing particular's error
        * `Error! New user’s {MISSING_PARTICULARS} are missing.`
* Relevant UI mock-ups

### Edit information details : `edit`

* What it does:
    * Allows managers to change the details of the information stored
* Format
    * `edit employee1`
    * Modify the details that you want to change
    * `n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS d/DATE_JOINED r/ROLE s/CHANGED_SALARY pr/{PROJECT1, PROJECT2…} u/USERNAME pa/PASSWORD`
* Example
    * User types: `update Amy`
    * CLI
      shows: `Here are the employee details: AMY 87654321 amy@u.nus.edu NUS UTOWN 06 sept Developer 4999 amy password123`
    * User
      types: `n/AMY p/87654321 e/amy@u.nus.edu a/NUS UTOWN d/06 sept r/Developer s/4999 pr/{login} u/amy pa/password12345`
    * CLI
      shows: `Here are the updated employee details: AMY 87654321 amy@u.nus.edu NUS UTOWN 06 sept Developer 6999 amy password123`
* Acceptable parameters
    * Inputs are the same as adding an employee but you just change the particular details that you want to modify.
    * Names can only consist of capital and small letters, spaces and hyphens.
    * Contact number has to 8 digits without spaces
    * Email has to be of the format <TEXT>@<TEXT>
    * Date joined has to be of format dd mmm yyyy (E.x 09 Sept 2022)
    * Role should be: HR, manager or developer
    * Salary has to be at least 4 digits
    * Project should be valid project name already listed in the company, multiple projects have to be comma separated
    * Password is at least 8 characters long, with a combination of uppercase letters, lowercase letters, numbers, and
      symbols
* When command succeeds
    * CLI shows:

```
Here are the updated employee details:
Name: AMY
Contact Number: 87654321
Email: amy@u.nus.edu
Address: NUS UTOWN
Date Joined: 06 Sep 2023
Role: Developer
Salary: 6999
Assigned Projects:login
Username: amy
Password: Password123!
```

* When command fails
    * Repetitive name
        * `More than 1 developer with the NAME has been found, please input employee’s phone number:`
    * Invalid input
        * `Please input the employee details in the right format`
    * No access error
        * `You do not have access to modify this. Please contact your administrator.`
    * Format error
        * `Error! New user’s <PARTICULAR> does not follow the format: <FORMAT>`
        * E.x.:  `Error! New user’s name does not follow the format:
          Names can only consist of capital and small letters, spaces and hyphens.`

* Relevant UI mock-ups

### Search according to type and industry details: `search`

* What it does
    * Every user can search for contacts related to the keyword (eg. by search prj name, members of the prj team will
      appear)
* Format
    * `Search p/<Project Name>`
    * `Search r/<Role>`
    * `Search n/<Name>`
* Example
    * `Search p/2103/T` (Prints everyone in 2103/T prj)
    * `Search r/Senior Developer` (Prints everyone of the senior developer role)
    * `Search n/Amy` (Prints everyone of the name Amy)
* Acceptable Parameters
    * Incomplete inputs for string searches work too
    * Anything beyond p/ and r/ and n/does not work
    * Name must be closely following / for more accurate output (eg. n/Amy vs n/ Amy)
* When command succeeds
    * `These are the project members for the project <Project Name>` followed by list of project members
    * `These are all the Senior Developers in this company` followed by list of senior developers
    * `This is Amy’s contact` / `These are the contact details for Amy` followed by Amy’s details or list of all the
      Amy’s contact
      `No relevant contact details can be found`
* When command fails
    * Missing
      header<br> `Please search with the correct input Search p/<Project Name> OR Search r/<Role> OR Search n/<Name>`
    * Invalid
      input<br> `x/ is not a valid type to search, Please search with the correct input Search p/<Project Name> OR Search r/<Role> OR Search n/<Name>`

* Relevant UI mock-ups

### Listing all developers : `list`

Shows a list of all developers in the address book.

Format: `list TYPE`

* lists the specific type of thing you are asking

Examples:

* `list developers` lists all the developers
* `list projects` lists all the projects

### Deleting a developer : `delete`

Deletes the specified developer from the address book.

Format: `delete INDEX`

* Deletes the developer at the specified `INDEX`.
* The index refers to the index number shown in the displayed developer list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:

* `list` followed by `delete 2` deletes the 2nd developer in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st developer in the results of the `find` command.

### Viewing help : `help`

Shows a message explaning how to access the help page.

Format: `help`

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Feedback System `[coming in v2.0]`

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains
the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only
   the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the
   application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

 Action     | Format, Examples                                                         
------------|--------------------------------------------------------------------------
 **add**    | Format: <br> <br> Example: <br><br>                                      
 **edit**   | Format: <br> <br> Example: <br><br>                                      
 **search** | Format: <br> <br> Example: <br><br>                                      
 **delete** | Format: <br> <br> Example: <br><br>                                      
 **list**   | Format: <br> `list developers` <br>  `list projects` <br> `list clients` 
 **help**   | `help`                                                                   
