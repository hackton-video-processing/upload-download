package com.postech.infra.handler;

import com.postech.domain.enums.AwsErrorEnum;
import com.postech.domain.enums.ProcessErrorEnum;
import com.postech.domain.exceptions.AwsException;
import com.postech.domain.exceptions.ProcessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/upload-download")
public class UploadDownloadControllerMock {

    @GetMapping("/test-error-process")
    public void throwErrorProcess() {
        throw new ProcessException(ProcessErrorEnum.NOT_FOUND);
    }

    @GetMapping("/test-error-aws")
    public void throwErrorAws() {
        throw new AwsException(AwsErrorEnum.S3_ERROR_SAVING);
    }
}
