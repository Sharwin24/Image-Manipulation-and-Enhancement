package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.fileformat.IFileFormat;
import cs3500.view.GUIView;
import java.awt.FileDialog;
import java.awt.Frame;
import java.util.Map;

public abstract class AImportCommand extends AGUICommand {

  protected IFileFormat fileFormat;

  /**
   * Constructs an {@link AImportCommand} based on the model it manipulates and the {@link GUIView}
   * that it renders these changes to.
   *
   * @param model the model to be manipulated.
   * @param frame the view that will reflect the changes to the model.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public AImportCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  /**
   * Gets the absolute path of a selected file.
   *
   * @return a String with the path.
   * @throws IllegalArgumentException if the selected file is not an image.
   */
  protected String getAbsolutePathOfFile() throws IllegalArgumentException {
    FileDialog dialog = new FileDialog((Frame) null, "Select File");
    dialog.setMode(FileDialog.LOAD);
    dialog.setVisible(true);
    String absolutePath = dialog.getDirectory() + dialog.getFile();
    // ensure valid fileType
    Map<String, IFileFormat> formats = GUIView.initFormatsMap();
    boolean validFileTypeSelected = false;
    for (String s : formats.keySet()) {
      if (absolutePath.endsWith(s)) {
        validFileTypeSelected = true;
        this.fileFormat = formats.get(s);
      }
    }
    if (!validFileTypeSelected) {
      frame.errorPopup("invalid file type selected, try again, specifying either "
          + ".png, .jpg, or .ppm", "Invalid File Type");
      throw new IllegalArgumentException("Invalid File Type");
    }
    return absolutePath;
  }

  @Override
  public abstract void execute();
}