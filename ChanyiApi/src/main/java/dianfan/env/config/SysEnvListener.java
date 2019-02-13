package dianfan.env.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import dianfan.logger.Logger;
import dianfan.util.PropertyUtil;

public class SysEnvListener extends ContextLoaderListener {
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		String active = context.getInitParameter("spring.profiles.active");
		PropertyUtil.PROPERTIES = active + "/" + "config" + "-" + active + ".properties";
		if ("dev".equals(active)) {
			Logger.info("开发环境");
		} else if ("test".equals(active)) {
			Logger.info("测试环境");
		} else if ("pro".equals(active)) {
			Logger.info("生产环境");
		} else {
			Logger.error("环境配置错误!");
		}
	}
}
