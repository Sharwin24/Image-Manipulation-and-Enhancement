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

public class CustomNoiseCommand extends ANoiseCommand{

  /**
   * Todo
   *
   * @param model
   * @param frame
   * @throws IllegalArgumentException
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
      addAnotherColor = JOptionPane.showConfirmDialog(frame.inputDialogPanel,
          "Would you like to add another color to the noise image to be generated?",
          "Add more colors?",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.QUESTION_MESSAGE);
      if (addAnotherColor == JOptionPane.YES_OPTION) { // Todo: Getters
        Color c = JColorChooser.showDialog(frame, "Choose a color",
            frame.colorChooserDisplay.getBackground());

        colorsPicked.add(c);
      }
    }

    IPixel[] colorsPickedArr = new IPixel[colorsPicked.size()];

    for (int i = 0; i < colorsPicked.size(); i++) {
      colorsPickedArr[i] = frame.colorToIPixel(colorsPicked.get(i));
    }

    return colorsPickedArr;
  }
}