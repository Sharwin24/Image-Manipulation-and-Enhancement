package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.model.fileformat.IFileFormat;
import cs3500.model.fileformat.PPMFile;
import cs3500.view.IIMEView;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ImportCommand implements IIMECommand {

  private final Map<String, IFileFormat> formats = this.initFormatsMap();

  @Override
  public void execute(Scanner s, IMultiLayerModel mdl, IIMEView vw)
      throws IllegalArgumentException, IllegalStateException {

    s.
    if (s.hasNext()) {
      String
    }

    if (s.hasNext()) {
      String inp = s.next();
      if (inp.equals("layers")) {
        // TODO
      }
      else {
        try {
          mdl.importImage(inp);
        }
      }
    }
    try {
      mdl.import
    }
  }

  private Map<String, IFileFormat> initFormatsMap() {
    Map<String, IFileFormat> formats = new HashMap<>();
    formats.putIfAbsent("PPM", new PPMFile());
    formats.putIfAbsent("JPEG", )
  }
}
