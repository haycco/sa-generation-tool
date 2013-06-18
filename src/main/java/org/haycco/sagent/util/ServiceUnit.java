package org.haycco.sagent.util;

/**
 * 默认情况下 对各个组件类型的名称定义类似于jsr pub route bean
 * 即JSR181("servicemix-jsr181"), HTTP("servicemix-http"), CAMEL("servicemix-camel"), BEAN("servicemix-bean");
 * 
 * @author lgc
 */
public enum ServiceUnit {
    JSR181("servicemix-jsr181"), HTTP("servicemix-http"), CAMEL("servicemix-camel"), BEAN("servicemix-bean");
    private String name;

    ServiceUnit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}