package cs3500.controller;

import cs3500.Utils;
import cs3500.controller.commands.ApplyCommand;
import cs3500.controller.commands.DeleteCommand;
import cs3500.controller.commands.ExportCommand;
import cs3500.controller.commands.IIMECommand;
import cs3500.controller.commands.ImportCommand;
import cs3500.controller.commands.CurrentCommand;
import cs3500.controller.commands.NewLayerCommand;
import cs3500.controller.commands.RedoCommand;
import cs3500.controller.commands.SaveCommand;
import cs3500.controller.commands.SwapCommand;
import cs3500.controller.commands.VisibilityCommand;
import cs3500.controller.commands.UndoCommand;
import cs3500.controller.commands.ProgrammaticImageCommand;
import cs3500.model.IMultiLayerModel;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.image.IImage;
import cs3500.view.IIMEView;
import cs3500.view.TextualIMEView;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A Controller for the {@link IMultiLayerModel} that
 * relies on I/O from a {@link Readable} such as System.in or a script file
 * and to a given {@link Appendable}. Communicates data to a view and
 * communicates to the model in order to provide I/O feedback to the user.
 *
 * In this controller, inputs are processed from the mentioned {@link Readable} with the following
 * syntax:
 *     Each command follows this meta-structure:
 *     <i><code>[CMD] (opt1) (opt2) ... (optm) [req1] [req2] ... [reqn]</code></i>,
 *     where <code>[CMD]</code> is the textual representation of a command,
 *     and <code>(opti)</code> is the <code>i</code>th optional argument of
 *     of a function whose optional arguments have an arity of <code>m</code>,
 *     and where <code>[reqj]</code> is the <code>j</code>th required argument of
 *     a function of arity <code>n</code>. Since different commands have different arities (and
 *     some take no optional paramters), the commands take arguments as expressed in the following
 *     table: TODO CREATE TABLE HERE
 */
public class MultiLayerIMEControllerImpl implements IMultiLayerIMEController<IImage> {

  private final IMultiLayerModel mdl;
  private final Readable rd;
  private final Appendable ap;
  private final IIMEView vw;
  private final Map<String, IIMECommand> cmds = this.initCommands();

  /**
   * TODO: Use builder to clean these up
   */
  public MultiLayerIMEControllerImpl() {
    this.mdl = new MultiLayerModelImpl();
    this.rd = new InputStreamReader(System.in);
    this.ap = System.out;
    this.vw = new TextualIMEView();

  }

  public MultiLayerIMEControllerImpl(IMultiLayerModel mdl)
  throws IllegalArgumentException {
    this.mdl = Utils.checkNotNull(mdl, "cannot create a IME controller with a "
        + "null model");
    this.rd = new InputStreamReader(System.in);
    this.ap = System.out;
    this.vw = new TextualIMEView(mdl, ap);


  }

  public MultiLayerIMEControllerImpl(IMultiLayerModel mdl, Readable rd)
      throws IllegalArgumentException {
    this.mdl = Utils.checkNotNull(mdl, "cannot create a IME controller with a "
        + "null model");
    this.rd = Utils.checkNotNull(rd, "cannot create a IME controller with a "
        + "null Readable");
    this.ap = System.out;
    this.vw = new TextualIMEView(mdl, ap);


  }

  public MultiLayerIMEControllerImpl(IMultiLayerModel mdl, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    this.mdl = Utils.checkNotNull(mdl, "cannot create a IME controller with a "
        + "null model");
    this.rd = Utils.checkNotNull(rd, "cannot create a IME controller with a "
        + "null Readable");
    this.ap = System.out;
    this.vw = new TextualIMEView(mdl, ap);



  }



  @Override
  public void run(IMultiLayerModel<IImage> mdl)
  throws IllegalArgumentException, IllegalStateException {
    Utils.checkNotNull(mdl, "cannot run the controller on a null model");

    Scanner s = new Scanner(rd);

    while(s.hasNextLine()) {
      String line = s.nextLine();

      Scanner lineScan = new Scanner(line);
      if (lineScan.hasNext()) {
        String cmd = lineScan.next();
        if (cmds.containsKey(cmd)) {
          // vw.write("received command \"" + cmd + "\"");
          cmds.get(cmd).execute(lineScan, mdl, vw);
        }
      }

    }
    System.out.println("done");
  }

  /**
   * TODO
   *
   * @return
   */
  private Map<String, IIMECommand> initCommands() {
    Map<String, IIMECommand> cmds = new HashMap<>();
    cmds.putIfAbsent("undo", new UndoCommand());
    cmds.putIfAbsent("redo", new RedoCommand());
    cmds.putIfAbsent("save", new SaveCommand());
    cmds.putIfAbsent("import", new ImportCommand());
    cmds.putIfAbsent("export", new ExportCommand());
    cmds.putIfAbsent("apply", new ApplyCommand());
    cmds.putIfAbsent("visibility", new VisibilityCommand());
    cmds.putIfAbsent("current", new CurrentCommand());
    cmds.putIfAbsent("delete", new DeleteCommand());
    cmds.putIfAbsent("swap", new SwapCommand());
    cmds.putIfAbsent("new", new NewLayerCommand());
    cmds.putIfAbsent("programmatic", new ProgrammaticImageCommand());
    return cmds;
  }
}
