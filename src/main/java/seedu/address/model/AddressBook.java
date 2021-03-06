package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.UniqueReminderList;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.UniqueTodoList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTodoList todos;
    private final UniqueReminderList reminders;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        todos = new UniqueTodoList();
        reminders = new UniqueReminderList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setTodos(newData.getTodoList());
        setReminders(newData.getReminderList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Removes {@code tag} from this {@code person}.
     * {@code person} must exist in the address book.
     */
    public void removeTagFromPerson(Tag tag, Person person) {
        Set<Tag> newTags = new HashSet<>(person.getTags());

        if (!newTags.remove(tag)) {
            return;
        }

        Person updatedPerson = new Person(person.getName(), person.getPhone(),
                                          person.getEmail(), person.getAddress(), newTags, person.getSchedules());

        updatePerson(person, updatedPerson);
    }

    /**
     * Removes {@code tag} from all {@code person}.
     * {@code person} must exist in the address book.
     */
    public void removeTag(Tag tag) {
        persons.forEach(person -> removeTagFromPerson(tag, person));
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }

    //=========== Todo tasks =============================================================

    //@@author linnnruoo
    /**
     * Replaces the contents of the todo tasks list with {@code todos}.
     * {@code todos} must not contain duplicate todos.
     */
    public void setTodos(List<Todo> todos) {
        this.todos.setTodos(todos);
    }

    //// todo-level operations

    /**
     * Returns true if a todo with the same identity as {@code todo} exists in the address book.
     */
    public boolean hasTodo(Todo todo) {
        requireNonNull(todo);
        return todos.contains(todo);
    }

    /**
     * Adds a todo task to the address book.
     * The todo task must not already exist in the address book.
     */
    public void addTodo(Todo td) {
        todos.add(td);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeTodo(Todo key) {
        todos.remove(key);
    }

    @Override
    public ObservableList<Todo> getTodoList() {
        return todos.asUnmodifiableObservableList();
    }

    //=========== Reminders =============================================================

    //@@author junweiljw
    /**
     * Replaces the contents of the reminder list with {@code reminders}.
     * {@code reminders} must not contain duplicate reminders.
     */
    public void setReminders(List<Reminder> reminders) {
        this.reminders.setReminders(reminders);
    }

    /**
     * Returns true if a reminder with the same identity as {@code reminder} exists in the address book.
     */
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return reminders.contains(reminder);
    }

    /**
     * Adds a reminder to the address book.
     * The reminder must not already exist in the address book.
     */
    public void addReminder(Reminder rm) {
        reminders.add(rm);
    }

    @Override
    public ObservableList<Reminder> getReminderList() {
        return reminders.asUnmodifiableObservableList();
    }
}
