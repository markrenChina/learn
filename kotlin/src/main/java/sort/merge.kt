package sort

/**
 *---------无优化
归并 无序的 的执行时间 829 毫秒
希尔 无序的 的执行时间 1700 毫秒
归并 接近有序的 的执行时间 496 毫秒
希尔 接近有序的 的执行时间 462 毫秒

归并 无序的 的执行时间 877 毫秒
归并 接近有序的 的执行时间 552 毫秒

 --------优化
归并 无序的 的执行时间 904 毫秒
归并 接近有序的 的执行时间 126 毫秒
归并 无序的 的执行时间 921 毫秒
归并 接近有序的 的执行时间 146 毫秒
归并 无序的 的执行时间 948 毫秒
归并 接近有序的 的执行时间 139 毫秒
 */
fun mergeSort(array: Array<Int>){
    mergeSort(array,0,array.size -1)
}

fun mergeSort(array: Array<Int>, left : Int, right : Int){
    if (left >= right){
        return
    }
    val mid = (left+right) ushr 1
    mergeSort(array,left,mid)
    mergeSort(array,mid + 1,right)
    if (array[mid] > array[mid+1]){
        mergeSort(array, left, mid, right)
    }
}

fun mergeSort(array: Array<Int>, left : Int,mid : Int, right : Int){
    var tempIndex = left - 1
    //对数组进行一次拷贝
    val temp = Array<Int>(right - left + 1){
        ++tempIndex
        array[tempIndex]
    }
    var i = left
    var j = mid + 1
    for (k in left .. right) {
        if (i > mid) {
            array[k] = temp[j - left]
            ++j
        } else if ( j > right) {
            array[k] = temp[i - left]
            ++i
        }else if (temp[i - left] < temp[j - left]){
            array[k] = temp[i - left]
            i++
        } else {
            array[k] = temp[j - left]
            j++
        }
    }
}