#!/usr/bin/env python
"""mapper.py"""
import sys

import numpy as np


EXIT_SEPARATOR = ' '
PRICE_COLUMN_NUMBER = 9
SEPARATOR = ','


def main():
    array = []
    # input comes from STDIN (standard input)
    for line in sys.stdin:
        # remove leading and trailing whitespace
        line = line.strip('\t').split(SEPARATOR)
        if len(line) < PRICE_COLUMN_NUMBER:
            continue
        price = line[PRICE_COLUMN_NUMBER]
        try:
            price = float(price)
        except ValueError:
            continue
        array.append(price)

    chunk_size = len(array)
    chunk_mean = np.mean(array)
    chunk_var = np.var(array)
    return EXIT_SEPARATOR.join(str(i) for i in [chunk_size, chunk_mean, chunk_var])


if __name__ == '_main__':
    print(main())
