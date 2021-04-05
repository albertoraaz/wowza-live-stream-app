package com.intrado.rapidview.wowza;

import com.wowza.wms.application.*;
import com.wowza.wms.logging.WMSLoggerFactory;
import com.wowza.wms.module.IModuleOnApp;
import com.wowza.wms.module.ModuleBase;

/**
 * The Class StreamingProject.
 */
public class RapidViewLiveModule extends ModuleBase implements IModuleOnApp {

	/**
	 * On app start.
	 * 
	 * @param arg0 the arg 0
	 */
	@Override
	public void onAppStart(IApplicationInstance arg0) {
		WMSLoggerFactory.getLogger(null).info("on App Start..."); 
	}

	/**
	 * On app stop.
	 *
	 * @param arg0 the arg 0
	 */
	@Override
	public void onAppStop(IApplicationInstance arg0) {
		WMSLoggerFactory.getLogger(null).info("on App Stop...");
	}

}
