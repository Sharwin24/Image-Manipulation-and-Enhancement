package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.fileformat.IFileFormat;
import cs3500.view.GUIView;
import java.awt.FileDialog;
import java.awt.Frame;
import java.util.Map;

/**
 * Class for Importing/Loading an image into the GUI.
 */
public class ImportOneCommand extends AGUICommand {

  /**
   * Constructs a ImportOneCommand based on the model to manipulate and the view that will reflect
   * these changes.
   *
   * @param model the model to manipulate.
   * @param frame view that will reflect these changes.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public ImportOneCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {
    FileDialog dialog = new FileDialog((Frame) null, "Select File");
    dialog.setMode(FileDialog.LOAD);
    dialog.setVisible(true);
    String absolutePath = dialog.getDirectory() + dialog.getFile();
    // ensure valid fileType
    IFileFormat fileFormat = null;
    Map<String, IFileFormat> formats = frame.initFormatsMap();
    boolean validFileTypeSelected = false;
    for (String s : formats.keySet()) {
      if (absolutePath.endsWith(s)) {
        validFileTypeSelected = true;
        fileFormat = formats.get(s);
      }
    }
    if (!validFileTypeSelected) {
      frame.errorPopup("invalid file type selected, try again, specifying either "
          + ".png, .jpg, or .ppm", "Invalid File Type");
    }

    model.load(fileFormat.importImage(absolutePath));
    frame.setImage();
  }
}