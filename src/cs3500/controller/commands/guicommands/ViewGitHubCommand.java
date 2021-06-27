package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * A command/listener to display the github source of this project in a new browser window.
 */
public class ViewGitHubCommand extends AGUICommand {

  /**
   * Constructs a ViewGitHubCommand based on the model to manipulate and the view that will reflect
   * these changes.
   *
   * @param model the model to manipulate.
   * @param frame view that will reflect these changes.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public ViewGitHubCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {

    try {
      Desktop.getDesktop().browse(new URL(GUIView.GITHUB_URL).toURI());
    } catch (URISyntaxException | IOException e) {
      frame.errorPopup("Could not open up the github URL. Congrats on breaking the "
          + "program. https://github.com/Sharwin24/Image-Manipulation-and-Enhancement.git is"
          + " the actual link. Contact us there about this issue", "Bad GitHub URL");
    }

  }

}