package org.bg181.sbd.adapter.api;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bg181.sbd.infra.Constants;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Sam Lu
 * @date 2021/6/4
 */
@Slf4j
@RestController("FileController")
@RequestMapping(Constants.API + "/files")
public class FileController {

    @SneakyThrows
    @PostMapping("/actions/upload")
    public byte[] submit(@RequestParam("file") MultipartFile file) {
        log.info("File Length: {}", file.getSize());
        return file.getBytes();
    }

}
