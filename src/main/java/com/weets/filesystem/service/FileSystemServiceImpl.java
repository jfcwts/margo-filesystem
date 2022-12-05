package com.weets.filesystem.service;

import com.weets.filesystem.exception.FileSystemException;
import com.weets.filesystem.exception.ResourceNotFoundException;
import com.weets.filesystem.model.FileSystemTreeNode;
import com.weets.filesystem.repository.FileSystemTreeNodeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class FileSystemServiceImpl implements  FileSystemService{

    private static final Pattern PATH_PATTERN = Pattern.compile("(.*\\/)([a-zA-Z]+)");
    private static final String ROOT_FILE_SYSTEM = "\\src\\resources\\jfs";
    @Autowired
    private FileSystemTreeNodeRepository nodeRepository;

    @Override
    public FileSystemTreeNode createNode(FileSystemTreeNode node) throws IOException {
        Matcher m = PATH_PATTERN.matcher(node.getPath());
        if(!m.find()) {
            throw new IllegalArgumentException(String.format("(%s) is not a path", node.getPath()));
        }

        String subPath = m.group(1);
        String folderPath = ROOT_FILE_SYSTEM + subPath.replace("/","\\");
        if(!Files.exists(Path.of(folderPath))) {
            throw new FileSystemException(String.format("(%s) repository does not exists ",node.getPath()));
        }

        String filePath = ROOT_FILE_SYSTEM + node.getPath().replace("/","\\");
        if(Files.exists(Path.of(filePath))){
            throw new FileSystemException(String.format("(%s) file already exists ",node.getPath()));
        }

        File myObj = new File(filePath);
        myObj.createNewFile() ;
        return nodeRepository.save(node);


    }


    @Override
    public void removeNode(String filename) {
        Matcher m = PATH_PATTERN.matcher(filename);

        if(!m.find()){
            throw new IllegalArgumentException(String.format("(%s) is not a path", filename));
        }

        String path = m.group(1);
        String name = m.group(2);

        List<FileSystemTreeNode> nodesDb = this.nodeRepository.findByNameAndPath(name, path);
        if(nodesDb.size() > 0) {
            nodesDb.stream().forEach(node -> this.nodeRepository.delete(node));
        }else{
            throw new ResourceNotFoundException(String.format("no node record found for id (%s)", filename));
        }
    }

    @Override
    public List<FileSystemTreeNode> findContentOf(String path) {
        //return this.nodeRepository.findAll().stream().filter((node) -> node.getPathTo().equals(path)).collect(Collectors.toList());
        return this.nodeRepository.findByPath(path);
    }
}
