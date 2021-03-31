package com.intrado.live.stream;

import com.wowza.wms.application.IApplication;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.application.WMSProperties;
import com.wowza.wms.logging.WMSLogger;
import com.wowza.wms.logging.WMSLoggerFactory;
import com.wowza.wms.server.IServer;
import com.wowza.wms.server.IServerNotify2;
import com.wowza.wms.stream.publish.IStreamActionNotify;
import com.wowza.wms.stream.publish.PlaylistItem;
import com.wowza.wms.stream.publish.Stream;
import com.wowza.wms.vhost.IVHost;
import com.wowza.wms.vhost.VHost;
import com.wowza.wms.vhost.VHostSingleton;

public class WowzaLiveStreamListener implements IServerNotify2 {

	private static String CLASSNAME = WowzaLiveStreamListener.class.getSimpleName();

	private static WMSLogger logger = WMSLoggerFactory.getLogger(WowzaLiveStreamListener.class);

	/** The vhost name. */
	private String vhostName = VHost.VHOST_DEFAULT; 

	/** The app names. */
	private String appNames = "";

	/**
	 * On server create.
	 *
	 * @param server the server
	 */
	@Override
	public void onServerCreate(IServer server) {
		logger.info(String.format("%s.onServerCreate: on Server Create", CLASSNAME, server));
	}

	/**
	 * On server init.
	 *
	 * @param server the server
	 */
	@Override
	public void onServerInit(IServer server) {

		logger.info(String.format("%s.onServerInit: on Server Init", CLASSNAME, server));

		try {

			IVHost vhost = VHostSingleton.getInstance(VHost.VHOST_DEFAULT);

			IApplication app = vhost.getApplication("live");

			IApplicationInstance appInstance = app.getAppInstance("_definst_");

			String videoFile = server.getProperties().getPropertyStr("video");

			Stream stream1 = Stream.createInstance(vhost, "live", "Stream1");

			stream1.play(videoFile, 0, -1, true);

			stream1.addListener(new StreamListener(appInstance));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * On server shutdown complete.
	 *
	 * @param server the server
	 */
	@Override
	public void onServerShutdownComplete(IServer server) {

		logger.info(String.format("%s.onServerShutdownComplete: on Server Shutdown Complete", CLASSNAME, server));
	}

	/**
	 * On server shutdown start.
	 *
	 * @param server the server
	 */
	@Override
	public void onServerShutdownStart(IServer server) {

		logger.info(String.format("%s.onServerShutdownStart: on Server Shutdown Start", CLASSNAME, server));

	}

	/**
	 * The listener interface for receiving stream events. The class that is
	 * interested in processing a stream event implements this interface, and the
	 * object created with that class is registered with a component using the
	 * component's <code>addStreamListener<code> method. When the stream event
	 * occurs, that object's appropriate method is invoked.
	 *
	 * @see StreamEvent
	 */
	class StreamListener implements IStreamActionNotify {
		/**
		 * Instantiates a new stream listener.
		 *
		 * @param appInstance the app instance
		 */
		StreamListener(IApplicationInstance appInstance) {
		}

		/**
		 * On playlist item stop.
		 *
		 * @param stream the stream
		 * @param item   the item
		 */
		public void onPlaylistItemStop(Stream stream, PlaylistItem item) {

			logger.info(String.format("%s.onServerShutdownStart: on Server Shutdown Start", CLASSNAME, stream, item));

		}

		/**
		 * On playlist item start.
		 *
		 * @param stream the stream
		 * @param item   the item
		 */
		public void onPlaylistItemStart(Stream stream, PlaylistItem item) {

			logger.info(String.format("%s.Item Started and on Stream:", CLASSNAME, stream, item));

		}
	}

	/**
	 * On server config loaded.
	 *
	 * @param server the server
	 */
	@Override
	public void onServerConfigLoaded(IServer server) {

		logger.info(String.format("%s.onServerConfigLoaded", CLASSNAME, server));

		try {
			WMSProperties props = server.getProperties();
			this.vhostName = props.getPropertyStr("VHost", this.vhostName);
			this.appNames = props.getPropertyStr("Applications", this.appNames);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
