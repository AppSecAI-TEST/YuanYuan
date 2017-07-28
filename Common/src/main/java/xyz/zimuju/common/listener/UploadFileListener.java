package xyz.zimuju.common.listener;

/*
 * @description IUploadListener ：上传的接口
 * @author Nathaniel-nathanwriting@126.com
 * @time 2016/8/17-10:18
 * @version v1.0.0
 */
public interface UploadFileListener {

    void prepareUpload(); // 准备上传

    void uploadComplete(String videoUrl, String imageUrl, String message); // 上传完成

    void uploadError(int errorCode, String message); // 删除失败

    void uploadProgress(long fileSize, int percent, long speed, long time); // 显示上传百分比
}
