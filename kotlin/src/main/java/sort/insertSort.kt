package sort

//原始的
fun insertSort (array: Array<Int>){
    for (i in array.indices) {
        for (j in i downTo 1 ){
            if (array[j] < array[j - 1]){
                swap(array,j,j-1)
            }else {
                break
            }
        }
    }
}

fun insertSort2 (array: Array<Int>){
    for (i in 1 until array.size) {
        var j = i - 1
        val tmp = array[i]
        while (j >= 0 && tmp < array[j]){
            array[j+1] = array[j]
            j -= 1
        }
        array[j+1]= tmp
    }
}