---
categories: General Stuff
date: 2010/06/29 15:25:00
title: Naive Bayes and linguistic features
---

When training supervised Machine Learning systems on text corpora,
all features have a well-defined role: helping the model to predict the
gold labels. Discriminative methods are pretty good for that.

But in the unsupervised case, features have quite a different role, because
there is not any known labels to infer any more.
Training (inference) then boils down optimizing the posterior, i.e.,
the probabilities of the latent variable given the features you defined.
This can be viewed as a kind of clustering, where the model will try to
cluster the data into classes that each represent a particular value of the
given features. In other words, the features are now very important, because
the resulting clustering will entirely depend on them:
If you define irrelevant features in the supervised case, then the model will
most likely just not consider at all, but with an unsupervised model,
it will just try to exhibit latent structures in the data that match such features.

