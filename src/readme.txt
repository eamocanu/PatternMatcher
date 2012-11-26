An experiment in pattern matching with neurons.

Run MainUi.java from package main.

Enter characters in the input box and see how the neurons change their dendrite values to match the input. Observe their learning process.

Input characters allowed: only 'a'-'z' or 'A'-'Z'


Future work is to leverage this code and apply it to image recognition.



When you first start the UI, you see something like this:
https://github.com/eamocanu/PatternMatcher/blob/master/snaps/snap0002.png

6 neurons and an input text.
At black box at the top of each neuron shows how well the entire neuron matches current input. If the box is white, a neuron matches the input perfectly, if it is black those neurons have the lowest match.

Underneath, each dendrite has a similar box associated with it, which shows how well each dendrite matches current input. 
