







# Table of Contents
1. [How to write scripts](#example)
2. [How to use the GUI](#example2)
3. [Third Example](#third-example)
4. [Fourth Example](#fourth-examplehttpwwwfourthexamplecom)


## How to write scripts
<p><u><b>How to write scripts for Image Manipulation and Enhancment</b></u>

In this controller, inputs are processed from the mentioned <code> Readable</code> with the following
syntax:
 <p> Each command follows this meta-structure:
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
## How to use the GUI
## Third Example
## [Fourth Example](http://www.fourthexample.com) 

