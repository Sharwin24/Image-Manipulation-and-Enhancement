package cs3500.controller.commands;

import cs3500.Utils;
import cs3500.model.IMultiLayerModel;
import cs3500.model.fileformat.IFileFormat;
import cs3500.view.IIMEView;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * <p>A function object used to represent the execution of a
 * {@link IMultiLayerModel#importImage(IFileFormat, String)} or {@link
 * IMultiLayerModel#importAllLayers(IFileFormat, String)} call in the {@link IMultiLayerModel}, to
 * be used to implement the <i>command design pattern</i> in the {@link
 * cs3500.controller.IMultiLayerIMEController} class.</p>
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
public class ImportCommand extends APortCommand {

  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw) {
    if (lineScan.hasNext()) {
      String fileFormat = lineScan.next();
      if (lineScan.hasNext()) {
        String relativePath = lineScan.next();

        if (fileFormat.equals("layers")) {
          try {
            this.importAllLayers(formatsMap.get(fileFormat), relativePath, mdl, vw);
          } catch (IllegalArgumentException e) {
            vw.write("could not import layers");
          }
        }

        IFileFormat destFileType = formatsMap.get(fileFormat);

        if (destFileType == null) {
          vw.write("invalid file format: \"" + fileFormat + "\"");
          return;
        }

        try {
          vw.write("importing from " + fileFormat + " file " + relativePath + "...");
          mdl.load(destFileType.importImage(relativePath));
          vw.write("successfully imported!");
        } catch (IllegalArgumentException e) {
          vw.write("failed to import from " + fileFormat + " file at path "
              + relativePath + ": " + e.getMessage());
        }
      }
    }
  }


  /**
   * Imports all of the images contained in the text file at the given relative path
   *
   * @param fileFormat   the format of the files to be imported
   * @param relativePath the path to a text file containing the locations of the files to be
   *                     imported.
   * @throws IllegalArgumentException if any of the parameters are {@code null}.
   */
  private void importAllLayers(IFileFormat fileFormat, String relativePath,
      IMultiLayerModel mdl, IIMEView vw) {
    Utils.checkNotNull(fileFormat, "cannot import all layers with a null file format");
    Utils.checkNotNull(relativePath, "cannot import all layers with a null path");
    Utils.checkNotNull(mdl, "cannot import all layers with a null file model");
    Utils.checkNotNull(vw, "cannot import all layers with a null file view");
    try {
      File txtFileWithPaths = new File(relativePath);
      Scanner lineScanner = new Scanner(txtFileWithPaths);
      while (lineScanner.hasNextLine()) {
        String fileLocation = lineScanner.nextLine();
        try {
          mdl.addLayer();
          mdl.setCurrentLayer(mdl.getLayers().size() - 1);
          mdl.load(fileFormat, fileLocation);
        } catch (IllegalArgumentException e) {
          vw.write("failed to load image " + relativePath +
              " onto layer at index " + (mdl.getLayers().size() - 1));
        }
      }
    } catch (FileNotFoundException e) {
      vw.write("couldnt find file :" + relativePath);
    }
  }

}