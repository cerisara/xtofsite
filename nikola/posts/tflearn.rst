.. title: Comparison of Keras vs. TFlearn
.. slug: tflearn
.. date: 2016-04-04 10:16:25 UTC+01:00
.. tags: lstm, keras, tflearn, seq2seq
.. category: 
.. link: 
.. description: 
.. type: text

TFLearn is a new abstract wrapper around Tensorflow.
I find it actually very similar to Keras, except that TFLearn only supports Tensorflow.
This is both good and bad, because it might be easier to bypass this abstraction layer and directly
code in Tensorflow; but well, you're stuck to TF.

So I tested for one of my classification task a simple LSTM in both Keras and Tensorflow.
Here's the code for the model both in Keras and TFLearn::

    if False:
        # TFLearn code
        g=tflearn.input_data([None,maxlen,len(char2id)])
        g=tflearn.lstm(g,nhid)
        g=tflearn.fully_connected(g,len(da2id), activation='softmax')
        g=tflearn.regression(g,optimizer='adam', loss='categorical_crossentropy')

        model = tflearn.DNN(g, clip_gradients=0., tensorboard_verbose=0)
        model.fit(Xtr, Ytr, validation_set=(Xte,Yte), n_epoch=10, show_metric=True, batch_size=128)
    else:
        # Keras code
        model = Sequential()
        model.add(LSTM(nhid, input_shape=(maxlen, nchars), go_backwards=False, return_sequences=False))
        model.add(Dense(len(da2id)))
        model.add(Activation('softmax'))
        model.compile(loss='categorical_crossentropy', optimizer='adam')
        model.fit(Xtr,Ytr,nb_epoch=10,validation_data=(Xte,Yte),show_accuracy=True)


Of course, I've used the Tensorflow backend for Keras.

As you can see, both codes are very similar !
Furthermore, the input and output tensors are exactly the same in both cases, which is
nice because it's thus very easy to compare them.

Both pieces of code run at approximately the same speed.
In terms of accuracy, as you can see on the following screenshots,
They give quite comparable results, with slightly better results for Keras.

Output from TFlearn::

    Training samples: 104564
    Validation samples: 2207
    --
    Training Step: 817  | total loss: 1.34866
    | Adam | epoch: 001 | loss: 1.34866 - acc: 0.6172 | val_loss: 1.48790 - val_acc: 0.5221 -- iter: 000000/104564
    --
    Training Step: 1634  | total loss: 1.24601
    | Adam | epoch: 002 | loss: 1.24601 - acc: 0.6445 | val_loss: 1.38776 - val_acc: 0.5452 -- iter: 000000/104564
    --
    Training Step: 2451  | total loss: 1.25703
    | Adam | epoch: 003 | loss: 1.25703 - acc: 0.6359 | val_loss: 1.35823 - val_acc: 0.5452 -- iter: 000000/104564
    --
    Training Step: 3268  | total loss: 1.21172
    | Adam | epoch: 004 | loss: 1.21172 - acc: 0.6435 | val_loss: 1.31693 - val_acc: 0.5470 -- iter: 000000/104564
    --
    Training Step: 4085  | total loss: 1.18737
    | Adam | epoch: 005 | loss: 1.18737 - acc: 0.6375 | val_loss: 1.27222 - val_acc: 0.5478 -- iter: 000000/104564
    --
    Training Step: 4902  | total loss: 1.15483
    | Adam | epoch: 006 | loss: 1.15483 - acc: 0.6420 | val_loss: 1.25492 - val_acc: 0.5501 -- iter: 000000/104564
    --
    Training Step: 5719  | total loss: 1.09600
    | Adam | epoch: 007 | loss: 1.09600 - acc: 0.6510 | val_loss: 1.23394 - val_acc: 0.5538 -- iter: 000000/104564
    --
    Training Step: 6536  | total loss: 1.10121
    | Adam | epoch: 008 | loss: 1.10121 - acc: 0.6576 | val_loss: 1.24449 - val_acc: 0.5608 -- iter: 000000/104564
    --
    Training Step: 7353  | total loss: 1.10770
    | Adam | epoch: 009 | loss: 1.10770 - acc: 0.6427 | val_loss: 1.17806 - val_acc: 0.5846 -- iter: 000000/104564
    --
    Training Step: 8170  | total loss: 1.06616
    | Adam | epoch: 010 | loss: 1.06616 - acc: 0.6594 | val_loss: 1.16912 - val_acc: 0.5816 -- iter: 000000/104564

Output from Keras::

    Train on 104564 samples, validate on 2207 samples
    Epoch 1/10
    104564/104564 [==============================] - 38s - loss: 1.3849 - acc: 0.6192 - val_loss: 1.3353 - val_acc: 0.5478
    Epoch 2/10
    104564/104564 [==============================] - 38s - loss: 1.1533 - acc: 0.6468 - val_loss: 1.2243 - val_acc: 0.5741
    Epoch 3/10
    104564/104564 [==============================] - 38s - loss: 1.0937 - acc: 0.6581 - val_loss: 1.2146 - val_acc: 0.5696
    Epoch 4/10
    104564/104564 [==============================] - 38s - loss: 1.0583 - acc: 0.6637 - val_loss: 1.1613 - val_acc: 0.5863
    Epoch 5/10
    104564/104564 [==============================] - 38s - loss: 1.0369 - acc: 0.6685 - val_loss: 1.1848 - val_acc: 0.5872
    Epoch 6/10
    104564/104564 [==============================] - 38s - loss: 1.0219 - acc: 0.6717 - val_loss: 1.1340 - val_acc: 0.5922
    Epoch 7/10
    104564/104564 [==============================] - 38s - loss: 1.0066 - acc: 0.6752 - val_loss: 1.1303 - val_acc: 0.5949
    Epoch 8/10
    104564/104564 [==============================] - 38s - loss: 0.9965 - acc: 0.6774 - val_loss: 1.1235 - val_acc: 0.5967
    Epoch 9/10
    104564/104564 [==============================] - 38s - loss: 0.9867 - acc: 0.6788 - val_loss: 1.1135 - val_acc: 0.5995
    Epoch 10/10
    104564/104564 [==============================] - 38s - loss: 0.9781 - acc: 0.6809 - val_loss: 1.1198 - val_acc: 0.6044


Disclaimer: this comparison is not reproducible, and is not meant to be, because
it relies on non-free data. But the code is so simple that you can reproduce this
experiment in less than 5 minutes with any dataset !
The results must be interpreted with caution, because they might be specific to these
particular experimental conditions, and may be very different in other conditions.
Note that no parameter has been tuned at all; results may also be very
different if parameters were tuned.

