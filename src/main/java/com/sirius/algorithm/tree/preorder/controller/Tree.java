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

/**
 * Created by pippo on 14-10-10.
 */
@Controller
@RequestMapping("/tree")
public class Tree extends BaseController {

	@Resource
	private TreeNodeService treeNodeService;

	@RequestMapping("/init")
	public Result init() {
		treeNodeService.init(5, 10);
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

}
