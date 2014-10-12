package com.sirius.algorithm.tree.preorder.controller;

import com.sirius.algorithm.tree.preorder.PluginConfig;
import com.sirius.plugin.framework.engine.module.SiriusBaseController;
import com.sirius.utils.servlet3.Environment;

/**
 * Created by pippo on 14-10-10.
 */
public class BaseController extends SiriusBaseController {

	@Override
	protected String getPluginName() {
		return PluginConfig.PLUGIN_NAME;
	}

	@Override
	protected String getPluginPath() {
		return Environment.getServletContext().getContextPath() + "/" + PluginConfig.PLUGIN_PATH;
	}

	@Override
	protected String getHomePath() {
		return getPluginPath() + "/home";
	}
}
