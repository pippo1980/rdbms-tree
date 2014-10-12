package com.sirius.algorithm.tree.preorder.service;

import com.sirius.algorithm.tree.preorder.domain.model.TreeNode;

import java.util.List;

/**
 * Created by pippo on 14-9-4.
 */
public interface TreeNodeService {

	void init(int depth, int amountFactor);

	TreeNode get(String nodeId);

	List<TreeNode> load(String parentId);

	void insert(String parentId, TreeNode node);

	void insertAfter(String brotherId, TreeNode node);

	void move(String parentId, String... nodeId);

	void remove(String nodeId);

}
