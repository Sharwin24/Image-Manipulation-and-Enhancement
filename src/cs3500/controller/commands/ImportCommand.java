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

public class ImportCommand implements IIMECommand {

  private final Map<String, IFileFormat> formats = this.initFormatsMap();

  @Override
  public void execute(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw)
      throws IllegalArgumentException, IllegalStateException {

    if (lineScan.hasNext()) {
      String format = lineScan.next(); // "layers" or the format
      if (format.equals("layers")) {
        try {
          mdl.importAllLayers(new ArrayList()); // TODO, might have to change signature
        } catch (IllegalArgumentException e) {
          vw.write("cannot import desired layers");
        }
      } else if (lineScan.hasNext()) {
        String relativePath = lineScan.next(); // the path name
        IFileFormat destFileType = formats.get(format);

        vw.write("importing current layer from " + format + " file \""
            + relativePath + "\"");

        try { // if destFileType is null, then the IllegalArgumentException is still caught
          mdl.importImage(destFileType, relativePath);
        } catch (IllegalArgumentException e) {
          vw.write("cannot import the given layer of file type " +
              format + " from path " + relativePath);
        }
      }
    }
  }

  /**
   * TODO
   *
   * @return
   */
  private Map<String, IFileFormat> initFormatsMap() {
    Map<String, IFileFormat> formats = new HashMap<>();
    formats.putIfAbsent("PPM", new PPMFile());
    formats.putIfAbsent("JPEG", new JPEGFile());
    formats.putIfAbsent("PNG", new PNGFile());

    return formats;
  }

}
