package main.service.chat;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

@Service
public class AwsS3Service {
    private final AmazonS3 amazonS3;
    private final String bucket;
    public AwsS3Service(AmazonS3 amazonS3,  @Value("${spring.cloud.aws.s3.bucket}")String bucket) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
    }
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        // file 이름 변경 후 저장
        String originalFileName = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s", "_");

        String fileName = dirName + "/" + uniqueFileName;
        // convert
        File uploadFile = convert(multipartFile);

        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }
    private File convert(MultipartFile file) throws IOException{
        String originalName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String uniqueFileName = uuid + "_" + originalName.replaceAll("\\s", "_");

        File convertFile = new File(uniqueFileName);
        if (convertFile.createNewFile()){
            try (FileOutputStream fos = new FileOutputStream(convertFile)){
                fos.write(file.getBytes());
            } catch(IOException e) {
                throw e;
            }
            return convertFile;
        }
        throw new IllegalArgumentException(String.format("파일 변환 실패 %s", originalName));
    }
    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, fileName).toString();
    }
    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            System.out.println("file delete");
        } else {
            System.out.println("file not delete");
        }
    }
    private void deleteFile(String fileName) {
        try{
            String decodedFileName = URLDecoder.decode(fileName, "UTF-8");
            amazonS3.deleteObject(bucket, decodedFileName);
        }catch(UnsupportedEncodingException e) {
            e.getMessage();
        }
    }
    public String updateFile(MultipartFile newFile, String oldFileName, String dirName) throws IOException {
        deleteFile(oldFileName);
        return upload(newFile, dirName);
    }
}
