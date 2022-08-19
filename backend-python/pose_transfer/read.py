import csv

f = open('fashion_data/fasion-resize-pairs-test.csv', 'w', encoding='utf-8')
wr = csv.writer(f)
wr.writerow(["from", "to"])
wr.writerow(["test.jpg", "output1"])
f.close()