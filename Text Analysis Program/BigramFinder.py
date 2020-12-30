import csv
import re
import nltk
from nltk.corpus import wordnet
from nltk.collocations import *
from IndividualLDA import *
from BuildData import *


def iterator(index):
    labels = ['ID', 'Name', 'Date', 'topicName', 'scrubbedtext']
    podKnow_Data = pd.DataFrame.from_records(results, columns=labels)

    # isolate scrubbed text values and convert to lowercase to avoid duplicates
    scrubbedData = str(podKnow_Data.iloc[index - 1:index, 4].values).lower()

    # remove junk values
    final_list = [re.sub(r'[^a-zA-Z]', '', i) for i in str(scrubbedData).split()]

    # filter to words that are English words
    final_list = [word for word in simple_preprocess(str(final_list)) if wordnet.synsets(word)]

    tokens = nltk.word_tokenize(' '.join(final_list))

    bigram_measures = nltk.collocations.BigramAssocMeasures()

    finder = BigramCollocationFinder.from_words(tokens)

    # only accepts words 3 letters or longer
    finder.apply_word_filter(lambda w: len(w) <= 2)

    scored = finder.score_ngrams(bigram_measures.likelihood_ratio)

    # sort by highest likelihood ratio
    bigrams = sorted(scored, key=lambda t: (-t[1], t[0]))

    return bigrams




def excelWriter(bigrams, fileName):
    # make csv file name the same as transcript file
    fileName = fileName.replace(".txt", ".csv")

    with open(os.path.join(bigramLocation, fileName), 'w', newline='') as newCsv:
        writer = csv.writer(newCsv)

        for a, b in bigrams:
            # clean junk values
            a = [re.sub(r'[^a-zA-Z]', '', i) for i in str(a).split()]

            # writes first word to column A, second word to column B, likelihood to column C
            writer.writerow([a[0], a[1], b])



results = []
totalList = []


def bigramDriver():
    counter = 0
    # driver code block that loops through every file in a folder
    for folderName, subfolders, fileName in os.walk(textLocation):

        for file in fileName:
            f = open(os.path.join(folderName, file), 'rb')
            data = f.read()

            rows = ("", "", "", "", data)
            results.append(rows)

            counter = counter + 1

            # gets scrubbed text bigrams for a given counter index
            bigrams = iterator(counter)

            excelWriter(bigrams, file)
