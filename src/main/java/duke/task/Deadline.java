package duke.task;

import duke.DukeCheckLineException;
import duke.DukeException;
import duke.Storage;
import duke.command.Command;

import java.util.ArrayList;
import java.util.List;

public class Deadline extends Time {

    public Deadline(String description, String time) {
        super(description, time);
    }

    public static void checkDescription(int index) throws DukeException {
        if (index == 1) {
            throw new DukeException("☹ OOPS!!!, the description cannot be empty.");
        }
    }

    public static void checkTime(int index) throws DukeCheckLineException {
        if (index == -1) {
            throw new DukeCheckLineException();
        }
    }

    /**
     * To generate a Deadline detail and store in the tasks list according to user input.
     *
     * @param tasks The list of tasks;
     * @param storage To save the deadline detail of the task.
     * @return A command which generates deadlines task.
     */
    public static Command getCommand(TaskList tasks, Storage storage) {
        return fullCommand -> {
            List<String> commandList = List.of(fullCommand);
            int position = commandList.indexOf("/by");
            try {
                checkDescription(position);
                checkTime(position);
                String description = String.join(" ", commandList.subList(1, position));
                String time = String.join(" ", commandList.subList(position + 1, fullCommand.length));
                Task deadlineTask = new Deadline(description, time);
                tasks.add(deadlineTask);
                storage.store(tasks.convertAsLines());
                return List.of("Got it. I've added this task: " + System.lineSeparator()
                        + "     " + deadlineTask + System.lineSeparator()
                        + "   Now you have " + tasks.size() + " tasks in the list.");
            } catch (DukeException e) {
                return List.of("☹ OOPS!!! The description of a " + "Deadline" + " cannot be empty.");
            } catch (DukeCheckLineException e) {
                return List.of("☹ OOPS!!! A deadline must have a time.");
            }
        };
    }

    /**
     * Return a list of strings that can be saved.
     *
     * @return A task list for saving.
     */
    @Override
    public List<String> getList() {
        List<String> list = new ArrayList<>();
        list.add("D");
        list.addAll(super.getList());
        list.add(convertSaveTimeString());
        return list;
    }

    /**
     * Return a list of strings to user.
     *
     * @return This string task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + convertTimeString() + ")";
    }
}
