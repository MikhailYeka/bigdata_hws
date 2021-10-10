#!/usr/bin/env python
"""reducer.py"""

import sys


SEPARATOR = ' '

def main():
    # input comes from STDIN
    size_total = 0
    mean_total = 0
    nu_total = 0
    for line in sys.stdin:
        chunk_size, chunk_mean, chunk_var = line.split(SEPARATOR)
        chunk_size = int(chunk_size)
        chunk_mean = float(chunk_mean)
        chunk_var = float(chunk_var)

        denominator = chunk_size + size_total
        nu_1 = (chunk_size * chunk_mean + size_total * nu_total) / denominator
        nu_2 = chunk_size * size_total * ((mean_total - chunk_mean) / denominator) ** 2

        size_total += chunk_size
        nu_total = nu_1 + nu_2

    return nu_total


if __name__ == '__main__':
    print(main())
