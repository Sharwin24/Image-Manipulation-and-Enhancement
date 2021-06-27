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
public class ExportOneCommand extends AGUICommand{

  /**
   * Todo
   *
   * @param model
   * @param frame
   * @throws IllegalArgumentException
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
        if (!frame.initFormatsMap().containsKey(frame.getFileExtension(absPath))) {
          ImageIO.write(model.getImage().getBufferedImage(), frame.getFileExtension(absPath), f);
        }
        absPath += ".png"; // default to save as png
        ImageIO.write(model.getImage().getBufferedImage(), "png", f);
      } catch (IOException e) {
        frame.errorPopup("Could not save the specified file", "I/O Error");
      }

    }
  }
}