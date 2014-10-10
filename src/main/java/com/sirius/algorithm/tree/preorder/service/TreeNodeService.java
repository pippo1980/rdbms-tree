package com.sirius.algorithm.tree.preorder.service;

import com.sirius.algorithm.tree.preorder.domain.model.TreeNode;

/**
 * Created by pippo on 14-9-4.
 */
public interface TreeNodeService {

	TreeNode get(String nodeId);

	void insert(String parentId, TreeNode node);

	void insertAfter(String brotherId, TreeNode node);

	void move(String parentId, String... nodeId);

	void remove(String nodeId);

}
