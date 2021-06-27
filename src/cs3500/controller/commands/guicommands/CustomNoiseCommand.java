package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.pixel.IPixel;
import cs3500.model.programmaticimages.IProgramImage;
import cs3500.model.programmaticimages.Noise;
import cs3500.view.GUIView;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;

/**
 * Command for Noise image with user defined colors.
 */
public class CustomNoiseCommand extends ANoiseCommand {

  /**
   * Constructs a Custom Noise Command for the GUI and passes the model and frame.
   *
   * @param model the multi-layer model for the GUI to use.
   * @param frame the JFrame for the GUI to use.
   * @throws IllegalArgumentException if any arguments are null or invalid.
   */
  public CustomNoiseCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  protected IProgramImage factoryProgrammaticImage() {
    return new Noise(getColorsFromUser());
  }

  /**
   * Prompts user to select colors and returns a list of pixels with the color specified.
   *
   * @return a list of pixels with each pixel's color being a selected color by the user.
   */
  private IPixel[] getColorsFromUser() {

    List<Color> colorsPicked = new ArrayList<>();

    int addAnotherColor = JOptionPane.YES_OPTION;

    while (addAnotherColor == JOptionPane.YES_OPTION) {
      addAnotherColor = JOptionPane.showConfirmDialog(frame.getInputDialogPanel(),
          "Would you like to add another color to the noise image to be generated?",
          "Add more colors?",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.QUESTION_MESSAGE);
      if (addAnotherColor == JOptionPane.YES_OPTION) {
        Color c = JColorChooser.showDialog(frame, "Choose a color",
            frame.getColorChooserDisplay().getBackground());

        colorsPicked.add(c);
      }
    }

    IPixel[] colorsPickedArr = new IPixel[colorsPicked.size()];

    for (int i = 0; i < colorsPicked.size(); i++) {
      colorsPickedArr[i] = GUIView.colorToIPixel(colorsPicked.get(i));
    }

    return colorsPickedArr;
  }
}