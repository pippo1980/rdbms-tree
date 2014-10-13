package com.sirius.algorithm.tree.preorder.service.impl;

import com.sirius.algorithm.tree.preorder.domain.model.TreeNode;
import com.sirius.algorithm.tree.preorder.domain.procedure.DetachNode;
import com.sirius.algorithm.tree.preorder.domain.procedure.NodeShift;
import com.sirius.algorithm.tree.preorder.domain.repository.TreeNodeRepository;
import com.sirius.algorithm.tree.preorder.service.TreeNodeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by pippo on 14-9-4.
 */
@Service
@Transactional
public class TreeNodeServiceImpl implements TreeNodeService {

	@Resource
	protected TreeNodeRepository treeNodeRepository;

	@Override
	public TreeNode get(String nodeId) {
		return treeNodeRepository.getOne(nodeId);
	}

	@Override
	public List<TreeNode> getAncestors(String nodeId) {
		TreeNode node = get(nodeId);
		List<TreeNode> ancestors = new ArrayList<>();

		while (node.getParent() != null) {
			ancestors.add(0, node.getParent());
			node = node.getParent();
		}

		return ancestors;
	}

	@Override
	public List<TreeNode> load(String parentId) {
		return treeNodeRepository.findByParentId(parentId, new Sort(Direction.ASC, "leftPriority"));
	}

	@Override
	public void save(TreeNode node) {
		treeNodeRepository.save(node);
	}

	@Override
	public void insert(String parentId, TreeNode node) {
		insert(parentId, null, node);
	}

	@Override
	public void insertAfter(String brotherId, TreeNode node) {
		TreeNode brother = get(brotherId);
		Validate.notNull(brother, "can not found brother node with id:[%s]", brotherId);
		insert(brother.getParent().getId(), brotherId, node);
	}

	protected void insert(String parent_id, String after_id, TreeNode node) {
		Validate.notNull(node, "node can not be null");
		Validate.notNull(parent_id, "parent_id can not be null");

		TreeNode parent = get(parent_id);
		Validate.notNull(parent, "parent with id:[%s] can not be found", parent);
		node.setParent(parent);

		if (StringUtils.isBlank(node.getId())) {
			node.setId(UUID.randomUUID().toString());
		}

		Long after_right = null;
		if (StringUtils.isBlank(after_id)) {
			/* 在兄弟节点中找到right最大的节点,在其后插入新节点 */
			/* 作为前序加权树,兄弟节点中最右边的righ=parent.right-1 */
			after_right = parent.getRightPriority() - 1;
		} else {
			TreeNode after = get(after_id);
			after_right = after.getRightPriority();
		}

		/* 更新插入位置之后节点的left和right */
		/* 在插入新节点之前,需要将插入位置之后的节点都向后移2位,腾出空间 */
		/* 具体操作为: */
		/* (1)找到left和right大于给定right_priority的节点 */
		/* (2)将这些节点的left和right都+2 */
		treeNodeRepository.execute(new NodeShift(after_right, 2L));

		/* 设置要插入节点的left和right */
		node.setLeftPriority(after_right + 1);
		node.setRightPriority(node.getLeftPriority() + 1);

		/* 设置节点的层深 */
		node.setDepth(parent.getDepth() + 1);
		/* 保存节点 */
		treeNodeRepository.save(node);

		/*迭代插入子节点*/
		if (node.getChildren() != null) {
			for (TreeNode treeNode : node.getChildren()) {
				insert(node.getId(), treeNode);
			}
		}
	}

	@Override
	public void move(String targetParentId, String afterBrotherId, String nodeId) {
		TreeNode node = get(nodeId);

		final long old_left = node.getLeftPriority();
		final long old_right = node.getRightPriority();
		long distance = old_right - old_left + 1;

		/*1)将要移动的子树parent置为空*/
		/*2)将要移动的子树的所有节点的左右权值置为当前值的复数*/
		/*3)将右边的兄弟节点左移 */
		treeNodeRepository.execute(new DetachNode(node));
		treeNodeRepository.execute(new NodeShift(old_right, -distance));

		/*将节点作为新节点插入(id不变,重新计算左右权值)*/
		insert(targetParentId, afterBrotherId, node);
	}

	@Override
	public void remove(String nodeId) {
		TreeNode node = this.get(nodeId);
		if (node == null) {
			return;
		}

		/* 将右边的兄弟节点左移 */
		long distance = node.getLeftPriority() - node.getRightPriority() - 1;
		treeNodeRepository.execute(new NodeShift(node.getRightPriority(), distance));

		/* 删除当前节点 */
		treeNodeRepository.delete(node);
	}

	@Override
	public void removeAll() {
		//不要用deleteAll,因为deleteAll是要把所有entity取出来,一条一条delete
		treeNodeRepository.deleteAllInBatch();
	}
}
