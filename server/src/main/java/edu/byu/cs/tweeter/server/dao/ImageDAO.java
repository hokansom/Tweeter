package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

public class ImageDAO {
    static final String BucketName = "cs-340-w2020";

    public String uploadImage(String imageString, String alias){
        String filename = String.format("%s.jpg", alias);
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
            meta.setContentType("image/png");
//
            System.out.println("Created byteArrayInputStream");
//
//            BufferedImage bufferedImage = ImageIO.read(bais);
//            System.out.println("Created buffered image");
//            File imageFile;
//            byte[] imageBytes = Base64.getMimeDecoder().decode(imageString);
//            System.out.println("Decoded base64");
//            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
//            System.out.println("Created byteArrayInputStream");
//            BufferedImage bufferedImage = ImageIO.read(bais);
//            System.out.println("Created buffered image");
//            File imageFile;
//            try{
//               imageFile = new File(filename);
//                ImageIO.write(bufferedImage, "jpg", imageFile);
//            } catch (IOException e){
//                throw new RuntimeException("[Internal Service Error]: Failed to copy image to file");
//            }
//            System.out.println("Created File");

//            PutObjectRequest request = new PutObjectRequest(BucketName, filename, imageFile);
//            request.setCannedAcl(CannedAccessControlList.PublicRead);
            PutObjectRequest request = new PutObjectRequest(BucketName, filename, stream, meta);
            request.setCannedAcl(CannedAccessControlList.PublicRead);

            System.out.println("created PutObject");



            s3.putObject(request);
            System.out.println("putObject succeeded");
            stream.close();
            System.out.println("closed inputstream");

        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("[Internal Service Error]: Could not upload profile image to s3");
        }
        return url;
    }

}
