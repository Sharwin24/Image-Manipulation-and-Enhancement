package com.company;

import cs3500.controller.IMultiLayerIMEController;
import cs3500.controller.MultiLayerIMEControllerImpl;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.fileformat.IFileFormat;
import cs3500.model.fileformat.PNGFile;
import java.io.InputStreamReader;

/**
 * Main class.
 */
public class Main {

  /**
   * Main method.
   *
   * @param args optional args.
   */
  public static void main(String[] args) {

    // add calls to methods to manipulate images here...

    IMultiLayerIMEController controller =
        MultiLayerIMEControllerImpl.controllerBuilder().buildController();
    controller.run(new MultiLayerModelImpl());
  }
}