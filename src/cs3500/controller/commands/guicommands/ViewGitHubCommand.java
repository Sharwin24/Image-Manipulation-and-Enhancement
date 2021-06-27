package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class ViewGitHubCommand extends AGUICommand {

  /**
   * Todo
   *
   * @param model
   * @param frame
   * @throws IllegalArgumentException
   */
  public ViewGitHubCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {

    try {
      Desktop.getDesktop().browse(new URL(frame.GITHUB_URL).toURI());
    } catch (URISyntaxException | IOException e) {
      frame.errorPopup("Could not open up the github URL. Congrats on breaking the "
          + "program. https://github.com/Sharwin24/Image-Manipulation-and-Enhancement.git is"
          + " the actual link. Contact us there about this issue", "Bad GitHub URL");
    }

  }

}
