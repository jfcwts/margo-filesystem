package com.weets.filesystem.controller;

import com.weets.filesystem.dto.FileDto;
import com.weets.filesystem.model.FileSystemTreeNode;
import com.weets.filesystem.service.FileSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;

import java.net.http.HttpResponse;
import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;

@RestController
@RequestMapping("/bank")
public class FileSystemController {
    Logger logger = LoggerFactory.getLogger(FileSystemController.class);
    @Autowired
    private FileSystemService fileSystemService;

    @GetMapping("/test")
    public HttpStatus test(){
        logger.warn("test warning");
        String filenametest = "src\\main\\resources\\test.txt";
        // create file
        try {
            File tempfile = new File(filenametest);
            tempfile.createNewFile();
            logger.info("file created");
        } catch (IOException e) {
            logger.error("fail to create :"+e.getMessage());
        }

        // append file
        FileWriter fw = null;
        try {
            fw = new FileWriter(filenametest, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Spain\nPortugal");
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            logger.error("fail to append :"+e.getMessage());
            throw new RuntimeException(e);
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filenametest));
            String line = br.readLine();
            while (line != null){
                System.out.println(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return HttpStatus.OK;
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileSystemTreeNode>> findAllChildren(@RequestParam String path){
        return ResponseEntity.ok().body(fileSystemService.findContentOf(path));
    }

    @PostMapping("/files")
    public ResponseEntity<Object> createNode(@RequestBody FileDto fileDto){
        try {
            logger.info("body : "+fileDto.getFilename());
            return ResponseEntity.ok().body(this.fileSystemService.createNode(fileDto));
        }catch(Exception exception) {
            logger.error(exception.getStackTrace().toString());
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @DeleteMapping("/files")
    public ResponseEntity<Object> removeNode(@RequestParam String filename){
        try {
            this.fileSystemService.removeNode(filename);
            return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception exception) {
            logger.error(exception.getStackTrace().toString());
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/files/append")
    public ResponseEntity<Object> appendFile(@RequestBody FileDto fileDto){
        try {
            this.fileSystemService.appendFile(fileDto);
            return ResponseEntity.ok().body("file content appended");
        }catch(Exception exception) {
            logger.error(exception.getStackTrace().toString());
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/files/concatenate")
    public ResponseEntity<Object> concatenateFiles(@RequestBody FileDto fileDto){
        try {
            this.fileSystemService.appendFile(fileDto);
            return ResponseEntity.ok().body("file content appended");
        }catch(Exception exception) {
            logger.error(exception.getStackTrace().toString());
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    //@DeleteMapping("/files/{nodeId}")
    //public ResponseEntity<HttpStatus> removeNode(@PathVariable long nodeId){
      //  this.fileSystemService.removeNode(nodeId);
       // return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
    //}
}
