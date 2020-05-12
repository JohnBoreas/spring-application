package com.boreas.lisenter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * 每日报表Listenter
 * @author xuhua.jiang
 * @date: 2020年5月9日
 */
public class DailyReportListenter implements ServletContextListener {
	/**
	 * 当Servlet 容器终止Web 应用时调用该方法。在调用该方法之前，容器会先销毁所有的Servlet 和Filter 过滤器。
	 * @param arg0
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}

	/**
	 * 当Servlet 容器启动Web 应用时调用该方法。在调用完该方法之后，容器再对Filter 初始化，
	 * 并且对那些在Web 应用启动时就需要被初始化的Servlet 进行初始化。
	 * @param arg0
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		new TimerManager();
	}

	class TimerManager {
	    //时间间隔:24h
	    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
	    public TimerManager() {}
	}
}
