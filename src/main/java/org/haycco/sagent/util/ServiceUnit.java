package org.haycco.sagent.util;

/**
 * 默认情况下 对各个组件类型的名称定义
 * <li>JSR181("servicemix-jsr181")</li>
 * <li>HTTP("servicemix-http")</li>
 * <li>CAMEL("servicemix-camel")</li>
 * <li>BEAN("servicemix-bean")</li>
 * 
 * @author haycco
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
    
    /**
     * 还原SU默认名称设置
     */
    public static void resetSUDefaultValue() {
        ServiceUnit.JSR181.setName("servicemix-jsr181");
        ServiceUnit.HTTP.setName("servicemix-http");
        ServiceUnit.CAMEL.setName("servicemix-camel");
        ServiceUnit.BEAN.setName("servicemix-bean");
    }

}