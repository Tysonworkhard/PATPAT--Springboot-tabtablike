package com.app.taptap.service;

import com.app.taptap.utils.AesUtils;
import idworker.Sid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ImageService {
    private String localPath = "/home/img/";
    private String remotePath = "http://8.134.164.97:80/imgs/";

    String serverPath_key = "http://8.134.164.97/keys/KEY.txt";
    String serverPath_iv = "http://8.134.164.97/keys/IV.txt";

    public String receiveImage(MultipartFile file) throws IOException {
        String imgId = Sid.next();
        String dir = createDirIfNotExist(imgId);
        if (dir == null) {
            // 目录创建失败
            return null;
        }
        String fileName = imgId + ".JPG";
        String path = dir + fileName;
        AesUtils aesUtils = new AesUtils();
        String key;
        String iv;
        key = getStringFromServer(serverPath_key);
        iv = getStringFromServer(serverPath_iv);
        try {
            aesUtils.setIV(iv);
            aesUtils.setKEY(key);
            path = aesUtils.encrypt(path);
        }catch (Exception e){
            e.printStackTrace();
        }
        long timestamp = Long.parseLong(Objects.requireNonNull(file.getOriginalFilename()).substring(0, file.getOriginalFilename().lastIndexOf(".")));
        // 存入文件系统
        File targetFile = new File(dir, fileName);

        // 计算接收到的图片的哈希值
        try {
            file.transferTo(targetFile);
            log.info("======>>>>图片存储成功");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("======>>>>图片存储失败");
            return null;
        }

        return path;
    }

    private String getStringFromServer(String fileName) {
        String line = null;
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(fileName);
            URLConnection connection = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = br.readLine()) != null) {
                // 在这里处理每行的密钥内容
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        line = content.toString();
        return line;
    }

    public List<String> changePictureAddresses(List<String> pictureAddresses){
        if(!pictureAddresses.isEmpty()){
            for(int i = 0; i < pictureAddresses.size(); i++){
                AesUtils aesUtils = new AesUtils();
                aesUtils.setKEY(getStringFromServer(serverPath_key));
                aesUtils.setIV(getStringFromServer(serverPath_iv));
                try{
                    pictureAddresses.set(i, aesUtils.decrypt(pictureAddresses.get(i)));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            pictureAddresses = replaceRemotePath(pictureAddresses);
        }
        return pictureAddresses;
    }
    public String changePictureAddress(String pictureAddress){
        if (pictureAddress == null){
            return null;
        }
        AesUtils aesUtils = new AesUtils();
        aesUtils.setKEY(getStringFromServer(serverPath_key));
        aesUtils.setIV(getStringFromServer(serverPath_iv));
        try{
            pictureAddress = aesUtils.decrypt(pictureAddress);
        }catch (Exception e){
            e.printStackTrace();
        }
        pictureAddress = replaceSingleRemotePath(pictureAddress);
        return pictureAddress;
    }
    private List<String> replaceRemotePath(List<String> list) {
        return list.stream()
                .map(image -> image.replace(localPath, remotePath).replace("\\", "/"))
                .collect(Collectors.toList());
    }
    private String replaceSingleRemotePath(String list) {
        return list.replace(localPath, remotePath).replace("\\", "/");
    }
    /**
     * 若指定目录不存在则创建目录
     * @param id 图片id
     * @return 图片存放路径
     */
    public String createDirIfNotExist(String id) {
        String dir = localPath + id.substring(0, 3) + File.separator + id.substring(3, 6) + File.separator;
        File file = new File(dir);
        if (!file.exists() && !file.mkdirs()) {
            // 目录创建失败
            log.error("======>>>>目录创建失败");
            return null;
        }
        return dir;
    }

    public String calculateHash(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            // 使用SHA-256哈希算法计算文件的哈希值
            String hash = DigestUtils.sha256Hex(fis);
            return hash;
        }
    }

    public void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
            log.info("已删除文件");
            } else {
                log.warn("无法删除文件");
            }
        }
}

