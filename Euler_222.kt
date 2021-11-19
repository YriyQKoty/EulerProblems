import kotlin.math.sqrt
//What is the length of the shortest pipe, of internal radius 50mm, that can fully contain 21 balls of radii 30mm, 31mm, ..., 50mm?

//Give your answer in micrometres (10-6 m) rounded to the nearest integer.

var totalLength = 0.0
val pipeRad = 50.0

fun main() {

    val minRad = 30
    val maxRad = 50
    val ballsQuantity = 21

    var ballsRadiuses = listOf<Int>()

    for (ball: Int in minRad..maxRad) {
        ballsRadiuses += ball
    }

    println("Balls radiuses: $ballsRadiuses")

    //Найгірший випадок
    worstCase(minRad*2,2,ballsQuantity)
    //Випадок послідовного підрахування, але м'ячі дотикаються почергово до різних сторін труби
    secondCase(ballsRadiuses)
    //Найркращий випадок, м'ячі дотикаються до різних сторін труби та їх порядок 50...30...49
    bestCase(ballsRadiuses)


}

//Найгірший варіант (найдовша труба) буде коли м'ячі розташовані по зменшенню/більшенню радіуса "прикріплені" до однієї сторони труби,
//Тоді маємо суму арифметичної прогресії з кроком d (різниця діаметрів між м'ячами: 100, 98, 96...60) для n м'ячів
fun worstCase(firstNum: Int,d: Int, n: Int) {
    var sum = ((2*firstNum + d*(n-1))/2)*n
    sum *= 1000
    println("Worst Case: biggest length: $sum mcm")
}

fun secondCase(balls: List<Int>) {
    //Відстаней між м'ячами на 1 менше ніж самих м'ячів
    //Оскільки аналог м'ячів в 2d просторі це коло,
    //то будемо рахувати відстані між ними,
    //сума усіх відстаней від початку першого кола до кінця останнього
    //буде довжиною труби
    var hypotenyze = 0.0
    for (index in balls.indices) {
        if (index > balls.size - 2) break;

        hypotenyze = (balls[index] + balls[index+1]).toDouble()
        totalLength += sqrt(hypotenyze*hypotenyze - (2*pipeRad - hypotenyze)*(2*pipeRad - hypotenyze))
    }

    //Додаємо радіус першого і останнього м'яча
    totalLength += balls[0] + balls[balls.size - 1]
    totalLength *=1000
    println("Second case: ${totalLength.toInt()} mcm")
}

fun bestCase(balls: List<Int>) {
    //Аналогічний підхід як і в другому випадку,
    //Однак в цей раз ми змінимо розташування м'ячів, тепер спробуємо
    //іншу послідовність: 50-48-46...32-30-31-...45-47-49
    //Отже треба змінити список на такий: 50-48-46...32-30 та 49-48-45...31-30
    var balles = (balls.filter {it % 2 == 0}).reversed() + balls.filter {it % 2 == 1}

    var hypotenyze = 0.0
    totalLength = 0.0
    for (index in balls.indices) {
        if (index > balls.size - 2) break;

        hypotenyze = (balles[index] + balles[index+1]).toDouble()
        totalLength += sqrt(hypotenyze*hypotenyze - (2*pipeRad - hypotenyze)*(2*pipeRad - hypotenyze))
    }

    //Додаємо радіус першого і останнього м'яча
    totalLength += balles[0] + balles[balles.size - 1]
    totalLength *= 1000
    println("Best case: ${totalLength.toInt()} mcm")
}
