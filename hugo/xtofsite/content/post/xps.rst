+++
title = "Experiments in scientific papers are just wrong"
date = "2016-07-03T18:02:45+02:00"
markup = "rst"
description = ""
+++

99% of all experimental results reported in scientific papers are just plain wrong.

There are several reasons that support this claim, but I'll just discuss the first of them here.
It's actually very simple, and we all know very well about it; it's just that nobody really cares.
So, here is the issue:

You can only run a *single* experiment on a given test set.
I mean, really just one experiment on your whole life time.
You must never, ever, run a second experiment on the same test.
In other words, once you have computed one accuracy value (or F-measure, or whatever metric you like)
on one test set, your have "burnt" your test set, and you must delete it immediately so that you can never
reuse it again.
Otherwise, your second and future accuracies on this test set are just plain wrong: you unconsciously
tune your systems on the test set; not that much, of course, but you're really doing system selection
on the test set.

Of course, in every paper, we honestly claim to tune hyper-parameters on some development corpus.
But is it really the first time in our life that we have downloaded this test corpus ?
Do we really delete the corpus forever once we have run a unique experiment ?

The problem is that nowadays, we heavily rely on experimental evaluation to compare systems (and
incidentaly accept or reject papers).
Beside this issue related to the test corpus, there are many other potential pitfalls in such comparisons:
Number of parameters ?
Are all parameters really used efficiently ?
Does training really converge to a good optimum for each model ?
How many time and efforts have been dedicated to improve a given model with respect to another ?
All these issues really make model comparisons awkward.

As scientists, we are all very well aware of these issues.
But wouldn't it be worse to not compare models at all ?

I believe it's best to at least try and experimentally compare models, but it's also important to
repeat again and again, as I do here, that no experimental result is perfect and that they must
always be interpreted with the greatest care.

In this regard, evaluation campaigns looks appealing, because they are designed so that it's much harder
to run some experiment several times on the test corpus. But it's a lot of efforts to invest for a single
one-time evaluation shot, and from my experience, evaluation campaigns are often plaged with their own
issues, in particular, is the test corpus really totally new ? It's not always the case in practice.

So what next ?

Maybe we should consider new paradigms for evaluation: for instance, good models are the ones that tend to
reveal themselves efficient in many different situations, when tested by many different researchers
over a long period of time.
Of course, such evaluations over the long run are incompatible with nowadays paradigm of publishing several
papers per year. But may be the source of the problem is really this exponential course for publications that prevent
researchers from correctly running long-term evaluations, and the real solution probably lies in changing
the way researchers are evaluated, and not our models.

