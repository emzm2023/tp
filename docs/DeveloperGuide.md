---
layout: page
title: Developer Guide
---

* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
  original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [
_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create
and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of
classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java)
and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is
in charge of the app launch and shut down.

* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues
the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding
  API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using
the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component
through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the
implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified
in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts
e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`,
inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the
visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
are in the `src/main/resources/view` folder. For example, the layout of
the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java)
is specified
in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API
** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API
call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates
   a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which
   is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a developer).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a
  placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse
  the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as
  a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser`
  interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API
** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Developer` objects (which are contained in a `UniqueDeveloperList` object),
  and similarly so for `Client` and `Project` objects.
* stores the currently 'selected' `Developer` objects (e.g., results of a search query) as a separate _filtered_ list
  which is exposed to outsiders as an unmodifiable `ObservableList<Developer>` that can be 'observed' e.g. the UI can be
  bound to this list so that the UI automatically updates when the data in the list change. This is similar for `Client`
  and `Project` objects.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as
  a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they
  should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

### Storage component

**API
** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,

* can save both address book data and user preference data in JSON format, and read them back into corresponding
  objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only
  the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
  that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Edit features

#### Implementation

The original edit feature from AB-3 has been extended to account for the editing of projects and specific people -
developers
and clients. The edit command will be parsed to return 1 of 3 different commands, depending on the
object to be edited.

The `AddressBookParser` will return the respective parser for the command depending on the user input in accordance to
the
respective command words defined in `CliSyntax`. Namely,

* `edit-developer` will return an `EditDeveloperCommandParser` that parses the user input and creates
  an `EditDeveloperCommand`
* `edit-client` will return an `EditClientCommandParser` that parses the user input and creates an `EditClientCommand`
* `edit-project` will return an `EditProjectCommandParser` that parses the user input and creates
  an `EditProjectCommand`

Each instance of `EditDeveloperCommand`, `EditClientCommand`, and `EditProjectCommand` objects have 2 private fields:

1. an instance of `Index` containing the index of the target object to edit in the currently displayed list, and
2. an instance of `EditDeveloperDescriptor`, `EditClientDescriptor`, or `EditProjectDescriptor` respectively, which
   contains the edited fields to update the target object with.

Executing the command will replace the existing object in the current `model` with the new object with the edited
fields.

Other than extending the commands, parsers, and descriptors to account for `Developer`, `Client`, and `Project`
separately,
some changes to the sequence of interactions between the `Logic` and `Model` components were also made. When the
`EditDeveloperCommandParser` and `EditClientCommandParser` parses edits to a `Project` assigned to a `Developer`
or `Client`,
it calls `Model##----` to check whether there is an existing `Project` with that name.

**Example usage scenario**

Given below is an example usage scenario where the user edits the projects assigned to a `Developer` using
the `edit-developer`
command.

Step 1. ....

#### Design considerations

**Aspect: Command syntax**

* Alternative 1 (current choice): Have separate commands for each `Developer`, `Client`, and `Project`. Executing the
  command
  automatically switches user to the respective tab.
    * Pros: More specific and straightforward, allowed parameters in command are easier to navigate for users. More
      flexible
      as do not need to be in respective tab to edit.
    * Cons: More classes to create, user needs to type more.
* Alternative 2: Have one general `edit` command. The edit will be made based on the current tab displayed.
    * Pros: User as can be less specific when typing command.
    * Cons: User needs to ensure that intended tab is open. Allowed parameters are less clearly defined, can lead to
      confusion and mistakes.

### Add Developer Feature

This feature allows users to add a developer to the bottom of the list of currently existing developers. Users are able
to add any valid developer to the list. If a record of the same developer already exists in the list, the command will
not be allowed and an error will be thrown to alert user.

Example Use: `add-d n/John Doe p/98765432 e/johnd@example.com`

#### Implementation

Upon entry of the add developer command, an `AddDeveloperCommand` class is created. The `AddDeveloperCommand` class
extends the abstract `Command` class and implements the `execute()` method. Upon execution of this method, a `Developer`
object is added to the model’s list of developers if all the attributes provided are valid and a duplicate instance does
not exist.

Given below is an example usage scenario of how the add developer is executed step by step.

Step 1. User launches the application

Step 2. User inputs `add-d n/John Doe p/98765432 e/johnd@example.com` to save a developer.

Step 3. The developer is added to the model’s list of developers if valid.

The following sequence diagram illustrates how the add developer operation works:

### Delete Developer Feature

Deletes a developer at the specified **one-based index** of list of currently existing/found developers. Users are able
to delete any developer in the list. If an index larger than or equal to the size of the developer’s list is provided,
the command will not be allowed and an error will be thrown to alert user.

Example Use: `del-d 1`

#### Implementation

Upon entry of the delete developer command, a `DeleteDeveloperCommand` class is created. The `DeleteDeveloperCommand`
class extends the abstract `Command` class and implements the `execute()` method. Upon execution of this method, the
doctor at specified **one-based index** is removed if the index provided is valid.

Given below is an example usage scenario of how the delete developer command behaves at each step.

Step 1. User launches the application

Step 2. User executes `del-d 1` to delete the developer at index 1 (one-based indexing).

Step 3. The developer at this index is removed if the index provided is valid.

The following sequence diagram illustrates how the delete developer operation works:

### \[Proposed\] Import feature

This feature will allow project managers to import existing spreadsheets of developer and client data in the specified
format in CSV

#### Proposed Implementation

There are 2 implementations: CLI and GUI

##### CLI Implementation

Upon entry of the import developer command, an `ImportDeveloperCommand` class is created. The `ImportDeveloperCommand`
class extends the abstract `Command` class and implements the `execute()` method. Upon execution of this method, a list
of `Developer` objects are added to the model’s list of developers if all the attributes provided are valid and a
duplicate instance does not exist.

Given below is an example usage scenario of how the import developer is executed step by step.

Step 1. User launches the application

Step 2. User inputs `import-developer developers.csv` to indicate path and filename of where the spreadsheet of
developers is located.

Step 3. The developers are added to the model’s list of developers if valid the column names are valid and each row of
input is valid.

The implementation follows likewise for clients.

The following sequence diagram illustrates how the add developer operation works:

##### GUI Implentation

A new menu item will be added under File called `Import developers` and `Import clients`

Clicking it will lead to a window to select the location of the respective file in csv format.

The backend implementation of logic follows the CLI implementation by creating a `ImportDeveloperCommand`
or `ImportClientCommand`

### Find Feature

#### Implementation

The find feature is facilitated by a map-based strategy, associating specific prefixes (e.g., "find-developer n/" or "
find-client r/") with corresponding predicates, allowing dynamic generation of filtering criteria based on user input.

Implemented operations include:

- `FindCommandParser#parse()`: Interprets the user's input and generates the appropriate predicate to filter the list of
  developers or clients.
- `Model#updateFilteredPersonList()`: Updates the list displayed in the UI based on the provided predicate.

Given below is an example usage scenario and how the find mechanism behaves at each step:

**Step 1.** The user launches the application. The list of developers and clients are displayed.

**Step 2.** To filter developers by name, the user executes the command `find-developer n/ alice bob`. The application
recognizes the "developer n/" prefix and uses the `NameContainsKeywordsPredicate` to generate a filtering criteria. The
list in the UI is updated to only display developers named Alice or Bob.

**Step 3.** Next, the user wants to find clients from a specific organisation. They use the
command `find-client o/ Google`. The "find-client o/" prefix maps to the `OrganisationContainsKeywordsPredicate` and
filters clients associated with Google.

**Step 4.** If the user provides an unrecognized prefix, e.g., `find-developer z/ alice`, an error message is displayed
informing them of the correct command format.

> :information_source: **Note:** While the user can search by multiple keywords, each keyword maps to an entire word in
> the attributes. For example, searching for "Ali" will not return "Alice".

The following sequence diagram provides an overview of how the find operation is executed:

[Diagram would be inserted here illustrating the parsing of the command, identification of the appropriate predicate, and subsequent filtering of the list.]

### Design Considerations:

**Aspect:** Implementation of the predicate map:

**Alternative 1 (current choice):**

- Use a long chain of `if-else` conditions for each attribute.
    - **Pros:** Explicit parsing logic for each attribute.
    - **Cons:** Code becomes lengthy and hard to maintain. Adding a new attribute involves modifying the parsing logic,
      increasing the risk of errors.

**Alternative 2:**

- Use a map linking prefixes to their corresponding predicate constructors.
    - **Pros:** Simplifies the parsing process. Adding a new attribute to search becomes as simple as adding a new entry
      in the map.
    - **Cons:** A potential mismatch between the prefix and its predicate can lead to wrong results.

Given the benefits of a more maintainable and scalable codebase, we've decided to go with the first alternative. Future
enhancements might include fuzzy search.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo
history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the
following operations:

* `VersionedAddressBook#commit()`— Saves the current address book state in its history.
* `VersionedAddressBook#undo()`— Restores the previous address book state from its history.
* `VersionedAddressBook#redo()`— Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()`
and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the
initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th developer in the address book. The `delete` command
calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes
to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book
state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new developer. The `add` command also
calls `Model#commitAddressBook()`, causing another modified address book state to be saved into
the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the developer was a mistake, and decides to undo that action by executing
the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer`
once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once
to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such
as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`.
Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not
pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be
purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern
desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
    * Pros: Will use less memory (e.g. for `delete`, just save the developer being deleted).
    * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

### \[Proposed\] ListDeveloperDeadlines Command
--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of colleague contacts internally
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps
* a project manager or someone with similar needs working within a software company

**Value proposition**: CodeContact aims to seamlessly integrate contact, client, and project management, simplifying
access to coding-related contacts, facilitating collaboration, and offering command-line efficiency for project
managers.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​         | I want to …​                                                                                   | So that I can…​                                                           |
|----------|-----------------|------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| `* * *`  | project manager | add a list of Developers and their contact information                                         | access contact details easily and quickly assemble teams for new projects |
| `* * *`  | project manager | add a list of Clients and their contact information                                            | access client details easily and know who is related to what project.     |
| `* * *`  | project manager | add a list of Projects and their details                                                       | access project details easily and see who is related to the project       |
| `* * *`  | project manager | delete information about a Client or Developer and the project details will update accordingly | don't repeat deleting several time                                        |
| `* * *`  | project manager | edit the the details of the Developers added in                                                | constantly update the contact book                                        |
| `* * *`  | project manager | edit the the details of the Clients added in                                                   | constantly update the contact book                                        |
| `* * *`  | project manager | edit the the details of the Projects added in                                                  | constantly update any changes to the project                              |
| `* * *`  | project manager | find the the Developers according to any details they have                                     | source for information related to developers easily                       |
| `* * *`  | project manager | find the the Clients according to any details they have                                        | source for information related to clients easily                          |
| `* * *`  | project manager | find the the Projects according to any details they have                                       | source for information related to projects easily                         |
| `* * *`  | project manager | list different groups of people according to the different commands                            | view projects, clients and developers can be as different lists           |
| `* * *`  | project manager | switch between tabs for Developers, Clients and Projects                                       | intuitively view the different data lists                                 |
| `* *`    | project manager |                                                                                                |                                                                           |
| `* *`    | project manager |                                                                                                |                                                                           |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `AddressBook`, and the **Actor** is the `user`, unless specified
otherwise)unless specified otherwise)

#### **Use case:** UC1 - Add a single employee

**Actor:** HR staff

**Preconditions:** User is logged in as an HR staff

**Guarantees:**

1. A new profile will be added to the company's system after every successful addition
2. A login credential for the added user will be created

**MSS**

1. User requests to add an employee.
2. System requests the details of the employee.
3. User enters the requested details in the required format.
4. System requests for confirmation.
5. User confirms.
6. System adds the new employee to the company database.

   Use case ends.

**Extensions**

* 1a. The request is done by a non-HR staff.

    * 1a1. System informs user that user does not have the access rights to add a new employee.

      Use case ends.

* 3a. The given details are invalid or in an invalid format.

    * 3a1. System informs user there is an error and requests for correct input.
    * 3a2. User enters requested details again.

      Steps 3a1-3a2 are repeated until details entered are valid.

      Use case resumes at step 4.

* *a. At any time, User chooses to cancel the action.
    * *a1. System requests to confirm the cancellation.
    * *a2. User confirms the cancellation.

      Use case ends.

#### **Use case:** UC2 - Logging in as a specific user

**Actor:** Any Employee

**Preconditions:**

1. The employee has valid login credentials provided by the HR.

**Guarantees:**

1. User is logged into their account system.
2. They only have the access rights to the user-specific features (eg. developers can only modify their personal
   particulars and no other information).

**MSS**

1. User accesses the login page of the system.
2. System presents the login page to the user.
3. User enters their own login credentials and submits to the system.
4. System validates the user's login credentials.
5. User logs in to system.

   Use case ends.

**Extensions**

* 4a. The login credentials are invalid (no record it is created).
    * 4a1. System informs user there is an erro and directs user to seek HR for help.

      Use case ends.

* 4b. The login credentials has an invalid format.
    * 4b1. System informs user there is an error and requests for correct input.
    * 4b2. User enters requested details again.

      Step 4b1-4b2 are repeated until details entered are valid.

      Use case resumed at step 5.

#### **Use case:** UC3 - Search for other employee's contacts

**Actor:** Any Employee

**Precondition:**

1. User is logged in.
2. System identifies what role the user is (so that no irrelevant information is shown).

**Guarantees:**

1. User can view the contact information and details of other users based on the
   search keyword.
2. User can only view relevant information their roles have access rights to.

**MSS**

1. User requests to search an employee
2. System requests the keywords for the search.
3. User enters the requested details in the required format.
4. System processes the search request and retrieves a list of employees matching the provided criteria.
5. System displays search results, which include the names of employees who match the search criteria along
   with information that user's role has access rights to.

   Use case ends.

**Extensions**

* 3a. The search has an invalid type or format.
    * 3a1. System informs user there is an error and requests for correct input.
    * 3a2. User enters requested details again.

      Steps 3a1-3a2 are repeated until details entered are valid.

      Use case resumes at step 4.

* 4a. There is no matching list of employees.
    * 4a1. System informs user that no relevant information is found

      Use case ends.

### Non-Functional Requirements

#### System/Performance Requirements

* Should work on any _mainstream OS_ as long as it has Java `11` or above installed.

#### Reliability Requirements

* Should be able to handle failures and show relevant error messages (hardware/network failures)
* Should ensure that data is protected from corruption or loss
* Should be able to recover immediately after inaccurate/invalid commands

#### Usability Requirements

* Should follow specific code design and usability guidelines
* The user interface shall follow a consistent design pattern and layout throughout the application.
* There shall be clear and intuitive pathways for accomplishing common tasks.
* Users shall receive informative feedback on their actions (e.g., success messages, error messages)
  in a clear and user-friendly manner.
* Context-sensitive help and tooltips shall be available to assist users in understanding complex features.
* A comprehensive user manual or online documentation shall be provided to explain how to use the application.

#### Process Requirements

* The project is expected to adhere to a schedule that completes a milestone set every two weeks.
* The projsct shall follow a iterative breadth-first development methodology
* Automated testing suits shall be maintained and run for each build
* Code review shall be conducted for all new code contrivution, with at least one team member
  reviewing each piece of code before it is merged
* All project source code shall be stored in a version control system (e.g., Git),
  and commits should follow a consistent naming convention.
* Coding standards and style guidelines shall be defined and followed consistently by all development team members.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be
       optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a developer

1. Deleting a developer while all developers are being shown

    1. Prerequisites: List all developers using the `list` command. Multiple developers in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.
       Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No developer is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
