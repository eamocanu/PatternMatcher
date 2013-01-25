An experiment in pattern matching with neurons.

Run MainUi.java from package main.

Enter characters in the input box and see how the neurons change their dendrite values to match the input. Observe their learning process.

Input characters allowed: only 'a'-'z' or 'A'-'Z'


Future work is to leverage this code and apply it to image recognition.
This is now available in package brain.model.images, however unfinished.
You will need to provide your own images if you run MainImageUi.


When you first start the UI, you see something like this:
https://github.com/eamocanu/PatternMatcher/blob/master/snaps/snap0002.png
6 neurons and an input text.

The black box at the top of each neuron shows how well the entire neuron matches current input. If the box is white, a neuron matches the input perfectly, if it is black those neurons have the lowest match.

Underneath, each dendrite has a similar box associated with it, which shows how well each dendrite matches current input. 


Output walkthrough:
Entering input TRU, the neuron that matches the most is TRUE, and it is highlighted in a blue box on the canvas (with 10% confidence and 75% match with input).
https://github.com/eamocanu/PatternMatcher/blob/master/snaps/snap0003.png

Typing in TRUE, the same neuron matches 100%, and its confidence is increased further:
https://github.com/eamocanu/PatternMatcher/blob/master/snaps/snap0004.png

Changing the input to QT, selects another neuron as best match - neuron SU
https://github.com/eamocanu/PatternMatcher/blob/master/snaps/snap0005.png

2 more tries and the neuron matches QT perfectly
https://github.com/eamocanu/PatternMatcher/blob/master/snaps/snap0006.png
However, since its confidence is still low (because it hasn't seen the input several times), it can learn other input patterns and forget this one.