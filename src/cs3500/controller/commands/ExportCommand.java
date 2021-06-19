package cs3500.controller.commands;

import cs3500.Utility;
import cs3500.model.IMultiLayerModel;
import cs3500.model.fileformat.IFileFormat;
import cs3500.model.layer.ILayer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import cs3500.view.IMEView;
import java.util.Scanner;

/**
 * <p>A function object used to represent the execution of an export command
 * call in the {@link IMultiLayerModel}, to be used to implement the <i>command design pattern</i>
 * in the {@link cs3500.controller.IMultiLayerIMEController} class.</p>
 *
 * <p>This class, in particular, allows the user to input a command in the form
 * "<code>export [ff] (layers) [p]</code>", where <code>[ff]</code> represents the {@link
 * IFileFormat} of the exported files--the file extension, and where <code>(layers)</code>
 * represents the optional parameter--the literal word "layers-- that will export all
 * <code>layers</code> of this image, or when <code>layers</code> is omitted,
 * only the current working layer will be exported. Finally,
 * <code>[p]</code> represents the <code>p</code>athname that the exported
 * layer(s) will be exported to, relative to the working (project) directory.
 */
public class ExportCommand extends APortCommand {

  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IMEView vw) {

    if (lineScan.hasNext()) {
      String fileFormat = lineScan.next();
      if (lineScan.hasNext()) {
        String relativePath = lineScan.next();

        if (fileFormat.equals("layers")) {
          try {
            this.exportAllLayers(formatsMap.get(fileFormat),
                relativePath, mdl, vw);
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
          vw.write("exporting to " + fileFormat + " file " + relativePath + "...");
          destFileType.exportImage(relativePath, mdl.getImage());
          vw.write("should've exported by now");
        } catch (IllegalArgumentException e) {
          vw.write("failed to export to " + fileFormat + " file at path "
              + relativePath + ": " + e.getMessage());
        }
      }
    }
  }

  private void exportAllLayers(IFileFormat fileFormat, String dirPath, IMultiLayerModel mdl,
      IMEView vw)
      throws IllegalArgumentException {
    Utility.checkNotNull(fileFormat, "cannot export all layers to a null file format");
    Utility.checkNotNull(dirPath, "cannot export all layers to a null path");
    Utility.checkNotNull(mdl, "cannot export all layers with a null file mdl");
    Utility.checkNotNull(vw, "cannot export all layers with a null file vw");

    try {
      File fileToStorePathsToImages = new File(dirPath);
      fileFormat.createDirectory(dirPath);
      Appendable sb = new StringBuilder();

      for (ILayer lyr : mdl.getLayers()) {
        int layerCtr = 0;
        if (!lyr.isInvisible()) {
          String layerName = dirPath + "/layer_" + layerCtr;
          try {
            sb.append(layerName + fileFormat.getFileExtension() + "\n");
          } catch (IOException e) {
            throw new IllegalArgumentException("writing to the appendable failed");
          }
          fileFormat.exportImage(layerName, lyr.getModel().getImage());
          layerCtr++;
        }
      }

      try {
        FileWriter fw = new FileWriter(fileToStorePathsToImages);
        fw.append(sb.toString());
        fw.close();
      } catch (IOException e) {
        throw new IllegalArgumentException("could not write to text file");
      }


    } catch (IllegalArgumentException e) {
      vw.write("could not export all layers: " + e.getMessage());
    }
  }

}