package sort

fun shellInsertSort(array: Array<Int>){
//    //分组 步长
//    var increment = array.size/2 //步长
//
//    var k = 0;
//    while (increment >= 1) {
//        //希尔排序
//        for (i in array.indices) {
//            for (j in i+increment until  array.size step increment){
//                val temp = array[i]
//                k = j
//                while (k > i){
//                    if (temp < array[k - increment]){
//                        array[k] = array[k-increment] //原地移动
//                    }else {
//                        break
//                    }
//                    k -= increment
//                }
//                array[k] = temp
//            }
//        }
//        increment /= 2
//    }

    var gap = array.size / 2
    while (1 <= gap) {
        for (i in gap until array.size) {
            var j = i - gap
            val tmp = array[i]
            while (j >= 0 && tmp < array[j]) {
                array[j + gap] = array[j]
                j -= gap
            }
            array[j + gap] = tmp
        }
        gap /= 2
    }
}