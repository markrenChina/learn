package sort

import java.util.concurrent.ThreadLocalRandom

fun quickSort(array: Array<Int>) {
    quickSort(array,0,array.size -1)
}

tailrec fun quickSort(array: Array<Int>, left:Int, right : Int){
    if (left > right) {
        return
    }
    val pos = partition(array, left, right)
    quickSort(array, left, pos - 1)
    quickSort(array,pos + 1,right)
}

fun partition(array: Array<Int>, left: Int, right: Int):Int {
    val random = ThreadLocalRandom.current()
    swap(array,left,random.nextInt(left,right+1))
    val v = array[left]
    var pos = left
    for (i in left+1..right){
        if (v > array[i]){
            pos++
            swap(array,pos,i)
        }
    }
    swap(array,left,pos)
    return pos
}
fun quickSort3way(array: Array<Int>) {
    quickSort3way(array,0,array.size -1)
}

fun quickSort3way(array: Array<Int>, left: Int, right: Int) {
    val random = ThreadLocalRandom.current()
    swap(array,left,random.nextInt(left,right+1))
    val v = array[left]
    var lessPoint = left
    var greaterPoint = right+1
    var i = left + 1
    while (greaterPoint > i) {
        if (array[i] > v) {
            greaterPoint--
            swap(array,i,greaterPoint)
        } else if(array[i] < v) {
            lessPoint++;
            swap(array,i,lessPoint)
            ++i
        } else {
            ++i
        }
    }
    swap(array,left,lessPoint)
    quickSort(array, left, lessPoint-1)
    quickSort(array,greaterPoint,right);
}