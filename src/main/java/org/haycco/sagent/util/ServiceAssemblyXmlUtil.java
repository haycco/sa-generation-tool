/**
 * CopyRright (C) 2000-2013:   YGsoft Inc. All Rights Reserved.
 * Author：                                haycco                
 * Create Date：                         2013-5-17 下午7:19:40   
 * Version:                                 1.0
 */
package org.haycco.sagent.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jaxen.JaxenException;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.dom4j.Dom4jXPath;

/**
 * @author haycco
 */
public class ServiceAssemblyXmlUtil {

    /**
     * 修改JSR181组件的xbean.xml配置文件
     * 
     * @param doc
     * @param startIndex
     * @return
     */
    public static Document updateJsrwsXbeanConfig(Document doc, int startIndex, List<String> serviceList) {
        try {
            Dom4jXPath xpath = createXPath("jsr181", "http://servicemix.apache.org/jsr181/1.0",
                    "//jsr181:endpoint//@service");
            @SuppressWarnings("unchecked")
            List<Node> nodes = xpath.selectNodes(doc.getRootElement());
            for (Node node : nodes) {
                String txt = node.getText();
                int index = txt.indexOf(":");
                if(index!=-1){
                    serviceList.add(txt.substring(index+1));
                }else{
                    serviceList.add(txt);
                }
                node.setText(txt + startIndex);
            }
        } catch (JaxenException e) {
            throw new RuntimeException("使用Xpath解析jsr181组件的xbean.xml文件发生异常:", e);
        }

        return doc;
    }

    /**
     * 更新http组件的xbean.xml 修改每个http:soap-consumer 节点属性 service 和 locationURI
     * 
     * @param pubwsDir
     * @param startIndex
     */
    public static Document updatePubwsXbeanConfig(Document doc, int startIndex) {
        try {
            Dom4jXPath xpath = createXPath("http", "http://servicemix.apache.org/http/1.0", "//http:soap-consumer");
            @SuppressWarnings("unchecked")
            List<Node> nodes = xpath.selectNodes(doc.getRootElement());
            for (Node node : nodes) {
                Node service = node.selectSingleNode("@service");
                service.setText(service.getText() + startIndex);
                // 允许url末尾为/
                Node locationURI = node.selectSingleNode("@locationURI");
                String url = locationURI.getText();
                if (url.endsWith("/")) {
                    locationURI.setText(url.substring(0, url.length() - 1) + startIndex);
                } else {
                    locationURI.setText(url + startIndex);
                }
            }
        } catch (JaxenException e) {
            throw new RuntimeException("使用Xpath解析jsr181组件的xbean.xml文件发生异常:", e);
        }
        return doc;
    }

    /**
     * 更新bean组件的xbean.xml文件
     * 
     * @param pubwsDir
     * @param startIndex
     * @param serviceList
     */
    public static Document updateBeanComponentXBeanConfig(Document doc, int startIndex, List<String> serviceList) {
        try {
            Dom4jXPath xpath = createXPath("bean", "http://servicemix.apache.org/bean/1.0", "//bean:endpoint");
            @SuppressWarnings("unchecked")
            List<Node> nodes = xpath.selectNodes(doc.getRootElement());
            for (Node node : nodes) {
                Node service = node.selectSingleNode("@service");
                // 收集可能在camel-context的jbi:endpoint中出现的service和endpoint
                int index = service.getText().indexOf(":");
                if (index != -1) {
                    serviceList.add(service.getText().substring(index + 1));
                } else {
                    serviceList.add(service.getText());
                }
                service.setText(service.getText() + startIndex);
            }
        } catch (JaxenException e) {
            throw new RuntimeException("使用Xpath解析bean组件的xbean.xml文件发生异常:", e);
        }
        return doc;
    }

    /**
     * 更新camel组件的camel-context.xml文件 
     * <li>1.修改jbi相关的组件camel:from camel:to</li>
     * <li>2.修改jms相关的组件 camel:from camel:to</li> 
     * <li>3.修改vm的名称 camel:from</li>
     * 
     * @param doc 需要更新的Document对象
     * @param startIndex 起始编号
     * @param serviceList 需要更名的服务列表
     */
    public static Document updateCamelContextConfig(Document doc, int startIndex, List<String> serviceList) {
        try {
            // 选择 camel:from 节点
            Dom4jXPath camelFrom = createXPath("camel", "http://camel.apache.org/schema/spring",
                    "//camel:camelContext//camel:route//camel:from");
            @SuppressWarnings("unchecked")
            List<Node> fromNodes = camelFrom.selectNodes(doc.getRootElement());
            dealWithEndpointNodes(fromNodes, serviceList, startIndex);
            // 选择camel:to节点
            Dom4jXPath camelTo = createXPath("camel", "http://camel.apache.org/schema/spring",
                    "//camel:camelContext//camel:route//camel:to");
            @SuppressWarnings("unchecked")
            List<Node> toNodes = camelTo.selectNodes(doc.getRootElement());
            dealWithEndpointNodes(toNodes, serviceList, startIndex);
        } catch (JaxenException e) {
            throw new RuntimeException("使用Xpath解析camel组件的camel-context.xml文件发生异常:", e);
        }
        return doc;
    }

    private static void dealWithEndpointNodes(List<Node> nodes, List<String> serviceList, int startIndex) {
        for (Node node : nodes) {
            Node uriAttr = node.selectSingleNode("@uri");
            String uri = uriAttr.getText();
            if (!uri.contains("bean:")) {
                // 处理jbi:endpoint
                if (uri.contains("jbi:endpoint")) {
                    for (String service : serviceList) {
                        if (uri.contains("/" + service + "/")) {
                            // 不需要replaceAll 或者replace 以免对url其它部分造成干扰
                            // 这部分的处理需要通过全字匹配 类似于 /service/这样的字符
                            // uri = uri.replaceFirst(service,
                            // service+startIndex);
                            uri = uri.replaceFirst("/" + service + "/", "/" + service + startIndex + "/");
                            break;
                        }
                    }
                }
                // 处理jms:queue: 或者 vm:
                if (uri.contains("jms:queue:") || uri.contains("vm:")) {
                    uri = uri + startIndex;
                }

                uriAttr.setText(uri);
            }
        }
    }

    /**
     * 更新jbi.xml
     * 
     * @param doc
     *            jbi.xml Document
     * @param startIndex
     */
    public static Document updateJbiConfig(Document doc, int startIndex) {

        Element sa = doc.getRootElement().element("service-assembly");
        // 修改sa jbi 配置
        Element saIndentif = sa.element("identification");
        Element name = saIndentif.element("name");
        Element description = saIndentif.element("description");
        name.setText(name.getText() + startIndex);
        description.setText(description.getText() + startIndex);

        // 修改su的jbi配置
        @SuppressWarnings("unchecked")
        List<Element> list = sa.elements("service-unit");
        for (Element el : list) {
            Element identification = el.element("identification");
            Element suName = identification.element("name");
            suName.setText(suName.getText() + startIndex);
            Element suDescription = identification.element("description");
            suDescription.setText(suDescription.getText() + startIndex);

            Element target = el.element("target");
            Element artifactsZip = target.element("artifacts-zip");
            String zipStr = artifactsZip.getText();
            int index = zipStr.indexOf(".");
            artifactsZip.setText(zipStr.substring(0, index) + startIndex + zipStr.substring(index, zipStr.length()));
        }
        return doc;
    }

    /**
     * 
     * @param prefix
     *            前缀 其实可以称之为一种简写
     * @param namespace
     *            命名空间全称
     * @param selectPath
     *            选择的xpath路径
     * @return
     * @throws JaxenException
     */
    public static Dom4jXPath createXPath(String prefix, String namespace, String selectPath) throws JaxenException {
        Map<String, String> nameSpaces = new HashMap<String, String>();
        nameSpaces.put(prefix, namespace);
        SimpleNamespaceContext context = new SimpleNamespaceContext(nameSpaces);
        Dom4jXPath xpath = new Dom4jXPath(selectPath);
        xpath.setNamespaceContext(context);
        return xpath;
    }

    /**
     * 将Document对象内容按照一定的编码写入到目标文件
     * 
     * @param doc
     * @param targetFile
     * @param charset
     */
    public static void writeDoc(Document doc, File targetFile, String charset) {
        // 定义一个dom4j 比较漂亮的xml格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = null;
        try {
            if (charset == null) {
                writer = new XMLWriter(new FileWriter(targetFile), format);
            } else {
                writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(targetFile), charset), format);
            }
            writer.write(doc);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("写入Xml数据到目标文件失败", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException("关闭文件流失败", e);
                }
            }
        }
    }
}