package com.weets.filesystem.model;



import jakarta.persistence.*;

@Entity
public class FileSystemTreeNode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="parent_id")
    private Long parentId;
    @Column(name="repository")
    private Boolean repository;
    @Column(name="name")
    private String name;


    protected FileSystemTreeNode(){}

    public FileSystemTreeNode(long parentId, boolean repository, String name){
        this.parentId = parentId;
        this.repository = repository;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public boolean isRepository() {
        return repository;
    }

    public void setRepository(boolean repository) {
        this.repository = repository;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
