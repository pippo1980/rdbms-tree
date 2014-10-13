package com.sirius.algorithm.tree.preorder.controller;

import com.sirius.algorithm.tree.preorder.domain.model.TreeNode;
import com.sirius.algorithm.tree.preorder.service.TreeNodeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;

/**
 * Created by pippo on 14-10-13.
 */
@Component
public class TreeCreator {

	@Resource
	private TreeNodeService treeNodeService;

	public void create(int maxDepth, int amountFactor) {
		//不要用deleteAll,因为deleteAll是要把所有entity取出来,一条一条delete
		treeNodeService.removeAll();

		//root node
		TreeNode root = new TreeNode();
		root.setId("#");
		root.setName("root");
		root.setLeftPriority(0L);
		root.setRightPriority(1L);
		root.setDepth(1);
		treeNodeService.save(root);

		create(root.getId(), root.getDepth() + 1, maxDepth, amountFactor);
	}

	private void create(String parentId, int currentDepth, int maxDepth, int amountFactor) {
		int amount = Math.max(2, new Random().nextInt(amountFactor));

		for (int i = 0; i < amount; i++) {
			TreeNode node = new TreeNode();
			node.setName("node");
			treeNodeService.insert(parentId, node);

			if (currentDepth < maxDepth) {
				create(node.getId(), currentDepth + 1, maxDepth, amountFactor);
			}
		}
	}

}
