package com.weijietech.framework.beans;

//import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
@SuppressWarnings("serial")
public abstract class Entity implements Serializable, UuidEntityInterf {

    protected String entityUuid;

//    public String getEntityUuid() {
//        return entityUuid;
//    }

    public void setEntityUuid(String entityUuid) {
        this.entityUuid = entityUuid;
    }
}
