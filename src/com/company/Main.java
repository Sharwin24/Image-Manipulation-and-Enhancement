package com.company;

import cs3500.controller.IMultiLayerIMEController;
import cs3500.controller.MultiLayerGUIController;
import cs3500.controller.MultiLayerIMEControllerImpl;
import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.MultiLayerModelImpl;
import cs3500.view.GUIView;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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

    //TODO: comment this out: this just runs the GUI from main for testing purposes
    IMultiLayerExtraOperations model = new MultiLayerModelImpl();
    IMultiLayerIMEController ctrlr = new MultiLayerGUIController(model,
        new GUIView());
    ctrlr.run();

    args = new String[]{"-interactive"};

    if (args.length == 0) {
      System.out.println("no args passed");
    }
    else {

      switch (args[0]) {
        case "-interactive":
          GUIView.setDefaultLookAndFeelDecorated(true);
          GUIView frame = new GUIView();

          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.setVisible(true);

          try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            //UIManager.getCrossPlatformLookAndFeelClassName();

          } catch (UnsupportedLookAndFeelException e) {
            // do something
          } catch (ClassNotFoundException e) {
            // do something
          } catch (InstantiationException e) {
            // do something
          } catch (IllegalAccessException e) {
            // do something
          }
          break;
        case "-script": // TODO: handle script path I/O
          System.out.println("script path");
          break;
        case "-text":
          IMultiLayerIMEController ctrlrText =
              MultiLayerIMEControllerImpl.controllerBuilder().buildController();
          ctrlr.run();
          break;
        default:
          System.out.println("error");
      }
    }

  }

  /**
   * Utility method to read a script text file for the controller directly into a StringReader that
   * will be passed to the main method.
   *
   * @param pathName the path where the script is located.
   * @return A StringReader with the contents of the script file
   * @throws IllegalArgumentException if the path name could not be read from.
   */
  private static StringReader scriptToStringBuilder(String pathName)
      throws IllegalArgumentException {
    try {
      byte[] outputFromFile = Files.readAllBytes(Paths.get(pathName));
      return new StringReader(new String(outputFromFile));
    } catch (IOException e) {
      throw new IllegalArgumentException("path \"" + pathName + "\" not able to be read from");
    }
  }
}