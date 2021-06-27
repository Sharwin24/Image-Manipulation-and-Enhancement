package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.fileformat.JPEGFile;
import cs3500.view.GUIView;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Command to import an image to all layers.
 */
public class ImportAllCommand extends AImportCommand {

  /**
   * Constructs an {@link ImportAllCommand} based on the model it manipulates and the {@link
   * GUIView} that it renders these changes to.
   *
   * @param model the model to be manipulated.
   * @param frame the view that will reflect the changes to the model.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public ImportAllCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }


  @Override
  public void execute() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Specify the txt file containing the paths of the files to import");
    FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("text files", "txt");
    fileChooser.setFileFilter(txtFilter);
    int selection = fileChooser.showSaveDialog(frame);
    if (selection == JFileChooser.APPROVE_OPTION) {
      File filePathsTxt = fileChooser.getSelectedFile();
      try {
        Scanner filePathsScanner = new Scanner(filePathsTxt);
        while (filePathsScanner.hasNextLine()) {
          String onePath = filePathsScanner.nextLine();

          model.addLayer();
          model.setCurrentLayer(model.getLayers().size() - 1);
          model.load(new JPEGFile().importImage(onePath));
        }
        frame.setImage();
        frame.renderLayers();
      } catch (FileNotFoundException e) {
        frame.errorPopup("Could not find specified file", "Bad file error");
      }
    }
  }
}