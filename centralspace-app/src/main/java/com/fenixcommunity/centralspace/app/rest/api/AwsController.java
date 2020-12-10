package com.fenixcommunity.centralspace.app.rest.api;

import static com.fenixcommunity.centralspace.utilities.common.Var.NEW_LINE;
import static java.util.Objects.requireNonNullElseGet;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.fenixcommunity.centralspace.app.rest.dto.aws.s3.PutObjectToBucketDto;
import com.fenixcommunity.centralspace.app.rest.dto.responseinfo.BasicResponse;
import com.fenixcommunity.centralspace.app.service.aws.s3.S3Service;
import com.fenixcommunity.centralspace.utilities.web.WebTool;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api/aws")
@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true) @AllArgsConstructor(access = PACKAGE)
public class AwsController {
    private final S3Service s3Service;

    @PostMapping("/bucket/create/{bucketName}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, response = BasicResponse.class, message = "created"),
    })
    public ResponseEntity<BasicResponse> createBucket(@PathVariable("bucketName") final String bucketName) {
        final Bucket bucket = s3Service.createBucket(bucketName);
        final BasicResponse response = BasicResponse.builder()
                .description(bucket.getName() + NEW_LINE + bucket.getCreationDate() + NEW_LINE + bucket.getOwner().getDisplayName())
                .status("CREATED").build();
        return ResponseEntity.created(WebTool.getCurrentURI()).body(response);
    }

    @GetMapping("/bucket/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BasicResponse> getListOfBuckets() {
        final List<Bucket> buckets = requireNonNullElseGet(s3Service.getListOfBuckets(), List::of) ;
        final BasicResponse response = BasicResponse.builder()
                .description(buckets.toString()).build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/bucket/object/put")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BasicResponse> putObjectToBucket(@Valid @RequestBody final PutObjectToBucketDto putObjectToBucketDto) {
        final PutObjectResult result = s3Service.putObjectToBucket(putObjectToBucketDto);
        final BasicResponse response = BasicResponse.builder()
                .description(result.getETag())
                .status("ADDED").build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bucket/{bucketName}/object/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BasicResponse> getListOfObjects(@PathVariable(name = "bucketName") String bucketName) {
        final ObjectListing objectListing = s3Service.getListOfObjects(bucketName);
        final BasicResponse response = BasicResponse.builder()
                .description(objectListing.getObjectSummaries().stream().map(Object::toString).collect(Collectors.joining(",")))
                // or .description(objectListing.getObjectSummaries().stream().map(S3ObjectSummary::getKey).reduce("", String::concat))
                .build();
        return ResponseEntity.ok(response);
    }

}