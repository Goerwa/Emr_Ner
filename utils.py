


def get_f1score(predict,test):
    tp = 0
    fp = 0
    fn = 0
    pred = ''
    t = ''
    end_tag = ['O','E','S']
    for i in range(len(predict)):
        if predict[i][0] not in end_tag:
            pred += predict[i] + ' '
            t += test[i] + ' '
        else:
            if pred != '' and pred == t:
                tp += 1
            pred = ''
            t = ''
    for i in range(len(predict)):
        if predict[i][0] in end_tag and predict[i][0] != 'O':
            fp += 1
        if test[i][0] in end_tag and test[i][0] != 'O':
            fn += 1
    print(tp,fp,fn)
    precision = float(tp / fp)
    recall = float(tp /fn)
    f1 = (2 * precision * recall) / (precision + recall)
    return precision, recall , f1

if __name__ == '__main__':
    preds = []
    label = []
    with open('prediction.txt', 'r',encoding='utf-8') as input_data:
        for line in input_data:
            preds.append(line[:-1])
    with open('result.txt', 'r',encoding='utf-8') as input_data1:
        for line in input_data1:
            label.append(line[:-1])

    # get_f1score(preds[:100], label[:100])

    p, r, f = get_f1score(preds,label)
    print('p', p)
    print('r', r)
    print('f1', f)
    '''
    p 0.9181511787315464
    r 0.9200119205269096
    f1 0.9178320949756121
    '''