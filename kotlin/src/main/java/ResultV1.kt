

/**
 * 通用返回接口
 * V1.0
 * @author 任家立
 * @date 2021/07/22
 */
class ResultV1<T>(
    var message:T? = null,
    var msg: String? = null
){

}

inline fun <reified T> T.resultV1() = ResultV1(this)