package sort

import java.util.Arrays


fun main(){
    val array1 = createBigArray(2000_000)
    val array2 = createSortBigArray(2000_000,10)
    val array1_2 = array1.clone()
    val array1_3 = array1.clone()
    val array1_4 = array1.clone()
    val array1_5 = array1.clone()
    val array2_2 = array2.clone()
    val array2_3 = array2.clone()
    val array2_4 = array2.clone()
    val array2_5 = array2.clone()

//    val array3 = createRepeatBigArray(2000_00,10)
//    val array3_1 = array3.clone()

    testArray(array1,"归并 无序的"){
        mergeSort(array1)
    }
    testArray(array1_2,"快排 无序的"){
        quickSort(array1_2)
    }
    testArray(array1_5,"快排3路 无序的"){
        quickSort3way(array1_5)
    }
    testArray(array1_3,"希尔 无序的"){
        quickSort(array1_3)
    }
    testArray(array1_4,"插入 无序的"){
        quickSort(array1_4)
    }
    testArray(array2,"归并 接近有序的"){
        mergeSort(array2)
    }
    testArray(array2_2,"快排 接近有序的"){
        quickSort(array2_2)
    }
    testArray(array2_5,"快排3路 接近有序的"){
        quickSort3way(array2_5)
    }
    testArray(array2_3,"希尔 接近有序的"){
        quickSort(array2_3)
    }
    testArray(array2_4,"插入 接近有序的"){
        quickSort(array2_4)
    }
//    testArray(array3_1,"快排3路 多重复的"){
//        quickSort3way(array3_1)
//    }
//    testArray(array3,"快排 多重复的"){
//        quickSort(array3)
//    }

}