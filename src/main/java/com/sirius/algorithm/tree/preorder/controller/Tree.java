package com.sirius.algorithm.tree.preorder.controller;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.sirius.algorithm.tree.preorder.domain.dto.JSTreeNode;
import com.sirius.algorithm.tree.preorder.domain.dto.Result;
import com.sirius.algorithm.tree.preorder.domain.model.TreeNode;
import com.sirius.algorithm.tree.preorder.service.TreeNodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by pippo on 14-10-10.
 */
@Controller
public class Tree extends BaseController {

	@Resource
	private TreeNodeService treeNodeService;

	@RequestMapping("/init")
	@ResponseBody
	public Result init() {
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

		//children

		while (root.getRightPriority() <= 100000) {
			init(root.getId(), root.getDepth() + 1, 10, 10);
			root = treeNodeService.get("#");
		}

		return Result.SUCCESS;
	}

	private void init(String parentId, int currentDepth, int maxDepth, int amountFactor) {
		int amount = new Random().nextInt(amountFactor);

		for (int i = 0; i < amount; i++) {
			TreeNode node = new TreeNode();
			node.setName("node");
			treeNodeService.insert(parentId, node);

			if (currentDepth < maxDepth) {
				init(node.getId(), currentDepth + 1, maxDepth, amountFactor);
			}
		}
	}

	@RequestMapping("/load")
	@ResponseBody
	public Collection<JSTreeNode> load(@RequestParam("id") String parentId) {
		List<TreeNode> nodes = treeNodeService.load(parentId);

		//		return nodes.stream().map(p -> {
		//			return new JSTreeNode(p);
		//		}).collect(Collectors.toList());

		return Collections2.transform(nodes, new Function<TreeNode, JSTreeNode>() {
			@Override
			public JSTreeNode apply(TreeNode input) {
				return new JSTreeNode(input);
			}
		});

	}

}
