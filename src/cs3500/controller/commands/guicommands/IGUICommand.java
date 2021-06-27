package cs3500.controller.commands.guicommands;

import cs3500.view.GUIView;
import java.awt.event.ActionEvent;

/**
 * <p>An interface for function objects representing a command that the GUI can execute.
 * Each class implementing this interface has an <code>execute()</code> command that, quite
 * obviously, just executes that command. Any relevant information that the object needs to
 * execute that command is passed in that object's constructor.</p>
 *
 * <p>This interface is marked <code>private</code> and nested inside of the
 * {@link GUIView} since it is only to be used to better organize action listeners and the {@link
 * GUIView#actionPerformed(ActionEvent)} method, and nowhere outside of that class. Having this
 * class nested inside of the {@link GUIView} class also gives it access to the frame and model
 * that are to be manipulated by this interface, meaning that we don't have to pass those objects
 * as parameters, and can instead reference them from within this context.</p>
 */
public interface IGUICommand{

  /**
   * Executes the GUI command. Mutates the model and GUI accordingly based on the command object.
   */
  void execute();
}