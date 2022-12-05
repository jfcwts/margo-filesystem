package com.weets.filesystem.controller;

import com.weets.filesystem.model.FileSystemTreeNode;
import com.weets.filesystem.service.FileSystemService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/bank")
public class FileSystemController {
    @Autowired
    private FileSystemService fileSystemService;

    @GetMapping("/test")
    public HttpStatus test(){
        return HttpStatus.OK;
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileSystemTreeNode>> findAllChildren(@RequestParam String path){
        return ResponseEntity.ok().body(fileSystemService.findContentOf(path));
    }

    @PostMapping("/files")
    public ResponseEntity<FileSystemTreeNode> createNode(@RequestBody FileSystemTreeNode node){
        return ResponseEntity.ok().body(this.fileSystemService.createNode(node));
    }

    @DeleteMapping("/files")
    public ResponseEntity<HttpStatus> removeNode(@RequestParam String filename){
        this.fileSystemService.removeNode(filename);
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //@DeleteMapping("/files/{nodeId}")
    //public ResponseEntity<HttpStatus> removeNode(@PathVariable long nodeId){
      //  this.fileSystemService.removeNode(nodeId);
       // return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
    //}
}
