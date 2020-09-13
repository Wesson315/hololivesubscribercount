package de.wesson.hololivesubscribercount;


import de.wesson.hololivesubscribercount.model.timer.CronoManager;

import javax.servlet.ServletContextEvent;

public class ServletContextListener implements javax.servlet.ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        CronoManager.getInstance().stopScheduler();
    }
}
