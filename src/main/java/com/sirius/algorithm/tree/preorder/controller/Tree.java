package com.sirius.algorithm.tree.preorder.controller;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.sirius.algorithm.tree.preorder.domain.dto.JSTreeNode;
import com.sirius.algorithm.tree.preorder.domain.dto.Result;
import com.sirius.algorithm.tree.preorder.domain.model.TreeNode;
import com.sirius.algorithm.tree.preorder.service.TreeNodeCheckService;
import com.sirius.algorithm.tree.preorder.service.TreeNodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Created by pippo on 14-10-10.
 */
@Controller
public class Tree extends BaseController {

	@Resource
	private TreeCreator treeCreator;

	@Resource
	private TreeNodeService treeNodeService;

	@Resource
	private TreeNodeCheckService treeNodeCheckService;

	@RequestMapping("/init")
	@ResponseBody
	public Result init() {
		treeCreator.create(5, 10);
		return Result.SUCCESS;
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

	@RequestMapping("/create/{parentId}")
	@ResponseBody
	public Result create(@PathVariable("parentId") String parentId) {
		TreeNode node = new TreeNode();
		node.setName("node");
		treeNodeService.insert(parentId, node);
		return Result.SUCCESS;
	}

	@RequestMapping("/move/{nodeId}")
	@ResponseBody
	public Result move(@PathVariable("nodeId") String nodeId,
			@RequestParam("targetParentId") String targetParentId,
			@RequestParam(value = "afterBrotherId", required = false) String afterBrotherId) {
		treeNodeService.move(targetParentId, afterBrotherId, nodeId);
		return Result.SUCCESS;
	}

	@RequestMapping("/remove/{nodeId}")
	@ResponseBody
	public Result remove(@PathVariable("nodeId") String nodeId) {
		treeNodeService.remove(nodeId);
		return Result.SUCCESS;
	}

	@RequestMapping("/check")
	@ResponseBody
	public Result check() throws Exception {
		List<TreeNode> nodes = treeNodeCheckService.check();
		Collection<JSTreeNode> payload = Collections2.transform(nodes, new Function<TreeNode, JSTreeNode>() {
			@Override
			public JSTreeNode apply(TreeNode input) {
				return new JSTreeNode(input);
			}
		});

		Result result = new Result();
		result.setPayload(payload);
		return result;
	}
}
