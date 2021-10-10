#!/usr/bin/env python
"""reducer.py"""

import sys


SEPARATOR = ' '


def main():
    # input comes from STDIN
    size_total = 0
    mean_total = 0
    var_total = 0
    for line in sys.stdin:
        chunk_size, chunk_mean, chunk_var = line.split(SEPARATOR)
        chunk_size = int(chunk_size)
        chunk_mean = float(chunk_mean)
        chunk_var = float(chunk_var)
        size_total += chunk_size
        mean_total = (chunk_size * chunk_mean + size_total * mean_total) / (chunk_size + size_total)
        size_total += chunk_size

    return mean_total


if __name__ == '__main__':
    print(main())
