package me.test.JavaCVdemo;

import java.util.concurrent.ExecutionException;

import javax.swing.WindowConstants;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.javacv.VideoInputFrameGrabber;
import org.junit.Test;

/**
 * Hello world!
 *
 */
public class App2 
{
    public static void main( String[] args )
    {
    	CanvasFrame canvas = new CanvasFrame("播放器");//新建一个窗口
        canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);
        String filePath="C:\\Users\\Public\\Videos\\Sample Videos\\movie.mp4";
        //String filePath="C:\\Users\\Public\\Videos\\Sample Videos\\陈政文-悲歌-(国语)[chedvd.com].avi";
		try {
			FFmpegFrameGrabber ffmpegFrameGrabber = FFmpegFrameGrabber.createDefault(filePath);
			ffmpegFrameGrabber.start();
			int maxStamp = (int) (ffmpegFrameGrabber.getLengthInTime()/1000000);//视频总秒数
			while (true) {
				Frame nowFrame = ffmpegFrameGrabber.grabImage();
				if (nowFrame == null) {
					System.exit(-1);
				}
				int startStamp = (int) (ffmpegFrameGrabber.getTimestamp() * 1.0/1000000);
				double present = (startStamp / maxStamp) * 100;
				System.out.println("00:"+String.format("%02d", startStamp));
				canvas.showImage(nowFrame);
				Thread.sleep(40);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    

    
    @Test
    public void testCamera() throws InterruptedException, FrameGrabber.Exception {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();   //开始获取摄像头数据
        CanvasFrame canvas = new CanvasFrame("摄像头");//新建一个窗口
        canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);
        while (true) {
            if (!canvas.isDisplayable()) {//窗口是否关闭
                grabber.stop();//停止抓取
                System.exit(-1);//退出
            }

            Frame frame = grabber.grab();

            canvas.showImage(frame);//获取摄像头图像并放到窗口上显示， 这里的Frame frame=grabber.grab(); frame是一帧视频图像
            Thread.sleep(50);//50毫秒刷新一次图像
        }
    }
    
    @Test
    public void testCamera1() throws FrameGrabber.Exception, InterruptedException {
        VideoInputFrameGrabber grabber = VideoInputFrameGrabber.createDefault(0);
        grabber.start();
        CanvasFrame canvasFrame = new CanvasFrame("摄像头");
        canvasFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        canvasFrame.setAlwaysOnTop(true);
        while (true) {
            if (!canvasFrame.isDisplayable()) {
                grabber.stop();
                System.exit(-1);
            }
            Frame frame = grabber.grab();
            canvasFrame.showImage(frame);
            Thread.sleep(30);
        }
    }
    
   
}
