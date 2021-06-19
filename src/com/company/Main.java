package com.company;

import cs3500.controller.IMultiLayerIMEController;
import cs3500.controller.MultiLayerIMEControllerImpl;

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
    controller.run();
  }
}