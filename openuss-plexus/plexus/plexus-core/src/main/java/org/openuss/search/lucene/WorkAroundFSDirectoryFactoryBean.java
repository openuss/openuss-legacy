/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package org.openuss.search.lucene;

import java.io.File;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

/**
 * This Spring factory bean configures a Lucene filesystem
 * directory using its location.
 * 
 * <p>The create property can be set to force the index to
 * be created when the directory instance is created. 
 * 
 * @author Brian McCallister
 * @author Thierry Templier
 * @author Rob Moore
 * @see org.apache.lucene.store.FSDirectory
 */
public class WorkAroundFSDirectoryFactoryBean implements FactoryBean, InitializingBean {

    private Resource location;
    private FSDirectory directory;
    private boolean create = false;

    public Object getObject() throws Exception {
        return directory;
    }

    public Class getObjectType() {
        return Directory.class;
    }

    public boolean isSingleton() {
        return true;
    }

    /**
     * This method constructs a filesystem Lucene directory.
     *  
     * <p>The location property must be set, and be a directory
     */
    public void afterPropertiesSet() throws Exception {
        if (location == null) {
            throw new BeanInitializationException(
                    "Must specify a location property");
        }

        File locationFile = location.getFile();
        boolean locationExists = locationFile.exists();
        if (!locationExists && !create) {
            throw new BeanInitializationException("location does not exist");
        } else if (locationExists && !locationFile.isDirectory()) {
            throw new BeanInitializationException(
                    "location must be a directory");
        }

        directory = FSDirectory.getDirectory(locationFile);
    }

    /**
     * Specify the path on the filesystem to use for this directory storage
     */
    public void setLocation(Resource location) {
        this.location = location;
    }

    /**
     * Same as FSDirectory.getDirectory(File, boolean) boolean argument.
     * 
     * <p>Defaults to false
     *
     * @param create if true, create, or erase any existing contents
     */
    public void setCreate(boolean create) {
        this.create = create;
    }
 
    /**
     * Fixe for Issue http://opensource.atlassian.com/projects/spring/browse/MOD-161
     * @throws Exception
     */
    public void destroy() throws Exception {
        if( directory!=null ) {
            directory.close();
        }
    } 
    
}