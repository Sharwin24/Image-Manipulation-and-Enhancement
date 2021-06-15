package cs3500.controller.commands;

import cs3500.model.fileformat.IFileFormat;
import cs3500.model.fileformat.JPEGFile;
import cs3500.model.fileformat.PNGFile;
import cs3500.model.fileformat.PPMFile;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: revise this:
 * abstract class for import/export commands
 */
public abstract class APortCommand extends AIMECommand {
  protected final Map<String, IFileFormat> formatsMap = this.initFormatsMap();




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
