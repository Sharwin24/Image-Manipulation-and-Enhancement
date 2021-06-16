package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.model.fileformat.IFileFormat;
import cs3500.view.IIMEView;
import java.util.Scanner;

/**
 * TODO
 */
public class ExportCommand extends APortCommand {

  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw) {

    if (lineScan.hasNext()) {
      String fileFormat = lineScan.next();
      if (lineScan.hasNext()) {
        String relativePath = lineScan.next();

        if (fileFormat.equals("layers")) {
          try {
            mdl.exportAllLayers(relativePath); // Todo: Change for Signature
          } catch (IllegalArgumentException e) {
            vw.write("could not export layers");
          }
        }

        IFileFormat destFileType = formatsMap.get(fileFormat);

        if (destFileType == null) {
          vw.write("invalid file format: \"" + fileFormat + "\"");
          return;
        }

        try {
          vw.write("exporting to " + fileFormat + " file " + relativePath);
          mdl.exportImage(destFileType, relativePath);
        } catch (IllegalArgumentException e) {
          vw.write("failed to export to " + fileFormat + " file at path "
              + relativePath + ": " + e.getMessage());
        }
      }
    }
  }

}