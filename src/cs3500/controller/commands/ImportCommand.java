package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.model.fileformat.IFileFormat;
import cs3500.model.fileformat.JPEGFile;
import cs3500.model.fileformat.PNGFile;
import cs3500.model.fileformat.PPMFile;
import cs3500.view.IIMEView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * TODO
 */
public class ImportCommand extends APortCommand {

  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw) {
    if (lineScan.hasNext()) {
      String fileFormat = lineScan.next();
      if(lineScan.hasNext()) {
        String relativePath = lineScan.next();

        if (fileFormat.equals("layers")) {
          try {
            mdl.importAllLayers(new ArrayList()); // TODO: rework signature
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
          vw.write("importing to " + fileFormat + " file " + relativePath);
          mdl.importImage(destFileType, relativePath);
        } catch (IllegalArgumentException e) {
          vw.write("failed to import to " + fileFormat + " file at path "
              + relativePath + ": " + e.getMessage());
        }
      }
    }
  }

}
