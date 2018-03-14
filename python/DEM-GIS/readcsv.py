f = open('data.csv')
all_data = []
lines = f.readlines()

for each in lines:
    each = each.strip('\n')
    all_data.append(each.split(','))


def printit(begin, j, tmp):
    print('(', end='')
    print(begin+1, j, tmp, sep=',', end='')
    print(')', end='')


def to_each_line():
    for i, row in enumerate(all_data):
        begin = 0
        tmp = all_data[i][0]
        print(i, ": ", end='', sep='')
        for j, col in enumerate(row):
            if col != tmp:
                printit(begin, j - 1, tmp)
                begin = j
                tmp = col
            if j == len(row) - 1:
                printit(begin, j, tmp)
        print()


to_each_line()
