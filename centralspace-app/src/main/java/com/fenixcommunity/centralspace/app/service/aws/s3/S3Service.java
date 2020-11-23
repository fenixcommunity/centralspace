package com.fenixcommunity.centralspace.app.service.aws.s3;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.fenixcommunity.centralspace.app.rest.dto.resource.InternalResourceDto;
import com.fenixcommunity.centralspace.app.rest.dto.aws.s3.PutObjectToBucketDto;
import com.fenixcommunity.centralspace.app.rest.exception.ServiceFailedException;
import com.fenixcommunity.centralspace.utilities.resourcehelper.InternalResource;
import com.fenixcommunity.centralspace.utilities.resourcehelper.InternalResourceLoader;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = PUBLIC) @FieldDefaults(level = PRIVATE, makeFinal = true)
@Log4j2
public class S3Service {

    @Qualifier("amazonS3Client")
    private final AmazonS3 amazonS3Client;
    private final InternalResourceLoader resourceTool;

    public Bucket createBucket(@NonNull final String bucketName) {
        final CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName, amazonS3Client.getRegion());
        return amazonS3Client.createBucket(createBucketRequest);
    }

    public List<Bucket> getListOfBuckets() {
        return amazonS3Client.listBuckets();
    }

    public PutObjectResult putObjectToBucket(@NonNull final PutObjectToBucketDto putObjectToBucketDto) {
        final InternalResourceDto internalResourceDto = putObjectToBucketDto.getInternalResourceDto();
        final File resourceFile;
        final var resource = resourceTool.loadResourceFile(InternalResource
                .resourceByNameAndType(internalResourceDto.getFileName(), internalResourceDto.getFileFormat()));
        try {
            resourceFile = resource.getFile();
        } catch (IOException e) {
            throw new ServiceFailedException("File not found", e);
        }
        final PutObjectRequest putObjectRequest = new PutObjectRequest(
                putObjectToBucketDto.getBucketName(),
                putObjectToBucketDto.getKey(),
                resourceFile);
        return amazonS3Client.putObject(putObjectRequest);
    }

    public ObjectListing getListOfObjects(String bucketName) {
        return amazonS3Client.listObjects(bucketName);
    }
}