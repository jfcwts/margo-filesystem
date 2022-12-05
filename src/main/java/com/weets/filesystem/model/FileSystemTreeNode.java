package com.weets.filesystem.model;



import jakarta.persistence.*;

@Entity
public class FileSystemTreeNode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private Boolean repository;


    protected FileSystemTreeNode(){}

    public FileSystemTreeNode(String name, String path, boolean repository){
        this.path = path;
        this.repository = repository;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
