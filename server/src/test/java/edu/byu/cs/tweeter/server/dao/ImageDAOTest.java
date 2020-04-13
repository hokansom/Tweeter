package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Base64;

import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.*;

class ImageDAOTest {
    static final String BucketName = "cs-340-w2020";

//    @Test
//    void testFileUpload(){
//        // Create AmazonS3 object for doing S3 operations
//        AmazonS3 s3 = AmazonS3ClientBuilder
//                .standard()
//                .withRegion("us-west-2")
//                .build();
//
//
//        String filename = "test.txt";
//        File file = new File(filename);
//
//        PutObjectRequest request = new PutObjectRequest(BucketName, filename, file);
//        try{
//            s3.putObject(request);
//            System.out.println("Uploaded succeeded");
//        }
//        catch (Error e){
//            System.out.println("Did not work");
//        }
//    }

}