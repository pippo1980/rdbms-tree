package com.sirius.algorithm.tree.preorder.service;

import com.sirius.algorithm.tree.preorder.domain.model.TreeNode;

import java.util.List;

/**
 * Created by pippo on 14-10-13.
 */
public interface TreeNodeCheckService {

	List<TreeNode> check() throws Exception;

}
