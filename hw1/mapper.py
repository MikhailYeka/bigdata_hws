#!/usr/bin/env python
"""mapper.py"""
import sys

EXIT_SEPARATOR = ' '
PRICE_COLUMN_NUMBER = 9
SEPARATOR = ','


def main():
    chunk_size = 0
    chunk_sum = 0
    chunk_squares_sum = 0
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
        chunk_sum += price
        chunk_squares_sum += price ** 2
        chunk_size += 1

    chunk_mean = chunk_sum / chunk_size
    chunk_var = chunk_squares_sum / chunk_size - chunk_mean ** 2
    return EXIT_SEPARATOR.join(str(i) for i in [chunk_size, chunk_mean, chunk_var])


if __name__ == '_main__':
    print(main())
