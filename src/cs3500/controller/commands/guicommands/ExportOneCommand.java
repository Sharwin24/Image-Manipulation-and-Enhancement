package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

/**
 * Exports the current image.
 */
public class ExportOneCommand extends AGUICommand {

  /**
   * Constructs a ExportOneCommand based on the model to manipulate and the view that will
   * reflect these changes.
   *
   * @param model the model to manipulate.
   * @param frame view that will reflect these changes.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public ExportOneCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {
    final JFileChooser fChooser = new JFileChooser("");
    fChooser.setDialogTitle("Choose the location to save the image");
    int selection = fChooser.showSaveDialog(frame);
    if (selection == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      String absPath = f.getAbsolutePath();
      try {
        if (!GUIView.initFormatsMap().containsKey(GUIView.getFileExtension(absPath))) {
          ImageIO.write(model.getImage().getBufferedImage(), GUIView.getFileExtension(absPath), f);
        }
        absPath += ".png"; // default to save as png
        ImageIO.write(model.getImage().getBufferedImage(), "png", f);
      } catch (IOException e) {
        frame.errorPopup("Could not save the specified file", "I/O Error");
      }

    }
  }
}