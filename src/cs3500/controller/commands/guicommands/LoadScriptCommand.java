package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Command to load a script into the GUI.
 */
public class LoadScriptCommand extends AGUICommand {

  /**
   * Todo
   *
   * @param model
   * @param frame
   * @throws IllegalArgumentException
   */
  public LoadScriptCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {
    FileDialog dialog = new FileDialog((Frame) null, "Select File");
    dialog.setMode(FileDialog.LOAD);
    dialog.setVisible(true);
    String absolutePath = dialog.getDirectory() + dialog.getFile();
    if (!absolutePath.endsWith(".txt")) {
      frame.errorPopup("Invalid file type selected, must be of type: .txt", "Invalid File Type");
      return;
    }
    String scriptInput = "";
    try {
      scriptInput = new String(Files.readAllBytes(Paths.get(absolutePath)));
    } catch (IOException e) {
      frame.errorPopup("Unable to read from File at: " + absolutePath, "Unable to read file");
    }
    frame.scriptArea.setText(scriptInput); // Todo: Getters
  }
  }
}