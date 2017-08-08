package org.t_robop.triclo.triclo;

/**
 * Created by fujitaken on 2017/08/07.
 */

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Vector;

import static android.content.ContentValues.TAG;
import static org.opencv.core.Core.merge;


public class MaskActivity extends Activity implements View.OnTouchListener{
    /** Called when the activity is first created. */

    private int preDx, preDy, newDx, newDy;
    private ImageView binView;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mask);

        // cv::Mat gray_img = cv::imread("../../image/lenna.png", 1);
        Mat srcMat = Imgcodecs.imread("/storage/sdcard0/DCIM/Camera/IMG_20170807_015133.jpg");
        Size sz = srcMat.size();
        Imgproc.resize(srcMat, srcMat, new Size(sz.width*0.5,sz.height*0.5));

        Vector<Mat> channels = new Vector<Mat>(3);
        Core.split(srcMat,channels);

        if (!srcMat.empty()) {
            // グレースケール画像に変換
            Mat grayMat = new Mat();
            Imgproc.cvtColor(srcMat, grayMat, Imgproc.COLOR_RGBA2GRAY, 4);
            // 固定の閾値処理
            // cv::Mat bin_img, bininv_img, trunc_img, tozero_img, tozeroinv_img;
            Mat binMat = new Mat();

            // 入力画像, 出力画像, 閾値, maxVal, 閾値処理手法
            // cv::threshold(gray_img, bin_img, 0, 255, cv::THRESH_BINARY|cv::THRESH_OTSU);　
            Imgproc.threshold(grayMat, binMat, 0, 255, Imgproc.THRESH_BINARY_INV | Imgproc.THRESH_OTSU);

            channels.add(binMat);

            //合体させる用
            Mat ImgMat = new Mat();
            merge(channels,ImgMat);

            Log.d(TAG,"srcMat ch ="+srcMat.channels()+" grayMat ch ="+grayMat.channels()+" binMat ch ="+binMat.channels()+" ImgMat ch ="+ImgMat.channels());

            Bitmap binImg = convMatToBitmap(ImgMat);

            binView = (ImageView) findViewById(R.id.bin_view);
            binView.setImageBitmap(binImg);
            binView.setOnTouchListener(this);
            preDx = preDy = newDx = newDy = 0;

        }
        else{
            Toast.makeText(this, "ないです", Toast.LENGTH_LONG).show();
        }
    }

    public boolean onTouch(View v, MotionEvent event){
        // x,y 位置取得
        newDx = (int)event.getRawX();
        newDy = (int)event.getRawY();

        switch (event.getAction()) {
            // タッチダウンでdragされた
            case MotionEvent.ACTION_MOVE:
                // ACTION_MOVEでの位置
                int dx = binView.getLeft() + (newDx - preDx);
                int dy = binView.getTop() + (newDy - preDy);

                // 画像の位置を設定する
                binView.layout(dx, dy, dx + binView.getWidth(), dy + binView.getHeight());

                Log.d("onTouch","ACTION_MOVE: dx="+dx+", dy="+dy);
                break;
        }

        // タッチした位置を古い位置とする
        preDx = newDx;
        preDy = newDy;


        return true;
    }


    /* Opencvのライブラリのインポート*/
    static {
        System.loadLibrary("opencv_java3");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }


    // MatからBitmapに変換
    Bitmap convMatToBitmap(Mat src) {
        Mat dst = new Mat();
        // GRAY→RGBAに変換
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGRA2RGBA, 4);
        Bitmap img = Bitmap.createBitmap(src.width(), src.height(), Bitmap.Config.ARGB_8888);
        // MatからBitmapに変換
        Utils.matToBitmap(dst, img);
        return img;
    }
}
