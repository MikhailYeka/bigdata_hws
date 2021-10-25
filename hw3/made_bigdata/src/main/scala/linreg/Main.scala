package linreg

import breeze.linalg._
import breeze.numerics._
import java.io._

object Main {

  def main(args: Array[String]): Unit = {

    val fileNameTrain = "../train.csv"
    val fileNameTest = "../test.csv"

    val vec_train  = csvread(new File(fileNameTrain), skipLines = 1)
    val vec_test  = csvread(new File(fileNameTest), skipLines = 1)

    val x_train = vec_train(::, 0 to 9)
    val y_train = vec_train(::, 10)

    val x_test = vec_test(::, 0 to 9)
    val y_test = vec_test(::, 10)
// Формулу взял отсюда http://www.machinelearning.ru/wiki/index.php?title=Линейная_регрессия_%28пример%29
    val ones_vector = DenseMatrix.ones[Double](rows = x_train.rows, cols = 1)
    val A: DenseMatrix[Double] = DenseMatrix.horzcat(ones_vector, x_train)
    println(A)
    val ws0 = A.t * A
    val ws1 = inv(ws0)
    val ws2 = ws1 * A.t
    val w = ws2 * y_train
    println(w)

//    Теперь оценим, как эта формула работает на тестовых данных, и какая получилась SSE ошибка
    val ones_vector1 = DenseMatrix.ones[Double](rows = x_test.rows, cols = 1)
    val A1 = DenseMatrix.horzcat(ones_vector1, x_test)

    val y_predicted = DenseVector.zeros[Double](A1.rows)
    var sse: Double = 0

    for (i <- 0 to x_test.rows-1){
      val y_pred: Double = A1(i, ::) * w
      sse = sse + (y_pred - y_test(i)) * (y_pred - y_test(i))
      y_predicted(i) += y_pred
    }
//    Оч коряво, но не смог найти, как сделать в бризе красивее

    println(sse)

//    Сохраним результат
    csvwrite(new File("../predicted.csv"), y_predicted.asDenseMatrix)
    csvwrite(new File("../w.csv"), w.asDenseMatrix)
    csvwrite(new File("../diff_true_predicted.csv"), (y_predicted - y_test).asDenseMatrix)
  }
}
