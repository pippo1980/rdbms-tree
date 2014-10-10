/* Copyright © 2010 www.myctu.cn. All rights reserved. */
/**
 * project : ctu-framework
 * user created : pippo
 * date created : 2013-7-28 - 下午7:04:56
 */
package com.sirius.algorithm.tree.preorder.domain.model;

import java.util.Comparator;

/**
 * @since 2013-7-28
 * @author pippo
 */
public class TreeNodeComparator implements Comparator<TreeNode> {

	@Override
	public int compare(TreeNode o1, TreeNode o2) {
		return o1.getLeftPriority() > o2.getLeftPriority() ? 1 : -1;
	}

}
