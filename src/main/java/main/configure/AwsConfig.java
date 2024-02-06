package main.configure;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AwsConfig {
    @Value("${spring.cloud.aws.credentials.accessKey}")
    private String s3AccessKey;
    @Value("${spring.cloud.aws.credentials.secretKey}")
    private String s3AccessSecret;

    @Bean
    @Primary
    public AmazonS3 s3Client() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(s3AccessKey, s3AccessSecret);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion("ap-northeast-2")
                .build();
    }
}
