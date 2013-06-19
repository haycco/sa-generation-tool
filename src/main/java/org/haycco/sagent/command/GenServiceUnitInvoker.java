/**
 * CopyRright (C) 2000-2013:   YGsoft Inc. All Rights Reserved.
 * Author：                                haycco
 * Create Date：                         2013-5-18 上午2:24:34   
 * Version:                                 1.0
 */
package org.haycco.sagent.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理SU命令以及修改步骤总调度类
 * 
 * @author haycco
 */
public class GenServiceUnitInvoker {

    /**
     * 编号
     */
    private int num;
    /**
     * SU类型处理命令
     */
    private SuCommand command;
    /**
     * SA的生产目录
     */
    private String targetServiceAssemblyDir = null;
    /**
     * SA的模版源目录
     */
    private String sourceServiceAssemblyDir = null;
    /**
     * 对不同类型的SU进行处理
     */
    protected final List<String> serviceList = new ArrayList<String>();

    public void setNum(int num) {
        this.num = num;
    }

    public void setCommand(SuCommand command) {
        this.command = command;
    }

    public void setTargetServiceAssemblyDir(String targetServiceAssemblyDir) {
        this.targetServiceAssemblyDir = targetServiceAssemblyDir;
    }

    public void setSourceServiceAssemblyDir(String sourceServiceAssemblyDir) {
        this.sourceServiceAssemblyDir = sourceServiceAssemblyDir;
    }

    public void action() throws IOException {
        //SA模版源目录
        this.command.setSourceServiceAssemblyDir(sourceServiceAssemblyDir);
        //设置当前生产编号
        this.command.setNum(num);
        //设置需要处理不同SU类型的service列表
        this.command.setServiceList(serviceList);
        //设置新生产服务组合件的文件夹
        this.command.setTargetServiceAssemblyDir(targetServiceAssemblyDir);
        this.command.execute();
    }

}
