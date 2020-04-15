package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;


public class ImageDAO {
    static final String BucketName = "cs-340-w2020";

    public String uploadImage(String imageString){
        String id = UUID.randomUUID().toString();
        String filename = String.format("%s.jpg", id);
        String url = "https://" + BucketName + ".s3-us-west-2.amazonaws.com/" + filename;

        // Create AmazonS3 object for doing S3 operations
        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-west-2")
                .build();

        try{
            byte[] imageBytes = Base64.getMimeDecoder().decode(imageString);
            InputStream stream = new ByteArrayInputStream(imageBytes);

            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(imageBytes.length);
            meta.setContentType("image/jpg");

            PutObjectRequest request = new PutObjectRequest(BucketName, filename, stream, meta);
            request.setCannedAcl(CannedAccessControlList.PublicRead);

            s3.putObject(request);

            stream.close();

        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("[Internal Service Error]: Could not upload profile image to s3");
        }
        return url;
    }

}
