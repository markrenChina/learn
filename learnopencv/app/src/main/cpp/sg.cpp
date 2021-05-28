#include <iostream>
#include <opencv2/opencv.hpp>
#include <vector>
#include <cmath>
#include <cmath>

using namespace cv;
using namespace std;

/// <summary>
/// 直线参数
/// </summary>
typedef struct {
    /// <summary>
    /// 截距
    /// </summary>
    float intercept;
    /// <summary>
    /// 斜率
    /// </summary>
    float slope;
} LineParam;

LineParam getLineParam(int startIdx, float startData, int endIdx, float endData) {
    LineParam res;
    res.slope = (endData - startData) / (endIdx - startIdx);
    res.intercept = startData - startIdx * res.slope;
    return res;
}

//float round(float r) {
//return (r > 0.0) ? floor(r + 0.5) : ceil(r - 0.5);
//}

// 矩阵X的广义逆
Mat Generalized_inverse(Mat &X) {
    Mat Xt = X.t();

    Mat Xt_X = Xt * X;

    Mat inverse_Xt_X;
    invert(Xt_X, inverse_Xt_X, DECOMP_LU);        //方阵LU分解,求逆矩阵

    return inverse_Xt_X * Xt;
}

Mat vander(int M, int N) {
    Mat move_box(2 * M + 1, N, CV_32F);
    for (int i = -M; i <= M; ++i) {
        for (int j = 0; j < N; ++j) {
            move_box.at<float>(i + M, j) = pow(i, j);
        }
    }
    return move_box;
}

vector <vector<float>> opencv_sg(vector <vector<float>> &test_data, int M, int N) {
    // SG平滑矩阵算子	
    Mat move_box = vander(M, N);
    // 矩阵move_box 的广义逆
    Mat gen_inv = Generalized_inverse(move_box);

    vector <vector<float>> test_data_clone = test_data;

    //校验
    if (test_data[0].size() <= 2 * M + 1) {
        cout << "the length of the input_data is too short!" << endl;
    }

    if (2 * M + 1 <= N) {
        cout << "box size is too small!" << endl;
    }

    //拟合
    for (int i = M; i < test_data[0].size() - M; ++i) {
        Mat y_box_value(2 * M + 1, 1, CV_32F);
        for (int j = -M; j <= M; ++j) {
            y_box_value.at<float>(j + M, 0) = test_data[1][i + j];
        }

        Mat ls_sol = gen_inv * y_box_value;                    // 最小二乘解
        Mat result = move_box * ls_sol;                        // sg滤波值
        //Mat result = move_box.mul(gen_inv.mul(y_box_value));
        test_data_clone[1][i] = result.at<float>(M, 0);
        if (i == M) {
            for (int k = 0; k < M; k++) {
                test_data_clone[1][k] = result.at<float>(k, 0);
            }
        } else if (i == (test_data[0].size() - M - 1)) {
            for (int k = 1; k < M + 1; k++) {
                test_data_clone[1][i + k] = result.at<float>(k + M, 0);
            }
        }
    }

    return test_data_clone;
}

vector<float> opencv_sg2(vector<float> &test_data, int M, int N) {
    // SG平滑矩阵算子	
    Mat move_box = vander(M, N);
    // 矩阵move_box 的广义逆
    Mat gen_inv = Generalized_inverse(move_box);

    vector<float> test_data_clone = test_data;

    //校验
    if (test_data.size() <= 2 * M + 1) {
        cout << "the length of the input_data is too short!" << endl;
    }

    if (2 * M + 1 <= N) {
        cout << "box size is too small!" << endl;
    }

    //拟合
    for (int i = M; i < test_data.size() - M; ++i) {
        Mat y_box_value(2 * M + 1, 1, CV_32F);
        for (int j = -M; j <= M; ++j) {
            y_box_value.at<float>(j + M, 0) = test_data[i + j];
        }

        Mat ls_sol = gen_inv * y_box_value;                    // 最小二乘解
        Mat result = move_box * ls_sol;                        // sg滤波值
        //Mat result = move_box.mul(gen_inv.mul(y_box_value));
        test_data_clone[i] = result.at<float>(M, 0);
        if (i == M) {
            for (int k = 0; k < M; k++) {
                test_data_clone[k] = result.at<float>(k, 0);
            }
        } else if (i == (test_data.size() - M - 1)) {
            for (int k = 1; k < M + 1; k++) {
                test_data_clone[i + k] = result.at<float>(k + M, 0);
            }
        }
    }

    return test_data_clone;
}

/// <summary>
/// 扩展数组
/// </summary>
/// <param name="data"></param>
/// <param name="multiple"></param>
/// <returns></returns>
vector<float> extend(vector<float> data, int multiple) {
    vector<float> res(data.size() * multiple - (multiple - 1));
    for (int i = 0; i < data.size() - 1; ++i) {
        //本段线段起始数据下标
        int startIdx = i * multiple;
        //本段线截止数据下标
        int endIdx = (i + 1) * multiple;

        res[startIdx] = data[i];

        LineParam lineParam = getLineParam(startIdx, data[i], endIdx, data[i + 1]);

        for (int j = startIdx + 1; j < endIdx; j++) {
            res[j] = round((lineParam.intercept + lineParam.slope * j) * 10) / 10.0;
        }
    }
    res[res.size() - 1] = data[data.size() - 1];
    return res;
}

int main() {
    // 第一次，测试二维数组第一维0为x坐标值，第一维1为y值
    //vector<vector<float>> test_data(2, vector<float>(150));
    vector<float> test_data2(150);
    int array[150] = {594,2909,3457,3844,4153,4444,4653,4866,5072,5255,5446,5645,5826,6007,6214,6410,6593,6810,6976,
                      7183,7368,7560,7748,7941,8135,8330,8515,8688,8863,9070,9240,9407,9578,9748,9909,10067,10247,
                      10444,10600,10734,10886,11019,11161,11296,11438,11580,11696,11815,11939,12054,12179,12266,12400,
                      12519,12629,12748,12866,13004,13120,13216,13336,13447,13545,13671,13770,13899,14006,14138,14295,
                      14446,14620,14841,15065,15389,15682,16088,16508,16944,17347,17669,17843,17836,17682,17430,17172,
                      17009,16812,16667,16577,16498,16461,16435,16410,16409,16399,16401,16412,16470,16469,16501,16532,
                      16609,16646,16721,16754,16807,16868,16960,17042,17121,17198,17308,17434,17574,17742,17957,18220,
                      18472,18835,19182,19512,19826,20024,20076,20011,19856,19638,19470,19311,19221,19147,19108,19118,
                      19084,19106,19113,19153,19200,19283,19348,19440,19571,19675,19872,20031,20226,20405,20617,20858,
                      21064};
    //for (size_t i = 0; i < test_data[0].size(); i++)
    //{
    //test_data[0][i] = (float)i;
    //}
    //for (size_t i = 0; i < test_data[1].size(); i++)
    for (size_t i = 0; i < test_data2.size(); i++) {
        //test_data[1][i] = (float)array[i];
        test_data2[i] = array[i];
    }
    cout << endl;
    cout << endl;
    //vector<vector<float>> res = opencv_sg(test_data, 2, 3);
    vector<float> fittedData = opencv_sg2(test_data2, 2, 3);

    //打印只是为了调试
    for (int i = 0; i < fittedData.size(); i++) {
        //cout << " 前 " << test_data[1][i];
        cout << i << " " << fittedData[i] << endl;
    }
    vector<float> extendedData = extend(fittedData, 4);

    //扩展之和再拟合
    vector<float> extendedFittedData = opencv_sg2(extendedData, 2, 3);

    for (int i = 0; i < extendedFittedData.size(); i++) {
        //cout << " 前 " << test_data[1][i];
        cout << i << " " << extendedFittedData[i] << endl;
    }

    cout << endl;

    //imshow('abc',res);

    getchar();

    return 0;
}