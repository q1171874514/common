package com.example.common.common.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class FileUtil {

    private static String directory = GetConfigUtil.get("application.yml")
                                        .get("file-util.directory").toString();

    /**
     * 上传
     * @param file  文件信息
     * @return      保存路径
     */
    public static String upload(MultipartFile file) {
        if(file.isEmpty()){
            return null;
        }
        File folder = new File(FileUtil.directory);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        File newFile = new File(folder, newName);
        try {
            //保存文件，返回文件名
            file.transferTo(newFile);
            return folder + "\\" + newName;
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    /**
     * 下载
     * @param path 文件路径
     * @param name 文件名称
     * @return
     * @throws Exception
     */
    public static ResponseEntity<byte[]> download(String path, String name) throws Exception{
        //下载文件,简单new个文件
        File downloadFile = new File(path);

        HttpHeaders headers = new HttpHeaders();
        //下载显示的文件名，并解决中文名称乱码问题
        String downloadFileName = new String(name.getBytes("UTF-8"),"iso-8859-1");
        //通知浏览器以attachment（下载方式）打开
        headers.setContentDispositionFormData("attachment", downloadFileName);
        //applicatin/octet-stream: 二进制流数据（最常见的文件下载）
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // 使用下org.apache.commons.io.FileUtils工具类
        byte[] bytes = FileUtils.readFileToByteArray(downloadFile);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
    }

    /**
     * 文件删除
     *
     * @param path 文件路径
     * @return false、true
     */
    public static Boolean delete(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {

    }
}
