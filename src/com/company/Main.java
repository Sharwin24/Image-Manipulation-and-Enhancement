package com.company;

import cs3500.controller.IMultiLayerIMEController;
import cs3500.controller.MultiLayerGUIController;
import cs3500.controller.MultiLayerIMEControllerImpl;
import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.MultiLayerModelImpl;
import cs3500.view.GUIView;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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

//    //TODO: comment this out: this just runs the GUI from main for testing purposes
//    IMultiLayerExtraOperations model = new MultiLayerModelImpl();
//    IMultiLayerIMEController ctrlr = new MultiLayerGUIController(model,
//        new GUIView());
////    ctrlr.run();

    // args = new String[]{"-interactive"};

    if (args.length == 0) {
      args = new String[]{"-interactive"}; // in the case of no params, the GUI is opened.
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

          } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
              | IllegalAccessException e) {
            throw new IllegalArgumentException("could not open GUI");
          }

          break;
        case "-script": 
          System.out.println("script path");
          if (args.length < 2) {
            throw new IllegalArgumentException("no script was passed. Try again and pass a script");
          }

          Path filePath = Path.of(args[1]);
          try {
            String scriptTxt = Files.readString(filePath, StandardCharsets.US_ASCII);
            StringReader script = new StringReader(scriptTxt);

            IMultiLayerIMEController scriptCtrlr = MultiLayerIMEControllerImpl.controllerBuilder()
                .readable(script).buildController();
            scriptCtrlr.run();

          } catch (IOException e) {
            throw new IllegalArgumentException("could not read script from file " +  filePath);
          }
          break;
        case "-text":
          IMultiLayerIMEController ctrlrText =
              MultiLayerIMEControllerImpl.controllerBuilder().buildController();
          ctrlrText.run();
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