package sort


//bubbleSort 的执行时间 1667860800 纳秒  ---无序
//bubbleSort1 优化前 的执行时间 30622700 纳秒 ---有序
fun bubbleSort(array: Array<Int>){
    for (i in 0 until array.size -1){
        for (j in 0 until array.size - 1- i ){
            if (array[j] > array[j + 1]){
                swap(array,j,j+1)
            }
        }
    }
}

//bubbleSort 的执行时间 1536914000 纳秒  ---无序
//bubbleSort2 优化后 的执行时间 23183500 纳秒 ---有序
fun bubbleSort2(array: Array<Int>){
    for (i in 0 until array.size -1){
        var swaped = false
        for (j in 0 until array.size - 1- i ){
            if (array[j] > array[j + 1]){
                swap(array,j,j+1)
                swaped = true;
            }
        }
        if (!swaped){
            break
        }
    }
}

fun bubbleSort3(array: Array<Int>){
    //记录上一次最后的位置
    var last = array.size -1;
    var temp = 0
    do {
        temp = 0;
        for (i in 0 until last) {
            if (array[i] > array[i + 1]){
                swap(array,i,i+1)
                temp = i
            }
        }
        last = temp
    }while (last > 0)
}