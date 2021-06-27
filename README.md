# Table of contents
1. [Design Summary](#design-section)
2. [How to use the Program](#help-section)


## Design Summary <a name="design-section"></a>
# Image-Manipulation-and-Enhancement

<p>This program serves as an image editing tool for photographers, artists, and personal use. 
It was developed in June 2021 as a project for cs3500: Object Oriented Design at Northeastern 
University.</p>

<p>This document serves as a tool for understanding the design of this project, from a 
high level. For convenience, this document will mimic the package structure of the acutal
project, and at the end, we provide a UML diagram to understand the overall structure and 
interactions between elements of the project from a visual standpoint.</p>

# Design Philosophy

The center goal of creating an image processor with capabilities to apply operations to an image was
only one aspect of this project. The other aspect(arguably more important too) was to build a
framework and design that enabled the developers to easily make modifications to the project in the
future in addition to outside developers being able to easily extend features for their own
implementations. An important step was to take a highly abstract approach to designing
implementations for what an image, operation, a pixel, etc. represent.

# Project Packages 📦

# The <code>Model</code> package

### The <code>IIMEModel</code> interface

An interface to represent functions of an Image-Manipulation-Enhancement model. The functions enable
the developer/user to import/export images, apply operations to an image, and create Programmed
images.

### The <code>IStateTrackingIMEModel</code> interface

The <code>IStateTrackingIMEModel</code> interface enables the Model to track the state of images
using a history. This interface offers useful methods such as <code>undo()</code>/<code>
redo()</code>,
<code>save()</code>, and <code>getImage()</code>. These methods allow the implementation to track
the state of images and to save and revert changes.

### The <code>StateTrackingModelImpl</code> class

The implementation of the Model class. The class implements the methods for applying operations,
saving image states, undoing/redoing operations, and importing/exporting images. The class applies
operations using the <code>IOperation</code> interface, and in order to implement a new operation,
an extension of the <code>IOperation</code> is all that's needed. Thus, the model can remain
unchanged while new operations are added.

### The <code>IMultiLayerModel</code> package

The <code>IMultiLayerModel</code> interface is an extension of the
<code>IStateTrackingIMEModel</code> interface. It also offers the ability to maintain multiple
layers for a group of images to be edited separately. The interface offers methods to edit a layer's
image, to add/remove/swap and get layers. It also provides functionality to set the current working
layer.

### The <code>MultiLayerModelImpl</code> class

The implementation of the MultiLayerModel interface. This class implements the interface's methods
and offers its use to the user.

## The <code>Matrix</code> package

### <code>IMatrix</code> Interface

The <code>IMatrix</code> interface is a generically typed interface that enables the developer to
create implementations of a Matrix using any Object or 'type'. The interface offers clever and
useful methods such as <code>map()</code> to map a function to all the values in a Matrix, <code>
fillWith(X entry)</code> to fill an entire Matrix with a single entry, as well as <code>
elementWiseOperation
()</code> to apply a BiFunction with values from 2 given Matrices.

### <code>AMatrix</code> Abstract class

The <code>AMatrix</code> class is abstract and implements many of the Matrix methods that are
universal to all Matrices regardless of the type of entries such as <code>getElement(int row, int
col)</code>, etc.

### <code>MatrixImpl</code> Class

Our implementation of a Matrix with a generic type. The class offers methods to build a Matrix in
multiple ways with its constructors.

## The <code>fileformat</code> package

### The <code>IFileFormat</code> interface

Provides a flexible way to adapt the import/export functionalities from the model. This interface's
implementations act as function objects with signatures
<ul>
<li>
<code>String -> I</code> for imports, where <code>I</code>
represents some representation of an image, and the given <code>String</code> represents the path
to import the file from</li>
<li>
<code>String, I -> File</code>, where again <code>I</code>
represents some representation of an image, and the given <code>String</code> represents the path
to import the file from.
</li>
</ul>
The advantage to this design is that if we want to import and export to new file types,
we can just add a class that implements this interface.

### The <code>PPMFile</code> class

Our implementation of the <code>IFileFormat</code> interface. Provides the ability to import from a
PPM file and export to a PPM file. When importing, this class checks that the imported image is an
actual PPM file ending in the  ".ppm" extension, and when exporting, this class always adds ".ppm"
to the end of the exported file.

## The <code>Image</code> package

### <code>IImage</code> Interface

The <code>IImage</code> interface is a representation of an image. It offers methods that an Image
class can use for manipulation and observing. The interface offers a method to get the Matrix of
pixels and to create a copy of the image.

### <code>ImageImpl</code> Class

Our implementation of an <code>IImage</code>. It offers being able to create an Image object with a
Matrix of pixels or a nested Arraylist of pixels. The class also offers methods to observe
properties of the Image such as <code>copy()</code>, <code>equals()</code>,
<code>getPixelArray()</code>, etc.

## The <code>operation</code> package

### The <code>IOperation</code> interface

The interface that all operations will implement. It offers the basic functionality of applying an
operation to an image.

### The <code>IFilter</code> interface

The interface for all Filtering Operations. Offers methods for Filters to utilize such as applying
to all or a single color channel.

### The <code>AFilter</code> abstract class

The abstract class to abstract methods that all filters will extend. This class deals with the heavy
lifting for a convolution approach to applying a kernel matrix to every pixel in the image. The
abstract class dynamically implements the kernel to the image depending on its size.

### The <code>ImageBlur</code> and <code>Sharpening</code> class

Implementations of <code>AFilter</code>, the classes provide their respective kernels, and the
abstract class handles their application. This implementation allows us to easily add new filters by
simply creating a new class to extend <code>AFilter</code>, and then implementing a method to
provide its respective kernel.

### The <code>IColorTransform</code> interface

The interface for all Color Transform operations. Offers methods for a color transform to use such
as applying to all channels at once.

### The <code>AColorTransform</code> abstract class

The abstract class to abstract methods that all color transforms will extend. This class deals with
the convolution for applying a matrix multiplication to each channel for every pixel. The abstract
class dynamically implements the kernel to the image depending on its size.

### The <code>Greyscale</code> and <code>Sepia</code> class

Implementations of <code>AColorTransform</code>, the classes provide their respective kernels, and
the abstract class handles their application. This implementation allows us to easily add new color
transformations by simply creating a new class to extend <code>AColorTransform</code>, and then
implementing a method to provide its respective kernel.

## The <code>Pixel</code> package

### The <code>IPixel</code> interface

The interface to represent the implementation for a pixel. The interface offers the observers for
the pixel's implementation.

### The <code>PixelImpl</code> class

The implementation for an <code>IPixel</code>. The class utilizes a <code>Channel</code> object to
represent the color channels for a pixel. The class implements the interface's methods and also
offers the overriden methods: <code>equals()</code>, <code>hashCode()</code>, and
<code>toString()</code>.

## The <code>Channel</code> package

### The <code>IChannel</code> interface

The interface to represent a color channel. A color channel has an intensity value that is enforced
to be within the range <code>[0,255]</code>, in addition to the channel's label: such as RED,GREEN,
and BLUE.

### The <code>ChannelImpl</code> class

The implementation of the <code>IChannel</code> interface. Implements the methods from the interface
to observe the properties of the Channel. The class also enforces the range for the intensity of the
channel within the constructor.

## The <code>ProgrammaticImages</code> package

### The <code>IProgamImage</code> interface

The interface for all programmatic images. Offers a construct method for subclasses to build their
programmatic images.

### The <code>Noise</code>,<code>PureNoise</code>, <code>RainbowNoise</code>, <code>BWNoise</code>, and the <code>Checkerboard</code> class

The subclasses to represent a specific programmatic image. Each class constructs its programmatic
image with a unit size, specified in the <code>setProgrammaticImage()</code> method in the model
class.

# The <code>View</code> Package

## The <code>IMEView</code> interface

The <code>IMEView</code> interface offers the ability to render a textual view for the model in use.
The interface offers functionality to render the layers and to write to the view with a given input
string.

## The <code>TextualIMEView</code> class

The textual implementation of the view interface. The class implements the methods and is used for
the final program.

## The <code>GUIView</code> class

The Graphical implementation of the view interface. This class hanles the construction and
organization of the graphical user interface for the IME. The class also extends JFrame to build the
GUI itself.

## The <code>GUITheme</code> class

A class to build a color theme for the GUI, supports many color themes like: Dark Theme, Light
Theme, Matrix Theme(green and black), and a Retro Theme(red and blue). The class offers support to
easily add new color themes.

# The <code>Controller</code> Package

## The <code>IMultiLayerIMEController</code> interface

The <code>IMultiLayerIMEController</code> interface offers the functionality to run a program
interacting with the model and the view. The implementation of this class will need to accept user
input in order to perform actions in the model and the view.

## The <code>MultiLayerIMEControllerImpl</code> class

The implementation of the controller. The controller initializes commands it has access to via
classes which contain the implementation of each command that the model offers. The controller
executes the commands by delegating the action through the command's class.

## The <code>Commands</code> package

## The <code>Text Commands</code> package

### The <code>IIMECommand</code> interface

The interface for all commands for an <code>IMEModel</code> interface. This interface offers the
functionality to execute a command given a user input. The implementations of this interface will
handle their specific command for the model and perform it with user input.

### The <code>AIMECommand</code> abstract class

An abstract class for the interface to assist handling the arguments given by the user. The abstract
class declares an abstract method <code>handleArgs</code> that each extended subclass will need to
implement.

### The <code>APortCommand</code> abstract class

An abstract class for the import/export commands. The class provides a <code>HashMap</code> of
strings to <code>IFileFormat</code> objects for import/export to utilize.

### The <code>ApplyCommand</code> class

Command for applying an operation to an image. The class is capable of applying multiple operations
in succession utilizing a list of given <code>IOperation</code>.

### The <code>CurrentCommand</code> class

Command for setting the current layer given a layer index.

### The <code>DeleteCommand</code> class
Command for deleting a layer given a layer index.
### The <code>ImportCommand</code> class
Command for importing an image. Extends the <code>APortCommand</code> abstract class to gain 
access to the <code>IFileFormat</code> HashMap. 
### The <code>ExportCommand</code> class
Command for exporting an image. Extends the <code>APortCommand</code> abstract class to gain
access to the <code>IFileFormat</code> HashMap.
### The <code>NewLayerCommand</code> class
Command for creating a new layer with an empty image at first.
### The <code>ProgrammaticImageCommand</code> class
Command for creating a Programmatic Image given parameters for the size and type of image. 
Offers the creation of: checkerboard, pure noise, black and white noise, and rainbow noise images.
### The <code>UndoCommand</code> class
Command for Undoing an operation to an image. Utilizes a stack structure for Undo/Redo History, 
which only allows Undoing/Redoing after a single operation.
### The <code>RedoCommand</code> class
Command for Redoing an operation to an image. Utilizes a stack structure for Undo/Redo History, 
which only allows Undoing/Redoing after a single operation.
### The <code>SaveCommand</code> class
Command for saving an image and pushing it's state to the Undo history.
### The <code>SwapCommand</code> class
Command for swapping two layers given the two layer indices.
### The <code>VisibilityCommand</code> class
Command for toggling the Visibility of a layer given a layer index.
## The <code>GUI Commands</code> package

### The <code>IGUICommand</code> interface
Interface for all GUI Command classes to extend. Provides the <code>execute()</code> method for 
each command class to implement with their respective execution.
### The <code>AGUICommand</code> abstract class
An abstract class for <code>IGUICommand</code>. Offers the construction of the 
<code>MultiLayer</code> model and the <code>GUIView</code> frame for each command to utilize in 
their executions.

### The <code>ThemeCommand</code> class
Command for setting a new color theme to the GUI. Prompts the user to select a 
<code>GUITheme</code> to switch to.

### The <code>ANoiseCommand</code> abstract class
Abstract class for all Noise images to be constructed and output to the GUI given a specific set 
of colors gathered from user input or by the user selecting a specific noise image such as 
RainbowNoise etc.

### The <code>BWNoiseCommand</code> class
Extends the <code>ANoiseCommand</code> abstract class, and provides the abstract class the 
colors required to create a black and white noise image.
### The <code>PureNoiseCommand</code> class
Extends the <code>ANoiseCommand</code> abstract class, and provides the abstract class the
colors required to create a pure noise image.
### The <code>RainbowNoiseCommand</code> class
Extends the <code>ANoiseCommand</code> abstract class, and provides the abstract class the
colors required to create a rainbow noise image.
### The <code>CustomNoiseCommand</code> class
Extends the <code>ANoiseCommand</code> abstract class, and prompts the user to select any number 
of colors using a <code>ColorChooser</code> to create a noise image with the selected colors.
###The <code>CheckerBoardCommand</code> class
Command for drawing a Checkerboard programmatic image by prompting the user for the size of the 
image and of the squares.
### The <code>GUIMosaicCommand</code> class
Command for creating a Mosaic image of the current image by prompting the user for the number of 
seeds to use.
### The <code>DownScaleCommand</code> class
Command for downscaling all the layers. Prompts the user to enter a new size to downscale to.
### The <code>CurrentLayerCommand</code> class
Command for setting a layer as the current one. Prompts the user for enter a specific layer 
index to set the current layer to.
### The <code>CurrentLayerWithIndexCommand</code> class
Command for setting a layer as the current one by pressing the layer button.
### The <code>DeleteLayerCommand</code> class
Command for deleting a layer. Prompts the user to enter a specific layer index to delete.
### The <code>DeleteLayerWithIndexCommand</code> class
Command for deleting a layer by pressing the delete button for a specific layer.
### The <code>VisibleLayerCommand</code> class
Command for setting the selected layer as visible/invisible using the GUI checkbox.
### The <code>SwapLayersCommand</code> class
Command for swapping two layers, prompts the user to enter the two layer indices to swap.
### The <code>ImportOneCommand</code> class
Command to import an image to the current selected layer. Prompts the user to select a file 
through a file explorer.
### The <code>ExportOneCommand</code> class
Command to export the image on the current selected layer. Prompts the user to select a 
directory and path through a file explorer.
### The <code>LoadScriptCommand</code> class
Command to load a script to the script panel. Prompts the user to select a file through a file 
explorer.
### The <code>RunScriptCommand</code> class
Command to run the script written in the script panel.
### The <code>OperationCommand</code> class
Command to apply a given <code>IOperation</code> to the current selected image.
### The <code>UndoCommand</code> class
Command to Undo the last <code>IOperation</code> applied to the image. Undoing is only possible 
after a single operation.
### The <code>RedoCommand</code> class
Command to Redo the last <code>IOperation</code> applied to the image. Redoing is only possible
after an Undo has been made on a single operation.
### The <code>ViewGitHubCommand</code> class
Command to send user to the GitHub webpage.
## The <code>Utility</code> class

A class with helpful methods to be utilized throughout the program. Includes many methods to
simplify complicated methods such as enforcing an integer to be within a range, checking for
nullness, etc.

## Submitted Images

### Elmo

The elmo photo was downloaded from google images at
this <a href="https://img.wattpad.com/3f59778a0de8b29ab8f459d9a61b1aceee4d441b/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f776174747061642d6d656469612d736572766963652f53746f7279496d6167652f7466664e446f65356f4b6f6f67513d3d2d3931303631323331362e313631633236643530326334653732653735343035383534303739302e6a7067?s=fit&w=720&h=720">
link.</a>

### Owl

The owl photo was downloaded from google images at
this <a href="https://bforblogging.com/wp-content/uploads/2019/03/download-copyright-free-images.jpg">
link.</a>

### CheckerBoard

This is a programmatic image created by the model utilizing the <code>Checkerboard</code> class.

### PureNoise

This is a programmatic image created by the model utilizing the <code>PureNoise</code> class.

### UML Class Diagram

The class diagram for the project, depicting the extensions and class implementations in a graphical
view. The diagram also shows the methods that each class contains.
![UMLDiagram](https://github.com/Sharwin24/Image-Manipulation-and-Enhancement/blob/main/res/IME-UML.png)





## How to use the Program <a name="help-section"></a>
# USEME Table of Contents
1. [How to use the GUI](#gui)
2. [How to write scripts](#scripts)

## How to use the GUI <a name="gui"></a>
<p>In the <code>GUIController</code> and <code>GUIView</code> classes, support is added for a 
Graphical User Interface (GUI) that supports all of the functionality of the model, as well as some 
extra features. Almost all functionality is exposed through dropdown menus, while some functionality
is also exposed through on-screen buttons, interactive text areas, or a combination.</p>
<p>In general, interactable elements such as menu items and buttons that have
"...", such as the button <code>Foo...</code>
indicate that this action will require extra input from the user, usually obtained through a popup 
window. The following 
provides a brief summary of all of the functionalities of the GUI and how they can be used:</p>
<p>
<ul>
 <li>
<b>The <i>File</i> menu</b>
<ul>
<li>
The <i>Import</i> submenu</li>
<ul>
<li><i>Import a multi-layered image</i> imports a group of layers--JPG/JPEG, PNG, or PPM files
from a file explorer/finder (for Windows/MacOS)-- referenced in a text file
that contains the absolute path of all of the images that are to be imported, one on each line.
An example is a file <i>images.txt</i> with contents 
<code>C:\Users\Joe\JoesImages\image1.png</code> <br> 
<code>C:\Users\Sally\SallysImages\dogImage.jpg</code> <br>
<code>D:\FancyBear\Images\russianFlag.ppm</code> <br> 
<code>C:\Sys\Images\Logo.jpeg</code></li>
<li><i>import one image</i> imports one JPG/JPEG, PNG, or PPM file 
from a file explorer/finder (for Windows/MacOS)</li>
</ul>
<li>The <i>Export</i> submenu</li> <ul>
<li><i>Export one image</i> exports the currently visibile layer to a given destination
via a file explorer/finder (for Windows/MacOS), as a PPM, PNG, or JPEG/PNG file</li>
<li>
<i>Export a multi-layered image</i> exports all **visible** layers to a folder with 
a name specified by the user. This folder contains a text file like the one describe in the 
<i>import all</i> section, as well as the images themselves.</li></ul></ul>
</li>
<li>The <i>Edit</i> menu</li>
<ul>
<li><i>Undo</i> undoes the most recent transformation applied to the current layer (see <i>
Transformations menu</i>).</li>
<li><i>Redo</i> redoes the most recent transformation undone from the current layer (see <i>
Transformations menu</i>).</li>
<li><i>New Layer</i> creates a new layer that comes after all of the preexisting layers. This layer
is visible and contains no image. Note that this layer is not set as the current, just created.</li>
<li><i>Set current layer...</i> asks the user to enter the layer number to switch to, and sets that 
layer as the current working layer.</li>
<li><i>Delete layer...</i> asks the user to enter the layer number to delete, and deletes that 
layer.</li></ul>

<li>The <i>Transformations</i> menu</li>
<ul>
<li><i>Sepia</i> applies a sepia filter to the current working layer.</li>
<li><i>Greyscale</i> applies a greyscale filter to the current working layer.</li>
<li><i>Blur</i> applies a blur filter to the current working layer.</li>
<li><i>Sharpen</i> applies a sharpen filter to the current working layer.</li>
<li><i>Mosaic...</i> asks the user to enter the number of seeds to mosaic the image with and then 
applies a mosaic filter to the current working layer with that number of seeds. The number of
seeds indicates how large 'clusters', or the stained-glass-looking pieces that result from the 
mosaic filter should be. A higher seed number will result in smaller, more ubiquitous clusters, 
while a smaller seed number will result in larger clusters that are fewer in size.</li>
<li><i>Downscale...</i> asks the user to enter a new width and height (smaller than the current width 
 height) to set the new dimensions as. This transformation is then applied to **all** layers that
are being worked on, since all layers must be of the same size.</li>
</ul>

<li>The <i>Programmatic images</i> menu</li>
<ul>
<li><i>Checkerboard...</i> asks the user for a width, height, and square size (all in pixels) for 
for the checkerboard image and then displays the checkerboard image on the current working layer.</li>
<li>The <i>Noise</i> submenu: all commands in this menu ask the user for the width and 
height of the noise image to be created, then display that noise image on the current working layer.
<ul>
<li><i>Pure noise...</i> creates a noise image where each pixel's color is completely randomized.
</li>
<li><i>Black and white noise...</i> creates a noise image where each pixel is either black or white.
</li>
<li>
<i>Rainbow noise...</i> creates a noise image where each pixel is either red, orange, yellow, 
green, blue, indigo, or violet--the colors of the rainbow.</li>
<li><i>Custom...</i> opens a color picker and allows the user to choose as many colors as they want
to be included in a noise image. Once the user is done picking colors, their specified noise image
is generated.</li></ul></li>
</ul>

<li>The <i>Theme</i> menu sets a color theme for the GUI. Currently, there are
four themes: <i>Light, Dark, Matrix, </i> and <i>Retro</i>. Try them out!</li>

<li>The <i>Help</i> menu provides resources for how to use the program. Currently, the
<i>View GitHub Source</i> item links to the README and USEME files contained in this project on 
its source on GitHub, since the repository is private. You likely opened this document using this
menu item. If you did, hello! Nice to see you here.</li>
</ul>
 </li>
 </ul></p>


## How to write scripts <a name="scripts"></a>

<p>In the <code>MultiLayerIIMEControllerImpl</code> class, inputs are processed from the a <code> Readable</code> with the following
syntax:
 <p> Each command follows this meta-syntax:
 <i><code>[CMD] [arg1] [arg2] ... [argn]</code></i>,
 where <code>[CMD]</code> is the textual representation of a command, and where
 <code>[argi]</code> is the <code>i</code>th required argument of a function of arity
 <code>n</code>. Additionally, some commands take an optional parameter, denoted with the syntax
 <code>(opt)</code>, where the use of parentheses distinguish that this parameter is optional and
 not <code>[required]</code>. Furthermore, this controller takes advantage of <code> IIMECommand</code>
 function objects associated with each textual command via a <code> Map</code> in order to easily
 support this functionality, and provide developers with an easy path to add/change the supported
 commands. Since different commands have different arities (and some take no optional paramters),
 the commands take arguments as expressed in the following table. Consider this a <i>guide</i> to
 using the commands, too</p>
 <p><b>How to read the guide:</b></p> the <i>textual command</i> shows
 what word leads off a command, and the <i>parameters</i> shows what parameters should be
 specified, where:
    <ul>
      <li>
        <code>[Type]</code> denotes a required parameter of type <code>Type</code>,
        for example, <code>[String]</code> represents a required parameter
        that is a String.
      </li>
      <li>
        <code>(Type)</code> denotes an optional parameter of type <code>Type</code>,
        for example, for parameters <code>[Integer] (Real) [Boolean]</code>,
        a valid command combination of parameters would be <code>0 12.3 true</code>,
       or <code>0 true</code>, since the <code>(Real)</code> optional parameter is, well, optional.
      </li>
      <li>
        <code>("literal")</code> and <code>["literal"]</code> denote
        an optional or required (resp.) parameter that is the literal value
        <code>literal</code>, as denoted by the quotation marks. For example,
        for parameters <code>[String] (Integer) ("5") ["hello"]</code>,
        valid combinations of parameters would be <code>Foo 2 5 hello</code>,
        <code>bar 5 hello</code>, or <code>BAZ hello</code>
      </li>
      <li>
        <code>[{t1, t2, ..., tn}]</code> and
        <code>({t1, t2, ..., tm})</code>
        denote a parameter that can be one of the literals <code>ti</code>,
        where <code>1 <= i <= n,m</code>, and <code>n != m || n == m</code>, in
        a required or optional parameter (resp.). For example, for parameters
        <code>[Character] ({1, 2, 3}) [{Foo, Bar, Baz, Quux}]</code>,
        a valid combination of parameters is <code>A 2 Quux</code>,
        <code>u Baz</code>, but not <code>f 4 Hello</code>, since
        "4" and "Hello" are not enumerated in
        <code>({1, 2, 3})</code> and <code>[{Foo, Bar, Baz, Quux}]</code>, (
        resp.)
      </li>
    </ul>

   <ul>
     <ul>
       <li>
       <b>Textual command:</b> <code>apply</code>
       </li>
       <ul>
         <li>
           <b>Arity:</b> n-ary
         </li>
         <li>
           <b>Parameters:</b>
           <code>({"sepia", "greyscale", "sharpen", "blur"})
           ({"sepia", "greyscale", "sharpen", "blur"})
           ({"sepia", "greyscale", "sharpen", "blur"}) ...</code>
         </li>
         <li><b>Description:</b> applies <code>n</code>
         <code> IOperation</code>s to the current image to manipulate it,
         where <code>n</code> is a non-negative integer.</li>
       </ul>
       <li>
         <b>Textual command:</b> <code>current</code>
         </li>
         <ul>
           <li>
             <b>Arity:</b> unary
           </li>
           <li>
             <b>Parameters:</b>
             <code>[Integer]</code>
           </li>
           <li><b>Description:</b> sets the current working layer
           to the layer at the given index (indexed from 0, from left
           to right with respect to the <code> java.util.List</code> that the layer
           is stored in.</li>
         </ul>
          <li>
           <b>Textual command:</b> <code>new</code>
           </li>
           <ul>
             <li>
               <b>Arity:</b> nullary
             </li>
             <li>
               <b>Parameters:</b> *none*
           </li>
             <li><b>Description:</b> creates a new layer to store and manipulate an image
             </li>
           </ul>


 <li>
           <b>Textual command:</b> <code>delete</code>
           </li>
           <ul>
             <li>
               <b>Arity:</b> unary
             </li>
             <li>
               <b>Parameters:</b>
               <code>[Integer]</code>
             </li>
             <li><b>Description:</b> deletes the
             layer at the given index (indexed from 0, from left
             to right with respect to the <code> java.util.List</code> that the layer
             is stored in.)</li>
           </ul>



 <li>
           <b>Textual command:</b> <code>export</code>
           </li>
           <ul>
            <li>
               <b>Arity:</b> binary, with one optional parameter
             </li>
             <li>
               <b>Parameters:</b>
               <code>[{"PPM", "PNG", "JPEG"}] ("layers") [String]</code>
             </li>
             <li><b>Description:</b> exports image(s) to the file format
             specified by the first parameter. If the optional parameter
             <code>("layers")</code> is included, all layers of the multilayer
             image are exported, otherwise only the current layer is exported.
             the second required parameter denotes the name of the file that is to be exported,
             such as "myImage".</li>
           </ul>



 <li>
           <b>Textual command:</b> <code>import</code>
           </li>
           <ul>
             <li>
               <b>Arity:</b> binary, with one optional parameter
             </li>
             <li>
               <b>Parameters:</b>
                 <code>[{"PPM", "PNG", "JPEG"}] ("layers") [String]</code>
               </li>
               <li><b>Description:</b> imports image(s) of the file format
               specified by the first parameter. If the optional parameter
               <code>("layers")</code> is included, all layers of the multilayer
               image are imported from a file containing all of the paths
               of the images to be loaded.
               The second required parameter denotes the name of the path to the image(s)
               that will be imported, relative to the current working directory,
               such as "res/myImage.png".</li>
           </ul>



 <li>
           <b>Textual command:</b> <code>programmatic</code>
           </li>
           <ul>
             <li>
               <b>Arity:</b> quaternary
             </li>
             <li>
               <b>Parameters:</b>
               <code>[{"checkerboard", "bwnoise", "rainbownoise", "purenoise"}]
               [Integer] [Integer] [Integer]</code>
             </li>
             <li><b>Description:</b> Creates a programmatic image as specified by the literal in the first argument,
             with a width, height, and unit size in pixels specified by the second, third, and
             fourth arguments (resp.) on the current layer.</li>
           </ul>


 <li>
           <b>Textual command:</b> <code>redo</code>
           </li>
           <ul>
             <li>
               <b>Arity:</b> nullary
             </li>
             <li>
               <b>Parameters:</b> *none*
             </li>
             <li><b>Description:</b> restores the last state of the multilayer
             model before an undo command was executed, if possible.</li>
           </ul>


 <li>
           <b>Textual command:</b> <code>undo</code>
           </li>
           <ul>
             <li>
               <b>Arity:</b> nullary
             </li>
             <li>
               <b>Parameters:</b> *none*
             </li>
             <li><b>Description:</b> undoes the most recent change to the
             multilayer model, if possible.</li>
           </ul>
<li>
             <b>Textual command:</b> <code>visibility</code>
             </li>
             <ul>
               <li>
                 <b>Arity:</b> unary
               </li>
               <li>
                 <b>Parameters:</b>
                 <code>[Integer]</code>
               </li>
               <li><b>Description:</b> toggles the visibility of the layer
               indicated by the given argument, indexed from 0, left to right
               (with respect to the <code> java.util.List</code> that the layers of the model
               are stored in.</li>
             </ul>
<li>
             <b>Textual command:</b> <code>swap</code>
             </li>
             <ul>
               <li>
                 <b>Arity:</b> binary
               </li>
               <li>
                 <b>Parameters:</b>
                 <code>[Integer] [Integer]</code>
               </li>
               <li><b>Description:</b> swaps the layers at indices given by the two arguments,
               indexed from 0, from left to right (with respect to the
               <code> java.util.List</code> that the layers are stored in.</li>
             </ul>
<li>
             <b>Textual command:</b> <code>save</code>
             </li>
             <ul>
               <li>
                 <b>Arity:</b> nullary
               </li>
               <li>
                 <b>Parameters:</b> none
               </li>
               <li><b>Description:</b> saves the most recent version of the model
               to its history, meaning that an undo command will do nothing.</li>
            </ul>






   </ul>
</ul></p>

<p><b><u>RULES:</u></b>
<ul>
<li>
There is only one command per line</li>
<li>
After a valid command is processed, the rest of the line is ignored</li>
<li>Invalid commands are ignored</li></ul></p>
<p><b><u>Example Scripts:</u></b> <br>
In the <code>/scripts</code> folder there are multiple example
scripts that can be used to control the IME program. Here, we give a brief commentary of what they
do. To view these scripts without comments, see the 
<code>scripts</code> folder:
<ul>
<li><i><code>ExampleScript1</code>:</i></li>
Shows several examples of valid commands that can be executed through 
the controller and demonstrates all functionality of a multi 
layer model <br>
<code>new</code><i><small>adds a layer at index 1 (starting from 0)</small></i><br>
<code>new</code><i><small>adds a layer at index 2</small></i><br> 
<code>current 2</code><i><small>switches the current layer to layer 2</small></i> <br>
<code>programmatic bwnoise 474 270 20</code><i><small>creates a black and white noise image
with width 474,
height 270, and unit size 20 (pixels) on layer 2. These dimensions are chosen
to allow us to import the rover image which has
the same dimensions.</small></i><br>
<code>current 1</code><i><small>sets the current layer to layer 1</small></i><br> 
<code>import PPM res/rover.ppm</code><i><small>imports a ppm image of rover, the windows dog,
from the file path in the
res folder</small></i><br>
<code>delete 0</code><i><small>deletes layer 0</small></i><br>
<code>apply sepia sharpen greyscale blur sharpen</code><i><small>applies the specified filters to 
the current layer</small></i><br>
<code>export JPEG layers res/exampleLayers</code><small>exports all layers (0 and 1, since there were
originally 3 but one was deleted) to the directory
<code>exampleLayers</code> and creates a text file containing the path
to all of those images.</small><br>
<code>undo</code><small>undoes the filters applied to the current layer</small><br>
<code>redo</code><i><small>redoes the filters that were just undone</small></i> <br>
<code>redo</code><i><small>does nothing, there is nothing to redo</small></i> <br>

<code>swap 0 1</code><i><small>swaps layers 0 and 1, swapping the image of rover with the
black and white noise image</small></i> <br>
<code>delete 0</code><i><small>deletes layer 0. Now there is only one layer at index 0</small></i> <br>
<code>delete 0</code><i><small>tries to delete layer 0, will not delete since there must be at
least one layer.</small></i> <br>
<code>import JPEG layers res/exampleLayers</code><i><small>imports the images that were just exported
and deleted, restoring layers 0 and 1 to the black and white noise
and rover images</small></i> <br>
<code>delete 0</code><i><small>deletes layer 0. Now there is only one layer at index 0</small></i> <br>
<code>delete 0</code><i><small>tries to delete layer 0, will not delete since there must be at
least one layer.</small></i> <br>
<code>programmatic rainbownoise 100 100 10</code><i><small>
sets layer 0 (the only layer) to a rainbow noise image</small></i> <br>
<code>new</code><i><small>creates a new layer at index 1</small></i> <br>
<code>current 1</code><i><small>sets the current layer to index 1</small></i> <br>
<code>programmatic purenoise 100 100 10
</code><i><small>sets the image at layer 1 to a pure noise image with
the specified dimensions</small></i> <br>
<code>new
</code><i><small>creates a new layer at index 2</small></i> <br>
<code>current 2
</code><i><small>sets the current layer to the one at index 2, the new layer</small></i> <br>
<code>programmatic checkerboard 100 100 10
</code><i><small>sets the image at layer index 2 to
a programmatically created checkerboard with the given dimensions
with a square size of 10 pixels. Note that these dimensions match the
pure noise image contained in layer 1</small></i> <br>
<code>save</code><i><small>saves the most recent state to the image history</small></i> <br>
<code>visibility 1
</code><i><small>toggles the visibility of layer 1, making it invisible</small></i> <br>
<code>current 0
</code><i><small>sets the current layer to layer 0, the rainbow
noise image</small></i> <br>
<code>export PNG res/finalImage-Rover
</code><i><small>exports the rainbow noise image
at layer 1 to the specified path.</small></i> <br>
<li>
<i><code>ExampleScript2-bogus</code></i></li></li>
A script to show bad calls to commands that will not be
processed as valid commands.

<code>delete 100</code><i><small>layer 100 does not exist</i></small><br>
<code>current 12</code><i><small>layer 12 does not exist</i></small><br>
<code>new foo</code><i><small>"foo" is an invalid parameter for the <code>new</code> command and
gets ignored</i></small><br>
<code>delete anms</code><i><small>"anms" is gibberish, gets ignored</i></small><br>
<code>apply dog cat sepia</code><i><small>"cat" and "dog" are not valid operations to apply. They
get ignored and a sepia filter is a"pplied</i></small><br>
<code>apply banana</code><i><small>"banana" is an invalid operation; gets ignored
</i></small><br>
<code>save -2</code><i><small>-2 is not a valid parameter for the save command, gets ignored
</i></small><br>
<code>delete -2</code><i><small>-2 is not a valid layer to be deleted</i></small><br>
<code>export TXT notGonnaWork.txt</code><i><small>"TXT" is not a supported file format</i></small><br>
<code>import jpeg layers notGonnaWorkShouldveBeenCapitalized</code><i><small>according to the guide,
"jpeg" should be capitalized as "JPEG"</i></small><br>
<code>export PPM directoryDoesntExist</code><i><small>
directory is assumed not to exist and will throw an error</i></small><br>
<code>swap 0 2</code><i><small>layer 2 does not exist and cannot be swapped</i></small><br>
<code>visibility kkkkkkk</code><i><small>"kkkkkkk" is not a layer whose visibility can be toggled
</i></small><br>
<code>undo undo</code><i><small>cannot undo twice if only one operation has been applied
</i></small><br>
<code>redo redo redo redo</code><i><small></i>cannot redo
four times when there is only one undone operation to redo (once)</small><br>
<code>notACommand</code><i><small>not a valid textual command</i></small><br>
<code>programmatic programmatic checkerboard 400 400 12</code><i>
<small>second call to "programmatic" textual command has valid parameters,
but since only one command is allowed per line, the second instance of
"programmatic" is interpreted as the <code>[Integer]</code>
parameter and gets ignored, since "programmatic" is not an
<code>[Integer]</code></i></small><br>
<code>programmatic purenoise 12 12 312 213 1231 2312 123</code><i><small>
way too many parameters passed, everything after "312" gets ignored (<code>programmatic</code>
is a quaternary command)</i></small><br>

</ul>