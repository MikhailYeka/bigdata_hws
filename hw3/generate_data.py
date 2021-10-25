import random 

from sklearn.model_selection import train_test_split
import pandas as pd
import numpy as np


NUM_POINTS = 100_000


df = pd.DataFrame({'x_0': np.linspace(0, 10, NUM_POINTS)})

for i in range(1, 10):
	start_point = random.randint(0, 1000)
	X = np.linspace(start_point, start_point+100, num=NUM_POINTS)
	df = df.merge(pd.DataFrame({f'x_{i}': X}), how='right', left_index=True, right_index=True)

print(df.shape)
coefs = np.array([100., -23., 132., -109., 28., 10.4, 983., -47., 32., 653.])

rand_step = 0.1
df['y'] = coefs @ df.to_numpy().T
df['y'] += random.uniform(-rand_step, rand_step)


train_index, test_index = train_test_split(df.index, test_size=0.1)

df.loc[train_index].to_csv('train.csv', index=False)
df.loc[test_index].to_csv('test.csv', index=False)

