.. title: Seq2seq model in Keras
.. slug: seq2seq
.. date: 2016-03-24 10:16:25 UTC+01:00
.. tags: lstm, keras, seq2seq
.. category: 
.. link: 
.. description: 
.. type: text

There are many different types of seq2seq models:

- The simplest one consists in training an LSTM to predict the next item in
  the sequence from a truncated history of previous items. An example is shown
  in the Keras examples/ directory to generate text.
- Another one `<http://papers.nips.cc/paper/5346-sequence-to-sequence-learning-with-neural-networks.pdf>`__
  rather assumes two different sequences: a source one, and a target one.
  The full input sequence is first encoded into a hidden state vector V
  by the encoding LSTM, and the decoding LSTM behaves just like the first
  seq2seq model described above to generate the output sequence item by item.
  This model may be implemented in Keras with a single LSTM, by carefully
  formatting its input observations.

  - Training can be done with the gold target sequence history, but isn't it
    better to rather use the predicted history ? Training can then not
    easily be realized with Keras.
  - When training with the predicted history, if the decoding LSTM is long,
    lots of errors will propagate, making training difficult to converge ?

- Another one `<http://arxiv.org/pdf/1406.1078.pdf>`__
  further conditions all hidden states of the decoder on the final encoding
  hidden state V. This V is the last hidden state of the encoder, passed
  through a mtrix and tanh, so we may encode it as the output of the
  encoder in Keras. But it is used, via another tanh(matrix) as the initial
  hidden state of the decoder. This is not so easy to do in Keras.
  Also, there is no way in Keras to pass the generated label as input afaik.

In the original paper for Seq2seq `<http://papers.nips.cc/paper/5346-sequence-to-sequence-learning-with-neural-networks.pdf>`__
the authors formulate the seq2seq problem as a first LSTM model that encodes the words in the source sentence
into a vector V, which is then passed to the decoding LSTM.

Note that in this paper, V is defined as being the hidden state of the LSTM, and not its output !
Similarly, V is used in the second LSTM to initialize its hidden state, and not as an input:
the inputs of the decoder LSTM are actually the predicted history, i.e., the sequence of predicted words
from 0 to t-1 is used as input to the decoder LSTM to predict the new word at t.

The generation process is then described as a dynamic programming process: the first word is predicted
given the input *StartOfSentence*, then the second word is predicted given the two inputs
(*StartOfSentence*,first word), and so on. The final solution is the best path that maximizes the product of the output probability
at every time step.
This best path could be found by a Viterbi algorithm, or an approximated beam search algorithm as used in the paper.
They even say that the greedy algorithm (with beam=1) is already very good.

In order to reproduce this model in Keras, we have to have fixed-length inputs and outputs.
This condition can be fulfilled by first padding all inputs to zero, and then predicting the first word.
In the next step, we "roll" this input buffer by successively pushing at its end the first predicted word, and then the second, and so on.

At training time, this process is the same (just replace every adjective 'predicted' with 'gold' in the previous paragraph).

Passing the hidden state from the encoder to the decode may be realized thanks to the stateful property of Keras LSTM,
but I find it easier to actually build a single LSTM that is the "concatenation" of both the encoder and decoder.
Of course, this big LSTM generates a single word per sample (so return_sequences=False), but it is compliant with the
word-by-word process described in the original paper.

Each sample does then correspond to the generation of a single word, and is composed of N+(N-1) inputs:
the N words of the source sentence, and the N-1 previous words predicted so far (or gold at training time).


