package cs3500.controller;

import cs3500.Utility;
import cs3500.controller.commands.textcommands.ApplyCommand;
import cs3500.controller.commands.textcommands.CurrentCommand;
import cs3500.controller.commands.textcommands.DeleteCommand;
import cs3500.controller.commands.textcommands.ExportCommand;
import cs3500.controller.commands.textcommands.IIMECommand;
import cs3500.controller.commands.textcommands.ImportCommand;
import cs3500.controller.commands.textcommands.MosaicCommand;
import cs3500.controller.commands.textcommands.NewLayerCommand;
import cs3500.controller.commands.textcommands.ProgrammaticImageCommand;
import cs3500.controller.commands.textcommands.RedoCommand;
import cs3500.controller.commands.textcommands.SaveCommand;
import cs3500.controller.commands.textcommands.SwapCommand;
import cs3500.controller.commands.textcommands.UndoCommand;
import cs3500.controller.commands.textcommands.VisibilityCommand;
import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.IMultiLayerModel;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.operation.IOperation;
import cs3500.view.IMEView;
import cs3500.view.TextualIMEView;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A Controller for the {@link IMultiLayerModel} that relies on I/O from a {@link Readable} such as
 * System.in or a script file and to a given {@link Appendable}. Communicates data to a view and
 * communicates to the model in order to provide I/O feedback to the user.
 *
 * <p>In this controller, inputs are processed from the mentioned {@link Readable}
 * with the following syntax:
 *
 * <p> Each command follows this meta-structure:
 * <i><code>[CMD] [arg1] [arg2] ... [argn]</code></i>,
 * where <code>[CMD]</code> is the textual representation of a command, and where
 * <code>[argi]</code> is the <code>i</code>th required argument of a function of arity
 * <code>n</code>. Additionally, some commands take an optional parameter, denoted with the syntax
 * <code>(opt)</code>, where the use of parentheses distinguish that this parameter is optional and
 * not <code>[required]</code>. Furthermore, this controller takes advantage of {@link IIMECommand}
 * function objects associated with each textual command via a {@link Map} in order to easily
 * support this functionality, and provide developers with an easy path to add/change the supported
 * commands. Since different commands have different arities (and some take no optional paramters),
 * the commands take arguments as expressed in the following table. Consider this a <i>guide</i> to
 * using the commands, too</p>
 * <p><b>How to read the table:</b></p> the <i>textual command</i> shows
 * what word leads off a command, and the <i>parameters</i> shows what parameters should be
 * specified, where:
 *    <ul>
 *      <li>
 *        <code>[Type]</code> denotes a required parameter of type <code>Type</code>,
 *        for example, <code>[String]</code> represents a required parameter
 *        that is a String.
 *      </li>
 *      <li>
 *        <code>(Type)</code> denotes an optional parameter of type <code>Type</code>,
 *        for example, for parameters <code>[Integer] (Real) [Boolean]</code>,
 *        a valid command combination of parameters would be 0 12.3 true,
 *        or 0 true, since the <code>(Real)</code> optional parameter is, well, optional.
 *      </li>
 *      <li>
 *        <code>("literal")</code> and <code>["literal"]</code> denote
 *        an optional or required (resp.) parameter that is the literal value
 *        <code>literal</code>, as denoted by the quotation marks. For example,
 *        for parameters <code>[String] (Integer) ("5") ["hello"]</code>,
 *        valid combinations of parameters would be <code>Foo 2 5 hello</code>,
 *        <code>bar 5 hello</code>, or <code>BAZ hello</code>
 *      </li>
 *      <li>
 *        <code>[{t1, t2, ..., tn}]</code> and
 *        <code>({t1, t2, ..., tm}</code>
 *        denote a parameter that can be one of the literals <code>ti</code>,
 *        where <code>1 <= i <= n,m</code>, and <code>n != m || n == m</code>, in
 *        a required or optional parameter (resp.). For example, for parameters
 *        <code>[Character] ({1, 2, 3}) [{Foo, Bar, Baz, Quux}]</code>,
 *        a valid combination of parameters is <code>A 2 Quux</code>,
 *        <code>u Baz</code>, but not <code>f 4 Hello</code>, since
 *        "4" and "Hello" are not enumerated in
 *        <code>({1, 2, 3})</code> and <code>[{Foo, Bar, Baz, Quux}]</code>, (
 *        resp.)
 *      </li>
 *    </ul>
 *
 *   <ul>
 *     <ul>
 *       <li>
 *       <b>Textual command:</b> <code>apply</code>
 *       </li>
 *       <ul>
 *         <li>
 *           <b>Arity:</b> n-ary
 *         </li>
 *         <li>
 *           <b>Parameters:</b>
 *           ({"sepia", "greyscale", "sharpen", "blur"})
 *           ({"sepia", "greyscale", "sharpen", "blur"})
 *           ({"sepia", "greyscale", "sharpen", "blur"}) ...
 *         </li>
 *         <li><b>Description:</b> applies <code>n</code>
 *         {@link IOperation}s to the current image to manipulate it,
 *         where <code>n</code> is a non-negative integer.</li>
 *       </ul>
 *
 *       <li>
 *         <b>Textual command:</b> <code>current</code>
 *         </li>
 *         <ul>
 *           <li>
 *             <b>Arity:</b> unary
 *           </li>
 *           <li>
 *             <b>Parameters:</b>
 *             [Integer]
 *           </li>
 *           <li><b>Description:</b> sets the current working layer
 *           to the layer at the given index (indexed from 0, from left
 *           to right with respect to the {@link java.util.List} that the layer
 *           is stored in.</li>
 *         </ul>
 *
 *
 *         <li>
 *           <b>Textual command:</b> <code>new</code>
 *           </li>
 *           <ul>
 *             <li>
 *               <b>Arity:</b> nullary
 *             </li>
 *             <li>
 *               <b>Parameters:</b> *none*
 *           </li>
 *             <li><b>Description:</b> creates a new layer to store and manipulate an image
 *             </li>
 *           </ul>
 *
 *
 * <li>
 *           <b>Textual command:</b> <code>delete</code>
 *           </li>
 *           <ul>
 *             <li>
 *               <b>Arity:</b> unary
 *             </li>
 *             <li>
 *               <b>Parameters:</b>
 *               <code>[Integer]</code>
 *             </li>
 *             <li><b>Description:</b> deletes the
 *             layer at the given index (indexed from 0, from left
 *             to right with respect to the {@link java.util.List} that the layer
 *             is stored in.)</li>
 *           </ul>
 *
 *
 *
 * <li>
 *           <b>Textual command:</b> <code>export</code>
 *           </li>
 *           <ul>
 *            <li>
 *               <b>Arity:</b> binary, with one optional parameter
 *             </li>
 *             <li>
 *               <b>Parameters:</b>
 *               <code>[{"PPM", "PNG", "JPEG"}] ("layers") [String]</code>
 *             </li>
 *             <li><b>Description:</b> exports image(s) to the file format
 *             specified by the first parameter. If the optional parameter
 *             <code>("layers")</code> is included, all layers of the multilayer
 *             image are exported, otherwise only the current layer is exported.
 *             the second required parameter denotes the name of the file that is to be exported,
 *             such as "myImage".</li>
 *           </ul>
 *
 *
 *
 * <li>
 *           <b>Textual command:</b> <code>import</code>
 *           </li>
 *           <ul>
 *             <li>
 *               <b>Arity:</b> binary, with one optional parameter
 *             </li>
 *             <li>
 *               <b>Parameters:</b>
 *                 <code>[{"PPM", "PNG", "JPEG"}] ("layers") [String]</code>
 *               </li>
 *               <li><b>Description:</b> imports image(s) of the file format
 *               specified by the first parameter. If the optional parameter
 *               <code>("layers")</code> is included, all layers of the multilayer
 *               image are imported from a file containing all of the paths
 *               of the images to be loaded.
 *               The second required parameter denotes the name of the path to the image(s)
 *               that will be imported, relative to the current working directory,
 *               such as "res/myImage.png".</li>
 *           </ul>
 *
 *
 *
 * <li>
 *           <b>Textual command:</b> <code>programmatic</code>
 *           </li>
 *           <ul>
 *             <li>
 *               <b>Arity:</b> quaternary
 *             </li>
 *             <li>
 *               <b>Parameters:</b>
 *               <code>[{"checkerboard", "bwnoise", "rainbownoise", "purenoise"}]
 *               [Integer] [Integer] [Integer]</code>
 *             </li>
 *             <li><b>Description:</b> Creates a programmatic image as specified by the literal
 *             in the first argument,
 *             with a width, height, and unit size in pixels specified by the second, third, and
 *             fourth arguments (resp.) on the current layer.</li>
 *           </ul>
 *
 *
 * <li>
 *           <b>Textual command:</b> <code>redo</code>
 *           </li>
 *           <ul>
 *             <li>
 *               <b>Arity:</b> nullary
 *             </li>
 *             <li>
 *               <b>Parameters:</b> *none*
 *             </li>
 *             <li><b>Description:</b> restores the last state of the multilayer
 *             model before an undo command was executed, if possible.</li>
 *           </ul>
 *
 *
 * <li>
 *           <b>Textual command:</b> <code>undo</code>
 *           </li>
 *           <ul>
 *             <li>
 *               <b>Arity:</b> nullary
 *             </li>
 *             <li>
 *               <b>Parameters:</b> *none*
 *             </li>
 *             <li><b>Description:</b> undoes the most recent change to the
 *             multilayer model, if possible.</li>
 *           </ul>
 *
 *           <li>
 *             <b>Textual command:</b> <code>visibility</code>
 *             </li>
 *             <ul>
 *               <li>
 *                 <b>Arity:</b> unary
 *               </li>
 *               <li>
 *                 <b>Parameters:</b>
 *                 <code>[Integer]</code>
 *               </li>
 *               <li><b>Description:</b> toggles the visibility of the layer
 *               indicated by the given argument, indexed from 0, left to right
 *               (with respect to the {@link java.util.List} that the layers of the model
 *               are stored in.</li>
 *             </ul>
 *
 *             <li>
 *             <b>Textual command:</b> <code>swap</code>
 *             </li>
 *             <ul>
 *               <li>
 *                 <b>Arity:</b> binary
 *               </li>
 *               <li>
 *                 <b>Parameters:</b>
 *                 <code>[Integer] [Integer]</code>
 *               </li>
 *               <li><b>Description:</b> swaps the layers at indices given by the two arguments,
 *               indexed from 0, from left to right (with respect to the
 *               {@link java.util.List} that the layers are stored in.</li>
 *             </ul>
 *
 *             <li>
 *             <b>Textual command:</b> <code>save</code>
 *             </li>
 *             <ul>
 *               <li>
 *                 <b>Arity:</b> nullary
 *               </li>
 *               <li>
 *                 <b>Parameters:</b> *none*
 *               </li>
 *               <li><b>Description:</b> saves the most recent version of the model
 *               to its history, meaning that an undo command will do nothing.</li>
 *            </ul>
 *
 *
 *
 *
 *     </ul>
 *   </ul>
 */
public class MultiLayerIMEControllerImpl implements IMultiLayerIMEController {

  private final IMultiLayerExtraOperations mdl;
  private final Readable rd;
  private final Appendable ap;
  private IMEView vw;
  private final Map<String, IIMECommand> cmds;

  /**
   * A Trivial initialization constructor that constructs a {@link MultiLayerIMEControllerImpl}
   * based on a given model to control, the view to render feedback to, and the {@link Readable} and
   * {@link Appendable} to read/write data I/O data from/to (resp.).
   *
   * @param mdl the model to control
   * @param rd  the readable to read input from
   * @param ap  the appendable to write data to
   * @param vw  the view to render feedback and interactions messages to
   * @throws IllegalArgumentException if any of the parameters are {@code null}.
   */
  public MultiLayerIMEControllerImpl(IMultiLayerExtraOperations mdl, Readable rd, Appendable ap,
      IMEView vw) throws IllegalArgumentException {
    this.mdl = Utility.checkNotNull(mdl, "cannot make an IME controller with a null model");
    this.rd = Utility.checkNotNull(rd, "cannot make an IME controller with a null"
        + " readable");
    this.ap = Utility.checkNotNull(ap, "cannot make an IME controller with a null "
        + "appendable");
    this.vw = Utility.checkNotNull(vw, "cannot make an IME controller with a null "
        + "IME view");
    this.cmds = this.initCommands();
  }


  /**
   * A utility builder class used to implement the <i>builder design pattern</i> in order to provide
   * customizability for constructing the controller. The 'default' controller sets the model to a
   * new model with no images, the readable to stdin, and the appendable to stdout, and the view to
   * a simple textual view.
   *
   * <p>The methods in this class allow the user to specify which fields they want to change from
   * the stated default values, and only customize what they want, taking advantage of method
   * chaining. For example, a call <code> MultiLayerIMEControllerImpl.controllerBuilder().
   * buildController()}
   * </code>
   * customizes no fields of the returned controller, setting them to the stated 'default' values,
   * while a more verbose call such as
   * <code>
   * MultiLayerIMEControllerImpl.controllerBuilder().model(...).appendable(...).readable(...)
   * .view(...).buildController()
   * </code> returns a controller with all fields customized.
   */
  public static final class ControllerBuilder {

    private IMultiLayerExtraOperations mdl;
    private Readable rd;
    private Appendable ap;
    private IMEView vw;

    /**
     * Private constructor to trivially initialize all fields of a {@link ControllerBuilder}. No
     * exceptions are thrown since this error-checking is handled in the {@link
     * MultiLayerIMEControllerImpl#MultiLayerIMEControllerImpl(IMultiLayerExtraOperations, Readable, Appendable, IMEView)} constructor.
     *  @param mdl the model to control
     * @param rd  the readable to read inputs from
     * @param ap  the appendable to write output messages to
     * @param vw  the view to display interactions messages
     */
    private ControllerBuilder(IMultiLayerExtraOperations mdl, Readable rd, Appendable ap, IMEView vw) {
      this.mdl = mdl;
      this.rd = rd;
      this.ap = ap;
      this.vw = vw;
    }

    /**
     * To set the model field of a {@link ControllerBuilder}, and finally a {@link
     * MultiLayerIMEControllerImpl} through use of the <i>builder design pattern</i>.
     *
     * @param mdl the model to control
     * @return a {@link ControllerBuilder} with the model field customized.
     */
    public ControllerBuilder model(IMultiLayerExtraOperations mdl) {
      this.mdl = mdl;
      return this;
    }

    /**
     * To set the readable field of a {@link ControllerBuilder}, and finally a {@link
     * MultiLayerIMEControllerImpl} through use of the <i>builder design pattern</i>.
     *
     * @param rd the readable to read input from.
     * @return a {@link ControllerBuilder} with the readable field customized.
     */
    public ControllerBuilder readable(Readable rd) {
      this.rd = rd;
      return this;
    }

    /**
     * To set the appendable field of a {@link ControllerBuilder}, and finally a {@link
     * MultiLayerIMEControllerImpl} through use of the <i>builder design pattern</i>.
     *
     * @param ap the appendable to write data to.
     * @return a {@link ControllerBuilder} with the appendable field customized.
     */
    public ControllerBuilder appendable(Appendable ap) {
      this.ap = ap;
      return this;
    }

    /**
     * To set the view field of a {@link ControllerBuilder}, and finally a {@link
     * MultiLayerIMEControllerImpl} through use of the <i>builder design pattern</i>.
     *
     * @param vw the view to which interactions messages will be written.
     * @return a {@link ControllerBuilder} with the view field customized.
     */
    public ControllerBuilder view(IMEView vw) {
      this.vw = vw;
      return this;
    }

    /**
     * The final implementation of the <i>builder design pattern</i>, where a new {@link
     * IMultiLayerIMEController} is returned with some or all fields customized.
     *
     * @return a new {@link IMultiLayerIMEController} is returned with some or all fields
     *         customized.
     * @throws IllegalArgumentException if any of the fields that the
     *                                  {@link IMultiLayerIMEController}
     *                                  to be constructed with have been set to {@code null}: allows
     *                                  us to check for nullness here instead of from within each
     *                                  setter method individually.
     */
    public IMultiLayerIMEController buildController()
        throws IllegalArgumentException {
      return new MultiLayerIMEControllerImpl(
          Utility.checkNotNull(mdl, "cannot build a controller with a null model."),
          Utility.checkNotNull(rd, "cannot build a controller with a null readable."),
          Utility.checkNotNull(ap, "cannot build a controller with a null appendable."),
          Utility.checkNotNull(vw, "cannot build a controller with a null view."));
    }
  }

  /**
   * Static method to return a builder object in order to construct {@link
   * IMultiLayerIMEController}s with greater customizability using  the
   * <i>builder design pattern</i>.
   *
   * @return a {@link ControllerBuilder} to use to build and customize a {@link
   * IMultiLayerIMEController}.
   */
  public static ControllerBuilder controllerBuilder() {
    return new ControllerBuilder(new MultiLayerModelImpl(),
        new InputStreamReader(System.in),
        System.out,
        new TextualIMEView());
  }


  @Override
  public void run(/*IMultiLayerModel mdl*/) {
    //Utility.checkNotNull(mdl, "cannot run the controller on a null model");
    this.vw = new TextualIMEView(mdl, ap);
    vw.write("Welcome to Image Manipulation and Enhancement! \n"
        + "Please consult the USEME file "
        + "for information on how to specify commands");
    vw.renderLayers();
    Scanner s = new Scanner(rd);

    while (s.hasNextLine()) {
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
    //vw.write("done");
  }

  /**
   * Initializes a one-to-one mapping between textual commands (see the JavaDoc for {@link
   * MultiLayerIMEControllerImpl}, and their {@link IIMECommand} function object counterpart.
   *
   * @return the mapping referenced above, and explained at nauseam in this class's JavaDoc comment.
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
    cmds.putIfAbsent("mosaic", new MosaicCommand());

    return cmds;
  }
}