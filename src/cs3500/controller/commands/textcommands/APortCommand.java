package cs3500.controller.commands.textcommands;

import cs3500.model.fileformat.IFileFormat;
import cs3500.model.fileformat.JPEGFile;
import cs3500.model.fileformat.PNGFile;
import cs3500.model.fileformat.PPMFile;
import java.util.HashMap;
import java.util.Map;

/**
 * Helps to eliminate duplicate code in the {@link ExportCommand} and {@link ImportCommand} classes,
 * specifically this class defines a {@link Map} to be inherited by the mentioned concrete
 * subclasses that can be used to identify file types that a {@link cs3500.model.IMultiLayerModel}
 * can import/export to, using the <i>command design pattern</i>.
 */
public abstract class APortCommand extends AIMECommand {

  protected final Map<String, IFileFormat> formatsMap = this.initFormatsMap();


  /**
   * A {@link Map} to be inherited by the mentioned concrete subclasses that can be used to identify
   * file types that a {@link cs3500.model.IMultiLayerModel} can import/export to, using the
   * <i>command design pattern</i>.
   *
   * @return the map between names representing supported file extensions to be imported/exported
   *         from/to and their corresponding {@link IFileFormat} function object.
   */
  private Map<String, IFileFormat> initFormatsMap() {
    Map<String, IFileFormat> formats = new HashMap<>();
    formats.putIfAbsent("PPM", new PPMFile());
    formats.putIfAbsent("JPEG", new JPEGFile());
    formats.putIfAbsent("PNG", new PNGFile());

    return formats;
  }


}