package com.weets.filesystem.service;

import com.weets.filesystem.exception.ResourceNotFoundException;
import com.weets.filesystem.model.FileSystemTreeNode;
import com.weets.filesystem.repository.FileSystemTreeNodeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FileSystemServiceImpl implements  FileSystemService{

    @Autowired
    private FileSystemTreeNodeRepository nodeRepository;

    @Override
    public FileSystemTreeNode createNode(FileSystemTreeNode node) {
        return nodeRepository.save(node);
    }

    @Override
    public FileSystemTreeNode removeNode(long nodeId) {
        Optional<FileSystemTreeNode> nodeDb = this.nodeRepository.findById(nodeId);
        if(nodeDb.isPresent()) {
            return nodeDb.get();
        }else{
            throw new ResourceNotFoundException(String.format("no node record found for id (%s)", nodeId));
        }
    }

    @Override
    public List<FileSystemTreeNode> findContentOf(String path) {
        return this.nodeRepository.findAll();
    }
}
