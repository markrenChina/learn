package sort

//选择排序 无序的 的执行时间 245971599 纳秒
//选择排序 接近有序的 的执行时间 205054500 纳秒
fun selectSort(array: Array<Int>) {
    for (i in array.indices){
        var min = i
        for (j in i+1 until array.size){
            if (array[min] > array[j]){
                min = j
            }
        }
        if (min != i) {
            swap(array,min,i)
        }
    }
}