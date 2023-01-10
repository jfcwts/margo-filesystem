package com.weets.filesystem.service;

import com.weets.filesystem.dto.FileDto;
import com.weets.filesystem.exception.FileSystemException;
import com.weets.filesystem.exception.ResourceNotFoundException;
import com.weets.filesystem.model.FileSystemTreeNode;
import com.weets.filesystem.repository.FileSystemTreeNodeRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class FileSystemServiceImpl implements  FileSystemService{
    Logger logger = LoggerFactory.getLogger(FileSystemServiceImpl.class);

    private static final Pattern PATH_PATTERN = Pattern.compile("(.*\\/)([a-zA-Z]*)");

    @Autowired
    private FileSystemTreeNodeRepository nodeRepository;

    @Override
    public FileSystemTreeNode createNode(FileDto fileDto) throws IOException {

        Matcher m = PATH_PATTERN.matcher(fileDto.getFilename());
        if(!m.find()){
            throw new IllegalArgumentException(String.format("(%s) is not a path. ", fileDto.getFilename()));
        }

        String path = m.group(1);
        String name = m.group(2);
        FileSystemTreeNode node = new FileSystemTreeNode(name, path, false);

        logger.info("formatted full filename : %s".formatted(node.getFormattedFullFilename()));
        File newFile = node.getFile();
        if(!newFile.createNewFile()){
            throw new FileSystemException(String.format("(%s) file already exists ",node.getPath()));
        }
        logger.info("file created successfully");
        return nodeRepository.save(node);

    }


    @Override
    public void removeNode(String filename) {

        FileSystemTreeNode nodeToDelete = getNodeFromDto(filename);
        File  fileToDelete = nodeToDelete.getFile();
        if(!fileToDelete.delete()){
            throw new FileSystemException(String.format("failed to delete file", filename));
        }
        this.nodeRepository.delete(nodeToDelete);
        logger.info("file removed successfully");
    }

    @Override
    public List<FileSystemTreeNode> findContentOf(String path) {
        return this.nodeRepository.findByPath(path);
    }

    @Override
    public void appendFile(FileDto fileDto) throws IOException {
        FileSystemTreeNode toAppend = getNodeFromDto(fileDto.getFilename());
        if(toAppend.isFolder()){
            throw new FileSystemException(String.format("this is a folder! ", toAppend.getFormattedFullFilename()));
        }

        FileWriter writer = new FileWriter(toAppend.getFormattedFullFilename(), true);
        BufferedWriter bw = new BufferedWriter(writer);
        bw.write(fileDto.getAppendedText());
        bw.newLine();
        bw.close();
        logger.info("file appended successfully");
    }

    private FileSystemTreeNode getNodeFromDto(String filename) {
        Matcher m = PATH_PATTERN.matcher(filename);
        if(!m.find()){
            throw new IllegalArgumentException(String.format("(%s) is not a path", filename));
        }

        String path = m.group(1);
        String name = m.group(2);
        logger.info("path arg OK : %s & %s".formatted(path, name));

        List<FileSystemTreeNode> nodesDb = this.nodeRepository.findByNameAndPath(name, path);
        if(nodesDb.size() == 0) {
            throw new ResourceNotFoundException(String.format("no node record found for id (%s)", filename));
        }
        logger.info("file found");

        return nodesDb.get(0);
    }
}
