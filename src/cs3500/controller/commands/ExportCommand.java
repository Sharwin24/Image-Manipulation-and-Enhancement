package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.model.fileformat.IFileFormat;
import cs3500.view.IIMEView;
import java.util.Scanner;

/**
 * <p>A function object used to represent the execution of a
 * {@link IMultiLayerModel#exportImage(IFileFormat, String)}
 * or {@link IMultiLayerModel#exportAllLayers(IFileFormat, String)}
 * call in the {@link IMultiLayerModel}, to be used to
 * implement the <i>command design pattern</i> in the
 * {@link cs3500.controller.IMultiLayerIMEController}
 * class.</p>
 *
 * <p>This class, in particular, allows the user to input a command in the form
 * "<code>export [ff] (layers) [p]</code>", where <code>[ff]</code> represents the
 * {@link IFileFormat} of the exported files--the file extension,
 * and where <code>(layers)</code> represents the optional parameter--the literal word "layers--
 * that will export all
 * <code>layers</code> of this image, or when <code>layers</code> is omitted,
 * only the current working layer will be exported. Finally,
 * <code>[p]</code> represents the <code>p</code>athname that the exported
 * layer(s) will be exported to, relative to the working (project) directory.
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
            mdl.exportAllLayers(formatsMap.get(fileFormat),
                relativePath);
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
          vw.write("should've exported by now");
        } catch (IllegalArgumentException e) {
          vw.write("failed to export to " + fileFormat + " file at path "
              + relativePath + ": " + e.getMessage());
        }
      }
    }
  }

}