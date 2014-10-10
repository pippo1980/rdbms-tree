package com.sirius.algorithm.tree.preorder;

import com.sirius.plugin.framework.engine.module.Plugin;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by pippo on 14-10-10.
 */
public class PluginConfig extends Plugin {

	public static final String PLUGIN_NAME = "console";

	public static final String PLUGIN_PATH =
			StringUtils.isBlank(Plugin.PLUGIN_WEB_PREFIX)
					? PLUGIN_NAME
					: String.format("%s/%s", Plugin.PLUGIN_WEB_PREFIX, PLUGIN_NAME);

	@Override
	public String getName() {
		return "tree/preorder";
	}
}
