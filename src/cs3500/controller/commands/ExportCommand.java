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

public class ExportCommand implements IIMECommand {

  private final Map<String, IFileFormat> formats = this.initFormatsMap();

  @Override
  public void execute(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw)
      throws IllegalArgumentException, IllegalStateException {

    if (lineScan.hasNext()) {
      String format = lineScan.next(); // "layers" or the format
      if (lineScan.hasNext()) {
        String relativePath = lineScan.next(); // the path name
        if (format.equals("layers")) {
          try {
            mdl.exportAllLayers(relativePath); // TODO, might have to change signature
          } catch (IllegalArgumentException e) {
            vw.write("cannot export desired layers");
          }
        } else {
          IFileFormat destFileType = formats.get(format);

          vw.write("exporting current layer to " + format + " file \""
              + relativePath + "\"");

          try { // if destFileType is null, then the IllegalArgumentException is still caught
            mdl.exportImage(destFileType, relativePath);
          } catch (IllegalArgumentException e) {
            vw.write("cannot export the given layer of file type " +
                format + " from path " + relativePath);
          }
        }
      }
    }
  }

    /**
     * TODO
     *
     * @return
     */
    private Map<String, IFileFormat> initFormatsMap () {
      Map<String, IFileFormat> formats = new HashMap<>();
      formats.putIfAbsent("PPM", new PPMFile());
      formats.putIfAbsent("JPEG", new JPEGFile());
      formats.putIfAbsent("PNG", new PNGFile());

      return formats;
    }
  }
