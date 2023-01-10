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

import java.io.File;
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
        /*Matcher m = PATH_PATTERN.matcher(node.getPath());
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
        }*/

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
        return nodeRepository.save(node);

    }


    @Override
    public void removeNode(String filePath) {
        Matcher m = PATH_PATTERN.matcher(filePath);
        if(!m.find()){
            throw new IllegalArgumentException(String.format("(%s) is not a path", filePath));
        }

        String path = m.group(1);
        String name = m.group(2);
        logger.info("path arg OK : %s & %s".formatted(path, name));

        List<FileSystemTreeNode> nodesDb = this.nodeRepository.findByNameAndPath(name, path);
        if(nodesDb.size() == 0) {
            throw new ResourceNotFoundException(String.format("no node record found for id (%s)", filePath));
        }
        logger.info("file found");

        FileSystemTreeNode nodeToDelete = nodesDb.get(0);
        File  fileToDelete = nodeToDelete.getFile();
        if(!fileToDelete.delete()){
            throw new FileSystemException(String.format("failed to delete file", filePath));
        }
        nodesDb.stream().forEach(node -> this.nodeRepository.delete(node));
        logger.info("file removed");
    }

    @Override
    public List<FileSystemTreeNode> findContentOf(String path) {
        //return this.nodeRepository.findAll().stream().filter((node) -> node.getPathTo().equals(path)).collect(Collectors.toList());
        return this.nodeRepository.findByPath(path);
    }
}
