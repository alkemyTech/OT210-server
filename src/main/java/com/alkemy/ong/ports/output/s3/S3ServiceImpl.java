package com.alkemy.ong.ports.output.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3;
    @Value("${s3.bucket}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        String fileUrl = "";
        try {
            File file = convertMultipartToFile(multipartFile);
            String fileName = generateFileName(file);
            fileUrl = amazonS3.getUrl(bucketName, fileName).toString();
            uploadFileToS3Bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            log.error("error uploading file", e);
            throw new RuntimeException(e);
        }
        return fileUrl;
    }

    @Override
    public String uploadFile(String fileBase64, String fileName) {

        MultipartFile multipartFile = decodeBase64ToMultipart(fileBase64,fileName);

        return uploadFile(multipartFile);
    }


    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File convertedFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convertedFile;
    }

    private String generateFileName(File multipartFile) {
        return new Date().getTime() + "-" + multipartFile.getName().replace(" ", "_");
    }

    private void uploadFileToS3Bucket(String filename, File file) {
        var request = new PutObjectRequest(bucketName, filename, file)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(request);
    }
    private MultipartFile decodeBase64ToMultipart(String imageBase64, String fileName) {

        String dataType;
        if (imageBase64.contains("data:image/png;")) {
            dataType = "image/png";
            imageBase64 = imageBase64.replace("data:image/png;base64,", "");
            fileName += ".png";
        } else {
            dataType = "image/jpeg";
            imageBase64 = imageBase64.replace("data:image/jpeg;base64,", "");
            fileName += ".jpeg";
        }
        try {
            byte[] imageByte = Base64.getDecoder().decode(imageBase64);
            FileItem fileItem = new DiskFileItem(new String(imageByte), dataType, true, fileName,
                    DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, new java.io.File(System.getProperty("java.io.tmpdir")));
            InputStream input = new ByteArrayInputStream(imageByte);
            OutputStream os = fileItem.getOutputStream();
            int ret = input.read();
            while (ret != -1) {
                os.write(ret);
                ret = input.read();
            }
            os.flush();
            return new CommonsMultipartFile(fileItem);

        } catch (IOException e) {
            log.error("error converting file", e);
            throw new RuntimeException(e);
        }
    }
}
