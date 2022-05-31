package com.alkemy.ong.ports.output.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

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
            e.printStackTrace();
        }
        return fileUrl;
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

}
