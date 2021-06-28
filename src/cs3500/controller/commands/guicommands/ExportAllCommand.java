package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.layer.ILayer;
import cs3500.view.GUIView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

/**
 * Command to Export all layers and create a text file with the paths of the exported paths.
 */
public class ExportAllCommand extends AGUICommand {

  /**
   * Constructs an {@link ExportAllCommand} based on the model it manipulates and the {@link
   * GUIView} that it renders these changes to.
   *
   * @param model the model to be manipulated.
   * @param frame the view that will reflect the changes to the model.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public ExportAllCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {
    try {
      final JFileChooser chooser = new JFileChooser("");
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      chooser.setAcceptAllFileFilterUsed(true);
      chooser.setDialogTitle("Select Directory to save all images to");
      int selection = chooser.showSaveDialog(frame);
      if (selection == JFileChooser.APPROVE_OPTION) {
        File directory = chooser.getSelectedFile();
        String path = directory.getAbsolutePath();
        if (!directory.exists()) {
          directory.mkdir();
        }
        System.out.println(path);
        try {
          int layerNum = 0;
          for (ILayer layer : model.getLayers()) {
            ImageIO.write(layer.getModel().getImage().getBufferedImage(),
                "png",
                new File(path + "\\layer_" + layerNum + ".png"));
            layerNum++;
          }
        } catch (IOException e) {
          frame.errorPopup("Could not save the specified file", "I/O Error");
        }
        // Create a txt file and save all the paths
        try {
          File textFile = new File(path + "\\layerLocations.txt");
          textFile.setWritable(true);
          FileWriter writer = new FileWriter(textFile);
          StringBuilder toWrite = new StringBuilder();
          for (int i = 0; i < model.getLayers().size(); i++) {
            toWrite.append(path).append("\\layer_").append(i).append(".png").append("\n");
          }
          System.out.println(toWrite);
          writer.write(String.valueOf(toWrite));
          writer.close();
        } catch (IOException e) {
          frame.errorPopup("Could not write to file", "I/O Error");
        }
      }
    } catch (IllegalArgumentException e) {
      frame.write(e.getMessage());
      return;
    }
  }
}