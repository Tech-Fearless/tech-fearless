package com.tech.designpattern.templet;

/**
 * 模板模式
 * 抽象出不变的主体和可变的部分
 * 定义模板的行为
 */
public abstract class Templet {

    /**
     * 多线程情况
     * @return
     */
    protected abstract boolean threads();
    /**
     *  记录日志
     * @return
     */
    protected abstract boolean recordLog();
    /**
     * 添加开关
     * @return
     */
    protected abstract boolean addSwitch();
    /**
     * lion配置
     * @return
     */
    protected abstract boolean readLion();
    /**
     * 核心逻辑
     * @return
     */
    protected abstract boolean coreLogic();
    /**
     * 添加开关
     * @return
     */
    protected abstract boolean catMonitor();

    public final void executeTask(){

        readLion();

        addSwitch();

        coreLogic();

        recordLog();

        catMonitor();
    }
}
